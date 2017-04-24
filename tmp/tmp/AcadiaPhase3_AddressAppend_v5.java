package com.reltio.Acadia_dt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.domain.ReltioObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.reltio.cleanse.CleanseFunctions;
import com.reltio.cst.exception.handler.APICallFailureException;
import com.reltio.cst.exception.handler.GenericException;
import com.reltio.cst.service.ReltioAPIService;
import com.reltio.cst.service.TokenGeneratorService;
import com.reltio.cst.service.impl.SimpleReltioAPIServiceImpl;
import com.reltio.cst.service.impl.TokenGeneratorServiceImpl;
import com.reltio.data.common.LocationAttrZip;
import com.reltio.data.common.LocationAttributes;
import com.reltio.data.common.LocationZip;
import com.reltio.dataload.DataloadFunctions;
import com.reltio.location.LocationObject;

public class AcadiaPhase3_AddressAppend_v5 {

	public static CleanseFunctions cf = new CleanseFunctions();
	static DataloadFunctions df = new DataloadFunctions();
	public static String SourceSystem = null;
	static DataloadFunctions helper = new DataloadFunctions();
	static String apiUrl="";
	
	public static void main(String[] args) throws Exception {
		System.out.println("Process Started..("+new Date()+")");
		Properties properties = new Properties();
		try {
			String propertyFilePath = args[0];
			FileReader in = new FileReader(propertyFilePath);
//			InputStream in = new AcadiaPhase3_AddressAppend_v5().getClass().getResourceAsStream("/addressAppend_v2.properties");
			properties.load(in);
		} catch (Exception e) {
			System.out.println("Failed reading properties File...");
			e.printStackTrace();
		}
		
		apiUrl = properties.getProperty("API_URL"); // <$APIURL$>/<tenantName>/entities/
		String inputDelimieter = properties.getProperty("INPUT_DELIMIETER");
		final String inputfile = properties.getProperty("INPUT_FILE_LOCATION");
		final String relinputfile1 = properties.getProperty("RELATION_INPUT_FILE_LOCATION1");
		final String username = properties.getProperty("USERNAME");
		final String password = properties.getProperty("PASSWORD");
		final String authURL = properties.getProperty("AUTHURL");
		
		final Gson gson = new Gson();
		TokenGeneratorService tokenGeneratorService = null;
        try {
//            tokenGeneratorService = new TokenGeneratorServiceImpl(username,password,"https://auth.reltio.com/oauth/token");
            tokenGeneratorService = new TokenGeneratorServiceImpl(username,password,authURL);
        } catch (APICallFailureException e) {
            System.out.println("Error Code: " + e.getErrorCode() + " >>>> Error Message: " + e.getErrorResponse());
        } catch (GenericException e) {
            System.out.println(e.getExceptionMessage());
        }
        final ReltioAPIService reltioAPIService = new SimpleReltioAPIServiceImpl(tokenGeneratorService);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
		
		String[] values = null;
		final Map<String,String> map=new HashMap<>();
		String input = reader.readLine();
		while ((input = reader.readLine()) != null) {
			
			values = input.split("\\" + inputDelimieter, -1);
			StringBuffer url=new StringBuffer(apiUrl+"entities?filter=equals(type,'configuration/entityTypes/HCP') and (equals(attributes.AcadiaIdentifier,'"+values[3]+"')");
			for(int i=0;i<50;i++){
				input = reader.readLine();
				if(input!=null){
					values = input.split("\\" + inputDelimieter, -1);
					url=url.append(" or equals(attributes.AcadiaIdentifier,'"+values[3]+"')");
				}else{
					break;
				}
			}
			url=url.append(")");
			System.out.println(url);
			String uri=reltioAPIService.get(url.toString());
			List<ReltioObject> reltioObject=gson.fromJson(uri,new TypeToken<List<ReltioObject>>(){}.getType());
			for(ReltioObject reltioObj:reltioObject){
				String entityURI = reltioObj.uri.substring(9);
				List<Object> objects2 = reltioObj.attributes.get("AcadiaIdentifier");
				if (objects2 != null && !objects2.isEmpty()) {
					for (Object obj : objects2) {
						com.google.gson.internal.LinkedTreeMap object2 = (LinkedTreeMap) obj;
						if(!map.containsKey((String)object2.get("value"))){
							map.put((String)object2.get("value"), entityURI);
						}
					}
				}
			}
			
		}
		reader.close();
		
		System.out.println(map.size());
		System.out.println(map);
		final Set<String> set=map.keySet();
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
		
		//Load the relatioN
		final List<String> relationJsonList=new ArrayList<String>();
		boolean eof = false;
		int threadsNumber=10;
		ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
		ArrayList<Future<Long>> futures = new ArrayList<Future<Long>>();
		reader.readLine();
		while (!eof) {
			for (int i = 0; i < threadsNumber; i++) {
				String nextHcp = reader.readLine();
				if (nextHcp == null) {
					eof = true;
					break;
				}
				if (nextHcp!=null) {
					final String[] suri = nextHcp.split("\\|",-1);
					futures.add(executorService.submit(new Callable<Long>() {
						public Long call()throws Exception {
							if(set.contains(suri[3])&&suri[4]!=null&&!"".equals(suri[4])){
								String keyValue=map.get(suri[3]);
								
								String json=convertJson(suri);
								System.out.println(json);
								String postUri=reltioAPIService.post(apiUrl+"entities?select=uri",json);
//								 System.out.println(postUri);
								 String reluri="";
								 if(!postUri.contains("error")&&postUri.contains("successful")){
									 reluri=postUri.substring(29,45);
								 }
								  //create relationship
								  String relJson=new String(Files.readAllBytes(Paths.get(relinputfile1)));
								  if(suri[11]==null||suri[11].isEmpty()||suri[11].equals("")){
									  relJson=relJson.replace("\"DEA\": [{ \"value\": { \"Number\": [{ \"value\": \"relationAttribute1\" }] } }],","");
								  }
								  if(suri[1]==null||suri[1].isEmpty()||suri[1].equals("")){
									  relJson=relJson.replace(", \"AcadiaAddressIdentifier\": [{ \"value\": relationAttribute2 }]","");
								  }
								  if(suri[2]==null||suri[2].isEmpty()||suri[2].equals("")){
									  relJson=relJson.replace(", \"SourceCodeIdentifier\": [{ \"value\": \"relationAttribute3\" }]","");
								  }
								  relJson=relJson.replace("relationAttribute1", suri[11]);
								  relJson=relJson.replace("relationAttribute2", suri[1]);
								  relJson=relJson.replace("relationAttribute3", suri[2]);
								  relJson=relJson.replace("startObjecturi", "entities/"+keyValue);
								  relJson=relJson.replace("endObjecturi", reluri);
								  relJson=relJson.replace("relatnType","configuration/sources/"+ suri[0]);
								  relJson=relJson.replace("relationvalue",  suri[1]+"_"+suri[2]+"_"+suri[3]);
								  relationJsonList.add(relJson);
							}
							return System.currentTimeMillis();
						}
					}));
				}
			}
			waitForTasksReady(futures,threadsNumber * 2);
		}
		waitForTasksReady(futures, 0);
		executorService.shutdown();
		reader.close();
		if(!relationJsonList.isEmpty()){
			if(relationJsonList.size()<=50){
				try{
					String relJsonResp=reltioAPIService.post(apiUrl+"relations?select=uri",relationJsonList.toString());
					System.out.println(relJsonResp);
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				for (List<String> partition : Lists.partition(relationJsonList, 50)) {
					try{
						String relJsonResp=reltioAPIService.post(apiUrl+"relations?select=uri",partition.toString());
						System.out.println(relJsonResp);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Process ended..("+new Date()+")");
	}

	public static String convertJson(final String[] values) throws Exception {

		SourceSystem ="configuration/sources/"+ values[0];
		String O_Acadia_Address_Identifier = values[1];
//		String O_Acadia_Identifier = values[3];

		String O_Address_Line_One = values[4];
		String O_Address_Line_Two = values[5];
		String O_Address_Line_Three = values[6];
		String O_City = values[7];
		String O_State = values[8];
		String O_Zip_5 = values[9];
		String O_Zip_4 = values[10];
		String O_DEA_NO = values[11];
		
		String JsonData;
		
		// Declaring objects for Reltio configuration java classes
		List<LocationObject> locationObjects = new ArrayList<LocationObject>();
		LocationObject locationObject = new LocationObject();
		LocationAttributes locationAttributes = new LocationAttributes();

		List<LocationZip> locationZips = null;
		LocationZip locationZip = null;
		LocationAttrZip locationAttrZip = null;
		locationAttributes.AddressLine1 = df.getAttribute(locationAttributes.AddressLine1,cf.PoBoxCleanse(cf.toTitleCase(O_Address_Line_One.trim())));
		locationAttributes.AddressLine2 = df.getAttribute(locationAttributes.AddressLine2,cf.PoBoxCleanse(cf.toTitleCase((O_Address_Line_Two+" "+O_Address_Line_Three).trim())));
		locationAttributes.City = df.getAttribute(locationAttributes.City,cf.toTitleCase(O_City));
		locationAttributes.StateProvince = df.getAttribute(locationAttributes.StateProvince, O_State);
//		locationAttributes.AddressRank = df.getAttribute(locationAttributes.AddressRank, O_State);
		locationZips = new ArrayList<LocationZip>();
		locationZip = new LocationZip();
		locationAttrZip = new LocationAttrZip();
		locationAttrZip.Zip5 = df.getAttribute(locationAttrZip.Zip5, O_Zip_5);
		locationAttrZip.Zip4 = df.getAttribute(locationAttrZip.Zip4, O_Zip_4);
		locationZip.value = locationAttrZip;
		locationZips.add(locationZip);
		
		
		if(checkNull(O_Zip_5) || checkNull(O_Zip_4))
		locationAttributes.Zip = locationZips;
		
		locationObject.crosswalks = df.getAttribute(locationObject.crosswalks,O_Acadia_Address_Identifier, SourceSystem);
		locationObject.attributes = locationAttributes;
		locationObjects.add(locationObject);

		Gson gson = new Gson();
		String input = gson.toJson(locationObjects);
		JsonData = input;
		return JsonData;
	}

	// Function to ignore null values
	public static boolean checkNull(String value) {
		if (value != null && !value.trim().equals("")&& !value.trim().equals("UNKNOWN")&& !value.trim().equals("<blank>")) {
			return true;
		}
		return false;
	}
	
	public static long waitForTasksReady(Collection<Future<Long>> futures,int maxNumberInList) throws Exception {
		while (futures.size() > maxNumberInList) {
			for (Future<Long> f : new ArrayList<Future<Long>>(futures)) {
				if (f.isDone()) {
					futures.remove(f);
				}
			}
		}
		return 0l;
	}

}

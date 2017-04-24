package com.abbvie.reltio.lca.process;

import com.abbvie.reltio.lca.util.IAbbvieConstants;
import com.cts.abbvie.SpecialCharRemoval.SpecialCharRemoval;
import com.reltio.lifecycle.framework.IAttributeValue;
import com.reltio.lifecycle.framework.IAttributes;
import com.reltio.lifecycle.framework.ILifeCycleObjectData;
import com.reltio.lifecycle.framework.INestedAttributeValue;
import com.reltio.lifecycle.framework.INestedAttributeValueBuilder;
import com.reltio.lifecycle.framework.IObject;
import com.reltio.lifecycle.framework.IReltioAPI;
import com.reltio.lifecycle.framework.ISimpleAttributeValue;
import com.reltio.lifecycle.framework.ISimpleAttributeValueBuilder;
import com.reltio.lifecycle.framework.LifeCycleActionBase;
import java.util.List;
import java.util.logging.Logger;






public class RawDataBeforeCleanseLCAImplementation
  extends LifeCycleActionBase
  implements IAbbvieConstants
{
  public static final Logger logger = Logger.getLogger(RawDataBeforeCleanseLCAImplementation.class.getName());
  public static SpecialCharRemoval schr_state = new SpecialCharRemoval();
  
  public RawDataBeforeCleanseLCAImplementation() {}
  
  public ILifeCycleObjectData rawDataBeforeCleanse(IReltioAPI reltioAPI, ILifeCycleObjectData data)
  {
    IObject myObject = data.getObject();
    IAttributes myAttributes = myObject.getAttributes();
    String entityJSONString = "";
    boolean rejectFlag = false;
    
    try
    {
      if (myObject.getType().equalsIgnoreCase("configuration/entityTypes/Location"))
      {
        List<IAttributeValue> country = myAttributes
          .getAttributeValues("Country");
        


        if ((country == null) || (country.isEmpty()))
        {
          entityJSONString = entityJSONString + "Default Country Population started;";
          IAttributeValue value = myAttributes
            .createSimpleAttributeValue(
            "Country")
            .value("US")
            .build();
          myAttributes.addAttributeValue(value);
          entityJSONString = entityJSONString + "Default Country Population completed;";
        }
        
        List<IAttributeValue> stateAttribute = myAttributes
          .getAttributeValues("StateProvince");
        
        int state_size = stateAttribute.size();
        for (int i = 0; i < state_size; i++) {
          ISimpleAttributeValue simpleState = 
            (ISimpleAttributeValue)stateAttribute.get(i);
          

          String cleanseState = 
            SpecialCharRemoval.getStrings(simpleState.getStringValue());
          simpleState.setValue(cleanseState);
        }
        

        List<IAttributeValue> cityAttribute = myAttributes
          .getAttributeValues("City");
        
        int size = cityAttribute.size();
        for (int i = 0; i < size; i++) {
          ISimpleAttributeValue simpleCity = 
            (ISimpleAttributeValue)cityAttribute.get(i);
          
          String cleanseCity = 
            SpecialCharRemoval.getStrings(simpleCity.getStringValue());
          simpleCity.setValue(cleanseCity);
        }
        

        List<IAttributeValue> addr_zip = myAttributes
          .getAttributeValues("Zip");
        
        for (int i = 0; i < addr_zip.size(); i++) {
          INestedAttributeValue zip = 
            (INestedAttributeValue)addr_zip.get(i);
          
          List<IAttributeValue> addr_zip4 = zip.getValue()
            .getAttributeValues("Zip4");
          
          List<IAttributeValue> addr_zip5 = zip.getValue()
            .getAttributeValues("Zip5");
          
          myAttributes.removeAttributeValue(zip);
          
          String cleanseZip4 = "";
          String cleanseZip5 = "";
          
          if ((addr_zip4 != null) && (!addr_zip4.isEmpty())) {
            ISimpleAttributeValue simpleZip4 = 
              (ISimpleAttributeValue)addr_zip4.get(0);
            cleanseZip4 = simpleZip4.getStringValue();
          }
          

          if ((addr_zip5 != null) && (!addr_zip5.isEmpty())) {
            ISimpleAttributeValue simpleZip5 = 
              (ISimpleAttributeValue)addr_zip5.get(0);
            cleanseZip5 = simpleZip5.getStringValue();
          }
          
          String[] zip_value = SpecialCharRemoval.standardizeZip(
            cleanseZip5, cleanseZip4);
          
          if ((zip_value[0] != null) || (zip_value[1] != null)) {
            INestedAttributeValue newZipValue = myAttributes
              .createNestedAttributeValue(
              "Zip").build();
            if (zip_value[0] != null) {
              ISimpleAttributeValue new_addr_zip5 = newZipValue
                .getValue()
                .createSimpleAttributeValue(
                "Zip5")
                .value(zip_value[0]).build();
              
              newZipValue.getValue().addAttributeValue(
                new_addr_zip5);
            }
            if (zip_value[1] != null) {
              ISimpleAttributeValue new_addr_zip4 = newZipValue
                .getValue()
                .createSimpleAttributeValue(
                "Zip4")
                .value(zip_value[1]).build();
              
              newZipValue.getValue().addAttributeValue(
                new_addr_zip4);
            }
            myAttributes.addAttributeValue(newZipValue);
          }
        }
        

        List<IAttributeValue> addressLine1Attribute = myAttributes
          .getAttributeValues("AddressLine1");
        
        for (int i = 0; i < addressLine1Attribute.size(); i++)
        {
          ISimpleAttributeValue simpleAddr1 = 
            (ISimpleAttributeValue)addressLine1Attribute.get(i);
          String cleanseAddr1 = simpleAddr1.getStringValue();
          
          boolean containsNumber = 
            SpecialCharRemoval.onlyContainsNumbers(cleanseAddr1);
          boolean containsMultiWord = 
            SpecialCharRemoval.wordCount(cleanseAddr1);
          
          if (!containsNumber) {
            rejectFlag = true;
          }
          else if (!containsMultiWord) {
            rejectFlag = true;
          }
          
          String addr1 = 
            SpecialCharRemoval.getStringsforAddress(cleanseAddr1);
          simpleAddr1.setValue(addr1);
        }
        
      }
      

    }
    catch (Exception exception)
    {
      logger.info(entityJSONString + "::" + exception.toString());
      


      logger.info(entityJSONString + "for record with uri " + 
        data.getObject().getUri());
      if (rejectFlag) {
        throw new RuntimeException(
          "Record Rejected from Address Cleansing Module");
      }
    }
    finally
    {
      logger.info(entityJSONString + "for record with uri " + 
        data.getObject().getUri());
      if (rejectFlag) {
        throw new RuntimeException(
          "Record Rejected from Address Cleansing Module");
      }
    }
    
    return data;
  }
}
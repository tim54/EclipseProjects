package tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class regexExamples {
	
	private static final String REGEX = java.util.regex.Pattern.quote(".");
    private static final String INPUT =
        "AC.2M4yplyl.user";
    private static final String[] myStrings = { "AC.1cdyzvyy.aPath", "CustomerStatusCode", "AC.1cdyzvyy.cType", "INSERT_ATTRIBUTE", "AC.1cdyzvyy.objType", "HCP", "AC.1cdyzvyy.objUri", "entities/0NsKyBa"};

	public static void main(String[] args) {
		
		String entity = "";
		String parameter = "";
		List myList = new ArrayList();
		
		Pattern p = Pattern.compile(REGEX);
		
		for (int i = 0; i < myStrings.length; i = i + 1) {
			String[] items = p.split(myStrings[i]);
			
			if (items.length == 3) {
				if (myList.isEmpty()) myList.add(items[1]);
				HashMap hm = new HashMap();
				hm.put(items[2], myStrings[i + 1]);
				
		        // System.out.println("entity: " + entity + ", parameter: " + parameter + ", value: " + myStrings[i + 1]);
			}
		}
	}
}

package common;


public class Validator {
	/***
	 * Class for validate things.
	 */

	
	/***
	 * Check whether the format of text is IPv4 address type. 
	 * @param text
	 * @return
	 */
	public static boolean isIPAddress(String text){
		if(text == null | text.isEmpty())
			return false;
		
		String[] classParts = text.split("\\.");
		if(classParts.length != 4)
			return false;
		
		for(String part : classParts){
			int valid = Integer.parseInt(part);
			if((valid < 0) || (valid > 255))
				return false;
		}
		if(text.endsWith("."))
			return false;
		
		return true;
	}
	
}

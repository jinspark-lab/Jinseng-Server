package common;

import java.util.Arrays;
import java.util.regex.Pattern;

public class TextUtil {
	
	public static String CRLF = "\r\n";
	public static String BLANK = " ";
	public static String DOT = ",";
	public static String QUOTATION = "\"";
	
	/***
	 *  Remote unnecessary bytes from information
	 * @param bytes
	 * @return
	 */
	public static byte[] ByteTrim(byte[] bytes){
		int i = bytes.length - 1;
		while(i >= 0 && bytes[i] == 0){
			--i;
		}
		return Arrays.copyOf(bytes, i+1);
	}
	
	
	/***
	 * Case depends Pattern Matching function
	 * @param text
	 * @param pattern
	 * @param caseInsensitive
	 * @return
	 */
	public static boolean TextContains(String text, String pattern, boolean caseInsensitive){
		if(caseInsensitive)
			return Pattern.compile(Pattern.quote(pattern), Pattern.CASE_INSENSITIVE).matcher(text).find();
		else
			return Pattern.compile(Pattern.quote(pattern), Pattern.LITERAL).matcher(text).find();
	}
	
	
	
	public static String BraceQuotation(String text){
		return QUOTATION + text + QUOTATION;
	}
	
}

package common;

import java.util.Arrays;

public class TextUtil {
	
	public static String CRLF = "\r\n";
	public static String BLANK = " ";

	public static byte[] ByteTrim(byte[] bytes){
		int i = bytes.length - 1;
		while(i >= 0 && bytes[i] == 0){
			--i;
		}
		return Arrays.copyOf(bytes, i+1);
	}
	
	//대소문자 비교 함수
	
	
}

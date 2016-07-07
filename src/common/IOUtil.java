package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOUtil {

	public static String loadFileToText(String filePath){
		String contents = "";
		try {
			BufferedReader bis = new BufferedReader(new FileReader(filePath));
			
			char[] buffer = new char[8192];
			int bufferRead = 0;
			
			while((bufferRead = bis.read(buffer)) != -1){
				String readData = String.valueOf(buffer);
				contents += readData.trim();
			}
			bis.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;	
	}
	
}

package core.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import common.TextUtil;

public class FileTransfer {


	
	public void Upload(Socket socket, String uploadPath){
		
		try {
			
			BufferedOutputStream bufferOut = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(new File(uploadPath)));
			byte[] buffer = new byte[8192];
			int byteRead = 0;
			
			while((byteRead = fileIn.read(buffer)) != -1){
				byte[] uploadBuffer = TextUtil.ByteTrim(buffer);						//Trim null byte from byte array.
				bufferOut.write(uploadBuffer);
			}
			fileIn.close();
//			bufferOut.close();						<- Do not need to close using socket stream. socket.close will close all regarding stream.
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void Download(Socket socket, String downloadPath){
		
		try {
			BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(new File(downloadPath)));
			BufferedInputStream bufferIn = new BufferedInputStream(socket.getInputStream());
			byte[] buffer = new byte[4096];
			int byteRead = 0;

			while((byteRead = bufferIn.read(buffer)) != -1){
				byte[] downloadBuffer = TextUtil.ByteTrim(buffer);					//Trim null byte from byte array.
				fileOut.write(downloadBuffer);
			}
			fileOut.close();
//			bufferIn.close();							<- Do not need to close using socket stream. socket.close will close all regarding stream.
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

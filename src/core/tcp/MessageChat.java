package core.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import common.TextUtil;

public class MessageChat {

	
	/***
	 * Method for sending message to socket through server.
	 * @param to
	 * @param message
	 */
	public void MessageSend(Socket to, String message){
		
		try {
			
			//Auto flush the stream
			PrintWriter msgPrint = new PrintWriter(to.getOutputStream(), true);

			msgPrint.println(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/***
	 * Method for sending message from socket to socket directly.
	 * @param from
	 * @param to
	 * @param message
	 */
	public void MessageSend(Socket from, Socket to, String message){
		
		try {
			
			Scanner msgScan = new Scanner(from.getInputStream());
			PrintWriter msgPrint = new PrintWriter(to.getOutputStream(), true);

			msgPrint.println(msgScan.nextLine());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

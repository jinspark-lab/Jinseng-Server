package sample.tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import connection.ConnectionManager;
import connection.ConnectionUnit;
import core.tcp.*;

public class ChatRoomService implements IServiceLogic{

	private String endId;
	private Socket endPoint;
	
	public Object ReceiveRequest(String connectionId, Socket socket){
		endId = connectionId;
		endPoint = socket;
		
		try {
			Scanner scanIn = new Scanner(socket.getInputStream());
			String message = scanIn.nextLine();
			System.out.println("Msg : " + message);

			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Object SendResponse(String connectionId, Object Request){
		
		String message = Request.toString();
		Map<String, ConnectionUnit> conMap = ConnectionManager.GetInstance().ReadConnectionMap();
		MessageChat chatting = new MessageChat();

		//Broadcasting the message.
		for(String key : conMap.keySet()){
			if(!key.equals(connectionId)){
				System.out.println("Send data to - " + key);
				chatting.MessageSend(conMap.get(key).getSocket(), message);
			}
		}
		return null;
	}
	
}


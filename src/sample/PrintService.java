package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import core.tcp.*;

public class PrintService implements ServiceLogic{

	public Object ReceiveRequest(String connectionId, Socket socket){
		try {
			ObjectInputStream objectStream = new ObjectInputStream(socket.getInputStream());
			String request = (String)objectStream.readObject();
			objectStream.close();
			return request;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	public Object SendResponse(String connectionId, Object request){
		System.out.println((String)request);
		return null;
	}
	
}

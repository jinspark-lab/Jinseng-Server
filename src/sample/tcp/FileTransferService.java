package sample.tcp;

import core.tcp.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class FileTransferService implements IServiceLogic {

	private Socket endSocket = null;
	
	public Object ReceiveRequest(String connectionId, Socket socket){
		
		ObjectInputStream objectStream;
		Object retVal = null;
		try {
			objectStream = new ObjectInputStream(socket.getInputStream());
			String request = (String)objectStream.readObject();
			
			//Receive File name from message. (sample Protocol)
			if(request.substring(0, 4).contains("FILE")){
				endSocket = socket;
				retVal = request.substring(5);
			}
//			objectStream.close();		<- Do not need to close the stream. because we are now in communicating.

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return retVal;
	}
	public Object SendResponse(String connectionId, Object request){

		FileTransfer ft = new FileTransfer();
		
		if(!request.equals(null)){
			ft.Download(endSocket, request.toString());
		}
		
		System.out.println("Download done");
		return null;
	}
}


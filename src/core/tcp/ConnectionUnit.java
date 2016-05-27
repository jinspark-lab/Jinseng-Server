package core.tcp;

import java.io.IOException;
import java.net.Socket;

public class ConnectionUnit implements Runnable{

	//connectionId will be given.
	private String connectionId = null;
	
	private Socket socket;
	private ServiceLogic logic;
	private boolean serviceLoop = false;				//Initially handle communication once.
	private int term = 1000;								//term to handle connection.
	
	public ConnectionUnit(String id, Socket endpoint, ServiceLogic businessLogic){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, ServiceLogic businessLogic, boolean loop){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		serviceLoop = loop;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, ServiceLogic businessLogic, boolean loop, int term){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		serviceLoop = loop;
		System.out.println("New connection generate : " + connectionId);
	}
	
	public String getConnectionId(){
		return connectionId;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void run(){
		try{
			
			do{
				Object response = logic.ReceiveRequest(connectionId, socket);			//Call request receiver method by passing this unit's socket.

				logic.SendResponse(connectionId, response);								//Call response handle method by passing this unit's id and response.
				
				Thread.sleep(term);
				
			}while(serviceLoop);
			
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
}


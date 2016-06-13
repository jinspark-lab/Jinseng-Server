package connection;

import java.io.IOException;
import java.net.Socket;

import core.tcp.IServiceLogic;

public class ConnectionUnit implements Runnable{
	/***
	 * 	Basic class of socket connection.
	 * It implements basic connection between two endpoints regardless of its protocol.
	 * 
	 */
	
	
	//connectionId will be given.
	private String connectionId = null;
	
	private Socket socket;
	private IServiceLogic logic;
	private boolean serviceLoop = false;				//Initially handle communication once.
	private int term = 1000;								//term to handle connection.
	
	public ConnectionUnit(String id, Socket endpoint, IServiceLogic businessLogic){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, IServiceLogic businessLogic, boolean loop){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		serviceLoop = loop;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, IServiceLogic businessLogic, boolean loop, int term){
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
	
	/***
	 * 	Run logic. It shows network I/O routine.
	 * tcp > connection based handshake.
	 * udp > connectless handshake.
	 * http > stateless request and response.
	 */
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


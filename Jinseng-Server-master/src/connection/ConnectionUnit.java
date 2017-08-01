package connection;

import java.io.IOException;
import java.net.Socket;

import core.tcp.ITcpServiceLogic;

public class ConnectionUnit implements Runnable{
	/***
	 * 	Basic class of TCP socket connection.
	 * It implements basic connection between two endpoints regardless of its protocol.
	 * 
	 */
	
	
	//connectionId will be given.
	private String connectionId = null;
	
	private Socket socket;
	private ITcpServiceLogic logic;
	private boolean serviceLoop = false;				//Initially handle communication once.
	private int term = 1000;								//term to handle connection.
	
	private ConnectionManager manager = ConnectionManager.GetInstance();
	
	public ConnectionUnit(String id, Socket endpoint, ITcpServiceLogic businessLogic){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, ITcpServiceLogic businessLogic, boolean loop){
		connectionId = id;
		socket = endpoint;
		logic = businessLogic;
		serviceLoop = loop;
		System.out.println("New connection generate : " + connectionId);
	}
	public ConnectionUnit(String id, Socket endpoint, ITcpServiceLogic businessLogic, boolean loop, int term){
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
	 * http > connection with stateless request and response.
	 */
	public void run(){
		try{
			
			do{
				Object request = logic.ReceiveRequest(connectionId, socket);			//Call request receiver method by passing this unit's socket.

				logic.SendResponse(connectionId, request);								//Call response handle method by passing this unit's id and response.
				
				Thread.sleep(term);
				
			}while(serviceLoop);
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endRun();
		}
	}
	
	/***
	 * End callback of the thread.
	 */
	public void endRun(){
		manager.DeleteUnit(this.connectionId);
	}
	
	
}


package connection;

import java.io.IOException;
import java.net.DatagramSocket;

import core.udp.IUdpServiceLogic;


public class ConnectlessUnit implements Runnable{
	/***
	 * 
	 */
	private IUdpServiceLogic logic = null;
	private DatagramSocket socket;
	private Object request;
	
	public ConnectlessUnit(IUdpServiceLogic logic, DatagramSocket socket, Object request){
		this.logic = logic;
		this.socket = socket;
		this.request = request;
		System.out.println("New client access the server.");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			logic.SendResponse(request);											//Call response handle method.

		}finally{
			//Do not close the socket.
		}
	}
	
}

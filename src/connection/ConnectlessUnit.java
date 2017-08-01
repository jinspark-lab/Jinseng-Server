package connection;

import java.io.IOException;
import java.net.DatagramSocket;

import core.udp.IUdpServiceLogic;


public class ConnectlessUnit implements Runnable{
	/***
	 * This is the Access Unit for handling UDP protocol.
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
	
	/***
	 * Only handle response as per given requests.
	 */
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

package core.udp;

import java.net.DatagramSocket;

public interface IUdpServiceLogic {
	/***
	 * This is Interface for service logic from outer application based on UDP.
	 * 
	 * You can implement the interface and applying it into your server.
	 * 
	 * 
	 * 
	 */
	
	/***
	 * Receive request from client.
	 * Remember, UDP does not have connection, so it does not need to manage connection.
	 * @param socket is the endpoint of the client.
	 * @return it returns the parsed request.
	 */
	public Object ReceiveRequest(DatagramSocket socket);
	
	/***
	 * Send Response from given request
	 * @param request is given data from sender.
	 * @return it returns the result of this method
	 */
	public Object SendResponse(Object request);
	
	
}

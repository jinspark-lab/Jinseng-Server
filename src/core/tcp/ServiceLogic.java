package core.tcp;

import java.net.Socket;

public interface ServiceLogic {
	/***
	 * This is interface of service logic(business logic) from outer application.
	 * You can implement the interface and applying it into your server.
	 * 
	 * @param socket
	 * @return
	 */
	
	/***
	 * Receive Request from endpoint.
	 * @param connectionId is id of the ConnectionUnit that takes requests.
	 * @param socket is endpoint socket of the ConnectionUnit.
	 * @return it returns the parsed request.
	 */
	public Object ReceiveRequest(String connectionId, Socket socket);
	
	/***
	 * Send Response from given request.
	 * @param connectionId is id of ConnectionUnit that takes requests.
	 * @param request is given data from sender.
	 * @return it returns the result of this method.
	 */
	public Object SendResponse(String connectionId, Object request);
}

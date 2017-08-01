package connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import common.TextUtil;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.HttpServiceRouter;
import core.http.HttpMethod;
import core.http.HttpProtocolParser;
import core.http.IWebServiceLogic;

public class WebConnectionUnit implements Runnable{

	private String connectionId;
	private Socket socket;

	public IWebServiceLogic webServiceLogic;		//Single webservice logic for simple static page. it does not be with router.
	private HttpServiceRouter router;				//Webservice logic router for multiple functional page. it does not be with webServiceLogic.

	private HttpProtocolParser httpParser;

	private ConnectionManager manager = ConnectionManager.GetInstance();
	
	public WebConnectionUnit(String id, Socket endPoint, IWebServiceLogic service){
		connectionId = id;
		socket = endPoint;
		webServiceLogic = service;
		router = null;
		
		httpParser = new HttpProtocolParser();
		System.out.println(endPoint.getInetAddress() + " has been connected");
	}
	
	public WebConnectionUnit(String id, Socket endPoint, HttpServiceRouter route){
		connectionId = id;
		socket = endPoint;
		webServiceLogic = null;
		router = route;
		
		httpParser = new HttpProtocolParser();
		System.out.println(endPoint.getInetAddress() + " has been connected");
	}
	
	public String getConnectionId(){
		return connectionId;
	}
	
	private void DoRespond(HttpResponse response){
		if(response == null)
			return ;
		
		//Write to the socket.
		try {
			OutputStream outp = socket.getOutputStream();
			outp.write(response.getResponseHeaderString().getBytes());		//Write header as byte stream.
			outp.write(new byte[]{13, 10});								//CRLF
			outp.write(response.getMessageBody());						//Write body as byte stream.
			outp.flush();
			outp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		//Gain Request and analyze it.
		System.out.println("Run on");
		
		//Encode message to proper HttpRequest type.
		HttpRequest request = httpParser.EncodeRequest(httpParser.ReceiveMessage(socket));

		if(request != null && request.getUrlPath().length() > 0){
			//Get request URL and Request Type
			String requestUrl = request.getUrlPath();
			HttpMethod requestType = request.getRequestType();
			
			HttpResponse response = null;
			
			IWebServiceLogic service = null;
			if(router != null){
				//Gain method from routing table.
				service = router.getRoutingMethod(requestUrl);
			}else{
				service = webServiceLogic;
			}
			if(service != null)
				response = service.Respond(request);
	
			DoRespond(response);
		}

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
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

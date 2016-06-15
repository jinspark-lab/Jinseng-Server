package connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.HttpMethod;
import core.http.HttpProtocolParser;
import core.http.IWebServiceLogic;

public class WebConnectionUnit implements Runnable{

	private String connectionId;
	private Socket socket;
	public IWebServiceLogic webServiceLogic;
	private HttpProtocolParser httpParser;
	
	public WebConnectionUnit(String id, Socket endPoint, IWebServiceLogic webService){
		connectionId = id;
		socket = endPoint;
		webServiceLogic = webService;
		httpParser = new HttpProtocolParser();
		System.out.println(endPoint.getInetAddress() + " has been connected");
	}
	
	private void DoRespond(HttpResponse response){
		if(response == null)
			return ;
		
		//Write to the socket.
		try {
			OutputStream outp = socket.getOutputStream();
			outp.write(response.getResponseHeader().getBytes());		//Write header as byte stream.
			outp.write(new byte[]{13, 10});								//CRLF
			outp.write(response.getMessageBody());						//Write body as byte stream.
			outp.flush();
			outp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("End Logic");
	}
	
	public void run(){
		
		//Gain Request and analyze it.
		System.out.println("Run on");
		
		//Encode message to proper HttpRequest type.
		HttpRequest request = httpParser.EncodeRequest(httpParser.ReceiveMessage(socket));
		
		HttpResponse response = null;
		
		//Get Response from defined routine.
		if(request.GetRequestType().equals(HttpMethod.GET)){
			
			response = webServiceLogic.HttpGetResponse(request);
			
		}else if(request.GetRequestType().equals(HttpMethod.POST)){

			response = webServiceLogic.HttpPostResponse(request);
			
		}else if(request.GetRequestType().equals(HttpMethod.UPDATE)){
			
			response = webServiceLogic.HttpUpdateResponse(request);
			
		}else if(request.GetRequestType().equals(HttpMethod.DELETE)){
			
			response = webServiceLogic.HttpDeleteResponse(request);
			
		}

		DoRespond(response);

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

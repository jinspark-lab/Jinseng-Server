package core.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

import common.TextUtil;

public class HttpProtocolParser {

	//Maximum size for method "GET" is 2048 in HTTP version 1.1, and it is minimum.
	private final int maxMinMsgSize = 2048;
	
	public HttpProtocolParser(){
		
	}
	
	public String ReceiveMessage(Socket socket){
		String received = "";

		try {

			//Based on O'reilly on Java style.
			StringBuffer httpIn = new StringBuffer(maxMinMsgSize);

			byte[] buffer = new byte[maxMinMsgSize];
			int byteRead = 0;
			
			byteRead = socket.getInputStream().read(buffer);
			
			for(int j=0; j<byteRead; j++)
				httpIn.append((char)buffer[j]);
			received = httpIn.toString();
			
			System.out.println(received);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Received req : " + received);
		return received;
	}
	
	public HttpRequest EncodeRequest(String requested){
		HttpRequest encodedRequest = null; //new HttpRequest();
		
		String[] requestRows = requested.split(TextUtil.CRLF);
		
		String[] requestLine = requestRows[0].split(TextUtil.BLANK);

		//Exception handling. -> A Number of Components in requestLine are less than 3.
		if(requestLine.length < 3){
			return null;
		}

		//Parse request line headers.
		HttpMethod method = HttpMethod.getType(requestLine[0]);
		String uri = requestLine[1];
		String version = requestLine[2];
		
		encodedRequest = new HttpRequest(method, uri, version);

		//Make header options.
		for(int i=1; i<requestRows.length-2; i++){
			String row = requestRows[i];
			String[] tuple = row.split(":");
			encodedRequest.ConfigHeaderProperty(tuple[0], tuple[1]);
		}
		encodedRequest.ConfigMessageBody(requestRows[requestRows.length-1]);
		
		return encodedRequest;
	}

	public String DecodeResponseText(HttpResponse responded){
		String decodeResponse = "";
		try {
			decodeResponse = responded.getResponseString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decodeResponse;
	}
	
}

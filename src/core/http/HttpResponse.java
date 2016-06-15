package core.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import common.TextUtil;

public class HttpResponse {

	private String statusLine = "";
	private Map<String, String> responseHeader = new HashMap<String, String>();
	private byte[] messageBody = {0, };
	
	public HttpResponse(String version, int responseCode, String responseMsg){
		
		statusLine += version + TextUtil.BLANK + String.valueOf(responseCode) + TextUtil.BLANK + responseMsg + TextUtil.CRLF;
	}

	public String GetStatusLine(){
		return statusLine;
	}
	
	public Map<String, String> GetResponseHeader(){
		return responseHeader;
	}
	
	public byte[] getMessageBody(){
		return messageBody;
	}
	
	public String getMessageBodyString(){
		return Arrays.toString(messageBody);
	}
	
	//Response Header elements.
	///Accept-Ranges, Age, ETag, Location, Proxy-Authenticate, Retry-After, Server, Vary, WWW-Authenticate
	public void setHeaderProperty(String property, String value){
		responseHeader.put(property,  value);
	}
	
	public void setMessageBody(String message){
		messageBody = (TextUtil.CRLF + message).getBytes();
	}
	
	public void setMessageBody(byte[] message){
		messageBody = new byte[message.length+2];
		for(int i=0; i<message.length; i++)
			messageBody[i] = message[i];
	}
	
	public String getResponseHeader(){
		String header = statusLine;
		for(String key : responseHeader.keySet()){
			header += key + ":" + responseHeader.get(key) + TextUtil.CRLF;
		}
		return header;
	}
	
	public String getResponseText() throws UnsupportedEncodingException{
		//Assemble reponse object as String object.
		String response = statusLine;
		for(String key : responseHeader.keySet()){
			response += key + ":" + responseHeader.get(key) + TextUtil.CRLF;
		}
		response += new String(messageBody, "UTF-8");
		return response;
	}

}

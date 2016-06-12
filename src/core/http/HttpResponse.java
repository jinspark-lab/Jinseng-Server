package core.http;

import java.util.HashMap;
import java.util.Map;

import common.TextUtil;

public class HttpResponse {

	private String statusLine = "";
	private Map<String, String> responseHeader = new HashMap<String, String>();
	private String messageBody = "";
	
	public HttpResponse(String version, int responseCode, String responseMsg){
		
		statusLine += version + TextUtil.BLANK + String.valueOf(responseCode) + TextUtil.BLANK + responseMsg + TextUtil.CRLF;
	}

	public String GetStatusLine(){
		return statusLine;
	}
	
	public Map<String, String> GetResponseHeader(){
		return responseHeader;
	}
	
	public String GetMessageBody(){
		return messageBody;
	}
	
	//Response Header elements.
	///Accept-Ranges, Age, ETag, Location, Proxy-Authenticate, Retry-After, Server, Vary, WWW-Authenticate
	public void ConfigHeaderProperty(String property, String value){
		responseHeader.put(property,  value);
	}
	
	public void ConfigBodyMessage(String message){
		messageBody = TextUtil.CRLF + message;
	}
	
	public String AssembleProtocol(){
		String response = statusLine;
		for(String key : responseHeader.keySet()){
			response += key + ":" + responseHeader.get(key) + TextUtil.CRLF;
		}
		response += messageBody;
		return response;
	}
	
}

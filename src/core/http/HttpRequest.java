package core.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import common.TextUtil;

public class HttpRequest {
	
	private String requestLine = "";
	private Map<String, String> requestHeader = new HashMap<String, String>();
	private String messageBody = "";
	
	private HttpMethod requestType;
	private String defaultVersion = "HTTP/1.1";
	private HttpUrl url = null;
	
	public HttpRequest(HttpMethod method, String uri){
		requestType = method;

		//Add handling ways for charset of the page.
		try {
			url = new HttpUrl(uri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestLine += method.getMethod() + TextUtil.BLANK + uri + TextUtil.BLANK + defaultVersion + TextUtil.CRLF;
	}
	
	//How to handle updating of http version???. current 1.1, but 1.0 is old.
	public HttpRequest(HttpMethod method, String uri, String version){
		requestType = method;
		
		//Add handling ways for charset of the page.
		try {
			url = new HttpUrl(uri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(requestLine);
		requestLine += method.getMethod() + TextUtil.BLANK + uri + TextUtil.BLANK + defaultVersion + TextUtil.CRLF;
	}
	
	public HttpMethod getRequestType(){
		return requestType;
	}

	public String getUrl(){
		//Remove '/' from query String.
		return url.toString();
	}
	
	//RequestHeader elements.
	///General Header : Cache-Control, Connection, Date, Pragma, Trailer, Transfer-Enco, Upgrade, Via, Warning
	///Request Header : Accept, Accept-Charset, Accept-Encoding, Accept-Language, Authorization, Expect, From, 
	///						Host, If-Match, If-Modified-Since, If-None-Match, If-Range, If-Unmodified-Since, Max-Forwards, Proxy-Authorization, Range, Referer, TE, User-Agent
	///Entity Header : Allow, Content-Encoding,Content-Language, Content-Length, Content-Location, Content-MD5, Content-Range, Content-Type, Expires, Last-Modified, extension-header
	public void ConfigHeaderProperty(String property, String value){
		requestHeader.put(property,  value);
	}
	
	public void ConfigMessageBody(String message){
		messageBody = message;
	}
	
	private String AssembleProtocol(){
		String request = requestLine;
		for(String key : requestHeader.keySet()){
			request += key + ":" + requestHeader.get(key) + TextUtil.CRLF;
		}
		request += TextUtil.CRLF;
		request += messageBody;
		return request;
	}
	
	public String getMessageBody(){
		return messageBody;
	}
		
	
}

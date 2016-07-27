package core.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import common.TextUtil;

public class HttpRequest {
	
	private String requestLine = "";
	private Map<String, String> requestHeader = new HashMap<String, String>();
	private String messageBody = "";
	
	private HttpMethod requestType;
	private String protocolVersion = HttpProtocolVersion.getMajorVersion();
	private HttpUrl url = null;
	private int port = 8080;
	
	
	public HttpRequest(HttpMethod method, String uri){
		requestType = method;

		//Add handling ways for charset of the page.
		try {
			url = new HttpUrl(uri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestLine += method.getMethod() + TextUtil.BLANK + uri + TextUtil.BLANK + protocolVersion + TextUtil.CRLF;
	}
	
	public HttpRequest(HttpMethod method, String uri, int port){
		requestType = method;

		//Add handling ways for charset of the page.
		try {
			url = new HttpUrl(uri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.port = port;
		
		requestLine += method.getMethod() + TextUtil.BLANK + uri + TextUtil.BLANK + protocolVersion + TextUtil.CRLF;
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
		
		HttpProtocolVersion newVersion = new HttpProtocolVersion(version);
		protocolVersion = newVersion.getVersion();
		
		System.out.println(requestLine);
		requestLine += method.getMethod() + TextUtil.BLANK + url.getEncodedUrl() + TextUtil.BLANK + protocolVersion + TextUtil.CRLF;
	}	
	
	//How to handle updating of http version???. current 1.1, but 1.0 is old.
	public HttpRequest(HttpMethod method, String uri, int port, String version){
		requestType = method;
		
		//Add handling ways for charset of the page.
		try {
			url = new HttpUrl(uri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.port = port;
		
		HttpProtocolVersion newVersion = new HttpProtocolVersion(version);
		protocolVersion = newVersion.getVersion();
		
		System.out.println(requestLine);
		requestLine += method.getMethod() + TextUtil.BLANK + url.getEncodedUrl() + TextUtil.BLANK + protocolVersion + TextUtil.CRLF;
	}
	
	public HttpMethod getRequestType(){
		return requestType;
	}

	public HttpUrl getUrl(){
		//Remove '/' from query String.
		return url;
	}
	
	public String getUrlPath(){
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
	
	public String getRequestString(){
		return AssembleProtocol();
	}
	
	
	/***
	 * Send response via input url and port.
	 * @return
	 */
	public String send(){
		
		try {
			Socket endpoint = new Socket(url.toString(), port);

			BufferedOutputStream requestOut = new BufferedOutputStream(endpoint.getOutputStream());
			BufferedInputStream responseIn = new BufferedInputStream(endpoint.getInputStream());

			byte[] reqBuff = getRequestString().getBytes();

			byte[] buffer = new byte[8192];
			int byteRead = 0;
			
			String response = "";
			
			requestOut.write(reqBuff);
			requestOut.flush();
			
			while((byteRead = responseIn.read(buffer)) != -1){
				byte[] uploadBuffer = TextUtil.ByteTrim(buffer);						//Trim null byte from byte array.
				response += new String(uploadBuffer, "UTF-8");
			}
			responseIn.close();
			requestOut.close();

			endpoint.close();
			
			return response;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

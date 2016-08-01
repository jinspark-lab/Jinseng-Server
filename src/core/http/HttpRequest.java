package core.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import common.TextUtil;
import common.Validator;

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
		request += " " + TextUtil.CRLF;
		return request;
	}
	
	public String getMessageBody(){
		return messageBody;
	}
	
	public String getRequestString(){
		return AssembleProtocol();
	}
	
	/***
	 * It describes simplest way to handshake in HTTP protocol. Just request and receive once.
	 * @param endpoint
	 * @return
	 */
	private byte[] handshakeIO(Socket endpoint){

		try{
			//Send Request from buffer to host.
			BufferedOutputStream requestOut = new BufferedOutputStream(endpoint.getOutputStream());
			BufferedInputStream responseIn = new BufferedInputStream(endpoint.getInputStream());
	
			requestOut.write(getRequestString().getBytes());
			requestOut.flush();
	
			//Receive Response from host.

			byte[] buffer = new byte[8192];
			int byteRead = 0;
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			
			while((byteRead = responseIn.read(buffer)) != -1){
				byte[] uploadBuffer = TextUtil.ByteTrim(buffer);						//Trim null byte from byte array.
				byteArray.write(uploadBuffer, 0, uploadBuffer.length);
			}

			byte[] response = byteArray.toByteArray();
			
			responseIn.close();
			requestOut.flush();
			requestOut.close();
	
			endpoint.close();
			
			return response;
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/***
	 * Send request via input url and port.
	 * @return
	 */
	public byte[] send(){

		try {
			String hostAddr = url.getHostAddr();

			Socket endpoint = new Socket(hostAddr, port);
			
			byte[] response = handshakeIO(endpoint);
			
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
	
	/***
	 * Send request via input url and port through proxy.
	 * @param proxy
	 * @return
	 */
	public byte[] send(Proxy proxy){

		try {
			String hostAddr = url.getHostAddr();
	
			Socket endpoint = new Socket(proxy);
		
			endpoint.connect(new InetSocketAddress(hostAddr, port));
			
			byte[] response = handshakeIO(endpoint);
			
			return response;
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

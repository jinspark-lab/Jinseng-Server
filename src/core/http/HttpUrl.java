package core.http;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import common.Validator;

public class HttpUrl {

	private String route = "";
	private String pathUrl = "";
	private String queryUrl = "";
	private String hostName = "";
	private String hostAddr = "";
	
	private ArrayList<String> pathParams = new ArrayList<String>();
	private LinkedHashMap<String, String> queryParams = new LinkedHashMap<String, String>();
	
	//It should be changed to Charset type to design clearly.
	private String charset = "UTF-8"; //Charset.defaultCharset();

	
	//Specialized URL path options.
	public static String ANYURL = "*";

	
	
	//Should separate exception handling ways. > UnsupportedEncodingException in default constructor ("UTF-8") => Only throw errors when original code has defects.
	
	public HttpUrl(String url) throws UnsupportedEncodingException{
		this.route = this.urlDecode(url, charset);
		String[] parts = url.split("\\?");

		if(parts.length > 0){
			String decodePath = this.urlDecode(parts[0], charset);
			pathUrl = decodePath;
			parseHost(this.route);
			buildPathes(decodePath);
			if(parts.length > 1){
				String decodeQuery = this.urlDecode(parts[1], charset);
				queryUrl = decodeQuery;
				buildQuery(decodeQuery);
			}
		}else{
			//Error handling.
		}
	}
	
	public HttpUrl(String url, String customCharset) throws UnsupportedEncodingException{
		this.route = url;
		String[] parts = url.split("\\?");
		if(parts.length > 0){
			String decodePath = this.urlDecode(parts[0], charset);
			pathUrl = decodePath;
			parseHost(this.route);
			buildPathes(decodePath);
			if(parts.length > 1){
				String decodeQuery = this.urlDecode(parts[1], charset);
				queryUrl = decodeQuery;
				buildQuery(decodeQuery);
			}
		}
		charset = customCharset;
	}
	
	public String toString(){
		return route;
	}
	
	public String getUrlCharset(){
		return charset;
	}
	
	public String getEncodedUrl(){
		String encodedUrl = null;
		try {
			encodedUrl = this.urlEncode(route, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodedUrl;
	}
	
	public String getHostName(){
		return hostName;
	}
	
	public String getHostAddr(){
		return hostAddr;
	}
	
	public String getPathString(){
		return pathUrl;
	}
	
	public String getQueryString(){
		return queryUrl;
	}
	
	public String getPathElement(int index){
		return pathParams.get(index);
	}
	
	public String getQueryElement(String key){
		return queryParams.get(key);
	}
	
	private void parseHost(String url){
		
		String addr = url;
		if(addr.contains("http://")){
			addr = addr.replace("http://", "");
		}
		
		String[] part = addr.split("/");
		
		String host = part[0];
		if(host.contains(":")){
			host = part[0].split(":")[0];
		}
		try {
			if(Validator.isIPAddress(host)){
				hostAddr = host;
				hostName = InetAddress.getByName(host).getHostName();
			}			
			if(!Validator.isIPAddress(host)){
				hostAddr = InetAddress.getByName(host).getHostName();
				hostName = host;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hostName = "";
		}
	}
	
	/***
	 * build path parameter from path part in url. build array.
	 * @param path
	 */
	private void buildPathes(String path){
		String[] pathes = path.split("/");

		//Remove first '/' from path.
		for(int i=1; i<pathes.length; i++){
			pathParams.add(pathes[i]);
		}
	}
	
	/***
	 *  build query parameter from query part in url. build map.
	 * @param query
	 * @throws UnsupportedEncodingException
	 */
	private void buildQuery(String query) throws UnsupportedEncodingException{
		String[] queries = query.split("&");
		for(String q : queries){
			String[] tuple = q.split("=");
			
			if(tuple.length > 1){
				String fieldName = tuple[0];
				String fieldVal = this.urlDecode(tuple[1], charset);
				queryParams.put(fieldName, fieldVal);
			}
		}
	}
	
	public static String urlEncode(String url, String charset) throws UnsupportedEncodingException{
		String encoded = URLEncoder.encode(url, charset);
		return encoded;
	}
	
	public static String urlDecode(String url, String charset) throws UnsupportedEncodingException{
		String decoded = URLDecoder.decode(url, charset);
		return decoded;
	}
	
}

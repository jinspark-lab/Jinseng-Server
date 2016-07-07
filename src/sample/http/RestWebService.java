package sample.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import common.data.JsonObject;
import common.TextUtil;
import core.http.HttpMethod;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.HttpUrl;
import core.http.IWebServiceLogic;

public class RestWebService implements IWebServiceLogic{

	private LinkedHashMap<String, JsonObject> memCache = new LinkedHashMap<String, JsonObject>();
	
	/***
	 * Send response message of printing certain json information to user in specific url.
	 */
	public HttpResponse Respond(HttpRequest request){
		
		String url = request.getUrlPath();
		HttpMethod method = request.getRequestType();
		
		String returnValue = "";
		if(method.equals(HttpMethod.GET)){
			returnValue = GET(url);
		}else if(method.equals(HttpMethod.POST)){
			returnValue = POST(request);
		}
				
		HttpResponse response = new HttpResponse(200, "OK");
		response.setHeaderProperty("Content-Type", "application/json;charset=utf-8");
		response.setHeaderProperty("Content-Length", String.valueOf(returnValue.length() + 2));
		response.setHeaderProperty("Cache-Control", "no-cache");
		response.setMessageBody(returnValue);
		
		return response;
	}
	
	//User-defined function do like this.
	private String GET(String url){
		String val = MockDatabase(url);
		if(val == null)
			return "";
		return val;
	}
	
	//User-defined function do like also this.
	private String POST(HttpRequest req){
		String returnMsg = "Success to save in server!";
		try{
			String name = "";
			String grade = "";
			String age = "";
			HttpUrl reqUrl = req.getUrl();

			//It depends on api speculations.
			name = reqUrl.getPathElement(1);
			grade = ClassifyGrade(reqUrl.getPathElement(2));
			age = reqUrl.getPathElement(3);
	
			JsonObject jobj = new JsonObject();
			jobj.PutElement("name", name);
			jobj.PutElement("grade", grade);
			jobj.PutElement("age", Integer.parseInt(age));
	
			ArrayList<Object> friends = CallFriends(name);
	
			jobj.PutElement("friends", friends);
	
			LinkedHashMap<String, Object> score = ReadScore(name);
			
			jobj.PutElement("score", score);
			
	
			memCache.put(req.getUrlPath(), jobj);
		}catch(Exception e){
			returnMsg = "Failed to save in server. maybe there is an error.";
		}
		return returnMsg;
	}
	
	
	private String ClassifyGrade(String part){
		String grade = "";
		if(part.equals("1")){
			grade = "freshman";
		}else if(part.equals("2")){
			grade = "sophomore";
		}else if(part.equals("3")){
			grade = "junior";
		}else if(part.equals("4")){
			grade = "senior";
		}else{
			grade = "unranked";
		}
		return grade;
	}
	
	/***
	 * Set dummy friends in student that of given name.
	 * @param name
	 * @return
	 */
	private ArrayList<Object> CallFriends(String name){
		ArrayList<Object> friends = new ArrayList<Object>();

		if(name.equals("tomas")){
			friends.add("mike");
			friends.add("james");
			friends.add("rollen");
		}else if(name.equals("james")){
			friends.add("tomas");
			friends.add("kate");
			friends.add("teddy");
		}else if(name.equals("rollen")){
			friends.add("nichole");
			friends.add("tomas");
			friends.add("sara");
		}
		return friends;
	}
	
	/***
	 * Set dummy scores in student that of given name.
	 * @param name
	 * @return
	 */
	private LinkedHashMap<String, Object> ReadScore(String name){
		LinkedHashMap<String, Object> score = new LinkedHashMap<String, Object>();
		
		if(name.equals("tomas")){
			score.put("math", 100);
			score.put("english", 97);
			score.put("science", 78);
		}else if(name.equals("james")){
			score.put("math", 80);
			score.put("english", 77);
			score.put("science", 100);
		}else if(name.equals("rollen")){
			score.put("math", 60);
			score.put("english", 100);
			score.put("science", 90);
		}
		return score;
	}
	
	/***
	 * This method mimics the action that of selecting data from database from given query.
	 * @param query
	 * @return
	 */
	private String MockDatabase(String url){

		if(memCache.containsKey(url)){
			JsonObject jobj = memCache.get(url);
			
			String result = jobj.Stringify();
			
			return result;
		}
		return null;
	}
	
}

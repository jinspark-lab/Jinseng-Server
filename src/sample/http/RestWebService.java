package sample.http;

import common.TextUtil;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;

public class RestWebService implements IWebServiceLogic{

	
	/***
	 * Send response message of printing certain json information to user in specific url.
	 */
	public HttpResponse Respond(HttpRequest request){
		
		String url = request.GetUrl();
		String refer = MockDatabase(url);
		
		HttpResponse response = new HttpResponse("HTTP/1.1", 200, "OK");
		response.setHeaderProperty("Content-Type", "application/json;charset=utf-8");
		response.setHeaderProperty("Content-Length", String.valueOf(refer.length() + 2));
		response.setHeaderProperty("Cache-Control", "no-cache");
		response.setMessageBody(refer);
		
		return response;
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
	
	private String MockDatabase(String query){
		String result = "{" + TextUtil.CRLF;
		String[] parts = query.split("/");
		if(parts.length < 3)
			return "Error in query string";
		
		String name = parts[0];
		String grade = ClassifyGrade(parts[1]);
		String age = parts[2];
		
		result += "name : " + name + TextUtil.DOT + TextUtil.CRLF;
		result += "grade : " + grade + TextUtil.DOT + TextUtil.CRLF;
		result += "age : " + age + TextUtil.DOT + TextUtil.CRLF;
		
		String info = "info : ";
		
		if(grade.equals("senior") && TextUtil.TextContains(name, "tomas", true)){
			info += "Leader";
		}
		
		result += info + TextUtil.CRLF + "}";
		
		return result;
	}
	
}

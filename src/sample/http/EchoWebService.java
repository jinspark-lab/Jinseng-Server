package sample.http;

import core.http.HttpProtocolParser;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;

public class EchoWebService implements IWebServiceLogic{

	public HttpResponse HttpGetResponse(HttpRequest request){
		
		
		String output = "[Jinseng] Says Hello : " + request.GetUrl();
		
		System.out.println(output);
		
		HttpResponse respond = new HttpResponse("HTTP/1.1", 200, "OK");
		respond.ConfigHeaderProperty("Content-Type", "text/html;charset=utf-8");
		respond.ConfigHeaderProperty("Content-Length", String.valueOf(output.length()));
		respond.ConfigBodyMessage(output);
		
		return respond;
	}
	
	public HttpResponse HttpPostResponse(HttpRequest request){
		return null;
	}
	
	public HttpResponse HttpUpdateResponse(HttpRequest request){
		return null;
	}
	
	public HttpResponse HttpDeleteResponse(HttpRequest request){
		return null;
	}
}

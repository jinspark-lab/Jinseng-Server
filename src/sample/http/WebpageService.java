package sample.http;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;

public class WebpageService implements IWebServiceLogic{

	public HttpResponse Respond(HttpRequest request){
		
		String htmlContents = contentsFromHTML("index.html");
		HttpResponse response = new HttpResponse(200, "OK");
		response.setHeaderProperty("Content-Type", "text/html;charset=utf-8");
		response.setHeaderProperty("Content-Length", String.valueOf(htmlContents.length() + 2));
		response.setHeaderProperty("Cache-Control", "no-cache");
		response.setMessageBody(htmlContents);
		
		
		return response;
	}
	
	private String contentsFromHTML(String path){
		String contents = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String inline = "";
			while((inline = reader.readLine())!= null){
				contents += inline;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}
	
}

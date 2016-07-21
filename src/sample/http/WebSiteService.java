package sample.http;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import common.IOUtil;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.HttpUrl;
import core.http.IWebServiceLogic;

public class WebSiteService implements IWebServiceLogic{

	public HttpResponse Respond(HttpRequest request){

		String html = loadHtmlText("main.html");

		HttpResponse response = new HttpResponse(200, "OK");
		response.setHeaderProperty("Content-Type", "text/html;charset=utf-8");
		response.setHeaderProperty("Content-Length", String.valueOf(html.length()));
		response.setHeaderProperty("Cache-Control", "no-cache");
		response.setMessageBody(html);
		
		return response;
	}
	
	private String loadHtmlText(String filePath){
		return IOUtil.loadFileToText(filePath);
	}
	
}


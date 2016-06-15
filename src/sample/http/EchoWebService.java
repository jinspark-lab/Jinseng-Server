package sample.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import core.http.HttpProtocolParser;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;

public class EchoWebService implements IWebServiceLogic{

	public HttpResponse HttpGetResponse(HttpRequest request){
		
		
		String output = "[Jinseng] Says Hello : " + request.GetUrl();
		
		System.out.println(output);
		
		HttpResponse respond = new HttpResponse("HTTP/1.1", 200, "OK");
		
		if(request.GetUrl().contains("image")){
			//Send Image when protocol has the string "image"
			File f = new File("toucan.jpg");
			try {
				FileInputStream is = new FileInputStream(f);
				byte[] obj = new byte[(int) f.length()];
				is.read(obj);
				respond.setHeaderProperty("Content-Type", "image/jpeg");
				respond.setHeaderProperty("Content-Length", String.valueOf(obj.length));
				respond.setHeaderProperty("Cache-Control", "no-cache");
				respond.setMessageBody(obj);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//Send url path echo system.
			respond.setHeaderProperty("Content-Type", "text/html;charset=utf-8");
			respond.setHeaderProperty("Content-Length", String.valueOf(output.length() + 2));
			respond.setHeaderProperty("Cache-Control", "no-cache");
			respond.setMessageBody(output);
		}
		
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

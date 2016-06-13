package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import common.TextUtil;
import core.http.*;

public class TestHttp {

	@Test
	public void testHttpRequestEncode(){
		
		String requestText = "GET /main.html HTTP/1.1" + TextUtil.CRLF + "ACCEPT_ENCODING: gzip,deflate,sdch" + TextUtil.CRLF 
				+ "CONNECTION: keep-alive" + TextUtil.CRLF + "ACCEPT: text/html,application/xhtml+xml" + TextUtil.CRLF + TextUtil.CRLF
				+ "So long and thanks for all the fish" + TextUtil.CRLF;
		HttpProtocolParser parser = new HttpProtocolParser();
		HttpRequest request = parser.EncodeRequest(requestText);
		
		assertEquals(HttpMethod.GET, request.GetRequestType());
		
		assertEquals("main.html", request.GetUrl());
		
		assertEquals("So long and thanks for all the fish", request.GetMessageBody());
	}
	
	@Test
	public void testHttpResponseDecode(){
		
		HttpResponse response = new HttpResponse("HTTP/1.1", 200, "OK");
		response.ConfigHeaderProperty("Cache-Control", "no-store, no-cache, must-revalidate");
		response.ConfigBodyMessage("Life is everything");
		
		HttpProtocolParser parser = new HttpProtocolParser();
		String responseText = parser.DecodeResponse(response);
		
		assertEquals("HTTP/1.1 200 OK" + TextUtil.CRLF + "Cache-Control:no-store, no-cache, must-revalidate" 
		+ TextUtil.CRLF + TextUtil.CRLF + "Life is everything",responseText);
	}
	
	
	
	
	
}

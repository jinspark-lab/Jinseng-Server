package test;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

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
		
		assertEquals(HttpMethod.GET, request.getRequestType());
		
		assertEquals("/main.html", request.getUrlPath());
		
		assertEquals("So long and thanks for all the fish", request.getMessageBody());
	}
	
	@Test
	public void testHttpResponseDecode(){
		
		HttpResponse response = new HttpResponse("HTTP/1.1", 200, "OK");
		response.setHeaderProperty("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setMessageBody("Life is everything");
		
		HttpProtocolParser parser = new HttpProtocolParser();
		String responseText = parser.DecodeResponseText(response);
		
		assertEquals("HTTP/1.1 200 OK" + TextUtil.CRLF + "Cache-Control:no-store, no-cache, must-revalidate" 
		+ TextUtil.CRLF + TextUtil.CRLF + "Life is everything",responseText);
	}
	
	@Test
	public void testHttpUrlHandle(){
		
		String url = "/rest/v1/test/mail.html?id=12345&account=admin";
		
		try {
			HttpUrl newUrl = new HttpUrl(url);
			
			assertEquals("/rest/v1/test/mail.html", newUrl.getPathString());
			
			assertEquals("id=12345&account=admin", newUrl.getQueryString());
			
			assertEquals("12345", newUrl.getQueryElement("id"));
			
			assertEquals("test", newUrl.getPathElement(2));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testParamRouting(){
		HttpServiceRouter route = new HttpServiceRouter();
		
		route.setRoutingMethod("/paths/{path}/accounts/{account}", null);

		assertEquals(null, route.getRoutingMethod("/paths/10/accounts/newman"));
		
	}
	
}

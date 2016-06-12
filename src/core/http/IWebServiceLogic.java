package core.http;

import java.net.Socket;

public interface IWebServiceLogic {

	public HttpResponse HttpGetResponse(HttpRequest request);
	
	public HttpResponse HttpPostResponse(HttpRequest request);
	
	public HttpResponse HttpUpdateResponse(HttpRequest request);
	
	public HttpResponse HttpDeleteResponse(HttpRequest request);
	
}

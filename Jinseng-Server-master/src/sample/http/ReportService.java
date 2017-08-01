package sample.http;

import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.IWebServiceLogic;
import operation.ServerStatus;

public class ReportService implements IWebServiceLogic{

	
	@Override
	public HttpResponse Respond(HttpRequest request){
		
		String systemReport = ServerStatus.getStatus().toString();
		
		HttpResponse response = new HttpResponse(200, "OK");
		response.setHeaderProperty("Content-Type", "text/html;charset=utf-8");
		response.setHeaderProperty("Content-Length", String.valueOf(systemReport.length()));
		response.setHeaderProperty("Cache-Control", "no-cache");
		response.setMessageBody(systemReport);

		
		return response;
	}
	
	
}

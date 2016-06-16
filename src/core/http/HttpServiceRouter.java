package core.http;

import java.util.TreeMap;

public class HttpServiceRouter {

	private TreeMap<String, IWebServiceLogic> routeTable = new TreeMap<String, IWebServiceLogic>();
	
	//Common Routing service. (ex) single page, echo service ... etc
	private IWebServiceLogic anyUrlService = null;
	
	public HttpServiceRouter(){
		
	}

	public IWebServiceLogic getRoutingMethod(String url){
		if(anyUrlService != null){
			return anyUrlService;
		}
		return routeTable.get(url);
	}
	
	public void setRoutingMethod(String url, IWebServiceLogic function){

		//Should implement URL Class for designating regex. * -> any url that is given.
		if(url.equals("*")){
			anyUrlService = function;
			return;
		}
		
		routeTable.put(url,  function);
	}
	
}

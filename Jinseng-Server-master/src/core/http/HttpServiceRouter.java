package core.http;

import java.util.TreeMap;

public class HttpServiceRouter {

	private TreeMap<String, IWebServiceLogic> routeTable = new TreeMap<String, IWebServiceLogic>();
	
	//Common Routing service. (ex) single page, echo service ... etc
	private IWebServiceLogic anyUrlService = null;
	
	public HttpServiceRouter(){
		
	}
	
	private String convertPathString(String url){
		
		if(routeTable.containsKey(url)){
			return url;
		}
		
		String[] urlparts = url.substring(1).split("/");
		String match = "";
		for(String urlkey : routeTable.keySet()){
			String[] keyparts = urlkey.substring(1).split("/");
			int matchcnt = 0;
			
			if(keyparts.length == urlparts.length){
				
				for(int i=0; i<urlparts.length; i++){
					
					if(keyparts[i].charAt(0) == '{' && keyparts[i].charAt(keyparts[i].length()-1) == '}'){
						//skip when a part is designated by path {param}
						matchcnt++;
						continue;
					}else{
						//check whether it is equals when it does not contains {param} type bracelet.
						if(keyparts[i].equals(urlparts[i])){
							matchcnt++;
							continue;
						}else{
							break;
						}
					}
				}
				//complete matching in whole url part.
				if(matchcnt == urlparts.length){
					match = urlkey;
					break;
				}
			}
		}
		if(!match.equals(""))
			return match;
		return null;
	}

	public IWebServiceLogic getRoutingMethod(String url){
		if(anyUrlService != null){
			return anyUrlService;
		}
		
		//convert given url path to specified path.
		String routingUrl = convertPathString(url);
		if(routingUrl == null)
			return null;
		
		//get converted url path from routing table.
		return routeTable.get(routingUrl);
	}
	
	public void setRoutingMethod(String url, IWebServiceLogic function){

		//Should implement URL Class for designating regex. * -> any url that is given.
		if(url.equals("*")){
			anyUrlService = function;
			return;
		}
		
		routeTable.put(url,  function);
	}
	
	public boolean containsRouteUrl(String url){
		String routingUrl = convertPathString(url);
		if(routingUrl == null)
			return false;
		return true;
	}
}

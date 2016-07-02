package core.http;

public enum HttpMethod {

	GET("GET"),
	POST("POST"), 
	UPDATE("UPDATE"), 
	DELETE("DELETE"),
	OPTIONS("OPTIONS"),
	HEAD("HEAD"),
	TRACE("TRACE"),
	CONNECT("CONNECT"),
	PATCH("PATCH"),
	COPY("COPY"),
	LINK("LINK"),
	UNLINK("UNLINK"),
	PURGE("PURGE"),
	LOCK("LOCK"),
	UNLOCK("UNLOCK"),
	PROPFIND("PROPFIND"),
	VIEW("VIEW");
	
	
	private String method;
	HttpMethod(String text){
		method = text;
	}
	public String getMethod(){
		return method;
	}
	public static HttpMethod getType(String data){
		if(data.equals("GET"))
			return GET;
		else if(data.equals("POST"))
			return POST;
		else if(data.equals("UPDATE"))
			return UPDATE;
		else if(data.equals("DELETE"))
			return DELETE;
		else if(data.equals("OPTIONS"))
			return OPTIONS;
		else if(data.equals("HEAD"))
			return HEAD;
		else if(data.equals("TRACE"))
			return TRACE;
		else if(data.equals("CONNECT"))
			return CONNECT;
		else if(data.equals("PATCH"))
			return PATCH;
		else if(data.equals("COPY"))
			return COPY;
		else if(data.equals("LINK"))
			return LINK;
		else if(data.equals("UNLINK"))
			return UNLINK;
		else if(data.equals("PURGE"))
			return PURGE;
		else if(data.equals("LOCK"))
			return LOCK;
		else if(data.equals("UNLOCK"))
			return UNLOCK;
		else if(data.equals("PROPFIND"))
			return PROPFIND;
		else if(data.equals("VIEW"))
			return VIEW;
		return null;
	}
}

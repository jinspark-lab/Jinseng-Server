package core.http;

public enum HttpMethod {

	GET("GET"),
	POST("POST"), 
	UPDATE("UPDATE"), 
	DELETE("DELETE"),
	OPTIONS("OPTIONS"),
	HEAD("HEAD"),
	TRACE("TRACE"),
	CONNECT("CONNECT");
	
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
		return null;
	}
}

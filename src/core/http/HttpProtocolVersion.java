package core.http;

public class HttpProtocolVersion {

//	HTTP/1.1("HTTP/1.1");
	
	private static String protocol = "HTTP/";
	private static String major = "1.1";
	private static String minor = "1.0";
	private String version = "1.1";
	
	public HttpProtocolVersion(String version){
		this.version = version;
	}
	
	public static String getMajorVersion(){
		return protocol+major;
	}
	
	public static String getMinorVersion(){
		return protocol+minor;
	}
	
	public String getVersion(){
		return protocol + version;
	}
	
}
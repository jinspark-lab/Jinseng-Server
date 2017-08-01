package connection;

public class ConfigurationInfo {

	private String serverName = "EchoServer";
	private String serverType = "HTTP";
	private String serverAddress = "127.0.0.1";		//10.89.1.173
	private int serverPort = 8080;

	private int timeout = 60000;
	private String usessl = "FALSE";
	
	private String proxyAddress = "168.219.61.252";
	private int proxyPort = 8080;
	
	private String serviceClass = "EchoWebService.java";
	
	
	public ConfigurationInfo(){
	}
	public ConfigurationInfo(String serverName, int port, String serverType, int timeout, String usessl, String proxy_host, int proxy_port){
		this.serverName = serverName;
		this.serverPort = port;
		this.timeout = timeout;
		this.serverType = serverType;
		this.usessl = usessl;
		this.proxyAddress = proxy_host;
		this.proxyPort = proxy_port;
	}
	
}

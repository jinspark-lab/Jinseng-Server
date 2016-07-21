package connection;

public class ConfigurationInfo {

	public String port;
	public String timeout;
	public String threadmax;
	public String serverType;
	public String usessl;
	public String proxy_host;
	public String proxy_port;
	
	public ConfigurationInfo(){
	}
	public ConfigurationInfo(String port, String timeout, String threadmax, String serverType, String usessl, String proxy_host, String proxy_port){
		this.port = port;
		this.timeout = timeout;
		this.threadmax = threadmax;
		this.serverType = serverType;
		this.usessl = usessl;
		this.proxy_host = proxy_host;
		this.proxy_port = proxy_port;
	}
	public void setPort(String port){
		this.port = port;
	}
	
}

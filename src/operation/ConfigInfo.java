package operation;

public class ConfigInfo {

	private String serverName;
	private String serverType;
	private String serverHost;
	private int serverPort;
	
	private int timeout;
	private String useSSL;
	private String proxyHost;
	private int proxyPort;
	
	
	public ConfigInfo(){
	}
	public ConfigInfo(String name, String type, String host, String port, String timeout, String useSSL, String proxyHost, String proxyPort){
		this.serverName = name;
		this.serverType = type;
		this.serverHost = host;
		this.serverPort = Integer.parseInt(port);
		this.timeout = Integer.parseInt(timeout);
		this.useSSL = useSSL;
		this.proxyHost = proxyHost;
		this.proxyPort = Integer.parseInt(proxyPort);
	}
}

package operation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigInfo {

	private String serverName = "";
	private String serverType = "";
	private String serverHost = "";
	private int serverPort = 0;
	
	private int timeout = 0;
	private String useSSL = "";
	private String proxyHost = "";
	private int proxyPort = 0;
		
	private Map<String, ServeInfo> serves = new LinkedHashMap<String, ServeInfo>();
	
	public class ServeInfo{
		private String url = "";
		private String pathName = "";
		private String className = "";
		
		public ServeInfo(){
			
		}
		public ServeInfo(String pathName, String className){
			this.pathName = pathName;
			this.className = className;
		}
		public ServeInfo(String url, String pathName, String className){
			this.url = url;
			this.pathName = pathName;
			this.className = className;
		}
		public String getUrl(){
			return url;
		}
		public void setUrl(String url){
			this.url = url;
		}
		public String getPathName(){
			return pathName;
		}
		public void setPathName(String pathName){
			this.pathName = pathName;
		}
		public String getClassName(){
			return className;
		}
		public void setClassName(String className){
			this.className = className;
		}
	}
	
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

	public String getServerName(){
		return serverName;
	}
	public void setServerName(String serverName){
		this.serverName = serverName;
	}
	public String getServerType(){
		return serverType;
	}
	public void setServerType(String serverType){
		this.serverType = serverType;
	}
	public String getServerHost(){
		return serverHost;
	}
	public void setServerHost(String serverHost){
		this.serverHost = serverHost;
	}
	public int getServerPort(){
		return serverPort;
	}
	public void setServerPort(int serverPort){
		this.serverPort = serverPort;
	}
	public int getTimeout(){
		return timeout;
	}
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	public String getUseSSL(){
		return useSSL;
	}
	public void setUseSSL(String useSSL){
		this.useSSL = useSSL;
	}
	public String getProxyHost(){
		return proxyHost;
	}
	public void setProxyHost(String proxyHost){
		this.proxyHost = proxyHost;
	}
	public int getProxyPort(){
		return proxyPort;
	}
	public void setProxyPort(int proxyPort){
		this.proxyPort = proxyPort;
	}
	
	public void putServeElement(String key, ServeInfo elem){
		serves.put(key, elem);
	}
	public Map<String, ServeInfo> getServeList(){
		return serves;
	}
	public ServeInfo getServeElement(String key){
		return serves.get(key);
	}
	public boolean containServeElement(String key){
		return serves.containsKey(key);
	}
	
	public ServeInfo newServeInfo(){
		return new ServeInfo();
	}
	

}

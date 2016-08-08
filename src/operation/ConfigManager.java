package operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import common.IOUtil;
import operation.ConfigInfo.ServeInfo;

public class ConfigManager {

	private Map<String, ConfigInfo> configMap = new LinkedHashMap<String, ConfigInfo>();
	private Map<String, Map<String, String>> configProperties = new LinkedHashMap<String, Map<String, String>>();
	
	private boolean filterTextTag(Node n){
		if(n instanceof Text){
			String v = n.getNodeValue().trim();
			if(v.equals(""))
				return false;
		}
		return true;
	}
	
	private void mapConfigInfo(String id, String key, String value){
	
		ConfigInfo info = null;
		if(configMap.containsKey(id)){
			info = configMap.get(id);
		}else{
			info = new ConfigInfo();
		}

		if(key.equals("server_name")){
			info.setServerName(value);
		}else if(key.equals("server_type")){
			info.setServerType(value);
		}else if(key.equals("server_host")){
			info.setServerHost(value);
		}else if(key.equals("server_port")){
			info.setServerPort(Integer.parseInt(value));
		}else if(key.equals("server_timeout")){
			info.setTimeout(Integer.parseInt(value));
		}else if(key.equals("server_useSSL")){
			info.setUseSSL(value);
		}else if(key.contains("server_services_service")){
			
			String editKey = key.replace("server_services_service_", "");
			
			String[] keyParts = editKey.split("_");
			String tag = keyParts[0];
			String element = keyParts[1];
			
			ServeInfo inf = null;
			if(info.containServeElement(tag)){
				inf = info.getServeElement(tag);
			}else{
				inf = info.newServeInfo();
			}
			if(element.equals("url")){
				inf.setUrl(value);
			}else if(element.equals("path")){
				inf.setPathName(value);
			}else if(element.equals("class")){
				inf.setClassName(value);
			}
			info.putServeElement(tag, inf);
		}
		configMap.put(id, info);
	}
	
	private void parseElement(String id, Node n, String prefix){
//		System.out.println(n.getNodeName());
		if(!filterTextTag(n))
			return;
		
		if(n.hasChildNodes()){
			//Get List's hierarchical information.
			NodeList children = n.getChildNodes();
			
			for(int k=0; k<children.getLength(); k++){
				
				if(!filterTextTag(children.item(k)))
					continue;

				String attr = "";
				if(n.hasAttributes() && n.getAttributes().getNamedItem("tag") != null){
					attr = n.getAttributes().getNamedItem("tag").getNodeValue() + "_";
				}
				parseElement(id, children.item(k), prefix + n.getNodeName() + "_" + attr);
			}
			
		}else{
			//Get Element information.
			String elementKey = (prefix + n.getNodeName()).replace("_#text",  "");
			mapConfigInfo(id, elementKey, n.getTextContent());
			configProperties.get(id).put(elementKey, n.getTextContent());
		}
	}
	
	public void loadFromFile(String filePath){
		
		String contents = IOUtil.loadFileToText(filePath);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filePath);
			
			//It may be configuration lists of server
			NodeList serverConfigs = document.getDocumentElement().getChildNodes();
			for(int i=0; i<serverConfigs.getLength(); i++){
				
				Node n = serverConfigs.item(i);
				
				if(!filterTextTag(n))
					continue;
				
				//Configuration Name
				
				if(n.getNodeType() == Node.ELEMENT_NODE){
					
					String serverId = n.getAttributes().getNamedItem("id").getNodeValue();

					configProperties.put(serverId, new LinkedHashMap<String, String>());
					
					parseElement(serverId, n, "");
				}
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printProperties(){
		for(String id : configProperties.keySet()){
			for(String key : configProperties.get(id).keySet()){
				System.out.println(key + ":" + configProperties.get(id).get(key));
			}
		}
	}
	
	public void prints(){

		for(String key : configMap.keySet()){
			ConfigInfo info = configMap.get(key);
			System.out.println("Server Name : " + info.getServerName());
			System.out.println("Server Host : " + info.getServerHost());
			System.out.println("Server Port : " + info.getServerPort());
			System.out.println("Server Type : " + info.getServerType());
			System.out.println("Server Timeout : " + info.getTimeout());
			System.out.println("Server UseSSL : " + info.getUseSSL());
			System.out.println("Server ProxyHost : " + info.getProxyHost());
			System.out.println("Server ProxyPort : " + info.getProxyPort());
			Map<String, ServeInfo> maps = info.getServeList();
			for(String elem : maps.keySet()){
				System.out.println("Service - " + elem + " : " + maps.get(elem).getUrl() + ", " + maps.get(elem).getPathName() + ", " + maps.get(elem).getClassName());
			}
		}
	}
	
}

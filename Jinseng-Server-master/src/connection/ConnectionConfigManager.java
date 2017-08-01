package connection;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.IOUtil;

public class ConnectionConfigManager {

	/***
	 * Need to think about this class's architecture.
	 */
	
	private LinkedHashMap<String, ConfigurationInfo> configProperties = new LinkedHashMap<String, ConfigurationInfo>();
	
	
	public ConnectionConfigManager(){
		
	}
	
	public void loadConfigurationFromFile(String filePath){
		configProperties = loadPropertiesFromXml(filePath);
	}
	
	private LinkedHashMap<String, ConfigurationInfo> loadPropertiesFromXml(String filePath){
		
		LinkedHashMap<String, ConfigurationInfo> propertyMap = new LinkedHashMap<String, ConfigurationInfo>();
		String contents = IOUtil.loadFileToText(filePath);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filePath);
			
			//It may be configuration lists of server
			NodeList serverConfigs = document.getDocumentElement().getChildNodes();
			for(int i=0; i<serverConfigs.getLength(); i++){
				
				Node n = serverConfigs.item(i);
				
				//Configuration Name
				String serverConfigId = n.getNodeName();
				
				if(n.getNodeType() == Node.ELEMENT_NODE){
					
					String timeout = n.getAttributes().getNamedItem("timeout").getNodeValue();
					String threadmax = n.getAttributes().getNamedItem("threadmax").getNodeValue();
					String port = n.getAttributes().getNamedItem("port").getNodeValue();
					String serverType = n.getAttributes().getNamedItem("serverType").getNodeValue();
					String usessl = n.getAttributes().getNamedItem("usessl").getNodeValue();
					String proxy_host = n.getAttributes().getNamedItem("proxy_host").getNodeValue();
					String proxy_port = n.getAttributes().getNamedItem("proxy_port").getNodeValue();
					
//					ConfigurationInfo cInfo = new ConfigurationInfo(timeout, threadmax, port, serverType, usessl, proxy_host, proxy_port);
					
//					propertyMap.put(serverConfigId, cInfo);
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
		return propertyMap;
	}
	
}

package operation;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import common.IOUtil;
import connection.ConfigurationInfo;

public class LaunchManager {
	
	private String serverName = "EchoServer";
	private String serverType = "HTTP";
	private String serverAddress = "127.0.0.1";		//10.89.1.173
	private int serverPort = 8080;
	private int timeout = 60000;
	private boolean usessl = false;
	private String proxyAddress = "";
	private int proxyPort = 8080;
	private String serviceClass = "EchoWebService.java";

	/***
	 * Need to think about this class's architecture.
	 */
	
	private LinkedHashMap<String, ConfigurationInfo> configProperties = new LinkedHashMap<String, ConfigurationInfo>();
	
	
	public LaunchManager(){
		
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
					
					NodeList childNodes = n.getChildNodes();
					
					for(int j=0; j<childNodes.getLength(); j++){
						if(childNodes.item(j) instanceof Text){
							String v = childNodes.item(j).getNodeValue().trim();
							if(v.equals(""))
								continue;
						}
						
						System.out.println(childNodes.item(j).getNodeName() + " : " + childNodes.item(j).getTextContent());
					}
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

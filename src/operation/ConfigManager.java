package operation;

import java.io.IOException;
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

public class ConfigManager {

	private Map<String, String> configProperties = new LinkedHashMap<String, String>();
	
	private boolean filterTextTag(Node n){
		if(n instanceof Text){
			String v = n.getNodeValue().trim();
			if(v.equals(""))
				return false;
		}
		return true;
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
				
				//Configuration Name
				String serverConfigId = n.getNodeName();
				
				if(n.getNodeType() == Node.ELEMENT_NODE){
					
					NodeList nList = n.getChildNodes();
					
					for(int j=0; j<nList.getLength(); j++){
						
						//Remove useless "#Test" tag.
						if(!filterTextTag(nList.item(j)))
							continue;

						//get proxy heirarchical informtaion.
						if(nList.item(j).getNodeName().equals("proxy")){
							NodeList proxyChildren = nList.item(j).getChildNodes();

							for(int k=0; k<proxyChildren.getLength(); k++){
								
								if(!filterTextTag(proxyChildren.item(k)))
									continue;
								
								//make camel case.
								String name = proxyChildren.item(k).getNodeName();
								String tmp = name.toUpperCase();
								name = tmp.substring(0, 1) + name.substring(1, name.length());
								
								configProperties.put("proxy" + name, proxyChildren.item(k).getTextContent());
							}

						}else{
							configProperties.put(nList.item(j).getNodeName(), nList.item(j).getTextContent());
						}
						
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
	}
	
	public void printProperties(){
		for(String key : configProperties.keySet()){
			System.out.println(key + ":" + configProperties.get(key));
		}
	}
	
}

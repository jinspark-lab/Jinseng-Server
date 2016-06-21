package common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject {

	
	private LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<String, Object>();	//Linked Hashmap for keeping insertion sequences.
	
	public JsonObject(){
	}
	
	public void PutElement(String key, int number){
		jsonMap.put(key, number);
	}
	public void PutElement(String key, String text){
		jsonMap.put(key, text);
	}
	public void PutElement(String key, boolean bool){
		jsonMap.put(key, bool);
	}
	public void PutElement(String key, ArrayList<Object> array){
		jsonMap.put(key, array);
	}
	public void PutElement(String key, LinkedHashMap<String, Object> object){
		jsonMap.put(key, object);
	}
	public void PutNull(String key){
		jsonMap.put(key,  null);
	}
	public void RemoveElement(String key){
		jsonMap.remove(key);
	}
	public void ClearObject(){
		jsonMap.clear();
	}
	
	/***
	 * Convert JSON Object to String.
	 * @return
	 */
	public String Stringify(){
		String text = Stringify(jsonMap);
		return text;
	}
	
	private String Stringify(LinkedHashMap<String, Object> jMap){
		String text = "{";
		int iter = 0;
		for(String key : jMap.keySet()){
			
			String sub = TextUtil.BraceQuotation(key) + ":";
			Object elem = jMap.get(key);
			
			if(elem.getClass().equals(Integer.class)){
				sub += elem.toString();
			}else if(elem.getClass().equals(String.class)){
				sub += TextUtil.BraceQuotation(elem.toString());
			}else if(elem.getClass().equals(boolean.class)){
				sub += elem.toString();
			}else if(elem.getClass().equals(ArrayList.class)){
				sub += Stringify((ArrayList<Object>)elem);
			}else if(elem.getClass().equals(LinkedHashMap.class)){
				sub += Stringify((LinkedHashMap<String, Object>)elem);
			}else if(elem == null){
				sub += " ";
			}
			if(iter < jMap.size()-1)
				sub += TextUtil.DOT;
			text += sub;
			iter++;
		}
		text += "}";
		return text;
	}
	
	private String Stringify(ArrayList<Object> array){
		String text = "[";
		int iter = 0;
		for(Object elem : array){
			String sub = "";
			
			if(elem.getClass().equals(Integer.class)){
				sub += elem.toString();
			}else if(elem.getClass().equals(String.class)){
				sub += TextUtil.BraceQuotation(elem.toString());
			}else if(elem.getClass().equals(boolean.class)){
				sub += elem.toString();
			}else if(elem.getClass().equals(ArrayList.class)){
				sub += Stringify((ArrayList<Object>)elem);
			}else if(elem.getClass().equals(LinkedHashMap.class)){
				sub += Stringify((LinkedHashMap<String, Object>)elem);
			}else if(elem == null){
				sub += " ";
			}
			if(iter < array.size()-1)
				sub += TextUtil.DOT;
			text += sub;
			iter++;
		}
		text += "]";
		return text;
	}
	
}

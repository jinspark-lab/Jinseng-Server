package common;


public class TestD{
	
	
	@TestAnno(params="Hello")
	public void Params(String param){
		System.out.println("Call : " + param);
	}
	
	
}
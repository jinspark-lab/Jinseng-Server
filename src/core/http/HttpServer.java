package core.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import connection.ConnectionManager;
import connection.ConnectionUnit;
import connection.WebConnectionUnit;
import core.tcp.IServiceLogic;

public class HttpServer {

	
	private ServerSocket server;
	private final int port = 8080;
	ExecutorService threadPool;
	private int threadMax = 12;
	private int timeout = 60000;
	private IWebServiceLogic serviceLogic;				//Service Logic of your application.
	
	private ConnectionManager manager = ConnectionManager.GetInstance();

	public HttpServer(IWebServiceLogic logic){
		
		//Do implement without using high level api.
		InitConfiguration(logic);
	}
	public HttpServer(IWebServiceLogic logic, int timeout){

		this.timeout = timeout;
		InitConfiguration(logic);
	}
	public HttpServer(IWebServiceLogic logic, int timeout, int threadMax){

		this.timeout = timeout;
		this.threadMax = threadMax;
		InitConfiguration(logic);
	}
	
	private void InitConfiguration(IWebServiceLogic service){
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadMax);			//Allocate Threadpool by max size.
			serviceLogic = service;
			server.setSoTimeout(timeout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void RunServer() throws IOException{
		System.out.println("Waiting for client message...");
		
		int tmpId = 0;
		try{
			while(true){
				try {
					
					//Accept connection and do the work in thread.
					Socket endpoint = server.accept();
					System.out.println("Accepted");
					//Passing parameters socket end point and service logic.
	//				ConnectionUnit handler = manager.CreateNewUnit(endpoint, serviceLogic, false);
					WebConnectionUnit handler = new WebConnectionUnit(String.valueOf(tmpId), endpoint, serviceLogic);
					
					tmpId++;
					
					//Execute thread pool.
					threadPool.execute(handler);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try{
						server.close();
					}catch(IOException exp){
						exp.printStackTrace();
					}
				}
			}
		}
		finally{
			server.close();
		}
	}
	
	public void EndServer(){
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

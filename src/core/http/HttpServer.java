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
	private int port = 8080;
	private ExecutorService threadPool;
	private int threadMax = 12;
	private int timeout = 60000;
	private IWebServiceLogic serviceLogic;				//Single webservice logic for simple static page. it does not be with router.
	private HttpServiceRouter router;					//Webservice logic router for multiple functional page. it does not be with webServiceLogic.
	
	private ConnectionManager manager = ConnectionManager.GetInstance();

	public HttpServer(HttpServiceRouter router){
		
		//Do implement without using high level api.
		InitConfiguration(router);
	}
	public HttpServer(HttpServiceRouter router, int timeout){

		this.timeout = timeout;
		InitConfiguration(router);
	}
	public HttpServer(HttpServiceRouter router, int timeout, int threadMax){

		this.timeout = timeout;
		this.threadMax = threadMax;
		InitConfiguration(router);
	}
	
	public HttpServer(IWebServiceLogic service){
		
		InitConfiguration(service);
	}
	
	public HttpServer(IWebServiceLogic service, int timeout){
		this.timeout = timeout;
		InitConfiguration(service);
	}
	
	public HttpServer(IWebServiceLogic service, int timeout, int threadMax){
		this.timeout = timeout;
		this.threadMax = threadMax;
		InitConfiguration(service);
	}

	private void InitConfiguration(IWebServiceLogic service){
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadMax);			//Allocate Threadpool by max size.
			serviceLogic = service;
			router = null;

			//Make server socket infinitely.
			server.setSoTimeout(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPort(){
		return this.port;
	}

	public void setPort(int port){
		this.port = port;
	}
	
	private void InitConfiguration(HttpServiceRouter route){
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadMax);			//Allocate Threadpool by max size.
			serviceLogic = null;
			router = route;

			//Make server socket infinitely.
			server.setSoTimeout(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void RunServer() throws IOException{
		System.out.println("Waiting for client message...");
		
		try{
			while(true){
				try {
					
					//Accept connection and do the work in thread.
					Socket endpoint = server.accept();
					//Make client socket has timeout.
					endpoint.setSoTimeout(this.timeout);
					System.out.println("Accepted");
					
					//Passing parameters socket end point and service logic.
					WebConnectionUnit handler = null;
					
					if(router != null)
						handler = manager.CreateNewWebUnit(endpoint, router);
					else
						handler = manager.CreateNewWebUnit(endpoint, serviceLogic);
					
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

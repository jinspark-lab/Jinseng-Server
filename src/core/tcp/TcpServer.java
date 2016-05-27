package core.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
	
	private ServerSocket server;
	private final int port = 12345;
	ExecutorService threadPool;
	private final int threadMax = 12;
	private ServiceLogic serviceLogic;				//Service Logic of your application.
	private boolean serviceLoop = true;
	
	private ConnectionManager manager = ConnectionManager.GetInstance();

	public TcpServer(ServiceLogic service){
		try {
			
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadMax);			//Allocate Threadpool by max size.
			serviceLogic = service;
			server.setSoTimeout(60000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TcpServer(ServiceLogic service, boolean loop){
		try {
			
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(threadMax);			//Allocate Threadpool by max size.
			serviceLogic = service;
			serviceLoop = loop;
			server.setSoTimeout(60000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TcpServer(ServiceLogic service, boolean loop, int numberOfThread){
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(numberOfThread);			//Allocate Threadpool by max size.
			serviceLogic = service;
			serviceLoop = loop;
			server.setSoTimeout(60000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void RunServer(){
		System.out.println("Waiting for client message...");
		
		while(true){
			try {
				
				//Accept connection and do the work in thread.
				Socket endpoint = server.accept();
				
				//Passing parameters socket end point and service logic.
				ConnectionUnit handler = manager.CreateNewUnit(endpoint, serviceLogic, serviceLoop);
				
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
	
	public void EndServer(){
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

package core.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import connection.ConnectionManager;
import connection.ConnectionUnit;
import core.IJinsengServer;

public class TcpServer implements IJinsengServer{
	
	private ServerSocket server;
	private int port = 12345;
	ExecutorService threadPool;
	private int threadMax = 12;
	private int timeout = 60000;
	private IServiceLogic serviceLogic;				//Service Logic of your application.
	private boolean serviceLoop = true;
	
	private ConnectionManager manager = ConnectionManager.GetInstance();

	public TcpServer(IServiceLogic service){
		InitConfiguration(service);
	}
	public TcpServer(IServiceLogic service, int port){
		this.port = port;
		InitConfiguration(service);
	}
	public TcpServer(IServiceLogic service, int port, int threadMax){
		this.port = port;
		this.threadMax = threadMax;
		InitConfiguration(service);
	}
	public TcpServer(IServiceLogic service, int port, int threadMax, boolean loop){
		this.port = port;
		this.threadMax = threadMax;
		this.serviceLoop = loop;
		InitConfiguration(service);
	}
	public TcpServer(IServiceLogic service, int port, int threadMax, boolean loop, int serverTimeout){
		this.port = port;
		this.threadMax = threadMax;
		this.serviceLoop = loop;
		this.timeout = serverTimeout;
		InitConfiguration(service);
	}
	
	
	private void InitConfiguration(IServiceLogic service){
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
		
		try{
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
		}finally{
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

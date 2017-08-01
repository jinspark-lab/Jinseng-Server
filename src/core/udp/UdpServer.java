package core.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import connection.ConnectlessUnit;
import core.IJinsengServer;

public class UdpServer implements IJinsengServer{

	private DatagramSocket socket;
	private int port = 12345;
	private boolean running=false;
	private IUdpServiceLogic serviceLogic;
	private int term = 1000;
	private boolean serviceLoop = true;
	private ExecutorService threadPool;
	private int threadMax = 12;
	
	public UdpServer(IUdpServiceLogic service){
		InitConfiguration(service);
	}
	public UdpServer(IUdpServiceLogic service, int port){
		this.port = port;
		InitConfiguration(service);
	}
	public UdpServer(IUdpServiceLogic service, int port, int threadMax){
		this.port = port;
		this.threadMax = threadMax;
		InitConfiguration(service);
	}
	private void InitConfiguration(IUdpServiceLogic service){
		try {
			serviceLogic = service;
			socket = new DatagramSocket(port);
			threadPool = Executors.newFixedThreadPool(this.threadMax);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void RunServer() throws IOException{
		running = true;
		System.out.println("Waiting for client access...");
		
		try{
			while(running){
				try {
					Object request = this.serviceLogic.ReceiveRequest(this.socket);
					
					//When receiving requests, pass it to handling thread.
					ConnectlessUnit unit = new ConnectlessUnit(this.serviceLogic, this.socket, request);
					threadPool.execute(unit);

				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}finally{
			EndServer();
		}
	}
	public void EndServer(){
		System.out.println("Server end");
		running = false;
		socket.close();
	}
}

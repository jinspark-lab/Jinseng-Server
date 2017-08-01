package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProxyServer {
	
	ServerSocket proxyServer = null;
	String remoteHost = "";
	int remotePort = 80;
	
	public ProxyServer(String host, int localPort, int remotePort){
		
		try {
			this.proxyServer = new ServerSocket(localPort);
			this.remoteHost = host;
			this.remotePort = remotePort;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void RunServer(){
		
		while(true){
			
			try {
				Socket endpoint = proxyServer.accept();
			
				final InputStream streamIn = endpoint.getInputStream();
				final OutputStream streamOut = endpoint.getOutputStream();
				
				Socket host = new Socket(remoteHost, remotePort);
				
				final InputStream hostIn = host.getInputStream();
				final OutputStream hostOut = host.getOutputStream();
				
				Thread th = new Thread(){
					public void run(){
						byte[] send = new byte[1024];
						int byteRead = 0;
						
						try {
							while((byteRead = streamIn.read(send)) != -1){
								hostOut.write(send, 0, byteRead);
								hostOut.flush();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							hostOut.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						byte[] recv = new byte[1024];
						byteRead = 0;
						try {
							while((byteRead = hostIn.read(recv)) != -1){
								streamOut.write(recv, 0, byteRead);
								streamOut.flush();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							System.out.println(new String(recv, "UTF-8"));
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							streamOut.close();
							host.close();
							endpoint.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				th.start();
				
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public void EndServer(){
		try {
			proxyServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

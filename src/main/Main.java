package main;


import core.tcp.*;
import sample.FileTransferService;
import sample.PrintService;
import sample.ChatRoomService;

public class Main {

	private static void LaunchPrintServer(){
		TcpServer server = new TcpServer(new PrintService());
		server.RunServer();
		server.EndServer();
	}
	
	private static void LaunchFileTransferServer(){
		TcpServer server = new TcpServer(new FileTransferService());
		server.RunServer();
		server.EndServer();
	}
	
	private static void LaunchChatRoomServer(){
		TcpServer server = new TcpServer(new ChatRoomService());
		server.RunServer();
		server.EndServer();
	}
	
	public static void main(String[] args){
		
		System.out.println("Hello World");
		
		LaunchChatRoomServer();
		
	}
	
}

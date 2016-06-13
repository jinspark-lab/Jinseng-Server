package main;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import common.LogReporter;
import common.TextUtil;
import core.http.HttpProtocolParser;
import core.http.HttpRequest;
import core.http.HttpResponse;
import core.http.HttpServer;
import core.tcp.*;
import sample.http.EchoWebService;
import sample.tcp.ChatRoomService;
import sample.tcp.FileTransferService;
import sample.tcp.PrintService;

public class Main {

	private static void LaunchPrintServer(){
		TcpServer server = new TcpServer(new PrintService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchFileTransferServer(){
		TcpServer server = new TcpServer(new FileTransferService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchChatRoomServer(){
		TcpServer server = new TcpServer(new ChatRoomService());
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchEchoWebServer(){
		HttpServer server = new HttpServer(new EchoWebService(), 60000);
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	public static void main(String[] args){
		
		System.out.println("Hello World");
		
//		LaunchChatRoomServer();
		
		LaunchEchoWebServer();
		
	}
	
}

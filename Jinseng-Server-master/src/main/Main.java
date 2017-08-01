package main;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import common.LogReporter;
import common.TextUtil;
import core.http.HttpMethod;
import core.http.HttpRequest;
import core.http.HttpServer;
import core.http.HttpServiceRouter;
import core.http.HttpUrl;
import core.tcp.*;
import core.udp.UdpServer;
import operation.ConfigManager;
import operation.ServerStatus;
import operation.ServiceLauncher;
import sample.http.EchoWebService;
import sample.http.RestWebService;
import sample.http.WebSiteService;
import sample.tcp.ChatRoomService;
import sample.tcp.FileTransferService;
import sample.tcp.PrintService;
import sample.udp.EchoService;


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

//		HttpServiceRouter route = new HttpServiceRouter();
//		route.setRoutingMethod(HttpUrl.ANYURL, new EchoWebService());
		
		HttpServer server = new HttpServer(new EchoWebService(), 60000);
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchRestWebServer(){

		HttpServiceRouter route = new HttpServiceRouter();
		route.setRoutingMethod("/students/{name}/{grade}/{age}", new RestWebService());
		HttpServer server = new HttpServer(route, 60000);
		
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchWebSite(){
		
		HttpServiceRouter route = new HttpServiceRouter();
		route.setRoutingMethod(HttpUrl.ANYURL, new WebSiteService());
		HttpServer server = new HttpServer(route, 60000);
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
	private static void LaunchUdpEchoServer(){
		UdpServer server = new UdpServer(new EchoService());
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
		
		/*** The way to start TCP Chatting server*/
//		LaunchChatRoomServer();
		
		/*** The way to run Echo web server*/
//		LaunchEchoWebServer();
		
		/*** The way to run Http Rest server*/
//		LaunchRestWebServer();

		/*** The way to check remote server status */

//		LaunchWebSite();

		/*** The way to check remote server status */
		
//		System.out.println(ServerStatus.getStatus());


		/*** The way to run server using configurations */
		
//		ConfigManager man = new ConfigManager();
//		man.loadFromFile("echo-server.xml");
//		
//		ServiceLauncher launcher = new ServiceLauncher();
//		launcher.loadService(man.getConfigInfo("echo"));
//		launcher.runService();

		/*** The way to use Udp Echo server */
		
		LaunchUdpEchoServer();

	}
	
}

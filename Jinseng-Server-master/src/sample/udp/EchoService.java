package sample.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import core.udp.IUdpServiceLogic;

public class EchoService implements IUdpServiceLogic{

//	DatagramPacket packet = new DatagramPacket(buf, buf.length);
//	socket.receive(packet);
//	
//	InetAddress address = packet.getAddress();
//	int port = packet.getPort();
//	String received = new String(packet.getData());
//	
//	System.out.println("Server received : " + received);
//	
//	String message = received;
//	byte[] sendbuf = new byte[256];
//	sendbuf = message.getBytes();
//	DatagramPacket sendPacket = new DatagramPacket(sendbuf, sendbuf.length, address, port);
//	
//	System.out.println("Server sent : " + message);
//	
//	socket.send(sendPacket);
//	Thread.sleep(term);
	
	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	
	@Override
	public Object ReceiveRequest(DatagramSocket socket) {

		try {
			this.socket = socket;
			
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			this.socket.receive(packet);
			
			address = packet.getAddress();
			port = packet.getPort();
			String received = new String(packet.getData());
			
			System.out.println("Server received : " + received);
			
			return new String(packet.getData());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object SendResponse(Object request) {
		// TODO Auto-generated method stub

		byte[] buf = new byte[1024];
		String message = (String)request;
		buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		System.out.println("Server sent : " + message);
		
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}


package Multiplayer;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Library to send objects, read objects, setup connections, setup listeners using UDP, TCP
 * @author Wesley
 *
 */
public class SocketManager{
	private static DatagramSocket dSock;
	private static Hashtable<InetAddress,TCPPacketListenerThread> tcpConnections = new Hashtable<InetAddress,TCPPacketListenerThread>();
	public static void startUDPSender() throws SocketException{
		dSock = new DatagramSocket();
	}
	//There are two types of listeners
	//TCP Connection listeners and TCP Packet Listeners
	public static TCPConnectionListenerThread startTCPConnectionListener(SocketHandler oh,int port) throws IOException{
		TCPConnectionListenerThread t = new TCPConnectionListenerThread(oh,port);
		t.start();
		return t;
	}
	public static TCPPacketListenerThread startTCPPacketListener(SocketHandler oh,String ip,int port) throws IOException{
		TCPPacketListenerThread t = new TCPPacketListenerThread(oh,ip,port);
		t.start();
		return t;
	}
	
	public static UDPListenerThread startUDPListener(SocketHandler oh,int port) throws SocketException{
		//Start a new thread
		UDPListenerThread t = new UDPListenerThread(oh,port);
		t.start();
		return t;
	}
	public static void changeTCPHandlers(SocketHandler oh){
		Enumeration<TCPPacketListenerThread> iter = tcpConnections.elements();
		while(iter.hasMoreElements()){
			TCPPacketListenerThread temp = iter.nextElement();
			temp.changeHandler(oh);
		}
	}
	public static void addTCPConnection(InetAddress ip,TCPPacketListenerThread t){
		synchronized(tcpConnections){
			if(tcpConnections.containsKey(ip)){
				System.err.println("Error in NetworkManager: Attempted to overwrite TCPConnection "+ip.getHostAddress());
				return;
			}
			tcpConnections.put(ip, t);
		}
	}
	public static void closeTCPConnection(InetAddress ip){
		synchronized(tcpConnections){
			if(!tcpConnections.containsKey(ip)){
				System.err.println("Error in NetworkManager: Attempted to remove nonexisting TCPConnection "+ip.getHostAddress());
				return;
			}
			tcpConnections.remove(ip);
		}
	}
	public static void sendTCPPacket(InetAddress ip,Object o) throws IOException{
		synchronized(tcpConnections){
			//First check if the output stream exists
			if(tcpConnections.containsKey(ip)){
				tcpConnections.get(ip).sendObject(o);
			}
			else{
				System.err.println("Error in NetworkManager: Attempted to send packet through nonexisting TCPConnection "+ip.getHostAddress());
				return;
			}
		}
	}
	
	public static void sendUDPPacket(InetAddress ip,int port,Object o) throws IOException{
		ByteArrayOutputStream byteStream = new
		ByteArrayOutputStream(5000);
		ObjectOutputStream os = new ObjectOutputStream(new
                              BufferedOutputStream(byteStream));
		os.flush();
		os.writeObject(o);
		os.flush();
	    //retrieves byte array
		byte[] sendBuf = byteStream.toByteArray();
		DatagramPacket packet = new DatagramPacket(
	                          sendBuf, sendBuf.length, ip, port);
      	dSock.send(packet);
      	os.close();
	}
}


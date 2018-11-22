package focusObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import Multiplayer.ClientPacket;
import Multiplayer.SocketManager;
import Multiplayer.TCPPacketListenerThread;


/**
 * This class will manage the threads and network migrate and state
 * This class will handle game state migration by accessing the network
 * threads and overwriting their handler
 * @author Wesley
 *
 */
public class TreeUIMultiplayer{
	private static boolean server = false;//Client mode assumed
	private static TreeUIManager tuim;
	private static int connectionInc = 0;
	private static Hashtable<Integer,InetAddress> connectionMap = new Hashtable<Integer,InetAddress>();
	private static ArrayList<Thread> threads = new ArrayList<Thread>();//Needs to keep all network threads
	public static Hashtable<Integer, InetAddress> getConnectionMap(){
		return connectionMap;
	}
	public static int newConnection(InetAddress connection){
		connectionInc++;
		connectionMap.put(connectionInc, connection);
		return connectionInc;
	}
	public static boolean isServer(){
		return server;
	}
	public static void startServer(TreeUIManager t,int port) throws IOException{
		if(server == false){
			//handle client to server transition
			handleClientToServer();
		}
		System.out.println("Starting Server");
		//Start server threads
		server = true;
		tuim = t;
		
		//Start the UDP Listener
		Thread udpl = SocketManager.startUDPListener(t,port);
		threads.add(udpl);
		
		//Start the TCP Listener
		Thread tcpl = SocketManager.startTCPConnectionListener(t,port);
		threads.add(tcpl);
	}
	public static void startClient(TreeUIManager t,String ip,int port) throws IOException{
		if(server == true){
			//handle server to client transition
			handleServerToClient();
		}
		System.out.println("Connecting to "+ip+":"+port+" ...");
		//Start threads for client
		server = false;
		tuim = t;
		
		//Start the UDP Listener
		Thread udpl = SocketManager.startUDPListener(t,port);
		threads.add(udpl);
		
		//Start the TCP Listener
		TCPPacketListenerThread tcpl = SocketManager.startTCPPacketListener(t,ip,port);
		threads.add(tcpl);
		
		InetAddress inet = tcpl.getAddress();
		SocketManager.sendTCPPacket(inet, new ClientPacket("NEW",inet));
		System.out.println("Connection Established");
	}
	public static void sendTCPPacket(InetAddress target,Object send){
		try {
			SocketManager.sendTCPPacket(target, send);
		} catch (IOException e) {
			System.err.println("Failed to send TCP Packet");
			e.printStackTrace();
		}
	}
	public static void sendUDPPacket(InetAddress target,int port,Object send){
		try {
			SocketManager.sendUDPPacket(target,port, send);
		} catch (IOException e) {
			System.err.println("Failed to send TCP Packet");
			e.printStackTrace();
		}
	}
	private static void handleClientToServer(){
		
	}
	private static void handleServerToClient(){
		
	}
	public static void changeGameState(TreeUIManager t){
		tuim = t;
		//Overwrite the handlers for the TCP Packet Threads
		SocketManager.changeTCPHandlers(t);
		
		
	}
}
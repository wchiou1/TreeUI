package focusObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

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
	private static ArrayList<Thread> threads = new ArrayList<Thread>();//Needs to keep all network threads
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
		System.out.println("Connecting to "+ip+":"+port);
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
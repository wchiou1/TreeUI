package Multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConnectionListenerThread extends Thread{
	private ServerSocket ss;
	private SocketHandler oh;
	private int port;
	byte[] receive = new byte[65535]; 
	public TCPConnectionListenerThread(SocketHandler oh,int port) throws IOException{
		//Create the listener
		this.ss = new ServerSocket(port); 
		this.oh = oh;
		this.port = port;
	}
	public void changeHandler(SocketHandler oh){
		this.oh = oh;
	}
	
	public void run(){
		try {
			while(true){
				Socket clientSocket = ss.accept();
			
			
				//Start a new thread using the SocketHandler as the handler
				
				Thread t = new TCPPacketListenerThread(oh,clientSocket);
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
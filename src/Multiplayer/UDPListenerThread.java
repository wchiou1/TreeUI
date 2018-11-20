package Multiplayer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPListenerThread extends Thread{
	private DatagramSocket ds;
	private SocketHandler oh;
	private DatagramPacket DpReceive = null; 
	byte[] receive = new byte[65535]; 
	public UDPListenerThread(SocketHandler oh,int port) throws SocketException{
		//Create the listener
		this.ds = new DatagramSocket(port); 
		this.oh = oh;
		
	}
	public void changeHandler(SocketHandler oh){
		this.oh = oh;
	}
	
	public void run(){
		
		try {
			while(true){
				// Step 2 : create a DatgramPacket to receive the data. 
				
	            DpReceive = new DatagramPacket(receive, receive.length); 
	  
	            // Step 3 : revieve the data in byte buffer. 
	            ds.receive(DpReceive); 
	            
	            InetAddress ip = DpReceive.getAddress();
	            
	            ByteArrayInputStream byteStream = new
	                                        ByteArrayInputStream(receive);
	            ObjectInputStream is = new
	                 ObjectInputStream(new BufferedInputStream(byteStream));
	            Object o = is.readObject();
	            is.close();
	            synchronized(oh){
	            	oh.handleObject(ip,o);
	            }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
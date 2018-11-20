package Multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPPacketListenerThread extends Thread{
	private Socket s;
	private SocketHandler oh;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private InetAddress address;
	public TCPPacketListenerThread(SocketHandler oh,String ip,int port) throws IOException{
		this(oh,new Socket(ip,port));
	}
	public TCPPacketListenerThread(SocketHandler oh,Socket s) throws IOException{
		this.oh = oh;
		this.s = s;
		out=new ObjectOutputStream(s.getOutputStream());
		in=new ObjectInputStream(s.getInputStream());
		address = s.getInetAddress();
		SocketManager.addTCPConnection(address, this);
	}
	public void changeHandler(SocketHandler oh){
		this.oh = oh;
	}
	public InetAddress getAddress(){
		return s.getInetAddress();
	}
	public ObjectOutputStream getOutputStream(){
		return out;
	}
	public void sendObject(Object o) throws IOException{
		out.writeObject(o);
		out.flush();
	}
	public void run(){
		try {
			while(true){
				Object readObj = in.readObject();
				synchronized(oh){
					oh.handleObject(address, readObj);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}

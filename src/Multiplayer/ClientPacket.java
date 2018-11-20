package Multiplayer;

import java.io.Serializable;

public class ClientPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object packet;
	public String type;
	public ClientPacket(String type,Object packet){
		this.packet = packet;
		this.type = type;
	}
	public ClientPacket(String type){
		this.type = type;
	}
}
package Multiplayer;

import java.io.Serializable;

public class ServerPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object packet;
	public String type;
	public ServerPacket(String type,Object packet){
		this.packet = packet;
		this.type = type;
	}
	public ServerPacket(String type){
		this.type = type;
	}
}
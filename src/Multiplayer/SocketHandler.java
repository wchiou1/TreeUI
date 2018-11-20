package Multiplayer;

import java.net.InetAddress;

public interface SocketHandler{
	/**
	 * Handles the object received from either a TCP or UDP connection
	 * @param readObj
	 */
	public void handleObject(InetAddress address,Object readObj);
}
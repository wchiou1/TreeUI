package focusObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import Multiplayer.ClientPacket;
import Multiplayer.SocketHandler;
import Multiplayer.SocketManager;
import Multiplayer.TCPPacketListenerThread;


/**
 * This class will manage the threads and network migrate and state
 * This class will handle game state migration by changing a static object
 * which is refered to when handling packets
 * @author Wesley
 *
 */
public class TreeUIMultiplayer implements SocketHandler{
	private static TreeUIMultiplayer handler = new TreeUIMultiplayer();
	private static boolean server = false;//Client mode assumed
	private static TreeUIManager tuim;
	private static int connectionInc = 0;
	private static int storedPort;
	private static Hashtable<Integer,InetAddress> connectionMap = new Hashtable<Integer,InetAddress>();
	private static ArrayList<Thread> threads = new ArrayList<Thread>();//Needs to keep all network threads
	private TreeUIMultiplayer(){};
	/**
	 * Gets the current list of TCP connections
	 * @return
	 */
	public static Hashtable<Integer, InetAddress> getConnectionMap(){
		return connectionMap;
	}
	/**
	 * Adds a new connection to 
	 * @param connection
	 * @return
	 */
	public static int newConnection(InetAddress connection){
		connectionInc++;
		connectionMap.put(connectionInc, connection);
		return connectionInc;
	}
	public static boolean isServer(){
		return server;
	}
	public static void setTuim(TreeUIManager new_tuim){
		tuim=new_tuim;
	}
	public static void startServer(TreeUIManager t,int port) throws IOException{
		if(server == false){
			//handle client to server transition
			handleClientToServer();
		}
		System.out.println("Starting Server");
		//Start server threads
		server = true;
		setTuim(t);
		storedPort = port;
		
		//Start the UDP Listener
		Thread udpl = SocketManager.startUDPListener(TreeUIMultiplayer.handler,port);
		threads.add(udpl);
		
		//Start the UDP Sender
		SocketManager.startUDPSender();
		
		//Start the TCP Listener
		Thread tcpl = SocketManager.startTCPConnectionListener(TreeUIMultiplayer.handler,port);
		threads.add(tcpl);
		System.out.println("Server Initialized");
	}
	public static void startClient(TreeUIManager t,String ip,int port) throws IOException{
		if(server == true){
			//handle server to client transition
			handleServerToClient();
		}
		System.out.println("Connecting to "+ip+":"+port+" ...");
		//Start threads for client
		server = false;
		setTuim(t);
		storedPort = port;
		
		//Start the UDP Listener
		Thread udpl = SocketManager.startUDPListener(TreeUIMultiplayer.handler,port);
		threads.add(udpl);
		
		//Start the UDP Sender
		SocketManager.startUDPSender();
		
		//Start the TCP Listener
		TCPPacketListenerThread tcpl = SocketManager.startTCPPacketListener(TreeUIMultiplayer.handler,ip,port);
		threads.add(tcpl);
		
		InetAddress inet = tcpl.getAddress();
		SocketManager.sendTCPPacket(inet, new ClientPacket("NEW",inet));
		System.out.println("Connection Established");
	}
	public static void sendTCPPacket(InetAddress target,Object send){
		try {
			SocketManager.sendTCPPacket(target, send);//Port is not needed because the connection is stored, inet is used as key
		} catch (IOException e) {
			System.err.println("Failed to send TCP Packet");
			e.printStackTrace();
		}
	}
	public static void sendUDPPacket(InetAddress target,Object send){
		try {
			SocketManager.sendUDPPacket(target,storedPort, send);
		} catch (IOException e) {
			System.err.println("Failed to send TCP Packet");
			e.printStackTrace();
		}
	}
	/**
	 * Uses the active tcp connections and sends a udp packet to all of them
	 * @param send
	 */
	public static void sendUDPPacketAll(Object send){
		//Iterate through all active connections
		Hashtable<Integer,InetAddress> connections = TreeUIMultiplayer.getConnectionMap();
		connections.forEach(
			(key,connection) -> {
				//Use the connection and send a packet
				sendUDPPacket(connection,send);
			}
		);
	}
	private static void handleClientToServer(){
		System.out.println("Transitioning from client to server");
	}
	private static void handleServerToClient(){
		System.out.println("Transitioning from server to client");
	}
	public void handleObject(InetAddress address, Object readObj) {
		try {
			//All traffic goes through this function
			synchronized(this){
				
				if(TreeUIMultiplayer.isServer()){
					TreeUIMultiplayer.tuim.handleClientPacket(address,readObj);
				}
				else{
					TreeUIMultiplayer.tuim.handleServerPacket(address,readObj);
					
				}
				
				
			}
		} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Hashtable<String,String> getSerializedObject(InteractableObject io) throws IllegalArgumentException, IllegalAccessException{
		//Global logic for packets goes here(panel synchronization)
		Hashtable<String,String> serializedObj = new Hashtable<String,String>();
		
		//Use reflection to generate a serialization object
		Field[] fields = tuim.getIncubator().getFields(io.getId());
		
		//Get a list of all params
		for(Field f:fields){
			//Put them in the hashtable
			if(InteractableObject.class.isAssignableFrom(f.getType())){
				InteractableObject temp = ((InteractableObject)f.get(io));
				String objId = "-1";
				if(temp!=null){
					objId = ""+temp.getId();
				}
				serializedObj.put(f.getName(),objId);
			}
			else if(ArrayList.class.isAssignableFrom(f.getType())){
				//Handle arraylists, for now, just support interactable objects
				ParameterizedType arrayListType = (ParameterizedType) f.getGenericType();
		        Class<?> arrayListClass = (Class<?>) arrayListType.getActualTypeArguments()[0];
		        
		        //If it's an arraylist of interactable objects
		        if(InteractableObject.class.isAssignableFrom(arrayListClass)){
		        	//convert this arraylist to a comma delimited string
		        	String list = "";
		        	ArrayList<InteractableObject> temp = (ArrayList<InteractableObject>) f.get(io);
		        	for(InteractableObject aio:temp){
		        		list += aio.getId()+",";
		        	}
		        	if(list.length()>0){
		        		list = list.substring(0, list.length()-1);
		        	}
		        	serializedObj.put(f.getName(),list);
		        }
			}
			else{
				serializedObj.put(f.getName(),f.get(io).toString());
			}
		}
		
		serializedObj.put("type", io.getClass().getName());
		System.out.println(serializedObj);
		
		//Hopefully hashtables are serializable
		return serializedObj;
		
	}
	/**
	 * Uses the currently set tree ui manager to overwrite the variables from a serialized object
	 * @param sobj
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 */
	public static void setSerializedObject(int id,Hashtable<String,String> sobj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//Global logic for packets goes here(panel synchronization)
		//TODO Only create new panels if the type is panel, ignore if the panel is already made
		
		Incubator inc = tuim.getIncubator();
		synchronized(inc){
			//Check if the object exists in the incubator
			InteractableObject io = inc.getEither(id);
			//Get the class object
			Class<?> sobj_type = Class.forName(sobj.get("type"));
			if(io!=null){
				
				//If it exists, check if it's a panel, ignore panels
				//TODO move this to tcp
				if(Panel.class.isAssignableFrom(sobj_type)){
					//return;
				}		
				
			}
			else{
				//Create the object
				
				//If it exists, check if it's a panel, ignore panels
				if(Panel.class.isAssignableFrom(sobj_type)){
					//If it's a panel, overwrite the open value and position
					//sobj.remove("open");
					//sobj.remove("rx");
					//sobj.remove("ry");				
				}
			}
			inc.updateObjectFromSerial(id,sobj);
		}
	}
	
}


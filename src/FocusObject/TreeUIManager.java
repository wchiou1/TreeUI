package focusObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Editor.EditorImmune;
import Editor.Tree.Bud;
import Editor.Tree.BudPanel;
import Editor.Tree.Sapling;
import Editor.Tree.SaplingPanel;
import Multiplayer.ClientPacket;
import Multiplayer.ServerPacket;
import Multiplayer.SocketManager;
import TreeUI.InventoryPanel;
import aspenNetwork.AspenNetwork;
import gameObjects.GameObject;
import smallGameObjects.SmallGameObject;

public class TreeUIManager{
	public static TreeUIManager master;
	private int stickiness;//How far the mouse delta has to be before a suggested snap is abandoned
	private AspenNetwork an;
	private InputManager inputManager;
	private InventoryManager inventoryManager;
	private LinkedList<InteractableObject> uiObjectList;
	private LinkedList<InteractableObject> gameObjectList;
	private Input input;
	private Incubator inc;
	private boolean editor = false;
	private int clientID = -1;
	//Placeholder object to indicate that lock is on nothing
	static InteractableObject empty=new Window(0,0,0,0,Color.green);
	public TreeUIManager(GameContainer container,ArrayList<Integer> keys, AspenNetwork an, int stickiness){
		this.stickiness=stickiness;
		this.input=container.getInput();
		this.an=an;
		uiObjectList = new LinkedList<InteractableObject>();
		gameObjectList = new LinkedList<InteractableObject>();
		inventoryManager = new InventoryManager(new InventoryPanel());
		this.inc = new Incubator(this,an);
		inputManager = new InputManager(input,this,inventoryManager,gameObjectList,uiObjectList,stickiness,keys);
		master=this;
	}
	/**
	 * Adds a prebuilt object to the incubator so it can be tracked for saving
	 * @param io
	 * @return
	 */
	public InteractableObject addObjectToIncubator(InteractableObject io){
		return io;
	}
	public UIElement createUIElement(Class<?> type,Panel panel, int rx, int ry){
		UIElement product = (UIElement) inc.getObject(inc.addUIElement(panel.getId(),type));
		product.rx=rx;
		product.ry=ry;
		return (UIElement) product;
	}
	public GameObject createGameObject(Class<?> type,int x, int y){
		InteractableObject product = inc.getObject(inc.addObject(type));
		product.x=x;
		product.y=y;
		return (GameObject) product;
	}
	/**
	 * Creates a gameobject which does NOT exist in the gameworld
	 * This is to create an object to put inside another object
	 * without having a copy generated in the gameworld
	 * @param type
	 * @param x
	 * @param y
	 * @return
	 */
	public GameObject createLooseGameObject(Class<?> type,int x, int y){
		InteractableObject product = inc.getObject(inc.addObject(type));
		product.x=x;
		product.y=y;
		gameObjectList.removeFirstOccurrence(product);
		return (GameObject) product;
	}
	public BudPanel createBPanel(Bud bud){
		BudPanel temp = new BudPanel(bud);
		addObject(temp);
		temp.setOrigin(bud);
		return temp;
	}
	public SaplingPanel createSPanel(Sapling sap){
		SaplingPanel temp = new SaplingPanel(sap);
		addObject(temp);
		temp.setOrigin(sap);
		return temp;
	}
	public Bud createBud(Panel panel,int x, int y){
		Bud bud = new Bud(x,y,this,panel);
		addObject(bud);
		return bud;
	}
	public Panel createPanel(OriginObject oo){
		return createPanel(oo,100,100);
	}
	public Panel createPanel(OriginObject oo,int height,int width){
		Panel panel = inc.getPanel(inc.addPanel());
		panel.height = height;
		panel.width = width;
		if(editor)
			panel.enableEditing(inc);
		panel.setOrigin(oo);
		return panel;
	}
	public Incubator getIncubator(){
		return inc;
	}
	public AspenNetwork getAspen(){
		return an;
	}
	public LinkedList<InteractableObject> getGameObjects(){
		return gameObjectList;
	}
	public void keyPressed(int key, char c) {
		inputManager.keyPressed(key, c);
		//Aspen Network debug
		if(key==57){
			update(20);
			an.manualUpdate();
		}
	}
	public void mouseWheelMoved(int arg0){
		inputManager.mouseWheelMoved(arg0);
	}
	public void addItemtoInv(SmallGameObject item){
		inventoryManager.addItem(item);
	}
	void enableEditor(){
		System.out.println("Editor enabled");
		editor = true;
		inc.enableEditor();
		inputManager.enableEditor();
		inputManager.enableInventory();
		addObject(inventoryManager.getPanel());
	}
	public void update(int delta){
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		inputManager.update(delta);
		for(InteractableObject io:uiObjectList){
			io.update(mouseX, mouseY,delta);
		}
		for(InteractableObject io:gameObjectList){
			io.update(mouseX, mouseY,delta);
		}
		
		if(TreeUIMultiplayer.isServer()){
			distributeObjectPackets();
		}
	}
	public void distributeObjectPackets(){
		try {
			Hashtable<Integer,Hashtable<String,String>> distributionArray = new Hashtable<Integer,Hashtable<String,String>>();
			//We want to iterate through all objects and send a packet to all connected users
			for(InteractableObject io:uiObjectList){
				//test if it's an editor object?
				if(io instanceof EditorImmune){
					continue;
				}
				distributionArray.put(io.getId(),TreeUIMultiplayer.getSerializedObject(io));
				
			}
			
			for(InteractableObject io:gameObjectList){
				if(io instanceof EditorImmune){
					continue;
				}
				distributionArray.put(io.getId(),TreeUIMultiplayer.getSerializedObject(io));
			}
			
			//Send it
			TreeUIMultiplayer.sendUDPPacketAll(new ServerPacket("SYNC",distributionArray));
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void removeObject(InteractableObject io){
		if(io==null){
			System.out.println("Null object, error");
		}
		if(!uiObjectList.remove(io)&&!gameObjectList.remove(io))
			System.out.println("Object not found, error");
	}
	public void addObject(InteractableObject io){
		if(io==null){
			System.out.println("Null object, error");
		}
		uiObjectList.addFirst(io);
	}
	void addGameObject(InteractableObject io){
		gameObjectList.addFirst(io);
	}
	/**
	 * Checks if there is a snap and then draws the panel in the suggested snap location
	 * This method SUGGESTS a snap location it does NOT move the panel to the snap location
	 * MouseManager handles actually moving the panel to the snap location
	 */
	private void drawPanelSnap(Graphics g,InteractableObject lock){
		//TODO change to support Snappable objects and not just panels
		if(!(lock instanceof Panel))
			return;
		Panel temp = (Panel)lock;
		int previousX=temp.x;
		int previousY=temp.y;
		
		//Find all active panels
		ArrayList<Panel> activePanels = new ArrayList<Panel>();
		for(InteractableObject io:uiObjectList)
			if(io instanceof Panel)
				if(((Panel)io).isActive())
					activePanels.add((Panel)io);
		
		//declare snap location(defaults to current panel location)
		int snapX=temp.x;
		int snapY=temp.y;
		
		for(Panel remote:activePanels){
			//Check horizontal compatibility
			if(!(remote.x>=temp.x+temp.getWidth()||remote.x+remote.getWidth()<=temp.x)){
				//Check top with remote bottom
				boolean stuck=false;
				if(stickiness>Math.abs(temp.y-(remote.y+remote.getHeight()))){
					snapY=remote.y+remote.getHeight();
					stuck=true;
				}
				
				//Check bottom with remote top
				if(stickiness>Math.abs(temp.y+temp.getHeight()-remote.y)){
					snapY=remote.y-temp.getHeight();
					stuck=true;
				}
				//If it's snapped to another panel, test for same side
				if(stuck){
					//Check left and remote left
					if(stickiness>Math.abs(temp.x-remote.x))
						snapX=remote.x;
					
					//Check right and remote right
					if(stickiness>Math.abs(temp.x+temp.getWidth()-(remote.x+remote.getWidth())))
						snapX=remote.x+remote.getWidth()-temp.getWidth();
				}
			}
			if(!(remote.y>temp.y+temp.getHeight()||remote.y+remote.getHeight()<temp.y)){
				boolean stuck=false;
				//Check right with remote left
				if(stickiness>Math.abs(temp.x+temp.getWidth()-remote.x)){
					snapX=remote.x-temp.getWidth();
					stuck=true;
				}
				
				//Check left with remote right
				if(stickiness>Math.abs(temp.x-(remote.x+remote.getWidth()))){
					snapX=remote.x+remote.getWidth();
					stuck=true;
				}
				//if there's a snap, check for same sides
				if(stuck){
					//Check top and remote top
					if(stickiness>Math.abs(temp.y-remote.y))
						snapY=remote.y;
					//check bottom and remote bottom
					if(stickiness>Math.abs(temp.y+temp.getHeight()-(remote.y+remote.getHeight())))
						snapY=remote.y+remote.getHeight()-temp.getHeight();
					
				}
			}
		}

		temp.move(snapX, snapY);
		temp.draw(g);
		temp.move(previousX, previousY);
	}
	public void moveToFront(InteractableObject io){
		uiObjectList.remove(io);
		uiObjectList.addFirst(io);
	}
	public void draw(Graphics g) {
		//Always draw the gameobjects FIRST
		Iterator<InteractableObject> it=gameObjectList.descendingIterator();
		while(it.hasNext()){
			InteractableObject temp=it.next();
			temp.draw(g);
		}
		
		//Draw all ui objects
		it=uiObjectList.descendingIterator();
		while(it.hasNext()){
			InteractableObject temp=it.next();
			if(!temp.show)
				continue;
			//Check if the object is locked by the mouseManager
			if(temp.equals(inputManager.llock)&&inputManager.llock instanceof Snappable)
				drawPanelSnap(g,temp);
			else
				temp.draw(g);
			
				
		}
		
		//Have the mouseManager draw the "held" item last along with it's overlay
		inputManager.draw(g);
		
		
		
	}
	
	void handleClientPacket(InetAddress source,Object readObj){
		ClientPacket temp = (ClientPacket)readObj;
		String type = temp.type;
		System.out.println("Received "+type+" Client Packet");
		try {
			if(type.equals("NEW")){
				int id = TreeUIMultiplayer.newConnection(source);
				
				SocketManager.sendTCPPacket(source, new ServerPacket("INIT",id));//Send the id back
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void handleServerPacket(InetAddress source,Object readObj){
		//Gamestate dependent logic goes here("lobby verses playground")
		ServerPacket temp = (ServerPacket)readObj;
		String type = temp.type;
		System.out.println("Received "+temp.type+" Server Packet");
		switch(type){
		case "INIT":{
			int id = (int) temp.packet;
			//This is the client's identifier
			clientID = id;
			System.out.println("Got client id of "+id);
			break;
		}
		case "SYNC":{
			//The sync packet should have an id
			Hashtable<Integer,Hashtable<String,String>> distro_data = (Hashtable<Integer,Hashtable<String,String>>)temp.packet;
			Enumeration<Integer> keys = distro_data.keys();
			while(keys.hasMoreElements()){
				int id = keys.nextElement();
				System.out.println("Object ID="+id);
				TreeUIMultiplayer.setSerializedObject(id, distro_data.get(id));
			}
			
			//Check if the id already exists
			//Verify the class type
			//Modify variables using the setSerializedObject from tuimultiplayer
			//What about panels? Panels will be clientside, but they still need to be created...
			//Panels will be created and attached via set ids, Tuim should not worry about panels, just call setSerialize
			
			
			break;
		}
		default:{
			System.out.println("Unhandled server packet of type "+type);
		}
		}
		
	}
}
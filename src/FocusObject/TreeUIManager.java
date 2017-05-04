package FocusObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import TreeUI.InventoryPanel;
import TreeUI.Snappable;

public class TreeUIManager{
	private int stickiness;//How far the mouse delta has to be before a suggested snap is abandoned
	private KeyboardManager keyManager;
	private MouseManager mouseManager;
	private InventoryManager inventoryManager;
	private GameObjectManager gameObjectManager;
	private LinkedList<InteractableObject> uiObjectList;
	private LinkedList<InteractableObject> gameObjectList;
	private Input input;
	private InventoryPanel invPan;
	
	//Placeholder object to indicate that lock is on nothing
	static InteractableObject empty=new Window(0,0,0,0,Color.green);
	public TreeUIManager(Input input,ArrayList<Integer> keys, int stickiness){
		this(input,keys,stickiness,true);
	}
	public TreeUIManager(Input input,ArrayList<Integer> keys, int stickiness,boolean inventory){
		this.stickiness=stickiness;
		this.input=input;
		uiObjectList = new LinkedList<InteractableObject>();
		gameObjectList = new LinkedList<InteractableObject>();
		gameObjectManager = new GameObjectManager();
		keyManager = new KeyboardManager(input,uiObjectList,keys);
		mouseManager = new MouseManager(input,gameObjectList,uiObjectList,stickiness);
		if(inventory){
			invPan=new InventoryPanel(3);
			uiObjectList.add(invPan);
			inventoryManager = new InventoryManager(invPan,4);
		}
			
	}
	public void update(){
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		mouseManager.update();
		keyManager.update(mouseManager.getMouseOn());
		gameObjectManager.update();
		for(InteractableObject io:uiObjectList){
			io.update(mouseX, mouseY);
		}
	}
	public void addObject(InteractableObject io){
		uiObjectList.add(io);
	}
	public void addGameObject(InteractableObject io){
		gameObjectList.add(io);
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
			//Check if the object is locked by the mouseManager
			if(temp.equals(mouseManager.lock)&&mouseManager.lock instanceof Snappable)
				drawPanelSnap(g,temp);
			else
				temp.draw(g);
		}
		//Have the mouseManager draw the "held" item last
		mouseManager.draw(g);
		
		
		
	}
}
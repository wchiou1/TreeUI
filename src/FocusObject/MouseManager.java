package FocusObject;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import TreeUI.UIItem;

//This class will contain all interactable objects and will take in mouse input
//This is to allow for an object to "lock" on to the mouse


public class MouseManager{
	private int previousX,previousY;
	private UIItem held;
	LinkedList<InteractableObject> uiObjectList;
	LinkedList<InteractableObject> gameObjectList;
	Input input;
	InteractableObject lock;
	int stickiness;
	protected MouseManager(Input input,LinkedList<InteractableObject>gameObjectList,LinkedList<InteractableObject> uiObjectList,int stickiness){
		this.input=input;
		this.uiObjectList=uiObjectList;
		this.gameObjectList=gameObjectList;
		this.stickiness=stickiness;
		previousX=0;
		previousY=0;
		held=null;
	}
	/**
	 * 
	 * @return the InteractableObject the mouse is currently on
	 */
	protected InteractableObject getMouseOn(){
		int mouseX=input.getMouseX();
		int mouseY=input.getMouseY();
		InteractableObject temp=null;
		for(InteractableObject io:uiObjectList){
			if(io.isMouseOver(mouseX,mouseY)){
				temp=io;
				if(io instanceof Panel){
					temp=((Panel) io).getObject(mouseX, mouseY);
					if(temp==null)
						temp=io;
				}
				return temp;
			}
		}
		for(InteractableObject io:gameObjectList){
			if(io.isMouseOver(mouseX,mouseY)){
				temp=io;
				if(io instanceof Panel){
					temp=((Panel) io).getObject(mouseX, mouseY);
					if(temp==null)
						temp=io;
				}
				break;
			}
		}
		return temp;
	
		
	}
	/**
	 * If there is a valid snap, then it moves the locked object to the snap location
	 * This method is run when the mouse is released
	 */
	private void checkSnap(){
		//TODO Change to support Snappable objects and not just Panels
		if(!(lock instanceof Panel))
			return;
		Panel temp = (Panel)lock;
		
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
			if(!(remote.x>temp.x+temp.getWidth()||remote.x+remote.getWidth()<temp.x)){
				boolean stuck=false;
				//Check top with remote bottom
				if(stickiness>Math.abs(temp.y-(remote.y+remote.getHeight()))){
					snapY=remote.y+remote.getHeight();
					stuck=true;
				}
				
				//Check bottom with remote top
				if(stickiness>Math.abs(temp.y+temp.getHeight()-remote.y)){
					snapY=remote.y-temp.getHeight();
					stuck=true;
				}
				//If there's a snap, check for same sides
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
	}
	
	void moveToFront(InteractableObject io){
		uiObjectList.remove(io);
		uiObjectList.addFirst(io);
	}
	
	protected void update(){
		int mouseX=input.getMouseX();
		int mouseY=input.getMouseY();
		if(input.isMouseButtonDown(0)){//If the mouse is down, check if it needs to lock an object
			if(lock==null){//There's no current lock yet, get a lock
				for(InteractableObject io:uiObjectList){//Iterate through all objects
					if(io.hover=io.isMouseOver(mouseX,mouseY)){
						InteractableObject temp=io;
						if(io instanceof Panel){
							temp=((Panel) io).getObject(mouseX, mouseY);
							if(temp==null)
								temp=io;
						}
						if(temp instanceof OriginObject)
							moveToFront(((OriginObject)temp).getView());
						held=temp.click(mouseX, mouseY,held);
						if(temp.isMoveable()){
							moveToFront(io);
						}
						System.out.println("Mouse locked on object "+temp.toString());
						lock=temp;
						break;
					}
				}
				//If it's not on an object, make it the "empty" object
				if(lock==null)
					lock=TreeUIManager.empty;
			}
			else{
				//Move the object if it is set to movable
				if(lock.isMoveable()){
					//If it is a panel, then let's do some snapping!
					lock.dmove(mouseX-previousX, mouseY-previousY);
				}
			}
			lock.locked=true;
		}
		else
		{
			if(lock!=null){
				checkSnap();
				lock.locked=false;
			}
			lock=null;
		}
		
		previousX=mouseX;
		previousY=mouseY;
	}
	/**
	 * Draws the held item
	 * @param g
	 */
	public void draw(Graphics g){
		if(held!=null)
			held.draw(g,input.getMouseX(),input.getMouseY());
	}
}
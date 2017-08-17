package FocusObject;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Editor.EditorBasePanel;
import Editor.EditorFunctional;
import TreeUI.UIItem;

//This class will contain all interactable objects and will take in mouse input
//This is to allow for an object to "lock" on to the mouse


public class MouseManager{
	private int previousX,previousY;
	private UIItem held;
	LinkedList<InteractableObject> uiObjectList;
	LinkedList<InteractableObject> gameObjectList;
	Input input;
	InteractableObject llock;
	InteractableObject rlock;
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
		if(!(llock instanceof Panel))
			return;
		Panel temp = (Panel)llock;
		
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
			if(llock==null){//There's no current lock yet, get a lock
				for(InteractableObject io:uiObjectList){//Iterate through all objects
					if(io.isMouseOver(mouseX,mouseY)){
						InteractableObject temp=io;
						//If it's a panel, check inside the panel for click logic
						//move to the front is handled by general object handling
						if(io instanceof Panel){
							temp=((Panel) io).getObject(mouseX, mouseY);
							if(temp==null)
								temp=io;
						}
						//If it's an origin object, ensure that the panel is on top
						if(temp instanceof OriginObject)
							moveToFront(((OriginObject)temp).getView());
						held=temp.click(mouseX, mouseY,held);
						//If it's movable, move it infront(Panels are movable)
						if(temp.isMoveable()){
							moveToFront(io);
						}
						System.out.println("LMouse locked on object "+temp.toString());
						llock=temp;
						break;
					}
				}
				
				if(llock==null)//We didn't find an object, try gameobjects
					for(InteractableObject io:gameObjectList){//Iterate through all objects
						if(io.isMouseOver(mouseX,mouseY)){
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
							System.out.println("LMouse locked on object "+temp.toString());
							llock=temp;
							break;
						}
					}
				//If it's not on an object, make it the "empty" object
				if(llock==null)
					llock=TreeUIManager.empty;
			}
			else{
				//Move the object if it is set to movable
				if(llock.isMoveable()){
					//If it is a panel, then let's do some snapping!
					llock.dmove(mouseX-previousX, mouseY-previousY);
				}
			}
			//Let the object know that it has been locked on(If there is no object, it will lock on to the empty object)
			llock.locked=true;
		}
		else
		{
			if(llock!=null){
				checkSnap();
				llock.locked=false;
			}
			llock=null;
		}
		
		
		//Process right click
		//Right clicks will ONLY process on editor wrapper classes
		if(input.isMouseButtonDown(0)){//If the mouse is down, check if it needs to lock an object
			if(rlock==null){//There's no current lock yet, get a lock
				for(InteractableObject io:uiObjectList){//Iterate through all objects
					if(io.isMouseOver(mouseX,mouseY)){
						InteractableObject temp=io;
						//First check if we have clicked on the base panel
						if(io instanceof EditorBasePanel){
							System.out.println("RMouse locked on object "+temp.toString());
							rlock=temp;
							break;
						}
						//If it's not the base panel it must have EditorFunctional to have right-click functionality
						if(io instanceof EditorFunctional){
							System.out.println("RMouse locked on object "+temp.toString());
							rlock=temp;
							break;
						}
					}
				}
				
				//If it's not on an object, make it the "empty" object
				if(rlock==null)
					rlock=TreeUIManager.empty;
			}
			else{
				//We already have a lock, what should we do?
				//Unless the lock is on the basepanel, move the thing to where the mouse is
				if(!(rlock instanceof EditorBasePanel)){
					//So we need to make sure we move it properly
				}
			}
			rlock.locked=true;
		}
		else
		{
			rlock=null;
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
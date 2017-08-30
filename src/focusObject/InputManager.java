package focusObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

import Editor.EditorImmune;
import GameLogic.RequiresTyping;
import GameObjects.GameObject;
import GameObjects.PaneledGameObject;
import TreeUI.UIElement;
import uiItem.HasOverlay;
import uiItem.UIItem;

//This class will contain all interactable objects and will take in mouse input
//This is to allow for an object to "lock" on to the mouse

public class InputManager implements InputListener{
	static Hashtable<Integer,SmartInteger> remapHash = new Hashtable<Integer,SmartInteger>();//<Input,listen>
	private RequiresTyping typingLock = null;//This will contain the object which keypresses will be forwarded
	private int previousX,previousY;
	private UIItem held;
	private boolean editor=false,inventory=false;;
	private ArrayList<Integer> keys;
	private InventoryManager iManager;
	LinkedList<InteractableObject> uiObjectList;//Panels
	LinkedList<InteractableObject> gameObjectList;//Gameobjects
	Input input;
	InteractableObject klock;
	InteractableObject llock;
	InteractableObject flock = TreeUIManager.empty;
	InteractableObject rlock;
	int stickiness;
	protected InputManager(Input input,InventoryManager iManager,LinkedList<InteractableObject>gameObjectList,LinkedList<InteractableObject> uiObjectList,int stickiness,ArrayList<Integer> keys){
		this.input=input;
		this.iManager=iManager;
		this.uiObjectList=uiObjectList;
		this.gameObjectList=gameObjectList;
		this.stickiness=stickiness;
		this.keys=keys;
		previousX=0;
		previousY=0;
		held=null;
	}
	public void enableEditor(){
		this.editor=true;
	}
	public void enableInventory(){
		//If you enable the inventory, be prepared to lose any item in the handslot
		this.inventory=true;
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
		if(!uiObjectList.remove(io)){
			System.out.println("WARNING: Panel does not exist in the TreeUIManager!");
		}
		else{
			uiObjectList.addFirst(io);
		}
	}
	
	protected void update(int delta){
		if(inventory)//Update the held with the active
			held=iManager.getActiveItem();
		//Receives what the mouse is currently on via the MouseManager
		int mouseX=input.getMouseX();
		int mouseY=input.getMouseY();
		
		
		
		
		//Testing for mouse clicks
		if(input.isMouseButtonDown(0)){//If the mouse is down, check if it needs to lock an object
			if(llock==null){//There's no current lock yet, get a lock
				for(InteractableObject io:uiObjectList){//Iterate through all objects
					if(io.masterIsMouseOver(mouseX,mouseY)){
						InteractableObject temp=io;
						//If it's a panel, check inside the panel for click logic
						//move to the front is handled by general object handling
						if(io instanceof Panel){
							temp=((Panel) io).getObject(mouseX, mouseY);
							if(temp==null)
								temp=io;
						}
						//If it's an origin object, ensure that the panel is on top
						if(temp instanceof OriginObject){
							System.out.println("OriginObject clicked, displaying panel("+((OriginObject)temp).getView().x+","+((OriginObject)temp).getView().y+")");
							moveToFront(((OriginObject)temp).getView());
						}
						//If it's movable, move it infront(Panels are movable)
						if(temp.isMoveable()){
							moveToFront(io);
						}
						System.out.println("LMouse locked on object "+temp.toString());
						flock.fleetingLock=false;
						llock=temp;
						held=temp.masterClick(mouseX, mouseY,held);
						if(inventory)//If the inventory is active, we want to change the active slot as well
							iManager.overwriteActive(held);
						break;
					}
				}
				
				if(llock==null)//We didn't find an object, try gameobjects
					for(InteractableObject io:gameObjectList){//Iterate through all objects
						if(io.masterIsMouseOver(mouseX,mouseY)){
							InteractableObject temp=io;
							if(io instanceof Panel){
								temp=((Panel) io).getObject(mouseX, mouseY);
								if(temp==null)
									temp=io;
							}
							if(temp instanceof OriginObject)
								moveToFront(((OriginObject)temp).getView());
							if(temp.isMoveable()){
								moveToFront(io);
							}
							System.out.println("LMouse locked on object "+temp.toString());
							flock.fleetingLock=false;
							llock=temp;
							held=temp.masterClick(mouseX, mouseY,held);
							if(inventory)//If the inventory is active, we want to change the active slot as well
								iManager.overwriteActive(held);
							break;
						}
					}
				//If it's not on an object, make it the "empty" object
				if(llock==null)
					llock=TreeUIManager.empty;
				flock=llock;
				flock.fleetingLock=true;
				setTypingLock(llock);
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
		if(input.isMouseButtonDown(1)){//If the mouse is down, check if it needs to lock an object
			if(rlock==null){//There's no current lock yet, get a lock
				for(InteractableObject io:uiObjectList){//Iterate through all objects
					if(io.masterIsMouseOver(mouseX,mouseY)){
						InteractableObject temp=io;
						//Check if it is rightclickable
						if(editor){
							if(io instanceof Panel){
								temp=((Panel) io).getObject(mouseX, mouseY);
								if(temp==null)
									temp=io;
							}
							System.out.println("RMouse locked on object "+temp.toString());
							rlock=temp;
							break;
						}
					}
				}
				
				
				//Didn't find it in uiobjectlist, try gameobjects
				if(rlock==null)
					for(InteractableObject io:gameObjectList){//Iterate through all objects
						if(io.masterIsMouseOver(mouseX,mouseY)){
							InteractableObject temp=io;
							//Check if it is rightclickable
							if(editor){
								if(io instanceof Panel){
									temp=((Panel) io).getObject(mouseX, mouseY);
									if(temp==null)
										temp=io;
								}
								System.out.println("RMouse locked on object "+temp.toString());
								rlock=temp;
								break;
							}
						}
					}
				
				//If it's not on an object, make it the "empty" object
				if(rlock==null)
					rlock=TreeUIManager.empty;
				else
					((InteractableObject)rlock).rightClick(mouseX, mouseY,held);
			}
			else{
				//We already have a lock, what should we do?
				//Unless the lock is on the basepanel, move the thing to where the mouse is
				if(!(rlock instanceof EditorImmune)){
					//So we need to make sure we move it properly
					//We will need to modify the xr and yr variables
					if(rlock instanceof GameObject){
						((InteractableObject)rlock).x+=mouseX-previousX;
						((InteractableObject)rlock).y+=mouseY-previousY;
					}
					if(rlock instanceof UIElement){
						((UIElement)rlock).rx+=mouseX-previousX;
						((UIElement)rlock).ry+=mouseY-previousY;
					}
				}
			}
			rlock.locked=true;
		}
		else
		{
			if(rlock!=null){
				rlock.locked=false;
			}
			rlock=null;
		}
		
		previousX=mouseX;
		previousY=mouseY;
		
		InteractableObject mouseOn = getMouseOn();
		
		
		//This code requires refactoring due to how typingInput needs to work
		if(typingLock!=null)
			return;//There is an object that has taken keyboard focus. We give zero shits about where the mouse is when this has happened
		
		//Are any keys pressed?
		boolean pressed=false;
		for(Integer i:keys)
			if(input.isKeyDown(i)){
				pressed=true;
				break;
			}
		if(pressed){
			//There is no lock, get a lock
			if(klock==null){
				//check if the mouse is currently on anything
				if(mouseOn==null){
					//Mouse is not on anything, lock onto empty
					klock=TreeUIManager.empty;
				}
				else{
					//Mouse is on something, lock it
					System.out.println("Keyboard locked on object "+mouseOn.toString());
					klock=mouseOn;
					if(mouseOn instanceof OriginObject){
						moveToFront(((OriginObject)mouseOn).getView());
					}
				}
			}
			
			//Test all keypresses on it
			Enumeration<Integer> iter=remapHash.keys();
			while(iter.hasMoreElements()){
				int iterListen=remapHash.get(iter.nextElement()).value;
				if(input.isKeyDown(iterListen))
					klock.keyPress(mouseX, mouseY, iterListen);
			}	
				
			klock.locked=true;
		}
		else{
			//If there are no key presses, unlock
			if(klock!=null)
				klock.locked=false;
			klock=null;
		}
	}
	
	/**
	 * This function is called everytime the leftmouse is pressed, it gets the object that the mouse was over
	 * @param newLock
	 */
	public void setTypingLock(InteractableObject newLock){
		//Do nothing if we already have the same object
		if(newLock==typingLock)
			return;
		//If the object does NOT require typing, set the typingLock to null
		if(newLock instanceof RequiresTyping){
			System.out.println("Set typingLock to "+newLock);
			typingLock=(RequiresTyping)newLock;
		}
		else{
			typingLock=null;
		}
		
	}
	
	/**
	 * Remaps a default key to another key
	 * @param inputKey, the default key
	 * @param listenKey, the new key
	 */
	public void remapKey(int inputKey, int listenKey){
		if(!remapHash.containsKey(inputKey)){
			System.out.println("Keyboard Manager Warning: Remapped non-original key!");
			remapHash.put(inputKey, new SmartInteger(listenKey));
		}
		else
			remapHash.get(inputKey).value=listenKey;
	}
	
	/**
	 * Draws the held item
	 * @param g
	 */
	public void draw(Graphics g){
		if(held!=null){
			if(held instanceof HasOverlay)
				((HasOverlay)held).drawOverlay(g, input.getMouseX(), input.getMouseY());
			held.draw(g,input.getMouseX(),input.getMouseY()-10);
		}
	}
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(int arg0) {
		//The mouse wheel will change the active inventory slot
		if(inventory){
			if(arg0>0){
				iManager.switchActiveDown();
			}
			else{
				iManager.switchActiveUp();
			}
		}
		
	}
	@Override
	public void keyPressed(int key, char c) {
		//If we have a focus lock, give it ALL the keyPresses
		if(typingLock!=null)
			typingLock.processUniversalKeyPress(key, c);
		
	}
	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
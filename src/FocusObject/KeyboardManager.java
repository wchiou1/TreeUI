package focusObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import GameLogic.RequiresTyping;

public class KeyboardManager implements KeyListener{
	static Hashtable<Integer,SmartInteger> remapHash = new Hashtable<Integer,SmartInteger>();//<Input,listen>
	private Input input;
	private RequiresTyping typingLock = null;//This will contain the object which keypresses will be forwarded
	private ArrayList<Integer> keys;
	private InteractableObject lock;
	LinkedList<InteractableObject> objectList;
	protected KeyboardManager(Input input,LinkedList<InteractableObject> objectList , ArrayList<Integer> keys){
		this.input=input;
		this.keys=keys;
		this.objectList=objectList;
		for(Integer i:keys){
			remapHash.put(i, new SmartInteger(i));
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
	
	private void moveToFront(InteractableObject io){
		objectList.remove(io);
		objectList.addFirst(io);
	}
	
	public void update(InteractableObject mouseOn){
		//This code requires refactoring due to how typingInput needs to work
		if(typingLock!=null)
			return;//There is an object that has taken keyboard focus. We give zero shits about where the mouse is when this has happened
		
		//Receives what the mouse is currently on via the MouseManager
		
		int mouseX=input.getMouseX();
		int mouseY=input.getMouseY();
		
		//Are any keys pressed?
		boolean pressed=false;
		for(Integer i:keys)
			if(input.isKeyDown(i)){
				pressed=true;
				break;
			}
		if(pressed){
			//There is no lock, get a lock
			if(lock==null){
				//check if the mouse is currently on anything
				if(mouseOn==null){
					//Mouse is not on anything, lock onto empty
					lock=TreeUIManager.empty;
				}
				else{
					//Mouse is on something, lock it
					System.out.println("Keyboard locked on object "+mouseOn.toString());
					lock=mouseOn;
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
					lock.keyPress(mouseX, mouseY, iterListen);
			}	
				
			lock.locked=true;
		}
		else{
			//If there are no key presses, unlock
			if(lock!=null)
				lock.locked=false;
			lock=null;
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
		return true;
	}
	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
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
}
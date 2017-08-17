package FocusObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import org.newdawn.slick.Input;

public class KeyboardManager{
	static Hashtable<Integer,SmartInteger> remapHash = new Hashtable<Integer,SmartInteger>();//<Input,listen>
	private Input input;
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
}
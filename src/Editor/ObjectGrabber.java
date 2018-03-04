package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.InteractableObject;
import uiItem.UIItem;

/**
 * This item will store a gameobject on the first click and connect it to another object of the second click
 * It will be activated using Ctrl within the editor
 * @author Wesley Chiou
 *
 */
public class ObjectGrabber extends UIItem implements EditorItem{
	private InteractableObject stored;//Stores the gameobject so we can use it as an argument
	public ObjectGrabber(){}
	
	public void grabObject(InteractableObject io){
		System.out.println("Storing:"+io);
		//If there is not stored object, get it
		if(stored==null){
			stored=io;
			return;
		}
	}
	public void clearGrabbed(){
		stored = null;
	}
	public InteractableObject getStored(){
		return stored;
	}
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.blue);
		g.drawLine(x+2, y+2, x+2, y+9);//Left
		g.drawLine(x+2, y+9, x+9, y+9);//Bottom
		g.drawLine(x+9, y+9, x+9, y+2);//Right
		g.drawLine(x+9, y+2, x+2, y+2);//Top
	}
	
}
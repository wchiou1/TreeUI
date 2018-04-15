package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

/**
 * This item will store a gameobject on the first click and connect it to another object of the second click
 * It will be activated using Ctrl within the editor
 * @author Wesley Chiou
 *
 */
public class ObjectGrabber extends SmallGameObject implements EditorItem{
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

	@Override
	public int getCenterX() {
		return x+4;
	}

	@Override
	public int getCenterY() {
		return y+4;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
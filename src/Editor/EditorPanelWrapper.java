package Editor;

import java.sql.Wrapper;

import org.newdawn.slick.Graphics;

import FocusObject.InteractableObject;
import FocusObject.OriginObject;
import FocusObject.Panel;
import TreeUI.UIElement;
import TreeUI.UIItem;

/**
 * This wrapper is to give an ordinary panel the ability to add objects using right-click
 * This class is NOT to be used during normal operation, only by the editor
 * When asked for the save string, it will return the underlying object
 * @author Wesley Chiou
 *
 */
public class EditorPanelWrapper extends Panel implements RightClickable{
	private Panel wrapped;
	//DO NOT USE ANY COORDINATES BESIDES THE WRAPPED PANEL
	//Need to mimic the wrapped class except for opening and closing
	public EditorPanelWrapper(Panel wrapped){
		this.wrapped=wrapped;
	}
	
	@Override
	public void update(int mouseX, int mouseY) {
		wrapped.update(mouseY, mouseY);
	}

	@Override
	public boolean isMoveable() {
		return wrapped.isMoveable();
	}

	@Override
	public void draw(Graphics g) {
		wrapped.draw(g);
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		return wrapped.click(x,y,item);
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		wrapped.keyPress(mouseX, mouseY, key);
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		return wrapped.isMouseOver(x, y);
	}

	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		return null;
	}
	@Override
	public void dmove(int x, int y){
		wrapped.dmove(x, y);
	}
	/**
	 * Adds an interactable object and uses the x and y as position delta from panel x and y
	 * @param AddedObject
	 */
	public void addObject(UIElement io){
		io.setDataLink(dataNode);
		objectList.add(io);
		io.setScreen(this);
	}

	/**
	 * Close the panel and any subsequent panels
	 */
	public void close(){
		for(InteractableObject io:objectList)
			if(io instanceof OriginObject)
				((OriginObject)io).close();
		active=false;
	}
	/**
	 * Returns if the panel is currently active
	 * @return
	 */
	public boolean isActive(){
		return active;
	}

	public InteractableObject getObject(int x, int y){
		if(!active)
			return null;
		for(InteractableObject io:objectList){
			if(io.isMouseOver(x, y))
				return io;
		}
		return null;
	}
	
	public void setOrigin(OriginObject oo){
		if(!virgin){
			System.out.println("Panel Error: Attempted to change origin object. Operation canceled.");
			return;
		}
		oo.setView(this);
		move(oo.getX()+oo.rx-width/2,oo.getY()+oo.ry-height);
	}
	@Override
	public String getSaveString(){
		String temp="";
		//Get the panel information
		//All objects including panels will be contained in SBrackets
		//We'll be using Javascript object syntax
		temp += "{type:PANEL,x:"+x+",y:"+y+",width:"+width+",height:"+height+",objects:[";
		//REMEMBER: WE NEED TO CAP THE SBRACKETS
		
		//Let's get the object strings now
		for(UIElement io:objectList){
			temp += io.getSaveString()+",";
		}
		temp = temp.substring(0, temp.length()-1);
		temp += "]}";
		System.out.println(temp);
		return temp;
		
		
	}
	
}
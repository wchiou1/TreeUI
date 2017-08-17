package Editor;

import org.newdawn.slick.Graphics;

import FocusObject.InteractableObject;
import TreeUI.UIItem;

/**
 * This wrapper is to give an ordinary panel the ability to add objects using right-click
 * This class is NOT to be used during normal operation, only by the editor
 * When asked for the save string, it will return the underlying object
 * @author Wesley Chiou
 *
 */
public class EditorPanelWrapper extends InteractableObject{
	
	
	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
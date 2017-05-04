package TreeUI;

import FocusObject.InteractableObject;

/**
 * Adds functionality for supporting snapping
 * @author Wesley
 *
 */
public abstract class Snappable extends InteractableObject{
	protected int height,width;
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
}
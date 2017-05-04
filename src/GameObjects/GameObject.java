package GameObjects;

import FocusObject.InteractableObject;

public abstract class GameObject extends InteractableObject{
	public void update(int x, int y){
		update();
	}
	/**
	 * Object declared update function which will be called every step
	 */
	public abstract void update();
}
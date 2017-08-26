package GameObjects;

import focusObject.InteractableObject;

/**
 * This class declares that the object exists within the gameworld, but CANNOT be clicked on to open a panel.
 * @author Wesley Chiou
 *
 */

public abstract class NonPaneledGameObject extends InteractableObject{
	protected int id;
	protected NonPaneledGameObject(){
		this.id=count;
		System.out.println(":"+id+" "+this.getClass());
	}
	@Override
	public void update(int x, int y){
		update();
	}
	/**
	 * Object declared update function which will be called every step
	 */
	@Override
	public abstract void update();
}
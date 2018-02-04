package GameObjects;

import aspenNetwork.AspenNode;
import focusObject.InteractableObject;

/**
 * This class declares that the object exists within the gameworld, but CANNOT be clicked on to open a panel.
 * @author Wesley Chiou
 *
 */

public abstract class NonPaneledGameObject extends InteractableObject implements GameObject{
	protected int id;
	protected NonPaneledGameObject(){
		this.id=count;
		dataNode = new AspenNode(this);
		System.out.println(":"+id+" "+this.getClass());
	}
	@Override
	public void update(int x, int y,int delta){
		update(delta);
	}
	/**
	 * Object declared update function which will be called every step
	 */
	@Override
	public abstract void update(int delta);
}
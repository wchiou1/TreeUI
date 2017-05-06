package GameObjects;

import FocusObject.OriginObject;

public abstract class PaneledGameObject extends OriginObject implements GameObject{
	protected int id;
	protected PaneledGameObject(){
		this.id=count;
		System.out.println(":"+id+" "+this.getClass());
	}
	public void update(int x, int y){
		update();
	}
	/**
	 * Object declared update function which will be called every step
	 */
	public abstract void update();
}
package GameObjects;

import aspenNetwork.AspenNode;
import focusObject.OriginObject;
import uiItem.UIItem;

public abstract class PaneledGameObject extends OriginObject implements GameObject{
	protected int id;
	protected PaneledGameObject(){
		this.id=count;
		dataNode = new AspenNode(this);
		System.out.println(":"+id+" "+this.getClass());
	}
	@Override
	public AspenNode getNode(){
		return dataNode;
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
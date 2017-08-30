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
	public void update(int x, int y){
		update();
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		if(view==null){
			System.out.println("ERROR: panel not set in origin object");
			return item;
		}
		view.toggle();
		return item;
	}
	/**
	 * Object declared update function which will be called every step
	 */
	@Override
	public abstract void update();
	@Override
	public String getSaveString(){
		return "{type:PANELEDGAMEOBJECT}";
	}
}
package GameObjects;

import DataLinkNetwork.DataNetworkNode;
import FocusObject.OriginObject;
import TreeUI.UIItem;

public abstract class PaneledGameObject extends OriginObject implements GameObject{
	protected int id;
	protected DataNetworkNode dataNode;
	protected PaneledGameObject(){
		this.id=count;
		System.out.println(":"+id+" "+this.getClass());
	}
	public DataNetworkNode getNode(){
		return dataNode;
	}
	public void update(int x, int y){
		update();
	}
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
	public abstract void update();
}
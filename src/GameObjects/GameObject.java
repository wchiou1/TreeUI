package GameObjects;

import org.newdawn.slick.Graphics;

import aspenNetwork.AspenNode;
import focusObject.InteractableObject;
import focusObject.OriginObject;

public abstract class GameObject extends OriginObject{
	protected int id;
	protected GameObject(){
		this.id=count;
		dataNode = new AspenNode(this);
		System.out.println(":"+id+" "+this.getClass());
	}
	/**
	 * Object declared draw function which will be called by the GameObjectManager
	 */
	public abstract void draw(Graphics g);
	public abstract int getCenterX();
	public abstract int getCenterY();
	public AspenNode getNode(){
		return dataNode;
	}

	
}
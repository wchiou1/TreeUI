package GameObjects;

import org.newdawn.slick.Graphics;

import aspenNetwork.AspenNode;

public interface GameObject{
	/**
	 * Object declared update function which will be called every step
	 */
	public abstract void update(int delta);
	/**
	 * Object declared draw function which will be called by the GameObjectManager
	 */
	public abstract AspenNode getNode();
	public abstract void draw(Graphics g);
	public abstract int getCenterX();
	public abstract int getCenterY();
}
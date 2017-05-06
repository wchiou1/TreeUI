package GameObjects;

import org.newdawn.slick.Graphics;

public interface GameObject{
	/**
	 * Object declared update function which will be called every step
	 */
	public abstract void update();
	/**
	 * Object declared draw function which will be called by the GameObjectManager
	 */
	public abstract void draw(Graphics g);
}
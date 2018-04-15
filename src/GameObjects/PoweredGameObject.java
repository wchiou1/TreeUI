package gameObjects;

import focusObject.OriginObject;

public abstract class PoweredGameObject extends GameObject{
	@Override
	public void update(int x, int y,int delta){
		powerUpdate(delta);
		objectUpdate(x,y,delta);
	}
	public abstract void powerUpdate(int delta);
	/**
	 * Object declared update function which will be called every step
	 */
	public abstract void objectUpdate(int x,int y, int delta);
}
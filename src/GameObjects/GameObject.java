package gameObjects;


import aspenNetwork.AspenNode;
import focusObject.OriginObject;
import smallGameObjects.SmallGameObject;

public abstract class GameObject extends OriginObject{
	protected GameObject(){
		dataNode = new AspenNode(this);
		System.out.println(":"+id+" "+this.getClass());
	}
	public SmallGameObject masterKeyPress(int x, int y, int key, SmallGameObject held){
		return objectKeyPress(x,y,key,held);
	}
	public SmallGameObject objectKeyPress(int mouseX, int mouseY, int key, SmallGameObject held) {
		//By default GameObjects do not respond to key pressed, this can be overrided if needed
		return held;
		
	}
	/**
	 * Object declared draw function which will be called by the GameObjectManager
	 */
	public abstract int getCenterX();
	public abstract int getCenterY();
	public AspenNode getNode(){
		return dataNode;
	}

	
}
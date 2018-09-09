package smallGameObjects;

import gameObjects.GameObject;

/**
 * Item object for TreeUI item handling
 * @author Wesley
 *
 */
public abstract class SmallGameObject extends GameObject{
	String name;
	public String getName(){
		return name;
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		if(item!=null&&item.id==this.id){
			//If the item is itself, open the panel
			togglePanel(x,y,item);
			return item;
		}
		else if(item!=null){
			//If the item is not null, apply item
			return sgoClick(x,y,item);
		}
		else{
			//If the item is null return self
			return this;
		}
	}
	/**
	 * Function which is called when a smallobject is applied to this object
	 * Defaults to doing nothing
	 * @param x
	 * @param y
	 * @param item
	 * @return
	 */
	protected SmallGameObject sgoClick(int x, int y, SmallGameObject item){
		return item;
	}
	@Override
	protected boolean panelOpenCondition(int x, int y, SmallGameObject item){
		return item.id==this.id;
	}
	@Override
	public SmallGameObject masterKeyPress(int x, int y, int key, SmallGameObject held){

		return objectKeyPress(x,y,key,held);
	}
	@Override
	public SmallGameObject objectKeyPress(int mouseX, int mouseY, int key, SmallGameObject held) {
		//By default SmallGameObjects don't do anything but move on key press
		return held;
		
	}
	public abstract int getCenterX();
	public abstract int getCenterY();
}
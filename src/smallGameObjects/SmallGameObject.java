package smallGameObjects;

import org.newdawn.slick.Graphics;

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
	public SmallGameObject masterKeyPress(int x, int y, int key, SmallGameObject held){
		//Move the small object if their held is empty
		System.out.println("SmallObject key pressed");
		if(held==null){
			//Need to remove this from the incubator
			//This will be done in the InputManager
			return this;//Looks like this is not good practice.
		}
		return objectKeyPress(x,y,key,held);
	}
	@Override
	public SmallGameObject objectKeyPress(int mouseX, int mouseY, int key, SmallGameObject held) {
		//By default SmallGameObjects don't do anything but move on key press
		return held;
		
	}
}
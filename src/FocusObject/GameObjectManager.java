package focusObject;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

import GameObjects.GameObject;

//Generic Manager which runs the object update method
class GameObjectManager{
	LinkedList<GameObject> objectList;
	GameObjectManager(){
		objectList=new LinkedList<GameObject>();
	}
	void addObject(GameObject go){
		objectList.addFirst(go);
	}
	void update(){
		for(GameObject go:objectList){
			go.update();
		}
	}
	void draw(Graphics g){
		for(GameObject go:objectList){
			go.draw(g);
		}
	}
}
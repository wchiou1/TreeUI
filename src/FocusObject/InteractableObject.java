package focusObject;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.newdawn.slick.Graphics;

import aspenNetwork.AspenNode;
import uiItem.UIItem;
import Editor.Bud;
import Editor.EditorImmune;
import Editor.EditorItem;
import Editor.NodeConnector;
import Editor.Selector;
import GameObjects.GameObject;

public abstract class InteractableObject{
	protected static int count = 0;
	public int x=0,y=0;
	protected AspenNode dataNode;
	protected boolean locked=false;
	protected boolean keyLock=false;//For when you "click" on an object using a key press
	protected boolean fleetingLock=false;
	private boolean hover=false;
	protected int id;
	protected InteractableObject(){
		id=count;
		count++;
	}
	public int getId(){
		return id;
	}
	public static final int getCount(){
		return count;
	}
	public boolean checkDataLink(){
		return dataNode!=null;
	}
	public void setDataLink(AspenNode dataNode){
		this.dataNode=dataNode;
	}
	/**
	 * Getter for the datanode
	 */
	public AspenNode getNode(){
		return dataNode;
	}
	/**
	 * Allows for animation updates and includes mouse coords
	 * This can be left empty if the object does not have an animation
	 */
	public abstract void update(int mouseX, int mouseY);
	/**
	 * Returns if the object is movable via the click and drag function
	 * of the MouseManager
	 */
	public abstract boolean isMoveable();
	/**
	 * Draws the object
	 * @param g
	 */
	public abstract void draw(Graphics g);
	public UIItem rightClick(int x, int y, UIItem item){
		return item;
	}
	public UIItem masterClick(int x, int y, UIItem item){
		if(item instanceof EditorItem&&!(this instanceof EditorImmune)){
			//Need to handle editoritems
			if(item instanceof Selector){
				((Selector)item).setNewSubject(this);
			}
			if(item instanceof NodeConnector&&this instanceof GameObject){
				((NodeConnector)item).applyGameObject(this);
			}
			//Cancel typical actions if there is an editor item
			return item;
		}
		return click(x,y,item);
	}
	/**
	 * Performs actions when clicked on
	 */
	public UIItem click(int x, int y,UIItem item){
		return item;
	}
	/**
	 * Applies a keypress to the interactable object
	 * @param mouseX
	 * @param mouseY
	 * @param key
	 */
	public abstract void keyPress(int mouseX, int mouseY, int key);
	/**
	 * Calls isMouseOver and stores the results in .hover for further processing
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean masterIsMouseOver(int x, int y){
		this.hover = isMouseOver(x,y);
		return this.hover;
	}
	/**
	 * Returns if the mouse if over the object.
	 * Mouse coords are given as parameters
	 * This is required for MouseManager to function correctly
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract boolean isMouseOver(int x, int y);
	/**
	 * Moves the object to the coords in the parameter.
	 * Used to support drag and drop
	 * @param x
	 * @param y
	 */
	public void move(int x, int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Moves the object by the delta given
	 * Used to support drag and drop
	 * @param x
	 * @param y
	 */
	public void dmove(int x, int y){
		this.x+=x;
		this.y+=y;
	}
	/**
	 * Getter for x coordinate
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * Getter for y coordinate
	 * @return
	 */
	public int getY(){
		return y;
	}
	public void update() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Returns the object type and arguments in string format for later construction
	 * @return
	 */
	public String getSaveString(){
		//Let's dynamically read the class we got
		Class<?> ourClass = this.getClass();
		//First let's get the class
		String temp = "{type:"+ourClass.getName();
		
		//If the class we get doesn't have a getSaveString, do toString? Do we need this?
		//We only need this for arrays and stuff, arrays won't have a getSaveString.
		//I guess the question is does a UIElement need to have fillable arrays?
		Field[] fields = ourClass.getFields();
		try {
			for(Field f:fields){
				temp+=","+f.getName()+":"+f.get(this);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return temp+"}";
	}
	/**
	 * Returns a hashtable with parameter names as keys and variable types as data
	 * @return
	 */
	public Hashtable<String,Class<?>> getParameters(){
		Hashtable<String,Class<?>> params = new Hashtable<String,Class<?>>();
		Class<?> ourClass = this.getClass();
		
		Field[] fields = ourClass.getFields();
		try {
			for(Field f:fields){
				params.put(f.getName(), f.getDeclaringClass());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return params;
	}
	
}
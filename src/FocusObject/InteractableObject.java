package FocusObject;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.newdawn.slick.Graphics;

import TreeUI.UIItem;
import DataLinkNetwork.DataNetworkNode;

public abstract class InteractableObject{
	protected static int count = 0;
	public int x=0,y=0;
	protected DataNetworkNode dataNode;
	protected boolean locked=false;
	private boolean hover=false;
	protected int id;
	protected InteractableObject(){
		id=count;
		count++;
	}
	public static final int getCount(){
		return count;
	}
	public void setDataLink(DataNetworkNode dataNode){
		this.dataNode=dataNode;
	}
	/**
	 * Handles removal of the instance
	 */
	public void masterDestroy(){
		dataNode.destroy();
		destroy();
	}
	public abstract void destroy();
	/**
	 * Getter for the datanode
	 */
	public DataNetworkNode getNode(){
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
	
	/**
	 * Performs actions when clicked on
	 */
	public abstract UIItem click(int x, int y,UIItem item);
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
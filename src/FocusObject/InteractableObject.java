package FocusObject;

import org.newdawn.slick.Graphics;

import TreeUI.UIItem;
import DataLinkNetwork.DataNetworkNode;

public abstract class InteractableObject{
	protected int x,y;
	protected DataNetworkNode dataNode;
	protected boolean locked=false;
	protected boolean hover=false;
	public void setDataLink(DataNetworkNode dataNode){
		this.dataNode=dataNode;
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
}
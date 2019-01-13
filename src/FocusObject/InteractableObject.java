package focusObject;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.newdawn.slick.Graphics;

import aspenNetwork.AspenNode;
import gameObjects.GameObject;
import smallGameObjects.SmallGameObject;
import Editor.EditorImmune;
import Editor.Item.EditorItem;
import Editor.Item.NodeConnector;
import Editor.Item.ObjectGrabber;
import Editor.Item.Selector;
import Editor.Tree.Bud;
import Test.Shell;

public abstract class InteractableObject{
	protected static int count = 0;
	public int x=0,y=0;
	public boolean show = true;
	protected transient AspenNode dataNode;
	protected boolean locked=false;
	protected boolean keyLock=false;//For when you "click" on an object using a key press
	protected boolean fleetingLock=false;
	private boolean hover=false;
	protected int id;
	protected InteractableObject(){
		id=count;
		count++;
	}
	void setId(int new_id){
		id = new_id;
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
	public abstract void update(int mouseX, int mouseY,int delta);
	/**
	 * Returns if the object is movable via the click and drag function
	 * of the MouseManager
	 */
	public abstract boolean isMoveable();
	/**
	 * Draws the object
	 * @param g
	 */
	public abstract void draw(Graphics g,int x, int y);
	public final void draw(Graphics g){
		draw(g,x,y);
	}
	public SmallGameObject masterClick(int x, int y, SmallGameObject item){
		if(item instanceof EditorItem&&!(this instanceof EditorImmune)){
			//Need to handle editoritems
			if(item instanceof Selector){
				((Selector)item).setNewSubject(this);
			}
			if(item instanceof NodeConnector&&this instanceof GameObject){
				((NodeConnector)item).applyGameObject(this);
			}
			if(item instanceof ObjectGrabber){
				((ObjectGrabber)item).grabObject(this);
			}
			//Cancel typical actions if there is an editor item
			return item;
		}
		return click(x,y,item);
	}
	//Always calls this function before keypress, this will be overwritten by classes which
	//wish to insert code before child calls
	public SmallGameObject masterKeyPress(int x, int y, int key, SmallGameObject held){
		//System.out.println("Default master key press invoked");
		return objectKeyPress(x,y,key,held);
	}
	public SmallGameObject objectKeyPress(int x, int y, int key, SmallGameObject held){
		return held;
	}
	/**
	 * Performs actions when clicked on
	 */
	public SmallGameObject click(int x, int y,SmallGameObject item){
		return item;
	}
	/**
	 * Applies a keypress to the interactable object(Depreciated via objectKeyPress)
	 * @param mouseX
	 * @param mouseY
	 * @param key
	 */
	//public abstract void keyPress(int mouseX, int mouseY, int key);
	/**
	 * Calls isMouseOver and stores the results in .hover for further processing
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean masterIsMouseOver(int x, int y){
		if(!this.show)
			return false;
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
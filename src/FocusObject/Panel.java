package focusObject;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Editor.Bud;
import TreeUI.Snappable;
import smallGameObjects.SmallGameObject;

public class Panel extends Snappable{
	public boolean active = false; //Whether the panel is active(inactive panels are invisible)
	protected ArrayList<UIElement> objectList = new ArrayList<UIElement>();//List of objects that the panel must render
	public int offset = 0;
	public boolean scrollX = false;
	public boolean scrollY = false;
	public int lowerBoundX,upperBoundX,lowerBoundY,upperBoundY;
	private Incubator inc;
	//The attached datanode which all UIELements
										//will use to communicate with the datanetwork
	protected boolean virgin = true;//If The panel has ever been opened before(used to error check setting the panel's origin object)
	public Panel(){
		this.width=100;
		this.height=100;
		recalculateBounds();
	}
	public Panel(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.active=true;
		recalculateBounds();
	}
	public void enableEditing(Incubator inc){
		this.inc=inc;
	}
	public ArrayList<UIElement> getObjList(){
		return objectList;
	}
	public boolean removeObject(UIElement io){
		boolean result = objectList.remove(io);
		recalculateBounds();
		return result;
	}
	public void addObjects(ArrayList<UIElement> ios){
		for(UIElement uie:ios){
			uie.setDataLink(dataNode);
			objectList.add(uie);
			uie.setScreen(this);
		}
		recalculateBounds();
	}
	/**
	 * Adds an interactable object and uses the x and y as position delta from panel x and y
	 * @param AddedObject
	 * @throws NoOriginObjectException 
	 */
	public void addObject(UIElement io){
		io.setDataLink(dataNode);
		objectList.add(io);
		io.setScreen(this);
		recalculateBounds();
	}
	public void recalculateBounds(){
		System.out.println("Recalculating");
		int lowerX=0,upperX=width;
		int lowerY=0,upperY=height;
		//Iterate through all uielements
		for(UIElement element:objectList){
			//Test for lower bounds
			if(element.rx<lowerX){
				lowerX = element.rx;
			}
			if(element.rx+element.getWidth()>upperX){
				upperX = element.rx+element.getHeight();
			}
			if(element.ry<lowerY){
				lowerY = element.ry;
			}
			if(element.ry+element.getHeight()>upperY){
				upperY = element.ry+element.getHeight();
			}
		}
		lowerBoundX = lowerX;
		upperBoundX = upperX;
		lowerBoundY = lowerY;
		upperBoundY = upperY;
	}
	/**
	 * Toggles the panel open and closed using the active boolean
	 */
	public void toggle(){
		if(active)
			close();
		else
			open();
	}
	/**
	 * Opens the panel
	 */
	public void open(){
		active=true;
	}
	void openAll(){
		open();
		for(UIElement io:objectList)
			if(io instanceof OriginObject)
				((OriginObject)io).openAll();
	}
	/**
	 * Close the panel and any subsequent panels
	 */
	public void close(){
		if(objectList==null)
			System.out.println("NULL! How did this happen?!");
		for(InteractableObject io:objectList)
			if(io instanceof OriginObject)
				((OriginObject)io).close();
		active=false;
	}
	/**
	 * Returns if the panel is currently active
	 * @return
	 */
	public boolean isActive(){
		return active;
	}
	/**
	 * Draws the panel borders and all UIElement
	 */
	@Override
	public void draw(Graphics g,int x, int y) {
		if(!active)
			return;
//		g.setColor(Color.black);
//		g.drawLine(x+width/2, y+height/2, origin.getX()+2, origin.getY()+2);
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 2);
		g.setColor(Color.white);
		g.fillRoundRect(x+2, y+2, width-4, height-4, 2);
		
		for(UIElement io:objectList){
			g.setClip(x+2, y+2, width-4, height-4);
			io.UDraw(g);
		}
		g.clearClip();
	}

	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		return item;
	}
	/*public SmallGameObject rightClick(int x, int y, SmallGameObject item) {
		//When right-click is called we want to create a bud at the mouse location
		System.out.println("PanelWrapper Right click at ("+x+","+y+")");
		//Now to create the Bud
		addObject(new Bud(x,y,inc,this));
		return null;
	}*/
	public InteractableObject getObject(int x, int y){
		if(!active)
			return null;
		for(InteractableObject io:objectList){
			if(io.masterIsMouseOver(x, y))
				return io;
		}
		return null;
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(!active)
			return false;
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}

	@Override
	public boolean isMoveable() {
		return true;
	}
	public void preUpdate(int x,int y, int delta){
		//TODO Put panel close and open animation logic here
	}
	@Override
	public void update(int x, int y,int delta) {
		
		for(InteractableObject io:objectList)
			io.update(x, y,delta);
	}
	/*@Override
	public void keyPress(int mouseX, int mouseY, int key,SmallGameObject held) {
		if(!active)
			return;
		if(!locked&&key==Input.KEY_E)
			close();
		//Apply key action to objects in objectlist
		for(InteractableObject io:objectList){
			if(io.isMouseOver(x, y))
				io.keyPress(x, y, key,held);
		}
	}*/
	
	public void setOrigin(OriginObject oo){
		if(!virgin){
			System.out.println("Panel Error: Attempted to change origin object. Operation canceled.");
			return;
		}
		setDataLink(oo.getNode());
		oo.setView(this);
		move(oo.getX()+oo.rx-width/2,oo.getY()+oo.ry-height);
	}
	public void setOrigin(OriginObject oo,int x, int y){
		if(!virgin){
			System.out.println("Panel Error: Attempted to change origin object. Operation canceled.");
			return;
		}
		setDataLink(oo.getNode());
		oo.setView(this);
		move(x,y);
	}
	
	public void dMoveTreeUI(int dx, int dy){
		dmove(dx,dy);
		for(UIElement e:objectList){
			if(e instanceof OriginObject)
				((OriginObject)e).dMoveTreeUI(dx, dy);
		}
	}
}
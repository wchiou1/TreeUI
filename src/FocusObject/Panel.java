package focusObject;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Editor.Tree.Bud;
import smallGameObjects.SmallGameObject;

public class Panel extends Snappable{
	public boolean active = false; //Whether the panel is active(inactive panels are invisible)
	public ArrayList<UIElement> objectList = new ArrayList<UIElement>();//List of objects that the panel must render
	public int offsetX = 0,offsetY = 0;
	public boolean scrollX = false, scrollY = false;
	public int leftBoundX,rightBoundX,topBoundY,bottomBoundY;
	public int previousX = 0,previousY = 0;
	public int barSize=0,buttonSize = 3;
	public boolean scrolling = false;
	private Incubator inc;
	//The attached datanode which all UIElements
										//will use to communicate with the datanetwork
	protected boolean virgin = true;//If The panel has ever been opened before(used to error check setting the panel's origin object)
	public Panel(){
		this.width=100;
		this.height=100;
	}
	public Panel(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.active=true;
	}
	public void enableEditing(Incubator inc){
		this.inc=inc;
	}
	public ArrayList<UIElement> getObjList(){
		return objectList;
	}
	public boolean removeObject(UIElement io){
		boolean result = objectList.remove(io);
		return result;
	}
	//TODO Make uielements function without initialization
	public void addObjects(ArrayList<UIElement> ios){
		for(UIElement uie:ios){
			uie.setDataLink(dataNode);
			objectList.add(uie);
		}
	}
	/**
	 * Adds an interactable object and uses the x and y as position delta from panel x and y
	 * @param AddedObject
	 * @throws NoOriginObjectException 
	 */
	public void addObject(UIElement io){
		io.setDataLink(dataNode);
		objectList.add(io);
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
	public final void draw(Graphics g,int x, int y) {
		if(!active)
			return;
		drawPanel(g,x,y);
		drawScrollBars(g,x,y);
		drawUIElements(g,x,y);
		
		g.clearClip();
	}
	protected void drawPanel(Graphics g,int x, int y){
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 2);
		g.setColor(Color.white);
		g.fillRoundRect(x+2, y+2, width-4, height-4, 2);
	}
	protected void drawScrollBars(Graphics g,int x, int y){
		if(scrollX){
			
			g.setColor(Color.black);
			//Draw left scroll button
			g.fillRect(x+2, height-buttonSize-2, buttonSize, buttonSize);
			
			
			//Draw horizontal scroll bar
			
			
			
			//Draw right scroll button
			g.fillRect(x+width-buttonSize-2, y+height-buttonSize-2, buttonSize, buttonSize);
			
			
			
		}
		if(scrollY){
			
			g.setColor(Color.black);
			//Draw top scroll button
			g.fillRect(x+width-buttonSize-2, y+2, buttonSize, buttonSize);
			
			
			//Draw vertical scroll bar
			//First get the size of the bar
			int totalSize = bottomBoundY - topBoundY;
			double sizeRatio =  1.0*height/totalSize;
			barSize = Math.max((int) Math.ceil(sizeRatio*(height-buttonSize*2-6)),1);
			
			//Get the position of the bar
			double posRatio = 1.0*offsetY/totalSize;
			int barPos = Math.max((int) Math.floor(posRatio*(height-buttonSize*2-6)+buttonSize+3),buttonSize+3);
			g.fillRect(x+width-5, y+barPos, buttonSize, barSize);
			
			//Draw bottom scroll button
			g.fillRect(x+width-5, y+height-5, 3, 3);
			
			
			
			
		}
	}
	protected final void drawUIElements(Graphics g,int x, int y){
		int drawX = 0;
		int drawY = 0;
		if(scrollX){
			drawX = -offsetX;
		}
		if(scrollY){
			drawY = -offsetY;
		}
		
		for(UIElement uie:objectList){
			if(!uie.show)
				continue;
			g.setClip(x+2, y+2, width-4, height-4);
			uie.UDraw(g,x+drawX+uie.getX(),y+drawY+uie.getY());
		}
	}
	public boolean mouseOnScrollBars(int x, int y){
		//Check if the scrollbars were clicked
		if(x>this.x+width-buttonSize-2&&scrollY){
			return true;
		}
		if(y>this.y+height-buttonSize-2&&scrollX){
			return true;
		}
		return false;
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		previousX = x;
		previousY = y;
		scrolling = mouseOnScrollBars(x,y);
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
			if(io.masterIsMouseOver(x-this.x, y-this.y))
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
		return !scrolling;
	}
	public void preUpdate(int x,int y, int delta){
		//TODO Put panel close and open animation logic here
	}
	@Override
	public void update(int x, int y,int delta) {
		for(InteractableObject io:objectList)
			io.update(x, y,delta);
		
		if(!locked){
			scrolling = false;
			return;
		}
		if(scrolling){
			updateScrollBars(x,y);
		}
	}
	private void updateScrollBars(int x, int y){
		int deltaX = x-previousX;
		int deltaY = y-previousY;
		if(scrollX){
			offsetX += deltaX*(rightBoundX-leftBoundX)/width;
			if(offsetX<leftBoundX){
				offsetX = leftBoundX;
			}
			if(offsetX>rightBoundX-width){
				offsetX = rightBoundX-width;
			}
		}
		if(scrollY){
			offsetY += deltaY*(bottomBoundY-topBoundY)/height;
			if(offsetY<topBoundY){
				offsetY = topBoundY;
			}
			if(offsetY>bottomBoundY-height){
				offsetY = bottomBoundY-height;
			}
		}
		
		previousX = x;
		previousY = y;
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
		move(oo.getX()-width/2,oo.getY()-height);
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
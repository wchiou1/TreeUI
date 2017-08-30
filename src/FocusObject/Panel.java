package focusObject;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Editor.Bud;
import GameLogic.Incubator;
import TreeUI.Snappable;
import TreeUI.UIElement;
import uiItem.UIItem;

public class Panel extends Snappable{
	public boolean active = false; //Whether the panel is active(inactive panels are invisible)
	protected ArrayList<UIElement> objectList = new ArrayList<UIElement>();//List of objects that the panel must render
	private Incubator inc;
	//The attached datanode which all UIELements
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
		return objectList.remove(io);
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
	public void draw(Graphics g) {
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
	public UIItem click(int x, int y,UIItem item) {
		return item;
	}
	public UIItem rightClick(int x, int y, UIItem item) {
		//When right-click is called we want to create a bud at the mouse location
		System.out.println("PanelWrapper Right click at ("+x+","+y+")");
		//Now to create the Bud
		addObject(new Bud(x,y,inc,this));
		return null;
	}
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
	@Override
	public void update(int x, int y) {
		//TODO Put panel close and open animation logic here
		for(InteractableObject io:objectList)
			io.update(x, y);
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		if(!active)
			return;
		if(!locked&&key==Input.KEY_E)
			close();
		//Apply key action to objects in objectlist
		for(InteractableObject io:objectList){
			if(io.isMouseOver(x, y))
				io.keyPress(x, y, key);
		}
	}
	
	public void setOrigin(OriginObject oo){
		if(!virgin){
			System.out.println("Panel Error: Attempted to change origin object. Operation canceled.");
			return;
		}
		setDataLink(oo.getNode());
		oo.setView(this);
		move(oo.getX()+oo.rx-width/2,oo.getY()+oo.ry-height);
	}
	public void dMoveTreeUI(int dx, int dy){
		dmove(dx,dy);
		for(UIElement e:objectList){
			if(e instanceof OriginObject)
				((OriginObject)e).dMoveTreeUI(dx, dy);
		}
	}
	@Override
	public String getSaveString(){
		String temp="";
		//Get the panel information
		//All objects including panels will be contained in SBrackets
		//We'll be using Javascript object syntax
		temp += "{type:PANEL,x:"+x+",y:"+y+",width:"+width+",height:"+height+",objects:[";
		//REMEMBER: WE NEED TO CAP THE SBRACKETS
		
		//Let's get the object strings now
		for(UIElement io:objectList){
			temp += io.getSaveString()+",";
		}
		temp = temp.substring(0, temp.length()-1);
		temp += "]}";
		System.out.println(temp);
		return temp;
		
		
	}
}
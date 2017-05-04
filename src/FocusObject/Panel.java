package FocusObject;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import DataLinkNetwork.DataNetworkNode;
import TreeUI.PanelExit;
import TreeUI.Snappable;
import TreeUI.UIElement;
import TreeUI.UIItem;

public class Panel extends Snappable{
	protected boolean active;
	protected ArrayList<UIElement> objectList;//List of objects that the panel must render
	protected DataNetworkNode dataNode;
	protected OriginObject origin;
	private boolean virgin;
	public Panel(int x, int y){
		this.x=x;
		this.y=y;
		objectList = new ArrayList<UIElement>();
	}
	public Panel(boolean relative,int x, int y, int height, int width,OriginObject origin,DataNetworkNode dataNode,boolean active){
		//x and y are relative to the origin object
		this(x,y);
		this.height=height;
		this.width=width;
		this.dataNode=dataNode;
		this.active=active;
		this.origin=origin;
		origin.setView(this);
		if(relative){
			this.x=origin.x+x;
			this.y=origin.y+y;
		}
		addObject(new PanelExit(width-13,3,origin));
	}
	public Panel(int x, int y, int height, int width,OriginObject origin,DataNetworkNode dataNode,boolean active){
		this(true,x, y,height,width,origin,dataNode,active);
	}
	public Panel(int x, int y, int height, int width,OriginObject origin,DataNetworkNode dataNode){
		this(true,x, y,height,width,origin,dataNode,false);
	}
	
	public Panel(int x, int y,OriginObject origin,DataNetworkNode dataNode){
		this(x,y,100,100,origin,dataNode);
	}
	/**
	 * Adds an interactable object and uses the x and y as position delta from panel x and y
	 * @param io
	 */
	public void addObject(UIElement io){
		io.setDataLink(dataNode);
		objectList.add(io);
		io.setScreen(this);
	}
	public void toggle(){
		if(active)
			close();
		else
			open();
	}
	/**
	 * Open the panel
	 */
	public void open(){
		active=true;
		for(UIElement io:objectList)
			io.hover=false;
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
		for(InteractableObject io:objectList)
			if(io instanceof OriginObject)
				((OriginObject)io).close();
		active=false;
	}
	public boolean isActive(){
		return active;
	}
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
		
		g.setClip(x+2, y+2, width-4, height-4);
		for(UIElement io:objectList)
			io.UDraw(g);
		g.clearClip();
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		return item;
	}
	public InteractableObject getObject(int x, int y){
		if(!active)
			return null;
		for(InteractableObject io:objectList){
			if(io.hover=io.isMouseOver(x, y))
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
		for(InteractableObject io:objectList)
			io.hover=false;
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
		if(origin!=null){
			System.out.println("Panel Error: Attempted to change origin object. Operation canceled.");
			return;
		}
		origin=oo;
		move(oo.getX()-width/2,oo.getY()-height);
	}
	public void dMoveTreeUI(int dx, int dy){
		dmove(dx,dy);
		for(UIElement e:objectList){
			if(e instanceof OriginObject)
				((OriginObject)e).dMoveTreeUI(dx, dy);
		}
	}
}
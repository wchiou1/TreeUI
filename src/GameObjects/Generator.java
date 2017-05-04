package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import DataLinkNetwork.DataNetworkNode;
import TreeUI.UIItem;

//This class will connect to a datanode and broadcast it's power value

public class Generator extends GameObject{
	private int id;
	private DataNetworkNode node;
	private String key;
	public Generator(int id,int x, int y, DataNetworkNode node){
		this.id=id;
		this.x=x;
		this.y=y;
		this.node=node;
		key=":P Generator "+id;
	}

	@Override
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the generator sprite
		g.setColor(Color.green);
		g.fillOval(x, y, 20, 20);
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		return item;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		//Just broadcast power for now
		node.changeData(key, 600);
	}
	
}
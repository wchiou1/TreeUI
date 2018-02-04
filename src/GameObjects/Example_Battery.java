package GameObjects;

import org.newdawn.slick.Graphics;

import aspenNetwork.ANKeyWrapper;
import aspenNetwork.AspenNode;

public class Example_Battery extends PaneledGameObject{
	private ANKeyWrapper powerNode;
	private int internalPower;
	public Example_Battery(){
		internalPower = 0;
	}
	@Override
	public void setDataLink(AspenNode node){
		this.dataNode=node;
		powerNode = new ANKeyWrapper(node,":P");
	}
	@Override
	public int getCenterX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCenterY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
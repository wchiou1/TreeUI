package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import aspenNetwork.ANKeyWrapper;
import uiItem.UIItem;

public class LightBulb extends NonPaneledGameObject{
	public boolean powered=false;
	public boolean toggle=true;
	public boolean on;
	public int recordedPower;
	private ANKeyWrapper powerNode;
	public String toggleFreq="";
	private String powerFreq="";
	private int power_draw=0;
	public LightBulb(){
		powerFreq=":P Lightbulb "+id;
		powerNode = new ANKeyWrapper(getNode(),":P");
		on=true;
	}
	@Override
	public void update() {
		recordedPower = powerNode.getTotalValue();
		//Check if it has been ordered to turn off
		if(dataNode.getData(toggleFreq)==0)
			toggle=false;
		else if(dataNode.getData(toggleFreq)==1)
			toggle=true;
		//Only turn on if there is positive power, if there is negative power, turn off
		if(powerNode.getTotalValue()>0)
			powered=true;
		else if(powerNode.getTotalValue()<0)
			powered=false;
		on=powered&&toggle;
		if(on)
			dataNode.changeData(powerFreq, -200);
		else
			dataNode.changeData(powerFreq, 0);
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the lightbulb sprite
		if(on)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.gray);
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
		if(GameMath.dis(this.x+10, this.y+10, x, y)<=10)
			return true;
		return false;
	}

	@Override
	public int getCenterX() {
		// TODO Auto-generated method stub
		return x+10;
	}

	@Override
	public int getCenterY() {
		// TODO Auto-generated method stub
		return y+10;
	}
}
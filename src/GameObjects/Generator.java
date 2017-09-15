package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import uiItem.UIItem;

//This class will broadcast it's power value

public class Generator extends PaneledGameObject{
	private String powerFreq;
	public String toggleFreq;
	public Generator(){
		powerFreq=":P Generator "+id;
		toggleFreq="";
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the generator sprite
		if(dataNode.getData(toggleFreq)==1||dataNode.getData(toggleFreq)==Integer.MIN_VALUE)
			g.setColor(Color.green);
		else
			g.setColor(Color.darkGray);
		g.fillOval(x, y, 20, 20);
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+10, this.y+10, x, y)<=10)
			return true;
		return false;
	}

	@Override
	public void update() {
		//Check if it's toggled on
		if(dataNode.getData(toggleFreq)==1||dataNode.getData(toggleFreq)==Integer.MIN_VALUE)
			dataNode.changeData(powerFreq, 600);
		else
			dataNode.changeData(powerFreq, 0);
	}

	@Override
	public int getCenterX() {
		return x+10;
	}

	@Override
	public int getCenterY() {
		return y+10;
	}
	
}
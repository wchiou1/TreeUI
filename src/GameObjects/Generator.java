package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import GameLogic.GameMath;
import GameLogic.ImageLoader;
import uiItem.UIItem;

//This class will broadcast it's power value

public class Generator extends PoweredGameObject{
	private String powerFreq;
	public String toggleFreq;
	public int output;
	public Generator(){
		powerFreq=":P Generator "+id;
		output = 0;
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
	public void powerUpdate(int delta) {
		//Check if it's toggled on
		if(dataNode.getData(toggleFreq)==1||dataNode.getData(toggleFreq)==Integer.MIN_VALUE){
			if(output<600)
				output++;
		}
		else
		{
			if(output>0)
				output--;
		}	
			
		dataNode.changeData(powerFreq, output);
	}

	@Override
	public int getCenterX() {
		return x+10;
	}

	@Override
	public int getCenterY() {
		return y+10;
	}

	@Override
	public void objectUpdate(int x, int y, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectKeyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}
	
}
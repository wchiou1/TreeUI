package gameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import aspenNetwork.ANKeyWrapper;
import smallGameObjects.SmallGameObject;

public class LightBulb extends GPoweredGO{
	public String toggleFreq="";
	public LightBulb(){
		posPower = 0;
		negPower = 0;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x ,int y) {
		//Draw the lightbulb sprite
		if(power_draw<-150){
			g.setColor(new Color((int)Math.round(Math.abs((power_draw*1.0)/150)*255),(int)Math.round(Math.abs((power_draw*1.0)/150)*255),0));
		}else{
			g.setColor(new Color((int)Math.round(Math.abs((power_draw*1.0)/150)*255),(int)Math.round(Math.abs((power_draw*1.0)/150)*255),0));
		}
		g.fillOval(x, y, 20, 20);
		
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		return item;
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
package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import GameLogic.GameMath;
import GameLogic.ImageLoader;
import aspenNetwork.ANKeyWrapper;
import uiItem.UIItem;

public class Fan extends GPoweredGO{
	private Image frame,blades;
	public double angle = 0;
	public Fan(){
		powerFreq=":P Fan "+id;
		frame = ImageLoader.getImage("res/TreeUI/fan_frame.png");
		blades = ImageLoader.getImage("res/TreeUI/fan_blades.png");
	}
	@Override
	public void objectUpdate(int mouseX, int mouseY, int delta) {
		angle+=Math.abs(1.0*power_draw/200);
		angle=angle%360;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the lightbulb sprite
		frame.draw(x,y,20,20);
		blades.setRotation(Math.round(angle));
		blades.draw(x,y,20,20);
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
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
	@Override
	public void objectKeyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}
	
}
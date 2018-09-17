package gameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import GameLogic.GameMath;
import GameLogic.ImageLoader;
import world.Sun;

public class Example_SolarPanel extends PoweredGameObject{
	private String powerFreq;
	private int output;
	public double angle=0;
	public String target_freq="";
	public int target_angle = 0;
	public int delta=0;
	private Image image;
	public Example_SolarPanel(){
		powerFreq=":P Solar_Generator "+id;
		output = 0;
		image = ImageLoader.getImage("res/TreeUI/solar_panel.png");
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x, int y) {
		//Draw the generator sprite
		g.setColor(Color.gray);
		g.fillOval(x, y, 40, 40);
		image.setRotation(Math.round(angle));
		image.draw(x,y,40,40);
		
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+20, this.y+20, x, y)<=20)
			return true;
		return false;
	}

	@Override
	public void powerUpdate(int delta) {
		
		output = calcPowerOutput();
		dataNode.changeData(powerFreq, output);
	}
	
	private int calcPowerOutput(){
		int temp = (int)Math.round(Math.cos((Sun.getAngle()-angle)*Math.PI/180)*600);
		return temp>0 ? temp : 0;
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
		target_angle=dataNode.getData(target_freq);
		if(target_angle==Integer.MIN_VALUE)
			target_angle=0;
		target_angle+=360;
		target_angle%=360;
		angle+=360;
		angle%=360;
		this.delta=delta;
		//If we are not at the target angle we gotta change something
		if(target_angle!=angle){
			if(target_angle>angle)
				if(target_angle-angle<180)
					angle+=1.0*delta*200/1000;
				else
					angle-=1.0*delta*200/1000;
			else
				if(angle-target_angle<180)
					angle-=1.0*delta*200/1000;
				else
					angle+=1.0*delta*200/1000;
		}
		if(Math.abs(target_angle-angle)<1){
			angle=target_angle;
		}
		
	}

	
}
package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import GameLogic.GameMath;
import GameLogic.ImageLoader;
import aspenNetwork.ANKeyWrapper;
import uiItem.UIItem;

public class Fan extends NonPaneledGameObject{
	public boolean powered=false;
	public boolean toggle=true;
	public int recordedPower;
	private ANKeyWrapper powerNode;
	public String toggleFreq="";
	private String powerFreq="";
	public int power_draw;
	private Image frame,blades;
	public double angle = 0;
	public Fan(){
		powerFreq=":P Fan "+id;
		powerNode = new ANKeyWrapper(getNode(),":P");
		frame = ImageLoader.getImage("res/TreeUI/fan_frame.png");
		blades = ImageLoader.getImage("res/TreeUI/fan_blades.png");
		power_draw = 0;
	}
	@Override
	public void update(int delta) {
		recordedPower = powerNode.getTotalValue();
		//Check if it has been ordered to turn off
		if(dataNode.getData(toggleFreq)==0)
			toggle=false;
		else if(dataNode.getData(toggleFreq)==1)
			toggle=true;
		if(recordedPower>=0){
			if(power_draw>-200)
				power_draw--;
		}
		else{
			if(power_draw<0)//Release power in bursts
				power_draw-=Math.floor(1.0*power_draw/3);
		}
		angle+=Math.abs(1.0*power_draw/200);
		angle=angle%360;
		dataNode.changeData(powerFreq, power_draw);
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
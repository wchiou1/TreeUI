package gameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import TreeUI.InventorySlot;

//This class will broadcast it's power value

public class FuelContainer extends GameObject{
	public int fuel;
	public String fuelFreq;
	public FuelContainer(){
		fuel = 100000;
		fuelFreq = ""+id+" Fuel";
	}

	@Override
	public boolean isMoveable() {
		return false;
	}
	
	public int siphon(){
		if(fuel>0){
			fuel--;
			return 1;
		}
		return 0;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.orange);
		g.fillOval(x, y, 20, 20);
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+10, this.y+10, x, y)<=10)
			return true;
		return false;
	}
	@Override
	public void update(int x, int y, int delta){
		dataNode.changeData(fuelFreq, fuel);
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
	protected int getWidth() {
		return 20;
	}

	@Override
	protected int getHeight() {
		return 20;
	}
}
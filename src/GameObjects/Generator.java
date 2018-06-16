package gameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import GameLogic.GameMath;
import GameLogic.ImageLoader;
import TreeUI.InventorySlot;
import smallGameObjects.PumpHandle;
import smallGameObjects.SmallGameObject;

//This class will broadcast it's power value

public class Generator extends GPoweredGO{
	public String toggleFreq;
	public int output;
	public int fuel;
	public InventorySlot fuelIntake;
	public Generator(){
		output = 0;
		toggleFreq="";
		fuel = 0;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
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
		boolean on = dataNode.getData(toggleFreq)==1||dataNode.getData(toggleFreq)==Integer.MIN_VALUE;
		boolean fueled = false;
		if(on){
			if(fuelIntake!=null){
				SmallGameObject fuelHandle = fuelIntake.getStored();
				if(fuelHandle!=null&&fuelHandle instanceof PumpHandle){
					fueled = ((PumpHandle)fuelHandle).getFuel() == 1;
				}
			}
		}
		//Check if it's toggled on
		if(fueled){
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
	
	
}
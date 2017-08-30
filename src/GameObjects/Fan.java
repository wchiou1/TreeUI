package GameObjects;

import org.newdawn.slick.Graphics;

import uiItem.UIItem;

public class Fan extends NonPaneledGameObject{
	private int angle=0;
	@Override
	public void update() {
		// TODO Auto-generated method stub
		//Update animation frame and set power consumption
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//Draw the fan(will need to include angle of fan blades)
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		// TODO Auto-generated method stub
		return false;
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
	
}
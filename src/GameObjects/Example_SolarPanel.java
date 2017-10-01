package GameObjects;

import org.newdawn.slick.Graphics;

public class Example_SolarPanel extends NonPaneledGameObject{
	private String powerFreq;
	public Example_SolarPanel(){
		powerFreq=":P Generator "+id;
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
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
	
}
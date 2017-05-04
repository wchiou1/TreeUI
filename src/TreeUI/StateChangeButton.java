package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import GameLogic.GameMath;
import Test.SuperGlobal;

public class StateChangeButton extends UIElement{
	private int radius;
	private String targetState;
	private StateBasedGame parent;
	public StateChangeButton(StateBasedGame parent,int x, int y,String targetState){
		this(parent,x,y,6,targetState);
	}
	public StateChangeButton(StateBasedGame parent,int x, int y, int radius, String targetState){
		this.parent=parent;
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.targetState=targetState;
	}
	
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillOval(x, y, radius, radius);
		
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		parent.enterState(SuperGlobal.getGameState(targetState).getID());
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+radius/2, this.y+radius/2, x, y)<=radius/2)
			return true;
		return false;
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Buttons don't do anything when a key is pressed
		
	}
	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
	
}
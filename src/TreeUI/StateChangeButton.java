package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import GameLogic.GameMath;
import Test.SuperGlobal;
import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

public class StateChangeButton extends UIElement{
	public int radius = 6;
	public String targetState = "";
	private StateBasedGame parent;
	/*public StateChangeButton(StateBasedGame parent,int x, int y,String targetState){
		this(parent,x,y,6,targetState);
	}
	public StateChangeButton(StateBasedGame parent,int x, int y, int radius, String targetState){
		this.parent=parent;
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.targetState=targetState;
	}*/
	public StateChangeButton(){
		parent = SuperGlobal.getShell();
	}
	
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x,int y) {
		g.setColor(Color.darkGray);
		g.fillOval(x, y, radius, radius);
		
	}

	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
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
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}
	
}
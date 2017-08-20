package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;

//This class will connect to a datanode and broadcast it's power value

public class Generator extends PaneledGameObject{
	public String key;
	public Generator(){
		key=":P Generator "+id;
	}

	@Override
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the generator sprite
		g.setColor(Color.green);
		g.fillOval(x, y, 20, 20);
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+10, this.y+10, x, y)<=10)
			return true;
		return false;
	}

	@Override
	public void update() {
		//Just broadcast power for now
		dataNode.changeData(key, 600);
	}
	
}
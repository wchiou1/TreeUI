package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Key extends SmallGameObject{
	public int pattern;
	public int name;
	public int getPattern(){
		return pattern;
	}
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.red);
		g.fillOval(x, y, 3, 5);
	}
	@Override
	public int getCenterX() {
		return 2;
	}
	@Override
	public int getCenterY() {
		return 3;
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+3)
			if(y>=this.y&&y<=this.y+5)
				return true;
		return false;
	}
}
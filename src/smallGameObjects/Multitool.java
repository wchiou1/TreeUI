package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Multitool extends SmallGameObject{
	@Override
	public void draw(Graphics g,int x, int y) {
		x+=2;
		y+=3;
		g.setColor(Color.yellow);
		g.fillOval(x, y, 5, 10);
	}

	@Override
	public int getCenterX() {
		return x+3;
	}

	@Override
	public int getCenterY() {
		return y+5;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x-1&&x<=this.x+6)
			if(y>=this.y-1&&y<=this.y+11)
				return true;
		return false;
	}
}
package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Wirecutters extends SmallGameObject{
	@Override
	public void draw(Graphics g,int x, int y) {
		x+=2;
		y+=3;
		g.setColor(Color.red);
		g.drawLine(x, y, x+5, y+5);
		g.drawLine(x, y+5, x+5, y);
	}

	@Override
	public int getCenterX() {
		return x+3;
	}

	@Override
	public int getCenterY() {
		return y+3;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+5)
			if(y>=this.y&&y<=this.y+5)
				return true;
		return false;
	}
}
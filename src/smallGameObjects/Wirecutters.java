package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Wirecutters extends SmallGameObject{
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.red);
		g.drawLine(x, y, x+5, y+5);
		g.drawLine(x, y+5, x+5, y);
	}

	@Override
	public int getCenterX() {
		return 3;
	}

	@Override
	public int getCenterY() {
		return 3;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x-1&&x<=this.x+6)
			if(y>=this.y-1&&y<=this.y+6)
				return true;
		return false;
	}
}
package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import gameObjects.FuelContainer;

public class PumpHandle extends SmallGameObject{
	public FuelContainer source;
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.red);
		g.drawOval(x+3, y+3, 5, 5);
		g.drawLine(x, y, x+5, y+5);
		g.drawLine(x, y, x+3, y);
	}
	public int getFuel(){
		if(source==null)
			return 0;
		return source.siphon();
		
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
		if(x>=this.x&&x<=this.x+5)
			if(y>=this.y&&y<=this.y+5)
				return true;
		return false;
	}
}
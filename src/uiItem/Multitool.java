package uiItem;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Multitool extends UIItem{
	@Override
	public void draw(Graphics g,int x, int y) {
		x+=2;
		y+=3;
		g.setColor(Color.yellow);
		g.fillOval(x, y, 5, 10);
	}
}
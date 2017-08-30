package uiItem;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Key extends UIItem{
	private int pattern;
	public Key(int pattern, String name){
		this.name=name;
		this.pattern=pattern;
	}
	public int getPattern(){
		return pattern;
	}
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.red);
		g.fillOval(x, y, 3, 5);
	}
}
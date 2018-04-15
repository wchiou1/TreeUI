package smallGameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Key extends SmallGameObject{
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
	@Override
	public int getCenterX() {
		return x+2;
	}
	@Override
	public int getCenterY() {
		return y+3;
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+3)
			if(y>=this.y&&y<=this.y+5)
				return true;
		return false;
	}
}
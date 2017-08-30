package uiItem;

import org.newdawn.slick.Graphics;

/**
 * Item object for TreeUI item handling
 * @author Wesley
 *
 */
public abstract class UIItem{
	String name;
	public String getName(){
		return name;
	}
	public abstract void draw(Graphics g,int x, int y);
}
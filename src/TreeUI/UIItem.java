package TreeUI;

import org.newdawn.slick.Graphics;

/**
 * Item object for TreeUI item handling
 * @author Wesley
 *
 */
public abstract class UIItem{
	UIItem_Enum itemType;
	String name;
	public UIItem_Enum getItemType(){
		return itemType;
	}
	public String getName(){
		return name;
	}
	public abstract void draw(Graphics g,int x, int y);
}
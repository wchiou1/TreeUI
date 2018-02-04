package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import uiItem.UIItem;

public class TextBox extends StaticText{
	public String key="";
	/*public TextBox(int x, int y,String key){
		this.x=x;
		this.y=y;
		this.key=key;
	}
	TextBox(int x, int y){
		this.x=x;
		this.y=y;
	}*/

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		//Does nothing on click
		return item;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int mouseX, int mouseY,int delta) {
		int data=dataNode.getData(key);
		if(data==Integer.MIN_VALUE)
			text="NO SIGNAL";
		else
			text=""+data;
		
	}
}
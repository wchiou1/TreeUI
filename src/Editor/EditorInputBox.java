package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.RequiresTyping;
import Test.Shell;
import TreeUI.InputBox;
import smallGameObjects.SmallGameObject;

public class EditorInputBox extends InputBox implements EditorImmune{
	protected boolean keyLocked = false;
	public EditorInputBox(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	public EditorInputBox(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	@Override
	public void update(int x, int y,int delta){
		
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		if(!fleetingLock)
			text="";
		return item;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setClip(x+2, y+2, width-4, height-4);
		g.setFont(Shell.SMALL_FONT);
		if(fleetingLock)
			g.drawString(text+"|", x+2, y+2);
		else
			g.drawString(text, x+2, y+2);
		
	}
	
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	
	@Override
	public void processUniversalKeyPress(int key, char c) {
		//System.out.println(key+":"+c+"|"+(int)c);
		int code = (int)c;
		if(key==14){
			if(text.length()!=0)
				text=text.substring(0, text.length()-1);
		}
		else if((code>=65&&code<=90)||(code>=97&&code<=122)||code>=48&&code<=57){
			text+=c;
		}	
	}
	
}
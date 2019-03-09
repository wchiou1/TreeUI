package Editor.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Editor.EditorImmune;
import Editor.EditorInputBox;
import Test.Shell;
import TreeUI.TextButton;
import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

/**
 * This ui element will grab the class buttons and 
 * hide the ones which do not fit the search string
 * @author Wesley
 *
 */
public class EditorSearchBox extends EditorInputBox implements EditorImmune{
	protected boolean keyLocked = false;
	private ArrayList<UIElement> buttonList;
	public EditorSearchBox(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	public EditorSearchBox(int x, int y, int width, int height,ArrayList<UIElement> buttonList){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.buttonList = buttonList;
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
			if(text.length()!=0){
				text=text.substring(0, text.length()-1);
				sortButtons(text);
			}
		}
		else if((code>=65&&code<=90)||(code>=97&&code<=122)||code>=48&&code<=57){
			text+=c;
			//Sort the class buttons
			sortButtons(text);
		}	
	}
	private void sortButtons(String query){
		System.out.println(query);
		query = query.toLowerCase();
		ArrayList<UIElement> shown = new ArrayList<UIElement>();
		ArrayList<String> texts = new ArrayList<String>();
		Hashtable<String,UIElement> uielements = new Hashtable<String,UIElement>();
		//Reset the show variable
		for(UIElement tb:buttonList){
			tb.show = true;
			if(tb instanceof TextButton){
				String text = ((TextButton)tb).text.toLowerCase();
				if(!query.isEmpty()&&text.indexOf(query)==-1){
					tb.show = false;
				}
				else{
					shown.add(tb);
					texts.add(text);
					uielements.put(text, tb);
				}
			}
		}
		//Change the ry based on the bottom of the bar
		/*int barBottom = this.ry+20;
		//Get the text and put them in an array hashtable pair
		
		Collections.sort(texts);
		int iteration = 0;
		for(String text:texts){
			UIElement button = uielements.get(text);
			button.ry = barBottom + (iteration * 20);
			iteration++;
		}*/
		
		
	}
	
}
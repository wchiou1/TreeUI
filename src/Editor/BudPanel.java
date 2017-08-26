package Editor;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import TreeUI.UIElement;
import focusObject.Panel;

public class BudPanel extends Panel implements EditorImmune{
	private Bud origin;
	public BudPanel(Bud origin){
		this.active=true;
		this.origin=origin;
		//We need to create all the buttons related to the gameobjects
		//Where will we get the list from? Look in shell

		createUIButtons();

	}
	
	private void createUIButtons(){
		//First get the list of gameobjects
		ArrayList<Class<?>> classes = Shell.getUITypes();
		//We need to adjust the height of the panel
		this.height = (classes.size()+1)*20;
		
		for(int i=0;i<classes.size();i++){
			UIClassButton tempButton = new UIClassButton(origin,classes.get(i),0,i*20);
			addObject(tempButton);
		}
		UIClassButton tempButton = new UIClassButton(origin,null,0,classes.size()*20);
		addObject(tempButton);
		
	}
	public void clearReferences(){
		origin=null;
		objectList=null;
	}
	
	@Override
	public void draw(Graphics g) {
		if(!active)
			return;
		g.setColor(Color.black);
		g.drawLine(x+width/2, y+height/2, origin.getX()+2, origin.getY()+2);
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 2);
		g.setColor(Color.white);
		g.fillRoundRect(x+2, y+2, width-4, height-4, 2);
		
		for(UIElement io:objectList){
			g.setClip(x+2, y+2, width-4, height-4);
			io.UDraw(g);
		}
		g.clearClip();
	}
}
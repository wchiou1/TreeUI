package Editor;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import focusObject.Panel;
import focusObject.UIElement;

public class SaplingPanel extends Panel implements EditorImmune{
	private Sapling origin;
	public SaplingPanel(Sapling origin){
		this.active=true;
		this.origin=origin;
		//We need to create all the buttons related to the gameobjects
		//Where will we get the list from? Look in shell

		createGOButtons();

	}
	
	private void createGOButtons(){
		ArrayList<UIElement> buttons = new ArrayList<UIElement>();
		//First get the list of gameobjects
		ArrayList<Class<?>> classes = Shell.getGOTypes();
		classes.addAll(Shell.getSGOTypes());
		//We need to adjust the height of the panel
		this.height = (classes.size()+1)*20;
		System.out.println(classes);
		
		for(int i=0;i<classes.size();i++){
			GOClassButton tempButton = new GOClassButton(origin,classes.get(i),0,i*20);
			buttons.add(tempButton);
		}
		GOClassButton tempButton = new GOClassButton(origin,null,0,classes.size()*20);
		buttons.add(tempButton);
		addObjects(buttons);
		
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
			io.UDraw(g,0,0);
		}
		g.clearClip();
	}
}
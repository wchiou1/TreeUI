package Editor;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.Incubator;
import TreeUI.BasicPanelButton;
import TreeUI.UIElement;
import focusObject.OriginObject;
import focusObject.Panel;
import uiItem.UIItem;

/**
 * This class will be used by the editor as a placeholder for a UIElement
 * It generates it's own specialized panel which allows the user to change this obj into any UIElement
 * This class can be moved using the right click functionality
 * @author Wesley Chiou
 *
 */
public class Bud extends BasicPanelButton implements EditorImmune{
	//Saplings will NEVER be made by the incubator
	//The object the sapling turns into WILL need to be created by the incubator
	private Incubator inc;//Incubator is required to "morph" into a savable object
	private BudPanel BPanel;
	private Panel branch;
	public Bud(int x, int y, Incubator inc,Panel branch){
		this.inc=inc;
		this.rx=x-branch.x;
		this.ry=y-branch.y;
		this.x=x;
		this.y=y;
		this.width=5;
		this.height=5;
		this.branch=branch;
		//Create the panel
		BPanel = new BudPanel(this);
		inc.getManager().addObject(BPanel);
		BPanel.setOrigin(this);
	}
	public void morph(Class<?> objectType){
		if(objectType==null){
			System.out.println("Got a null type, morphing into nothing");
			//Destroy itself
			BPanel.clearReferences();
			destroy();
			return;
		}
		System.out.println("Morphing to "+objectType);
		//Time to morph! Let's not fuck this up...
		//We need to first create the object
		UIElement product;
		try {
			product = (UIElement)inc.getObject(inc.addUIElement(branch.getId(),objectType));
		
			product.rx=this.rx;
			product.ry=this.ry;
			
			
			//Check if the object is an originObject
			if(OriginObject.class.isAssignableFrom(objectType)){
				System.out.println("OriginObject detected, creating a panel");
				
				//Create the panel
				Panel panel = inc.getPanel(inc.addPanel());
				panel.enableEditing(inc);
				
				//Connect our new panel to our new object, use the incubator
				inc.setOrigin(panel.getId(), product.getId());
			}
		
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Destroy itself
		BPanel.clearReferences();
		destroy();
	}
	public void destroy(){
		branch.removeObject(this);
		inc.getManager().removeObject(BPanel);
		BPanel=null;
		
	}
	
	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if(highlight)
			g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
}
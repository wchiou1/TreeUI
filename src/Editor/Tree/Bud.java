package Editor.Tree;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Editor.EditorImmune;
import TreeUI.BasicPanelButton;
import focusObject.Incubator;
import focusObject.OriginObject;
import focusObject.Panel;
import focusObject.TreeUIManager;
import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

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
	private TreeUIManager tuim;
	private BudPanel BPanel;
	private Panel branch;
	public Bud(int x, int y, TreeUIManager tuim,Panel branch){
		this.tuim=tuim;
		this.rx=x-branch.x+branch.offsetX;
		this.ry=y-branch.y+branch.offsetY;
		this.x=x;
		this.y=y;
		this.width=5;
		this.height=5;
		this.branch=branch;
		//Create the panel
		BPanel=tuim.createBPanel(this);
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
		UIElement product = tuim.createUIElement(objectType, branch, this.rx, this.ry);
			
			
			//Check if the object is an originObject
			if(OriginObject.class.isAssignableFrom(objectType)){
				System.out.println("OriginObject detected, creating a panel");
				
				//Create the panel
				tuim.createPanel((OriginObject)product);
			}
		
		//Destroy itself
		BPanel.clearReferences();
		destroy();
	}
	public void destroy(){
		branch.removeObject(this);
		tuim.removeObject(BPanel);
		BPanel=null;
		
	}
	
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
}
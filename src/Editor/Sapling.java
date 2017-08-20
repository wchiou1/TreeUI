package Editor;

import FocusObject.InteractableObject;
import FocusObject.OriginObject;
import FocusObject.Panel;
import GameLogic.Incubator;
import GameObjects.BasicPaneledGameObject;
import TreeUI.UIItem;

/**
 * This class will be used by the editor as a placeholder for a gameobject
 * It generates it's own specialized panel which allows the user to change this obj into any gameobject
 * This class can be moved using the right click functionality
 * @author Wesley Chiou
 *
 */
public class Sapling extends BasicPaneledGameObject implements RightClickable{
	//Saplings will NEVER be made by the incubator
	//The object the sapling turns into WILL need to be created by the incubator
	private Incubator inc;//Incubator is required to "morph" into a savable object
	private SaplingPanel SPanel;
	public Sapling(int x, int y, Incubator inc){
		this.inc=inc;
		this.x=x;
		this.y=y;
		//Create the panel
		SPanel = new SaplingPanel(this);
		inc.getManager().addObject(SPanel);
		SPanel.setOrigin(this);
	}
	public void morph(Class<?> objectType){
		System.out.println("Morphing to "+objectType);
		//Time to morph! Let's not fuck this up...
		//We need to first create the object
		InteractableObject product = inc.getObject(inc.addObject(objectType));
		product.x=this.x;
		product.y=this.y;
		
		//Check if the object is an originObject
		if(OriginObject.class.isAssignableFrom(objectType)){
			System.out.println("OriginObject detected, creating and wrapping panel");
			//Alright, we need to make a panel! BUT we must wrap it first!
			//The incubator will not ever see the wrapper. We must remember to connect the panel and the origin object before saving!
			
			//Create the panel
			Panel wrappedPanel = inc.getPanel(inc.addPanel());
			
			//Wrap the panel
			EditorPanelWrapper wrapper = new EditorPanelWrapper(wrappedPanel);
			
			//Connect the wrapper to our new object, THIS CONNECTION IS NOT TO BE SAVED, DO NOT USE THE INCUBATOR
			wrapper.setOrigin((OriginObject)product);
			
			//Destroy itself
			//SPanel.destroy();
			//destroy();
			
			
		}
	}
	public void destroy(){
		inc.getManager().removeObject(this);
		SPanel=null;
		
	}
	
	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
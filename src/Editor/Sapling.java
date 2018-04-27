package Editor;

import focusObject.InteractableObject;
import focusObject.OriginObject;
import focusObject.Panel;
import focusObject.TreeUIManager;
import gameObjects.BasicPaneledGameObject;
import smallGameObjects.SmallGameObject;

/**
 * This class will be used by the editor as a placeholder for a gameobject
 * It generates it's own specialized panel which allows the user to change this obj into any gameobject
 * This class can be moved using the right click functionality
 * @author Wesley Chiou
 *
 */
public class Sapling extends BasicPaneledGameObject implements EditorImmune{
	//Saplings will NEVER be made by the incubator
	//The object the sapling turns into WILL need to be created by the incubator
	private TreeUIManager tuim;//Incubator is required to "morph" into a savable object
	private SaplingPanel SPanel;
	public Sapling(int x, int y, TreeUIManager tuim){
		this.tuimtuim;
		this.x=x;
		this.y=y;
		//Create the panel
		SPanel = new SaplingPanel(this);
		tuim.addObject(SPanel);
		SPanel.setOrigin(this);
	}
	public void morph(Class<?> objectType){
		if(objectType==null){
			System.out.println("Got a null type, morphing into nothing");
			//Destroy itself
			SPanel.clearReferences();
			destroy();
			return;
		}
		
		System.out.println("Morphing to "+objectType);
		//Time to morph! Let's not fuck this up...
		//We need to first create the object
		InteractableObject product = inc.getObject(inc.addObject(objectType));
		product.x=this.x;
		product.y=this.y;
		
		//Check if the object is an originObject
		if(OriginObject.class.isAssignableFrom(objectType)){
			System.out.println("OriginObject detected, creating and wrapping panel");
			
			//Create the panel
			Panel panel = inc.getPanel(inc.addPanel());
			panel.enableEditing(inc);
			
			//Connect our new panel to our new object, use the incubator
			inc.setOrigin(panel.getId(), product.getId());
		}
		//Destroy itself
		SPanel.clearReferences();
		destroy();
	}
	public void destroy(){
		inc.getManager().removeObject(this);
		inc.getManager().removeObject(SPanel);
		SPanel=null;
		
	}
	
	@Override
	public SmallGameObject rightClick(int x, int y, SmallGameObject item) {
		return item;
	}
	
}
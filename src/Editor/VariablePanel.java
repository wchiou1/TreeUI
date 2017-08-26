package Editor;

import java.lang.reflect.Field;

import GameLogic.Incubator;
import Test.SuperGlobal;
import focusObject.InteractableObject;
import focusObject.Panel;

/**
 * This panel will display the variables of the object it has stored
 * @author Wesley Chiou
 *
 */
public class VariablePanel extends Panel implements EditorImmune{
	private Incubator inc;
	private InteractableObject subject;
	public VariablePanel(Incubator inc){
		this.inc=inc;
		this.active=true;
		this.width=200;
		this.x = SuperGlobal.getWidth()-width;
		this.y = 0;
	}
	
	public void setObject(InteractableObject io){
		subject = io;
		//We need to destroy all our variable boxes and tune them to the new object
		objectList.clear();
		Field[] fields;
		try {
			fields = inc.getFields(io.getId());
			//First, change the height of this panel to fit all the boxes
			this.height = fields.length*20;
			
			
			//We got the fields, let's create the boxes
			for(int i=0;i<fields.length;i++)
				addObject(new VariableBox(0,i*20,inc,fields[i],io));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
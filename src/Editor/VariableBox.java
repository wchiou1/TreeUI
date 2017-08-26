package Editor;

import java.lang.reflect.Field;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.Incubator;
import Test.Shell;
import TreeUI.InputBox;
import TreeUI.UIItem;
import focusObject.InteractableObject;

/**
 * This UIElement will display the variables of the object it has listed
 * Need to think about how to
 * @author Wesley Chiou
 *
 */
public class VariableBox extends InputBox implements EditorImmune{
	private Field field;
	private InteractableObject object;
	private String prefix;
	private String postfix;
	private Incubator inc;
	private String type;
	private boolean error=false;
	public VariableBox(int x, int y,Incubator inc,Field field,InteractableObject subject){
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.width=180;
		this.field=field;
		this.object=subject;
		this.type=field.getType().getSimpleName();
		prefix = field.getName()+"("+type+"): ";
	}
	@Override
	public void update(int x, int y){
		if(type=="int")
			error = !isNumeric(postfix);
		try {
			//Only display the variable stuff if it's not selected for typing
			if(!fleetingLock){
				postfix = ""+field.get(object);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		}
		text = prefix+postfix;
	}
	
	@Override
	public UIItem click(int x, int y,UIItem item) {
		if(!fleetingLock){
			postfix="";
		}
		return item;
	}
	private void attemptVariableOverwrite(){
		System.out.println("Got "+field.getType().getSimpleName());
		if(field.getType()==int.class){
			System.out.println("Attempting to overwrite "+field.getName()+"("+field.getType().getSimpleName()+") to "+postfix);
			inc.writeParam(object.getId(), field.getName(), Integer.parseInt(postfix));
		}
		if(field.getType()==String.class){
			System.out.println("Attempting to overwrite "+field.getName()+"("+field.getType().getSimpleName()+") to "+postfix);
			inc.writeParam(object.getId(), field.getName(), postfix);
		}
	}
	public boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	@Override
	public void processUniversalKeyPress(int key, char c) {
		//We need to have it so that it attempts to apply the new variable to the object once enter has been pressed
		if(key==28){
			attemptVariableOverwrite();
			return;
		}
		else if(key==14){
			if(postfix.length()!=0)
				postfix=postfix.substring(0, postfix.length()-1);
		}
		else{
			postfix+=c;
		}	
	}
	
	@Override
	public void draw(Graphics g) {
		if(!error)
			g.setColor(Color.black);
		else
			g.setColor(Color.red);
		g.drawRect(x, y, width, height);
		
		g.setClip(x+2, y+2, width-4, height-4);
		g.setFont(Shell.SMALL_FONT);
		if(fleetingLock)
			g.drawString(text+"|", x+2, y+2);
		else
			g.drawString(text, x+2, y+2);
		
	}
	
}
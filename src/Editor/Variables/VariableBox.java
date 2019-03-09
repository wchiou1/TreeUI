package Editor.Variables;

import java.lang.reflect.Field;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Editor.EditorImmune;
import Editor.Item.ObjectGrabber;
import GameLogic.StringUtils;
import Test.Shell;
import TreeUI.InputBox;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

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
		this.x=x;
		this.y=y;
		this.inc=inc;
		this.width=180;
		this.field=field;
		this.object=subject;
		this.type=field.getType().getSimpleName();
		prefix = field.getName()+"("+type+"): ";
		try {
			postfix = ""+field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void update(int x, int y,int delta){
		isError();
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
	
	private void isError(){
		if(field.getType()==int.class)
			error = !StringUtils.isInteger(postfix);
		if(field.getType()==double.class)
			error = !StringUtils.isDouble(postfix);
		if(field.getType()==boolean.class)
			error = !StringUtils.isBoolean(postfix);
	}
	
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		if(item instanceof ObjectGrabber){
			System.out.println("ObjectGrabber detected:"+((ObjectGrabber)item).getStored());
			//Only allow the write if the object grabber has a CHILD of the current field
			if(((ObjectGrabber) item).getStored()!=null&&field.getType().isAssignableFrom(((ObjectGrabber) item).getStored().getClass())){
				inc.writeParam(object.getId(), field.getName(), ((ObjectGrabber)item).getStored().getId());
				((ObjectGrabber)item).clearGrabbed();
			}
		}
		if(!fleetingLock){
			postfix="";
		}
		return item;
	}
	private void attemptVariableOverwrite(){
		System.out.println("Got "+field.getType().getSimpleName());
		inc.writeParam(object.getId(), field.getName(), postfix);
	}
	
	
	@Override
	public void processUniversalKeyPress(int key, char c) {
		//We need to have it so that it attempts to apply the new variable to the object once enter has been pressed
		int code = (int)c;
		if(key==28){
			attemptVariableOverwrite();
			return;
		}
		else if(key==14){
			if(postfix.length()!=0)
				postfix=postfix.substring(0, postfix.length()-1);
		}
		else if(code>=32&&code<=126){
			postfix+=c;
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
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
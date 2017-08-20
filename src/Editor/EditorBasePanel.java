package Editor;

import FocusObject.Panel;
import GameLogic.Incubator;
import Test.SuperGlobal;
import TreeUI.UIElement;
import TreeUI.UIItem;

/**
 * This panel will act as the baseplate for the editor. This is to allow capturing of the mouse clicks in order to create new objects
 * This panel should NOT be used in place of ordinary panels
 * @author Wesley Chiou
 *
 */
public class EditorBasePanel extends Panel implements RightClickable{
	private Incubator inc;//We need to the incubator so we can allow saplings to create savable objects
	public EditorBasePanel(Incubator inc){
		this.x=0;
		this.y=0;
		this.width=SuperGlobal.getWidth();
		this.height=SuperGlobal.getLength();
		this.active=true;
		this.inc=inc;
	}
	public void close(){
		System.out.println("Error: Attempted to close EditorBasePanel. Operation canceled");
	}
	
	@Override
	public boolean isMoveable() {
		return false;
	}
	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		//When right-click is called we want to create a sapling at the mouse location
		System.out.println("BasePanel Right click at ("+x+","+y+")");
		//Now to create the sapling
		inc.getManager().addGameObject(new Sapling(x,y,inc));
		return null;
	}
}
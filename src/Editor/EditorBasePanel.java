package Editor;

import FocusObject.Panel;
import Test.SuperGlobal;

/**
 * This panel will act as the baseplate for the editor. This is to allow capturing of the mouse clicks in order to create new objects
 * This panel should NOT be used in place of ordinary panels
 * @author Wesley Chiou
 *
 */
public class EditorBasePanel extends Panel{
	public EditorBasePanel(){
		this.x=0;
		this.y=0;
		this.width=SuperGlobal.getWidth();
		this.height=SuperGlobal.getLength();
		this.active=true;
	}
	public void close(){
		System.out.println("Error: Attempted to close EditorBasePanel. Operation canceled");
	}
	
	@Override
	public boolean isMoveable() {
		return false;
	}
}
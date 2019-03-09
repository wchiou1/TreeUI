package focusObject;

import org.newdawn.slick.Graphics;

/**
 * Allows access to the relative coords and setting the parent panel
 * It will use the currently set coords for relative coords
 * Do NOT move the object before setting the screen!
 * You MUST call super.update if you want the UIElement to update it's position relative to the panel
 * Relative coords are immutable
 * @author Wesley
 *
 */
public abstract class UIElement extends InteractableObject{
	public Panel screen;
	
	public final void UDraw(Graphics g,int x, int y){
		draw(g,x,y);
	}
	@Override
	public boolean isMoveable(){
		return false;
	}
}

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
	public int rx=0,ry=0;
	/**
	 * Sets the parent panel and thus uses relative x and y instead of hard x and y
	 * NOTE: If this is not called, then the object will use the hard x and y for calculations
	 * @param screen
	 */
	void setScreen(Panel screen){
		if(this.screen!=null){
			System.out.println(""+this.getClass()+"UIElement Error: Attempted to change screen, operation canceled.");
			return;
		}
		this.screen=screen;
		x=this.screen.getX()+x+this.screen.offsetX;
		y=this.screen.getY()+y+this.screen.offsetY;
		
	}
	/**
	 * Updates the hard x and y based on parent panel and relative coords
	 */
	private void snapUpdate(int x, int y){
		if(screen!=null){
			this.x=screen.getX()+rx+x;
			this.y=screen.getY()+ry+y;
		}
	}
	
	public final void UDraw(Graphics g,int x, int y){
		snapUpdate(x,y);
		draw(g);
	}
	@Override
	public boolean isMoveable(){
		return false;
	}
}

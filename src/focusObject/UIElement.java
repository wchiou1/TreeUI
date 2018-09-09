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
	protected Panel screen;
	public int rx=0,ry=0;
	protected abstract int getWidth();
	protected abstract int getHeight();
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
		x=this.screen.getX()+x;
		y=this.screen.getY()+y;
		
	}
	/**
	 * Updates the hard x and y based on parent panel and relative coords
	 */
	private void snapUpdate(int x, int y){
		if(screen!=null){
			this.x=screen.getX()+rx;
			this.y=screen.getY()+ry;
		}
	}
	
	public final void UDraw(Graphics g){
		snapUpdate(0,0);
		draw(g);
	}
	@Override
	public boolean isMoveable(){
		return false;
	}
}

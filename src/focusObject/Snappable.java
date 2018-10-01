package focusObject;

/**
 * Adds functionality for supporting snapping
 * @author Wesley
 *
 */
public abstract class Snappable extends UIElement{
	public int height,width;
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
}
package Editor;

import TreeUI.TextButton;
import TreeUI.UIItem;

public class GOClassButton extends TextButton{
	private Class<?> GOClass;
	private Sapling origin;
	public GOClassButton(Sapling origin,Class<?> GOClass,int x, int y){
		this.rx=x;
		this.ry=y;
		this.origin=origin;
		this.GOClass=GOClass;
		this.text=GOClass.getSimpleName();
	}
	
	@Override
	public UIItem click(int x, int y,UIItem item) {
		//On click, it morphs the origin
		origin.morph(GOClass);
		return item;
	}
}
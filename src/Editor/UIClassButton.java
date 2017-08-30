package Editor;

import TreeUI.TextButton;
import uiItem.UIItem;

public class UIClassButton extends TextButton implements EditorImmune{
	private Class<?> UIClass;
	private Bud origin;
	public UIClassButton(Bud origin,Class<?> UIClass,int x, int y){
		this.rx=x;
		this.ry=y;
		this.origin=origin;
		this.UIClass=UIClass;
		if(UIClass==null)
			this.text="Delete";
		else
			this.text=UIClass.getSimpleName();
	}
	
	@Override
	public UIItem click(int x, int y,UIItem item) {
		//On click, it morphs the origin
		origin.morph(UIClass);
		return item;
	}
}
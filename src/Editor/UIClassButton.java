package Editor;

import TreeUI.TextButton;
import smallGameObjects.SmallGameObject;

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
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//On click, it morphs the origin
		origin.morph(UIClass);
		return item;
	}
}
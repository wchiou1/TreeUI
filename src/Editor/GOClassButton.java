package Editor;

import TreeUI.TextButton;
import smallGameObjects.SmallGameObject;

public class GOClassButton extends TextButton implements EditorImmune{
	private Class<?> GOClass;
	private Sapling origin;
	public GOClassButton(Sapling origin,Class<?> GOClass,int x, int y){
		this.rx=x;
		this.ry=y;
		this.origin=origin;
		this.GOClass=GOClass;
		if(GOClass==null)
			this.text="Delete";
		else
			this.text=GOClass.getSimpleName();
	}
	
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//On click, it morphs the origin
		origin.morph(GOClass);
		return item;
	}
}
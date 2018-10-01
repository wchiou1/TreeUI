package Editor.Save;

import Editor.EditorImmune;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.Panel;
import smallGameObjects.SmallGameObject;

public class SaveTreeButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private Panel source;
	private String saveType;
	public SaveTreeButton(int x, int y, String saveType,Incubator inc,Panel source){
		this.source=source;
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.width=180;
		this.text="Save Panel";
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//On click, it calls
		System.out.println(inc.IOScan(source.getId()));
		return item;
	}
}
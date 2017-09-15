package Editor;

import TreeUI.TextButton;
import focusObject.Panel;
import uiItem.UIItem;

public class SaveTreeButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private Panel source;
	public SaveTreeButton(int x, int y,Incubator inc,Panel source){
		this.source=source;
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.width=180;
		this.text="Save Panel";
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		//On click, it calls
		System.out.println(inc.IOScan(source.getId()));
		return item;
	}
}
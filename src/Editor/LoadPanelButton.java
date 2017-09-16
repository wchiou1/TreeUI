package Editor;

import TreeUI.InputBox;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.InteractableObject;
import uiItem.UIItem;

public class LoadPanelButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private InteractableObject source;
	private InputBox fileNameBox;
	public LoadPanelButton(int x, int y,Incubator inc,InteractableObject source,InputBox fileNameBox){
		this.source=source;
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.fileNameBox=fileNameBox;
		this.width=90;
		this.text="Load Panel";
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		//On click, it calls 
		if(fileNameBox.text.isEmpty()){
			System.out.println("Error: No filename");
			return item;
		}
		inc.readFileToObject(fileNameBox.text);
		return item;
	}
	
	public void setObject(InteractableObject io){
		source=io;
	}
}
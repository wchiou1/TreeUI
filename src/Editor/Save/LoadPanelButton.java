package Editor.Save;

import Editor.EditorImmune;
import TreeUI.InputBox;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

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
		this.width=80;
		this.text="Load Panel";
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
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
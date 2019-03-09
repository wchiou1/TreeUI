package Editor.Save;

import Editor.EditorImmune;
import TreeUI.InputBox;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

public class LoadEnvironmentButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private InputBox fileNameBox;
	public LoadEnvironmentButton(int x, int y,Incubator inc,InputBox fileNameBox){
		this.x=x;
		this.y=y;
		this.inc=inc;
		this.fileNameBox=fileNameBox;
		this.width=80;
		this.text="Load Environment";
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
}
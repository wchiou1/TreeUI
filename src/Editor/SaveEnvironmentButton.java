package Editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import TreeUI.InputBox;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

public class SaveEnvironmentButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private InputBox fileNameBox;
	public SaveEnvironmentButton(int x, int y,Incubator inc,InputBox fileNameBox){
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.fileNameBox=fileNameBox;
		this.width=80;
		this.text="Save Environment";
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//On click, it calls 
		System.out.println("Getting save string...");
		try {
			if(fileNameBox.text.isEmpty()){
				System.out.println("Error: No filename");
			}
			PrintWriter writer=new PrintWriter("nursery/"+fileNameBox.text+".tree","UTF-8");
			//loop through ALL gameobjects in the incubator
			writer.write(inc.IOScanAll());
			
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
}
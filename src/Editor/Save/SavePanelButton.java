package Editor.Save;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Editor.EditorImmune;
import TreeUI.InputBox;
import TreeUI.TextButton;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.SmallGameObject;

public class SavePanelButton extends TextButton implements EditorImmune{
	private Incubator inc;
	private InteractableObject source;
	private InputBox fileNameBox;
	public SavePanelButton(int x, int y,Incubator inc,InteractableObject source,InputBox fileNameBox){
		this.source=source;
		this.rx=x;
		this.ry=y;
		this.inc=inc;
		this.fileNameBox=fileNameBox;
		this.width=80;
		this.text="Save Panel";
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//On click, it calls 
		System.out.println("Getting save string...");
		System.out.println(inc.IOScan(source.getId()));
		try {
			if(fileNameBox.text.isEmpty()){
				System.out.println("Error: No filename");
			}
			PrintWriter writer=new PrintWriter("nursery/"+fileNameBox.text+".tree","UTF-8");
			writer.write(inc.IOScan(source.getId()));
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/*String[] parts=parseResult.split("\t",-1);
	    	int qid=Integer.parseInt(parts[0]);
	    	double gazeX=Double.parseDouble(parts[2]);
	    	double gazeY=Double.parseDouble(parts[3]);
	    	String[] trans={parts[4],parts[5]};
	    	double[] result=new double[2];
	    	//System.out.println(parseResult);
			writer.print(qid+"\t");
			writer.print(parts[1]+"\t");
			writer.print("graph: "+graph+"\t");*/
		return item;
	}
	public void setObject(InteractableObject io){
		source=io;
	}
}
package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.InteractableObject;
import uiItem.UIItem;

public class Selector extends UIItem implements EditorItem{
	private EditorCommandPanel ecp;
	//We need to set the variable panel somehow
	public Selector(EditorCommandPanel ecp ){
		this.ecp=ecp;
	}
	
	public EditorCommandPanel getEditorCommandPanel(){
		return ecp;
	}
	
	public void setNewSubject(InteractableObject io){
		System.out.println("Set variable panel to object "+io);
		ecp.setObject(io);
		ecp.openVPanel();
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.blue);
		g.fillRect(x+2, y+5, 10, 4);
		g.fillRect(x+5, y+2, 4, 10);
	}
	
}
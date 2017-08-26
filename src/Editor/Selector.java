package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.UIItem;
import focusObject.InteractableObject;

public class Selector extends UIItem implements EditorItem{
	private VariablePanel vp;
	//We need to set the variable panel somehow
	public Selector(VariablePanel vp){
		this.vp=vp;
	}
	
	public VariablePanel getVariablePanel(){
		return vp;
	}
	
	public void setNewSubject(InteractableObject io){
		System.out.println("Set variable panel to object "+io);
		vp.setObject(io);
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.blue);
		g.fillRect(x+2, y+5, 10, 4);
		g.fillRect(x+5, y+2, 4, 10);
	}
	
}
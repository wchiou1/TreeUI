package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.InteractableObject;
import focusObject.Panel;
import smallGameObjects.SmallGameObject;

public class Selector extends SmallGameObject implements EditorItem{
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
		g.fillRect(x, y+3, 10, 4);
		g.fillRect(x+3, y, 4, 10);
	}

	@Override
	public int getCenterX() {
		return 5;
	}

	@Override
	public int getCenterY() {
		return 5;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		//System.out.println("("+x+","+y+")("+this.x+","+this.y+")");
		if(x>=this.x&&x<=this.x+10)
			if(y>=this.y&&y<=this.y+10){
				//System.out.println("ON");
				return true;
			}
		return false;
	}
	
}
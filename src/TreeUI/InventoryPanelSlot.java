package TreeUI;

import Editor.EditorImmune;

public class InventoryPanelSlot extends InventorySlot implements EditorImmune{

	public InventoryPanelSlot(int x, int y) {
		super(x, y);
	}
	public UIItem getStored(){
		return stored;
	}
	public void forcePush(UIItem item){
		if(stored!=null)
			System.out.println("InventoryPanelSlot WARNING: forcePush overwrited item("+stored+")");
		stored=item;
	}
	
}
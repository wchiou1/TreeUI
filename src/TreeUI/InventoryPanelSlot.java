package TreeUI;

public class InventoryPanelSlot extends InventorySlot{

	public InventoryPanelSlot(int x, int y) {
		super(x, y);
	}
	public void forcePush(UIItem item){
		if(stored!=null)
			System.out.println("InventoryPanelSlot WARNING: forcePush overwrited item("+stored+")");
		stored=item;
	}
	
}
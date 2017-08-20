package Editor;

import FocusObject.Panel;
import GameLogic.Incubator;
import TreeUI.BasicPanelButton;
import TreeUI.UIItem;

/**
 * This class will be used by the editor as a placeholder for a UIElement
 * It generates it's own specialized panel which allows the user to change this obj into any UIElement
 * This class can be moved using the right click functionality
 * @author Wesley Chiou
 *
 */
public class Bud extends BasicPanelButton implements RightClickable{
		//Saplings will NEVER be made by the incubator
		//The object the sapling turns into WILL need to be created by the incubator
		private Incubator inc;//Incubator is required to "morph" into a savable object
		private BudPanel BPanel;
		public Bud(int x, int y, Incubator inc){
			this.inc=inc;
			this.rx=x;
			this.ry=y;
			//Create the panel
			BPanel = new BudPanel(this);
			inc.getManager().addObject(BPanel);
			BPanel.setOrigin(this);
		}
		public void morph(Class<?> objectType){
			
		}
		
		@Override
		public UIItem rightClick(int x, int y, UIItem item) {
			// TODO Auto-generated method stub
			return null;
		}
		
	
}
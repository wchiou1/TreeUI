package Editor;

import GameObjects.BasicPaneledGameObject;
import TreeUI.UIItem;

/**
 * This class will be used by the editor as a placeholder for a gameobject
 * It generates it's own specialized panel which allows the user to change this obj into any gameobject
 * This class can be moved using the right click functionality
 * @author Wesley Chiou
 *
 */
public class Sapling extends BasicPaneledGameObject implements EditorFunctional{

	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
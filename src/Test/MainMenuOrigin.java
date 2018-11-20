package Test;

import TreeUI.InputBox;
import gameObjects.BasicPaneledGameObject;

public class MainMenuOrigin extends BasicPaneledGameObject{
	public InputBox ip_input;
	public InputBox port_input;
	
	public void update(int mouseX, int mouseY,int delta) {
		SuperGlobal.ip = ip_input.text;
		if(port_input.text.length()!=0){
			SuperGlobal.port = Integer.parseInt(port_input.text);
		}
	}
}
package Editor;

import Test.SuperGlobal;
import focusObject.Panel;

public class FileScannerPanel extends Panel{
	public FileScannerPanel(){
		this.active=false;
		this.width=200;
		this.height=60;
		this.x = SuperGlobal.getWidth()-width;
		this.y = 0;
	}
}
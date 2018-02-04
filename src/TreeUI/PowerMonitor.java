package TreeUI;


import aspenNetwork.ANKeyWrapper;
import aspenNetwork.AspenNode;

public class PowerMonitor extends TextBox{

	//PowerMonitor uses a keywrapper instead of a direct key
	ANKeyWrapper powerNode;
	/*public PowerMonitor(int x, int y) {
		super(x, y);
	}*/
	@Override
	public void setDataLink(AspenNode node){
		this.dataNode=node;
		powerNode = new ANKeyWrapper(node,":P");
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {
		int data=powerNode.getTotalValue();
		if(data==Integer.MIN_VALUE)
			text = "NO SIGNAL";
		else
			text = ""+data;
		
		
	}
	
}
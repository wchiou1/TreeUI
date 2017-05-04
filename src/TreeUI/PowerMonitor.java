package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import DataLinkNetwork.DNKeyWrapper;
import DataLinkNetwork.DataNetworkNode;

public class PowerMonitor extends TextBox{

	//PowerMonitor uses a keywrapper instead of a direct key
	DNKeyWrapper powerNode;
	public PowerMonitor(int x, int y) {
		super(x, y);
	}
	@Override
	public void setDataLink(DataNetworkNode dataNode){
		this.dataNode=dataNode;
		powerNode=new DNKeyWrapper(dataNode,":P");
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		int data=powerNode.getTotalValue();
		if(data==Integer.MIN_VALUE)
			g.drawString("NO SIGNAL", x, y);
		else
			g.drawString(""+data, x, y);
		
		
	}
	
}
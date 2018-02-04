package aspenNetwork;

import java.util.ArrayList;

public class AspenNetwork{
	private ArrayList<AspenNode> nodes;
	private int updateSpeed;
	private int updateBuffer=0;
	public AspenNetwork(int updateSpeed){
		this.updateSpeed=updateSpeed;
		nodes=new ArrayList<AspenNode>();
	}
	
	public void add(AspenNode node){
		if(node==null){
			System.out.println("Attempted to add a null node to a datanetwork");
			return;
		}
		nodes.add(node);
	}
	
	public void update(int delta){
		if(updateSpeed==-1)
			return;
		updateBuffer+=delta;
		if(updateBuffer<updateSpeed)
			return;
		updateBuffer=0;
		for(AspenNode node:nodes)
			node.update();
		for(AspenNode node:nodes)
			node.transmit();
		for(AspenNode node:nodes)
			node.fillTransmitBuffer();
	}
	public void manualUpdate(){
		System.out.println("Manually updating the Aspen Network...");
		updateBuffer=0;
		for(AspenNode node:nodes)
			node.update();
		for(AspenNode node:nodes)
			node.transmit();
		for(AspenNode node:nodes)
			node.fillTransmitBuffer();
		System.out.println("Done!");
	}
}
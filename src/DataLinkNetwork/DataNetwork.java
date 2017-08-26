package DataLinkNetwork;

import java.util.ArrayList;

public class DataNetwork{
	private ArrayList<DataNetworkNode> nodes;
	public DataNetwork(){
		nodes=new ArrayList<DataNetworkNode>();
	}
	
	public void add(DataNetworkNode node){
		if(node==null){
			System.out.println("Attempted to add a null node to a datanetwork");
			return;
		}
		nodes.add(node);
	}
	
	public void update(){
		for(DataNetworkNode node:nodes)
			node.update();
	}
}
package DataLinkNetwork;

import java.util.ArrayList;

public class DataNetwork{
	private ArrayList<DataNetworkNode> nodes;
	public DataNetwork(){
		nodes=new ArrayList<DataNetworkNode>();
	}
	
	public void add(DataNetworkNode node){
		nodes.add(node);
	}
	
	public void update(){
		for(DataNetworkNode node:nodes)
			node.update();
	}
}
package DataLinkNetwork;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import focusObject.SmartInteger;

public class DataNetworkNode{
	static int nodeCount=0;
	private int nodeID;
	private boolean blocked;
	private ArrayList<DataNetworkNode> neighbors;
	private boolean changed;
	Hashtable<String,SmartInteger> dataLink;
	private Hashtable<String,SmartInteger> dataTime;//Keeps track of how recent the data it has is
	public DataNetworkNode(){
		neighbors=new ArrayList<DataNetworkNode>();
		dataLink=new Hashtable<String,SmartInteger>();
		dataTime=new Hashtable<String,SmartInteger>();
		changed=false;
		blocked=false;
		nodeID=nodeCount;
		nodeCount++;
	}
	public int getID(){
		return nodeID;
	}
	public void unblock(){
		blocked=false;
	}
	public void block(){
		blocked=true;
	}
	public int getData(String key){
		if(!dataLink.containsKey(key))
			return Integer.MIN_VALUE;
		return dataLink.get(key).value;
	}
	public void changeData(String key,int data){
		if(!dataLink.containsKey(key)){
			dataLink.put(key, new SmartInteger(data));
			dataTime.put(key, new SmartInteger(5000));
		}
		else{
			dataLink.get(key).value=data;
			dataTime.get(key).value=5000;
		}
	}
	public void addNeighbor(DataNetworkNode node){
		neighbors.add(node);
	}
	public void changeNetwork(){
		changed=true;
		preClear();
		clear();
	}
	protected void preClear(){
		if(changed)
			return;
		changed=true;
		for(DataNetworkNode node:neighbors)
			node.preClear();
	}
	protected void clear(){
		if(!changed)
			return;
		changed=false;
		dataLink.clear();
		for(DataNetworkNode node:neighbors)
			node.clear();
	}
	private void tick(){
		Enumeration<String> keys=dataTime.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(dataTime.get(key).value>0)
				dataTime.get(key).value--;
		}
	}
	public void update(){
		if(changed)
			return;
		tick();
		if(blocked)
			return;
		transmit();
		
	}
	protected void recieve(Hashtable<String,SmartInteger> foreignData,Hashtable<String,SmartInteger> foreignTime){
		if(blocked)
			return;
		Enumeration<String> keys=foreignData.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(!dataTime.containsKey(key)||foreignTime.get(key).value>dataTime.get(key).value){
				dataLink.put(key, foreignData.get(key));
				dataTime.put(key, foreignTime.get(key));
			}
		}
	}
	private void transmit(){
		for(DataNetworkNode node:neighbors){
			node.recieve(dataLink,dataTime);
		}
	}
}
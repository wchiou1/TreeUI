package aspenNetwork;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import focusObject.InteractableObject;
import focusObject.SmartInteger;
import gameObjects.GameObject;
import gameObjects.LightBulb;

public class AspenNode{
	static int nodeCount=0;
	private int nodeID;
	private boolean blocked;
	private ArrayList<AspenNode> neighbors;
	private boolean changed;
	Hashtable<String,SmartInteger> dataLink;
	Hashtable<String,SmartInteger> transmitBuffer;
	private Hashtable<String,SmartInteger> dataTime;//Keeps track of how recent the data it has is
	private Hashtable<String,SmartInteger> dataTimeBuffer;
	private GameObject parent;
	public AspenNode(GameObject parent){
		neighbors=new ArrayList<AspenNode>();
		dataLink=new Hashtable<String,SmartInteger>();
		transmitBuffer = new Hashtable<String,SmartInteger>();
		dataTimeBuffer = new Hashtable<String,SmartInteger>();
		dataTime=new Hashtable<String,SmartInteger>();
		changed=false;
		blocked=false;
		nodeID=nodeCount;
		nodeCount++;
		this.parent=parent;
	}
	public ArrayList<AspenNode> getNeighbors(){
		return (ArrayList<AspenNode>) neighbors.clone();
	}
	public GameObject getParent(){
		return parent;
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
		if(!dataLink
				.containsKey(key))
			return Integer.MIN_VALUE;
		return dataLink.get(key).value;
	}
	public void changeData(String key,int data){
		if(key.isEmpty())
			return;
		if(!dataLink.containsKey(key)){
			dataLink.put(key, new SmartInteger(data));
			dataTime.put(key, new SmartInteger(5000));
		}
		else{
			dataLink.get(key).value=data;
			dataTime.get(key).value=5000;
		}
	}
	public void addNeighbor(AspenNode node){
		neighbors.add(node);
	}
	public boolean checkNeighbor(AspenNode node){
		return neighbors.contains(node);
	}
	public void removeNeighbor(AspenNode node){
		neighbors.remove(node);
	}
	public void changeNetwork(){
		System.out.println("Clearing network");
		preClear();
		clear();
	}
	protected void preClear(){
		if(changed)
			return;
		changed=true;
		for(AspenNode node:neighbors)
			node.preClear();
	}
	protected void clear(){
		if(!changed)
			return;
		changed=false;
		dataLink.clear();
		transmitBuffer.clear();
		dataTime.clear();
		dataTimeBuffer.clear();
		for(AspenNode node:neighbors)
			node.clear();
	}
	private void tick(){
		Enumeration<String> keys=dataTime.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(dataTime.get(key).value>0)
				dataTime.get(key).value--;
			if(dataTimeBuffer.containsKey(key)&&dataTimeBuffer.get(key).value>0)
				dataTimeBuffer.get(key).value--;
		}
	}
	void update(){
		if(changed)
			return;
		tick();
	}
	protected void recieve(Hashtable<String,SmartInteger> foreignData,Hashtable<String,SmartInteger> foreignTime){
		if(blocked)
			return;
		Enumeration<String> keys=foreignData.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(!dataLink.containsKey(key)||foreignTime.get(key).value>dataTime.get(key).value){
				
				dataLink.put(key, ((SmartInteger)foreignData.get(key).clone()));
				dataTime.put(key, ((SmartInteger)foreignTime.get(key).clone()));
			}
		}
	}
	void fillTransmitBuffer(){
		//Takes the datalink hashtable and puts it's contents in the transmit buffer
		transmitBuffer.clear();
		dataTimeBuffer.clear();

		Enumeration<String> keys=dataLink.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			transmitBuffer.put(key, ((SmartInteger)dataLink.get(key).clone()));
			dataTimeBuffer.put(key, ((SmartInteger)dataTime.get(key).clone()));
		}
		
	}
	void transmit(){
		if(blocked)
			return;
		
		for(AspenNode node:neighbors)
			node.recieve(transmitBuffer,dataTimeBuffer);
		
	}
}
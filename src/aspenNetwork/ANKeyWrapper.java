package aspenNetwork;

import java.util.Enumeration;
import java.util.Hashtable;


//This class wraps a datanode and filters out specific broadcasts based on the string
//and compiles math data based on that
//For instance: Broadcast of ":P Generator 1" with value of 600 would be translated
//to 600 power. Broadcast of ":P LightBulb 1" with value of -200 would be a drain of
//200 power.
public class ANKeyWrapper{
	private AspenNode node;
	private String wrapkey;
	public ANKeyWrapper(AspenNode node,String key){
		this.node=node;
		this.wrapkey=key;
	}
	public int getTotalValue(){
		int total=0;
		Enumeration<String> keys=node.dataLink.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(key.startsWith(wrapkey)){
				total+=node.dataLink.get(key).value;
			}
		}
		return total;
	}
	public int getPositiveTotal(){
		int total=0;
		Enumeration<String> keys=node.dataLink.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(key.startsWith(wrapkey)){
				if(node.dataLink.get(key).value>0)
					total+=node.dataLink.get(key).value;
			}
		}
		return total;
	}
	public int getNegativeTotal(){
		int total=0;
		Enumeration<String> keys=node.dataLink.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(key.startsWith(wrapkey)){
				if(node.dataLink.get(key).value<0)
					total+=node.dataLink.get(key).value;
			}
		}
		return total;
	}
	
	public Hashtable<String,Integer> getKeyList(){
		Hashtable<String,Integer> list=new Hashtable<String,Integer>();
		Enumeration<String> keys=node.dataLink.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(key.startsWith(":P"))
				list.put(key, node.dataLink.get(key).value);
		}
		return list;
	}
	
	
}
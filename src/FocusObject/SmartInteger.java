package focusObject;

public class SmartInteger{
	public int value;
	public SmartInteger(){
		value=0;
	}
	public SmartInteger(int value){
		this.value=value;
	}
	public Object clone(){
		return new SmartInteger(value);
	}
	public String toString(){
		return ""+value;
	}
}
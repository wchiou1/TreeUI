package world;

public class Sun{
	public static int getAngle(){
		return Math.round(System.currentTimeMillis()/1000%360);
	}
}
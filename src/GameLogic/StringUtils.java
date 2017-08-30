package GameLogic;

public class StringUtils{
	public static boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}
	public static boolean isBoolean(String s){
		return s.equalsIgnoreCase("TRUE")||s.equalsIgnoreCase("FALSE")||s.equalsIgnoreCase("1")||s.equalsIgnoreCase("0");
	}
}
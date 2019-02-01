package GameLogic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConfigReader{
	public static Hashtable<String,String> configs = new Hashtable<String,String>();
	public static void readConfig(String directory){
		try {
			BufferedReader br = new BufferedReader(new FileReader(directory));
			String line;
			while((line = br.readLine()) != null){
				if(line.contains(":")){
					String[] parts = line.split(":");
					String type = parts[0];
					String value = parts[1];
					configs.put(type,value);
				}
				else{
					System.err.println("No colon found in config line;"+line);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("No config found at "+directory);
		} catch (IOException e) {
			System.err.println("Unexpected eof");
			e.printStackTrace();
		}
	}
	public static void writeConfig(String directory){
		try {
			FileWriter fw = new FileWriter(directory);
			Enumeration<String> types = configs.keys();
			while(types.hasMoreElements()){
				String type = types.nextElement();
				String value = configs.get(type);
				fw.write(type+":"+value+"\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getConfig(String type){
		if(configs.containsKey(type))
			return configs.get(type);
		return "";
	}
	public static void setConfig(String type,String value){
		configs.put(type, value);
	}
}
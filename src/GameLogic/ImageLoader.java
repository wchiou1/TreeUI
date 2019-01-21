package GameLogic;

import java.util.Hashtable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader{
	private static  Hashtable<String,Image> images = new Hashtable<String,Image>();
	
	public static synchronized Image getImage(String url){
		try {
		if(!images.containsKey(url))
			images.put(url, new Image(url));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return images.get(url);
	}
}
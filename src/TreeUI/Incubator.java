package TreeUI;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import FocusObject.InteractableObject;
import FocusObject.Panel;

public class Incubator{
	private int objectCount = 0;//Total
	
	public Incubator(){}
	Hashtable<Integer,Panel> panels = new Hashtable<Integer,Panel>();
	Hashtable<Integer,InteractableObject> objects = new Hashtable<Integer,InteractableObject>();
	public int addPanel(){
		objectCount++;
		panels.put(objectCount, new Panel());
		return objectCount;
	}
	public void removePanel(int panelID){
		if(!panels.containsKey(panelID)){
			System.out.println("Error in Incubator-removePanel:PanelID is invalid("+panelID+")");
			return;
		}
		panels.remove(panelID);
	}
	/**
	 * Adds a UIElement of given type to the panel with objectID given, error checks if object is of type Panel
	 * @return object id of the new uielement, -1 if there was an error
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int addUIElement(int panelID,Class<?> elementType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//Errorcheck for panel
		if(panelID>objectCount||panelID<0){
			System.out.println("Error in Incubator-addUIElement:Invalid ID("+panelID+")");
			return -1;
		}
		//Check if the thing is actually a panel
		if(!panels.containsKey(panelID)){
			System.out.println("Error in Incubator-addUIElement:NOT A PANEL("+panelID+")");
			return -1;
		}
		
		Panel selectedPanel = panels.get(panelID);
		
		Object newElement = elementType.getConstructor().newInstance();
		
		//Check elementType is of instance UIElement
		if(!(newElement instanceof UIElement)){
			System.out.println("Error in Incubator-addUIElement:NOT A PANEL("+panelID+")");
			return -1;
		}
		
		selectedPanel.addObject((UIElement)newElement);
		
		objectCount++;
		return objectCount;
	}
	/**
	 * Accepts an objectID and a parameter string and creates
	 * @param objectID
	 * @param param
	 */
	public void writeParam(int objectID, String param, Object arg){
		//Check if the thing is contained in either hashtable
		if(!objects.containsKey(objectID)&&!panels.containsKey(objectID)){
			System.out.println("Error in Incubator-writeParam:Invalid ObjectID("+objectID+")");
			return;
		}
		
		InteractableObject io = null;
		if(objects.containsKey(objectID))
			io = objects.get(objectID);
		else
			io = panels.get(objectID);
			
		//Let's get the possible params
		Field superField = findUnderlying(io.getClass(),param);
		try {
			superField.set(io, arg);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static Field findUnderlying(Class<?> clazz, String fieldName) {
	    Class<?> current = clazz;
	    do {
	       try {
	           return current.getDeclaredField(fieldName);
	       } catch(Exception e) {}
	    } while((current = current.getSuperclass()) != null);
	    return null;
	}
}
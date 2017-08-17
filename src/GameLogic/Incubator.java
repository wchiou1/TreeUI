package GameLogic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import DataLinkNetwork.DataNetwork;
import FocusObject.InteractableObject;
import FocusObject.OriginObject;
import FocusObject.Panel;
import FocusObject.TreeUIManager;
import TreeUI.UIElement;

/**
 * This class will create a panel tree.
 * How will we attach it to a origin object?
 * The Incubator will be able to create everything, it will have a save feature
 * @author Wesley Chiou
 */
public class Incubator{
	private int objectCount = 0;//Total
	private TreeUIManager tuim;
	private DataNetwork dn;
	public Incubator(TreeUIManager tuim, DataNetwork dn){
		this.tuim=tuim;
		this.dn=dn;
	}
	Hashtable<Integer,Panel> panels = new Hashtable<Integer,Panel>();
	Hashtable<Integer,InteractableObject> objects = new Hashtable<Integer,InteractableObject>();
	public int addPanel(){
		objectCount++;
		Panel p = new Panel();
		panels.put(objectCount,p);
		tuim.addObject(panels.get(objectCount));
		dn.add(p.getNode());
		return objectCount;
	}
	public void removePanel(int panelID){
		if(!panels.containsKey(panelID)){
			System.out.println("Error in Incubator-removePanel:PanelID is invalid("+panelID+")");
			return;
		}
		panels.remove(panelID);
	}
	public int addObject(Class<?> objectType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Object newObject = objectType.getConstructor().newInstance();
		
		//Check elementType is of instance InteractableObject
		if(!(newObject instanceof InteractableObject)){
			System.out.println("Error in Incubator-addUIElement:Not an InteractableObject("+objectType+")");
			return -1;
		}
		
		//Check if the element is of type OriginObject
		if(newObject instanceof OriginObject){
			//Add the datanode to the datanodenetwork
			dn.add(((OriginObject)newObject).getNode());
		}
		
		objectCount++;
		objects.put(new Integer(objectCount), (InteractableObject)newObject);
		tuim.addObject(objects.get(objectCount));
		return objectCount;
	}
	public void loadPanelString(String panelStr){
		
	}
	public String getPanelString(int panelID){
		return "";
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
		if(!containsPanel(panelID)){
			System.out.println("Error in Incubator-addUIElement:Not a Panel("+panelID+")");
			return -1;
		}
		
		Panel selectedPanel = panels.get(panelID);
		
		Object newElement = elementType.getConstructor().newInstance();
		
		//Check elementType is of instance UIElement
		if(!(newElement instanceof UIElement)){
			System.out.println("Error in Incubator-addUIElement:Not a UIElement("+elementType+")");
			return -1;
		}
		
		selectedPanel.addObject((UIElement)newElement);
		
		objectCount++;
		objects.put(new Integer(objectCount), (UIElement)newElement);
		
		return objectCount;
	}
	/**
	 * Accepts an objectID and a parameter string and creates
	 * @param objectID
	 * @param param
	 */
	public void writeParam(int objectID, String param, Object arg){
		//Check if the thing is contained in either hashtable
		if(!objectExists(objectID)){
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
	/**
	 * 
	 */
	public void connectNodes(int objectID1,int objectID2){
		//Check object1
		if(!objectExists(objectID1)){
			System.out.println("Error in Incubator-connectDataNetworkNodes:Invalid ObjectID("+objectID1+")");
			return;
		}
		
		InteractableObject io1 = null;
		if(objects.containsKey(objectID1))
			io1 = objects.get(objectID1);
		else
			io1 = panels.get(objectID1);
		
		//Check object2
		if(!objectExists(objectID2)){
			System.out.println("Error in Incubator-connectDataNetworkNodes:Invalid ObjectID("+objectID2+")");
			return;
		}
		
		InteractableObject io2 = null;
		if(objects.containsKey(objectID2))
			io2 = objects.get(objectID2);
		else
			io2 = panels.get(objectID2);
		
		io1.getNode().addNeighbor(io2.getNode());
	}
	
	/**
	 * Sets the origin object for the panelID, You need to add the origin object as a UIElement BEFORE setting the origin
	 * @param panelID
	 * @param originID
	 */
	public void setOrigin(int panelID,int originID){
		if(!containsPanel(panelID)){
			System.out.println("Error in Incubator-setOrigin:NOT A Panel("+panelID+")");
			return;
		}
		if(!objectExists(originID)){
			System.out.println("Error in Incubator-setOrigin:Object DNE("+originID+")");
			return;
		}
		
		
		Panel selectedPanel = panels.get(panelID);
		
		Object selectedObject = objects.get(originID);
		
		//Check elementType is of instance OriginObject
		if(!(selectedObject instanceof OriginObject)){
			System.out.println("Error in Incubator-setOrigin:Not an OriginObject("+originID+")");
			return;
		}
		
		selectedPanel.setOrigin((OriginObject)selectedObject);
	} 
	public Field[] getFields(int objectID) throws IllegalArgumentException, IllegalAccessException{
		if(!objectExists(objectID)){
			System.out.println("Error in Incubator-getFields:Object DNE("+objectID+")");
			return null;
		}
		Object selectedObject = objects.get(objectID);
		Field[] fields = selectedObject.getClass().getFields();
		for(Field f:fields){
			System.out.println(f.getName()+":"+f.get(selectedObject));
		}
			
		return fields;
	
	}
	
	private boolean objectExists(int objectID){
		return objects.containsKey(objectID)||panels.containsKey(objectID);
	}
	
	private boolean containsPanel(int panelID){
		return panels.containsKey(panelID);
	}
	
	private Field findUnderlying(Class<?> clazz, String fieldName) {
	    Class<?> current = clazz;
	    do {
	       try {
	           return current.getDeclaredField(fieldName);
	       } catch(Exception e) {}
	    } while((current = current.getSuperclass()) != null);
	    return null;
	}
}
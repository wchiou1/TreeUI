package Editor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import GameLogic.StringUtils;
import GameObjects.GameObject;
import Imported.ClassFinder;
import aspenNetwork.AspenNetwork;
import focusObject.InteractableObject;
import focusObject.OriginObject;
import focusObject.Panel;
import focusObject.TreeUIManager;
import focusObject.UIElement;

/**
 * This class will create a panel tree. How will we attach it to a origin
 * object? The Incubator will be able to create everything, it will have a save
 * feature
 * 
 * The purpose of this class is to combine classloading with a monitor so we can
 * save the Tree ANY OBJECT YOU CREATE WITHOUT THE INCUBATOR CANNOT BE SAVED
 * 
 * @author Wesley Chiou
 */
public class Incubator {
	// private int objectCount = 0;
	// Use the built in IO counter, don't use this pos
	private TreeUIManager tuim;
	private AspenNetwork dn;

	public Incubator(TreeUIManager tuim, AspenNetwork dn) {
		this.tuim = tuim;
		this.dn = dn;
	}

	Hashtable<Integer, Panel> panels = new Hashtable<Integer, Panel>();
	Hashtable<Integer, InteractableObject> objects = new Hashtable<Integer, InteractableObject>();

	public TreeUIManager getManager() {
		return tuim;
	}

	public int addPanel() {
		Panel p = new Panel();
		panels.put(p.getId(), p);
		tuim.addObject(panels.get(p.getId()));
		return p.getId();
	}

	public void removePanel(int panelID) {
		if (!panels.containsKey(panelID)) {
			System.out.println("Error in Incubator-removePanel:PanelID is invalid(" + panelID + ")");
			return;
		}
		panels.remove(panelID);
	}

	public InteractableObject getEither(int objectID) {
		if (!objectExists(objectID))
			return null;

		InteractableObject io = null;
		if (objects.containsKey(objectID))
			io = objects.get(objectID);
		else
			io = panels.get(objectID);
		return io;
	}

	public Panel getPanel(int panelID) {
		return panels.get(panelID);
	}

	public InteractableObject getObject(int objectID) {
		return objects.get(objectID);
	}

	public int addObject(Class<?> objectType) {

		Object newObject;
		try {
			newObject = objectType.getConstructor().newInstance();

			// Check elementType is of instance InteractableObject
			if (!(newObject instanceof InteractableObject)) {
				System.out.println("Error in Incubator-addUIElement:Not an InteractableObject(" + objectType + ")");
				return -1;
			}

			// Check if the element is of type GameObject
			if (newObject instanceof GameObject) {
				// Add the datanode to the datanodenetwork
				dn.add(((GameObject) newObject).getNode());
			}
			int objId = ((InteractableObject) newObject).getId();
			objects.put(new Integer(objId), (InteractableObject) newObject);
			if (newObject instanceof GameObject)
				tuim.addGameObject(objects.get(objId));
			else
				tuim.addObject(objects.get(objId));
			return objId;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * Adds a UIElement of given type to the panel with objectID given, error
	 * checks if object is of type Panel
	 * 
	 * @return object id of the new uielement, -1 if there was an error
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public int addUIElement(int panelID, Class<?> elementType) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// Errorcheck for panel
		if (panelID > InteractableObject.getCount() || panelID < 0) {
			System.out.println("Error in Incubator-addUIElement:Invalid ID(" + panelID + ")");
			return -1;
		}
		// Check if the thing is actually a panel
		if (!containsPanel(panelID)) {
			System.out.println("Error in Incubator-addUIElement:Not a Panel(" + panelID + ")");
			return -1;
		}

		Panel selectedPanel = panels.get(panelID);

		Object newElement = elementType.getConstructor().newInstance();

		// Check elementType is of instance UIElement
		if (!(newElement instanceof UIElement)) {
			System.out.println("Error in Incubator-addUIElement:Not a UIElement(" + elementType + ")");
			return -1;
		}

		int objId = ((UIElement) newElement).getId();
		selectedPanel.addObject((UIElement) newElement);

		objects.put(new Integer(objId), (UIElement) newElement);

		return objId;
	}

	/**
	 * Accepts an objectID and a parameter string and writes the object to the
	 * parameter
	 * 
	 * @param objectID
	 * @param param
	 */
	public void writeParam(int objectID, String param, Object arg) {
		// Check if the thing is contained in either hashtable
		if (!objectExists(objectID)) {
			System.out.println("Error in Incubator-writeParam:Invalid ObjectID(" + objectID + ")");
			return;
		}

		InteractableObject io = null;
		if (objects.containsKey(objectID))
			io = objects.get(objectID);
		else
			io = panels.get(objectID);

		// Let's get the possible params
		Field superField = findUnderlying(io.getClass(), param);
		try {
			if (arg instanceof String) {
				String stringArg = (String) arg;
				if (superField.getType() == int.class) {
					if (StringUtils.isNumeric(stringArg)) {
						superField.set(io, Integer.parseInt(stringArg));
					} else {
						System.out.println(
								stringArg + " failed errorchecking for type " + superField.getType().getSimpleName());
					}
				}
				if (superField.getType() == String.class) {
					superField.set(io, stringArg);
				}
				if (superField.getType() == boolean.class) {
					if (StringUtils.isBoolean(stringArg)) {
						superField.set(io, Boolean.parseBoolean(stringArg));
					} else {
						System.out.println(
								stringArg + " failed errorchecking for type " + superField.getType().getSimpleName());
					}

				}
				// We've already processed this, terminate the method
				return;
			}

			superField.set(io, arg);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public void connectNodes(int objectID1, int objectID2) {
		// Check object1
		if (!objectExists(objectID1)) {
			System.out.println("Error in Incubator-connectDataNetworkNodes:Invalid ObjectID(" + objectID1 + ")");
			return;
		}

		InteractableObject io1 = null;
		if (objects.containsKey(objectID1))
			io1 = objects.get(objectID1);
		else
			io1 = panels.get(objectID1);

		// Check object2
		if (!objectExists(objectID2)) {
			System.out.println("Error in Incubator-connectDataNetworkNodes:Invalid ObjectID(" + objectID2 + ")");
			return;
		}

		InteractableObject io2 = null;
		if (objects.containsKey(objectID2))
			io2 = objects.get(objectID2);
		else
			io2 = panels.get(objectID2);

		io1.getNode().addNeighbor(io2.getNode());
	}

	/**
	 * Sets the origin object for the panelID, You need to add the origin object
	 * as a UIElement BEFORE setting the origin
	 * 
	 * @param panelID
	 * @param originID
	 */
	public void setOrigin(int panelID, int originID) {
		if (!containsPanel(panelID)) {
			System.out.println("Error in Incubator-setOrigin:NOT A Panel(" + panelID + ")");
			return;
		}
		if (!objectExists(originID)) {
			System.out.println("Error in Incubator-setOrigin:Object DNE(" + originID + ")");
			return;
		}

		Panel selectedPanel = panels.get(panelID);

		Object selectedObject = objects.get(originID);

		// Check elementType is of instance OriginObject
		if (!(selectedObject instanceof OriginObject)) {
			System.out.println("Error in Incubator-setOrigin:Not an OriginObject(" + originID + ")");
			return;
		}

		selectedPanel.setOrigin((OriginObject) selectedObject);
	}

	public Field[] getFields(int objectID) throws IllegalArgumentException, IllegalAccessException {
		if (!objectExists(objectID)) {
			System.out.println("Error in Incubator-getFields:Object DNE(" + objectID + ")");
			return null;
		}
		Object selectedObject;
		if (containsPanel(objectID))
			selectedObject = panels.get(objectID);
		else
			selectedObject = objects.get(objectID);
		Field[] fields = selectedObject.getClass().getFields();

		return fields;

	}

	private void recursiveSave(Hashtable<Integer, String> ht, int objectID) {
		if (ht.containsKey(objectID))
			return;
		if (getEither(objectID) == null) {
			System.out.println("Error in Incubator-recursiveSave:Invalid objectID(" + objectID + ")");
			return;
		}
		InteractableObject subject = getEither(objectID);
		// We haven't done this object yet
		ht.put(objectID, "");
		// For now it'l be an empty string
		String tempSaveString = "{";
		try {
			Field[] fields = getFields(objectID);
			for (Field f : fields) {
				// Check if it's an interactable object
				if (f.getType().isAssignableFrom(InteractableObject.class)) {
					// Check if this object even exists, if not, leave(Only way
					// this happens is if someone created an object without the
					// incubator)

					int fieldObjID = ((InteractableObject) f.get(subject)).getId();
					recursiveSave(ht, fieldObjID);
					tempSaveString += f.getName() + ":~" + fieldObjID + ",";
				}
				// TODO implement support for arrays
				else {
					// Assume it's a primitive
					tempSaveString += f.getName() + ":" + f.get(subject) + ",";

				}
			}
			// Check if the object is a panel
			if (subject instanceof Panel) {
				// If it's a panel, we need to process the array of objects it
				// contains
				tempSaveString += "objectList:[";
				ArrayList<UIElement> panelObjList = ((Panel) subject).getObjList();
				for (UIElement uie : panelObjList) {
					recursiveSave(ht, uie.getId());
					tempSaveString += ht.get(uie.getId());
				}
			}
			// Check if the object is an originObject
			if (subject instanceof OriginObject) {

			}

		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method will get the save string for ONLY PANELS
	 * For now the filehandling is done on the UI side, I will change this when universal object saving is rolled out
	 * @param objectID
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String IOScan(int objectID){
		Hashtable<Integer,String> strings = new Hashtable<Integer,String>();
		try {
			recursiveScan(strings,objectID,true);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = "";
		Enumeration<Integer> keys = strings.keys();
		while(keys.hasMoreElements()){
			result+=strings.get(keys.nextElement())+"\n";
		}
		return result;
	}
	
	
	private void recursiveScan(Hashtable<Integer,String>ht,int objectID,boolean main) throws IllegalArgumentException, IllegalAccessException{
		if(ht.containsKey(objectID))
			return;
		InteractableObject subject=getEither(objectID);
	    if(subject==null)
	    	return;
	    //Add it to the table
	    ht.put(objectID,"");

	    //Create the object string
	    String tempSaveString="";

	    if(main)
	    	tempSaveString+="@";

	    tempSaveString+=objectID+":";
	    tempSaveString+="{";
	    
	    //Add the object type
	    tempSaveString+="type:"+subject.getClass().getSimpleName()+",";

	    //Read through the public fields
	    Field[] fields = getFields(objectID);
	    for(Field f:fields){
	    	//First add the field name
	    	tempSaveString+=f.getName()+":";


	    	//Check if it's an object
	    	if(f.getType().isAssignableFrom(Object.class)){
	    		//It's an object, but is it a string or an array?
	    		if(f.getType().isAssignableFrom(String.class)){
	    			//It's a string, just treat it like a primitive
	    			tempSaveString+=f.get(subject);
	    			continue;
	    		}
	    		if(f.getType().isAssignableFrom(Array.class)){
	    			//It's an array
	    			//For now do nothing
	    			continue;
	    		}
	    		if(f.getType().isAssignableFrom(ArrayList.class)){
	    			//For now do nothing
	    			continue;
	    		}
	    		if(f.getType().isAssignableFrom(InteractableObject.class)){
	    			//We have a valid object
	    			//Get the object
	    			InteractableObject subject2 = (InteractableObject)f.get(subject);
	    			if(subject2==null)
	    				tempSaveString+="~#,";
	    			continue;
	    		}
	    		//If it's not the classes we want, throw it out.
	    		System.out.println("Error, got "+f.getType().getSimpleName());
	    		tempSaveString+="~#,";
	    	}
	    	//It's not an object, it must be a primitive
	    	tempSaveString+=f.get(subject)+",";
	    }
	    //We've processed the fields now
	    //We need to make special exceptions for OriginObjects and Panels
	    if(subject instanceof OriginObject){
	    	//If it's an OriginObject, we need to read it's panel
	    	Panel temp=((OriginObject)subject).getView();
	    	tempSaveString+="view:~"+temp.getId()+",";
	    	//Read the panel and put it in the table
	    	recursiveScan(ht,temp.getId(),false);
	    }
	    if(subject instanceof Panel){
	    	//It's a panel, we will need to process it's objList
	    	tempSaveString+="objectList:[";
	    	ArrayList<UIElement> panelObjList = ((Panel)subject).getObjList();
	    	for(UIElement uie:panelObjList){
	    		//First let's get the id of the element
	    		tempSaveString+="~"+uie.getId()+",";
	    		//Now we need to read te element
	    		recursiveScan(ht,uie.getId(),false);
	    		//We will assume there are no nulls
	    	}
	    	//we have finished processing the object list
	    	
	    	if(panelObjList.isEmpty()){
	    		tempSaveString+="],";
	    	}
	    	else{
		    	//Remove the last comma
		    	tempSaveString=tempSaveString.substring(0,tempSaveString.length()-1);
		    	//Close the array bracket
		    	tempSaveString+="],";//We add the comma so there is consistancy
	    	}
	    }
	    //Remove the last comma
	    tempSaveString=tempSaveString.substring(0,tempSaveString.length()-1);
	    //Add the object end bracket
	    tempSaveString+="}";
	    //Push it to the table
	    ht.put(subject.getId(),tempSaveString);

	  }

	private InteractableObject recursiveFileRead(Hashtable<Integer, String> lines,Hashtable<Integer, InteractableObject> objects,int objectID){
		 //Check if this object is already being translated(recursive reference check)
		if(objects.containsKey(objectID))
			return null;
		//We need to create the object immediately
		//First read the type
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @param objectID
	 */
	public void readFileToObject(String fileName,int objectID){
		//We need to account for 3 conditions
		
		//if it was loaded to the basepanel(create a gameobject)
		
		//if it was loaded to a gameobject(modify or replace the panel)
		
		//if it was loaded to a panel(create a uielement)
		
		//Create new hashtable for the lines
		Hashtable<Integer, String> lines = new Hashtable<Integer, String>();
		
		try {
			//Read the file and put each line in a hashtable
			BufferedReader reader = new BufferedReader(new FileReader("nursery/"+fileName+".tree"));

			int baseKey=-1;
			String line;

		 
			while((line=reader.readLine())!=null){
				 //We need to read the objectID and put the line in the hashtable
				 String prefix = line.split(":")[0];
				 //Declare the objectID for this string
				 int key;
				 //First test for the @ symbol
				 if(prefix.substring(0,1).equalsIgnoreCase("@")){
					 //It has a @
					 key = Integer.parseInt(prefix.substring(1,prefix.length()));
					 //It's base object, set the baseKey to it
					 baseKey=key;
					 System.out.println("Base key identified as "+baseKey);
				 }
				 else
					 key = Integer.parseInt(prefix);
				//We've read the prefix and have the id, time to put it in the Ht
				lines.put(key,line.split(":",2)[1]);
			}
			
			//Check if the hashtable was correctly written
			Enumeration<Integer> keys = lines.keys();
			while(keys.hasMoreElements()){
				System.out.println(lines.get(keys.nextElement()));
			}

			//Check if it has a base object, if it doesn't, cancel operation
			if(baseKey==-1){
			 	System.out.println("Error, file has no base object");
			 	return;
		 	}
			

		 	//Create the object hashtable
		 	Hashtable<Integer, InteractableObject> objects = new Hashtable<Integer, InteractableObject>();

		 	//We have the base object, let's start the recursiveRead
		 	recursiveFileRead(lines,objects,baseKey);
		 } catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	private boolean objectExists(int objectID) {
		return objects.containsKey(objectID) || panels.containsKey(objectID);
	}

	private boolean containsPanel(int panelID) {
		return panels.containsKey(panelID);
	}

	private Field findUnderlying(Class<?> clazz, String fieldName) {
		Class<?> current = clazz;
		do {
			try {
				return current.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		} while ((current = current.getSuperclass()) != null);
		return null;
	}
}
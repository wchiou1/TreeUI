package focusObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import GameLogic.StringUtils;
import Imported.ClassFinder;
import aspenNetwork.AspenNetwork;
import gameObjects.GameObject;
import smallGameObjects.SmallGameObject;

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
public class Incubator{
	// private int objectCount = 0;
	// Use the built in IO counter, don't use this pos
	//private TreeUIManager tuim;
	private AspenNetwork dn;
	private TreeUIManager tuim;
	private boolean editor=false;
	//PointPromises contains links to objects which are "promised" to be made
	//<promised_id<source_id,source_param>>
	private Hashtable<Integer,Hashtable<Integer,String>> pointerPromises = new Hashtable<Integer,Hashtable<Integer,String>>();

	public Incubator(TreeUIManager tuim, AspenNetwork dn) {
		this.tuim=tuim;
		this.dn = dn;
	}

	Hashtable<Integer, Panel> panels = new Hashtable<Integer, Panel>();
	Hashtable<Integer, InteractableObject> objects = new Hashtable<Integer, InteractableObject>();

	public void enableEditor(){
		editor=true;
		//Create and add the EditorBasePanel
		//tuim.addGameObject(new EditorBasePanel(this));
	}
	public TreeUIManager getManager(){
		return tuim;
	}
	public synchronized int addPanel() {
		return addPanel(-1);
	}
	public synchronized int addPanel(int id){
		Panel p = new Panel();
		if(id!=-1){
			p.setId(id);
		}
		panels.put(p.getId(), p);
		tuim.addObject(panels.get(p.getId()));
		fulfillPointerPromise(id);
		return p.getId();
	}
	public synchronized void removeObject(int objectID){
		if (!objects.containsKey(objectID)) {
			System.out.println("Error in Incubator-removeObject:ObjectID is invalid(" + objectID + ")");
			return;
		}
		objects.remove(objectID);
	}
	public synchronized void removePanel(int panelID) {
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

	public synchronized int addObject(Class<?> objectType){
		return addObject(-1,objectType);
	}
	public synchronized int addObject(int id,Class<?> objectType){

		Object newObject;
		try {
			newObject = objectType.getConstructor().newInstance();

			// Check elementType is of instance InteractableObject
			if (!(newObject instanceof InteractableObject)) {
				System.out.println("Error in Incubator-addObject:Not an InteractableObject(" + objectType + ")");
				return -1;
			}

			// Check if the element is of type GameObject
			if (newObject instanceof GameObject) {
				// Add the datanode to the datanodenetwork
				dn.add(((GameObject) newObject).getNode());
			}
			
			InteractableObject io = (InteractableObject)newObject;
			
			if(id!=-1){
				io.setId(id);
			}
			
			int objId = io.getId();
			
			fulfillPointerPromise(objId);
			
			objects.put(new Integer(objId), io);
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
	public synchronized int addUIElement(int panelID, Class<?> objectType){
		return addUIElement(-1,panelID,objectType);
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
	public synchronized int addUIElement(int elementID,int panelID, Class<?> elementType){
		try{
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

		UIElement newElement = createUIElement(elementID,elementType);
		selectedPanel.addObject(newElement);

		return newElement.getId();
		}catch(Exception e){
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
	public synchronized int addUIElement(int panelID, int elementID) throws InstantiationException, IllegalAccessException,
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

		Object newElement = getObject(elementID);

		// Check elementType is of instance UIElement
		if (!(newElement instanceof UIElement)) {
			System.out.println("Error in Incubator-addUIElement:Not a UIElement(" + elementID + ")");
			return -1;
		}

		int objId = ((UIElement) newElement).getId();
		selectedPanel.addObject((UIElement) newElement);

		return objId;
	}
	
	//Creates a UIElement, only to be used by the incubator's recursive fileread for temporary storage
	private UIElement createUIElement(int elementID,Class<?> elementType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Object obj = elementType.getConstructor().newInstance();

		// Check elementType is of instance UIElement
		if (!(obj instanceof UIElement)) {
			System.out.println("Error in Incubator-addUIElement:Not a UIElement(" + elementType + ")");
			return null;
		}
		UIElement newElement = (UIElement)obj;
		if(elementID!=-1){
			newElement.setId(elementID);
		}
		int objId = newElement.getId();
		
		fulfillPointerPromise(elementID);
		
		objects.put(new Integer(objId), newElement);
		return newElement;
	}
	private synchronized void fulfillPointerPromise(int paramID){
		if(pointerPromises.containsKey(paramID)){
			//Process them
			Hashtable<Integer,String> promises = pointerPromises.get(paramID);
			promises.forEach(
				(objectID,param) -> {
					writeParamPointer(objectID,param,paramID);
				}
			);
		}
		pointerPromises.remove(paramID);
	}
	private synchronized void addPointerPromise(int objectID,String param,int paramID){
		if(!pointerPromises.containsKey(paramID)){
			pointerPromises.put(paramID, new Hashtable<Integer,String>());
		}
		pointerPromises.get(paramID).put(objectID,param);
	}
	/**
	 * 
	 * @param objectId
	 * @param param
	 * @param paramId
	 */
	public synchronized void writeParamPointer(int objectID,String param,int paramID){
		// Check if the thing is contained in either hashtable
		InteractableObject source = getEither(objectID);
		if (source == null) {
			System.out.println("Error in Incubator-writeParamPointer:Invalid ObjectID(" + objectID + ")");
			return;
		}
		Field superField = findUnderlying(source.getClass(), param);
		if(paramID == -1){//param pointer id of -1 means NULL
			try {
				superField.set(source, null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		InteractableObject paramObject = getEither(paramID);
		if(paramObject == null){
			addPointerPromise(objectID,param,paramID);
			return;
		}
		
		if(InteractableObject.class.isAssignableFrom(superField.getType())){
			try {
				superField.set(source, paramObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ArrayList.class.isAssignableFrom(superField.getType())){
			//Handle arraylists, for now, just support interactable objects
			ParameterizedType arrayListType = (ParameterizedType) superField.getGenericType();
	        Class<?> arrayListClass = (Class<?>) arrayListType.getActualTypeArguments()[0];
	        
	        //If it's an arraylist of interactable objects
	        if(InteractableObject.class.isAssignableFrom(arrayListClass)){
	        	//Add the object to the arraylist
	        	try {
					ArrayList<InteractableObject> temp = (ArrayList<InteractableObject>) superField.get(source);
					temp.add(paramObject);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
		}
		else{
			System.out.println("Error in Incubator-writeParamPointer:Param(" + param + ") is NOT of type InteractableObject");
			return;
		}
	}
	/**
	 * Accepts an objectID and a parameter string and writes the object to the
	 * parameter
	 * 
	 * @param objectID
	 * @param param
	 */
	public synchronized void writeParam(int objectID, String param, Object arg) {
		// Check if the thing is contained in either hashtable
		InteractableObject io = getEither(objectID);
		if (io == null) {
			System.out.println("Error in Incubator-writeParam:Invalid ObjectID(" + objectID + ")");
			return;
		}
		
		// Let's get the possible params
		Field superField = findUnderlying(io.getClass(), param);
		try {
			if (arg instanceof String) {
				String stringArg = (String) arg;
				
				//PARAM IS INTEGER
				if (superField.getType() == int.class) {
					if (StringUtils.isInteger(stringArg)) {
						superField.set(io, Integer.parseInt(stringArg));
					} else {
						System.out.println(
							stringArg + " failed errorchecking for type " + superField.getType().getSimpleName());
					}
					return;
				}
				
				//PARAM IS DOUBLE
				if(superField.getType() == double.class){
					if (StringUtils.isDouble(stringArg)) {
						superField.set(io, Double.parseDouble(stringArg));
					} else {
						System.out.println(
								stringArg + " failed errorchecking for type " + superField.getType().getSimpleName());
					}
					return;
				}
				
				//PARAM IS STRING(This is easy)
				if (superField.getType() == String.class) {
					superField.set(io, stringArg);
					return;
				}
				
				//PARAM IS BOOLEAN
				if (superField.getType() == boolean.class) {
					if (StringUtils.isBoolean(stringArg)) {
						superField.set(io, Boolean.parseBoolean(stringArg));
					} else {
						System.out.println(
								stringArg + " failed errorchecking for type " + superField.getType().getSimpleName());
					}
					return;

				}
				
				//PARAM IS INTERACTABLE OBJECT
				//If it's a string for an object, try using the incubator ids
				if(InteractableObject.class.isAssignableFrom(superField.getType())){
					writeParamPointer(objectID,param,Integer.parseInt((String)arg));
					return;
				}
				
				//PARAM IS AN ARRAYLIST
				if(ArrayList.class.isAssignableFrom(superField.getType())){
					//Check if it's of type interactable object
					ParameterizedType arrayListType = (ParameterizedType) superField.getGenericType();
			        Class<?> arrayListClass = (Class<?>) arrayListType.getActualTypeArguments()[0];
			        
			        //If it's an arraylist of interactable objects
			        if(InteractableObject.class.isAssignableFrom(arrayListClass)){
			        	//Clear the arraylist first
			        	ArrayList<InteractableObject> temp = (ArrayList<InteractableObject>) superField.get(io);

			        	temp.clear();
			        	//Now parse the string using the commas and call write pointer on all of them
			        	String[] ids = stringArg.split(",");
			        	for(String id:ids){
			        		if(StringUtils.isInteger(id)){
			        			writeParamPointer(objectID,param,Integer.parseInt(id));
			        		}
			        	}
			        	return;
			        }
			        
				}
				
				System.out.println("Attempted write \""+stringArg+"\"(String) into "+superField.getType().getSimpleName()+" was rejected");
				//It's not a valid string input, output an error
				return;
			}
			
			//PARAM IS INTERACTABLE OBJECT, BUT WE DID NOT GET A STRING
			//If it's an int for the object, assume it's an incubator id
			if(InteractableObject.class.isAssignableFrom(superField.getType())){
				if(int.class == arg.getClass()){
					writeParamPointer(objectID,param,(int)arg);
					return;
				}
			}
			
			//Assume it's an object, attempt general object insertion
			superField.set(io, arg);
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public synchronized void connectNodes(int objectID1, int objectID2) {
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
	public synchronized void setOrigin(int panelID, int originID) {
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
	
	/**
	 * Takes in a serializedObject and updates the object with that id
	 * Creates a new object if that object doesn't exist yet
	 * @param id
	 * @param sobj
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public synchronized void updateObjectFromSerial(int id,Hashtable<String,String> sobj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		String sobj_type = sobj.get("type");
		Class<?> c = Class.forName(sobj_type);
		//System.out.println("Updating object "+id+"("+sobj_type+")");
		InteractableObject io = getEither(id);
		if(io!=null){
			
			//Object exists, update it
			
			String inc_type = io.getClass().getName();
			
			//Check if the type is the same, if it's not, report an error
			if(!inc_type.equals(sobj_type)){
				System.err.println("Type mismatch (Local: "+inc_type+" ,Remote: "+sobj_type+"), packet for "+id+" ignored");
				return;
			}
			
			Field[] fields= getFields(id);
		
			for(Field f: fields){
				String field_name = f.getName();
				if(sobj.containsKey(field_name)){
					String value = sobj.get(field_name);
					writeParam(id,field_name,value);
				}
			}
			
		}
		else{
			
			//Object dne, create it and update it
			System.out.println("Creating object "+id+"("+sobj_type+")");
			if(GameObject.class.isAssignableFrom(c)){
				addObject(id,c);
			}
			else if(Panel.class.isAssignableFrom(c)){
				addPanel(id);
			}
			else{
				createUIElement(id,c);
			}
			
			updateObjectFromSerial(id,sobj);
		}
		
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
	
	/**
	 * This method will get the save string for THE ENTIRE ENVIRONMENT
	 * For now the filehandling is done on the UI side, I will change this when universal object saving is rolled out
	 * @param objectID
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public synchronized String IOScanAll(){
		//Scan ALL gameobjects!
		String fullResult = "";
		LinkedList<InteractableObject> gos = tuim.getGameObjects();
		Iterator<InteractableObject> itr = gos.iterator();
		while(itr.hasNext()){
			InteractableObject obj = itr.next();
			if(containsGameObject(obj.getId())){
				fullResult += IOScan(obj.getId());
			}
		}
		return fullResult;
	}

	/**
	 * This method will get the save string for ONLY THE OBJECT
	 * For now the filehandling is done on the UI side, I will change this when universal object saving is rolled out
	 * @param objectID
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public synchronized String IOScan(int objectID){
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
	    	System.out.println(f.getName());

	    	//Check if it's an object
	    	if(Object.class.isAssignableFrom(f.getType())){
	    		//It's an object, but is it a string or an array?
	    		if(String.class.isAssignableFrom(f.getType())){
	    			//It's a string, just treat it like a primitive
	    			tempSaveString+="\""+f.get(subject)+"\",";
	    			continue;
	    		}
	    		if(Array.class.isAssignableFrom(f.getType())){
	    			//It's an array
	    			//For now do nothing
	    			continue;
	    		}
	    		if(ArrayList.class.isAssignableFrom(f.getType())){
	    			//For now do nothing
	    			continue;
	    		}
	    		if(InteractableObject.class.isAssignableFrom(f.getType())){
	    			//We have a valid object
	    			//Get the object
	    			InteractableObject subject2 = (InteractableObject)f.get(subject);
	    			if(subject2==null)
	    				tempSaveString+="~#,";
	    			else{
	    				tempSaveString+="~"+subject2.getId()+",";
	    				recursiveScan(ht,subject2.getId(),false);
	    			}
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
	    if(subject instanceof OriginObject){//Everything is an oo now, legacy test
	    	//If it's an OriginObject, we need to read it's panel
	    	Panel temp=((OriginObject)subject).getView();
	    	if(temp!=null){
		    	tempSaveString+="view:~"+temp.getId()+",";
		    	//Read the panel and put it in the table
		    	recursiveScan(ht,temp.getId(),false);
	    	}
	    }
	    if(subject instanceof Panel){
	    	//It's a panel, we will need to process it's objList
	    	tempSaveString+="objectList:[";
	    	ArrayList<UIElement> panelObjList = ((Panel)subject).getObjList();
	    	for(UIElement uie:panelObjList){
	    		//First let's get the id of the element
	    		tempSaveString+="~"+uie.getId()+"|";
	    		//Now we need to read the element
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

	private InteractableObject recursiveFileRead(Hashtable<Integer, String> lines,Hashtable<Integer, InteractableObject> objects,int parentID,int objectID) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		 //Check if this object is already being translated(recursive reference check)
		if(objects.containsKey(objectID))
			return objects.get(objectID);
		
		//Get the string for the object
		String objString = lines.get(objectID);
		
		System.out.println(objString);
		
		//Let's start by removing the starting and ending bracket
		objString=objString.substring(1,objString.length()-1);
		
		//We need to create the object immediately
		
		//Parse the string into field-data parts
		String[] parts = objString.split(",");
		
		//Let's get the type first
		String typestr = parts[0].split(":")[1];
		
		//Declare the class and object for this object
		Class<?> c = null;
		InteractableObject subject=null;
		
		//First check if the class exists as a gameobject or a uielement
		if(ClassFinder.existsWithin(typestr, "gameObjects")){
			//It's a gameobject
			c = Class.forName("gameObjects."+typestr);
			//We need to know if it's paneled or not
			if(GameObject.class.isAssignableFrom(c)){
				System.out.print("GameObject detected: "+c);
			}
			//Check if it's the root so if it's not, do not add it to the environment
			if(parentID == -1)
				subject = getObject(addObject(c));
			else
				subject = tuim.createLooseGameObject(c, 0, 0);
			
			//System.out.println("Checking DATALINK for "+typestr+":"+subject.checkDataLink());
		}
		if(ClassFinder.existsWithin(typestr, "smallGameObjects")){
			//It's a small gameobject
			c = Class.forName("smallGameObjects."+typestr);
			//
			if(SmallGameObject.class.isAssignableFrom(c)){
				System.out.print("SmallGameObject detected: "+c);
			}
			
			//Check if it's the root so if it's not, do not add it to the environment
			if(parentID == -1)
				subject = getObject(addObject(c));
			else
				subject = tuim.createLooseGameObject(c, 0, 0);
			
		}
		if(ClassFinder.existsWithin(typestr, "TreeUI.Engineering")){
			//It's a UIElement
			c = Class.forName("TreeUI.Engineering."+typestr);
			//We need to know if it's paneled or not
			System.out.println("Engineering UIElement detected:"+c);
			System.out.println(""+parentID+"|"+objects.get(parentID).getId());
			
			subject = createUIElement(-1,c);
			//subject = getObject(addUIElement(objects.get(parentID).getId(), c));
			//System.out.println("Checking DATALINK for "+typestr+":"+subject.checkDataLink());
		}
		else if(ClassFinder.existsWithin(typestr, "TreeUI")){
			//It's a UIElement
			c = Class.forName("TreeUI."+typestr);
			//We need to know if it's paneled or not
			System.out.println("UIElement detected:"+c);
			
			subject = createUIElement(-1,c);
			//Dead code, addUIElement fails since it requires panel id which is not supplied if the parent is not the panel
			//subject = getObject(addUIElement(objects.get(parentID).getId(), c));
			//System.out.println("Checking DATALINK for "+typestr+":"+subject.checkDataLink());
		}
		
		if(typestr.equalsIgnoreCase("Panel")){
			c = Panel.class;
			//We have a special case for panels
			//System.out.print("Panel detected");
			subject = getPanel(addPanel());
			//Before setting the origin, make sure that the parent is an originobject
			//What if a user wants to have a panel stored in an originobject, but not as a view?
			//What the fuck, why would they do that. Just... no.
			if(objects.get(parentID) instanceof OriginObject)
				setOrigin(subject.getId(),objects.get(parentID).getId());
			else
				System.out.println("Alert in recursiveFileRead: Panel created without an originobject");
			if(editor)
				((Panel)subject).enableEditing(this);
		}
		
		if(c==null){
			//No class found in GameObjects or UI
			System.out.println("Unable to find class("+typestr+")");
			return null;
		}
		objects.put(objectID, subject);
		
		//We need to check if there are more fields to read, how would we do that?
		for(int i=1;i<parts.length;i++){
			//First we need to get the field
			String part = parts[i];
			String fieldstr = part.split(":")[0];
			String fieldData = "";
			if(part.split(":").length>1)
				fieldData = part.split(":")[1];
			
		//Process the fieldstr
			//Get the actual field
			Field field = findUnderlying(subject.getClass(),fieldstr);
			if(field==null){
				System.out.println("ERROR: Unable to find field "+fieldstr);
			}
			System.out.println(field.getName()+"("+field.getType().getSimpleName()+"):"+fieldData);
			
			//Is it an object?
			if(Object.class.isAssignableFrom(field.getType())){
				//The field is an object
				if(String.class.isAssignableFrom(field.getType())){
	    			//It's a string, just treat it like a primitive
					if(!fieldData.isEmpty())
						fieldData = fieldData.substring(1, fieldData.length()-1);
					writeParam(subject.getId(),fieldstr,fieldData);
					continue;
	    			
	    		}
	    		if(Array.class.isAssignableFrom(field.getType())){
	    			//It's an array
	    			//For now do nothing
	    			continue;
	    		}
	    		if(ArrayList.class.isAssignableFrom(field.getType())){
	    			//Strip the brackets
	    			fieldData = fieldData.substring(1, fieldData.length()-1);
	    			
	    			//We got an arraylist, is the subject a panel by any chance?
	    			if(subject instanceof Panel&&fieldstr.equals("objectList")){
	    				System.out.println("ArrayList detected");
	    				System.out.println(fieldData);
	    				//This is a panel objectList
	    				//Let's start by parsing this badboy.
	    				//First check if it's empty
	    				if(fieldData.isEmpty())
	    					//It's empty
	    					continue;
	    				String[] dataParts = fieldData.split("\\|");
	    				for(String s:dataParts){
	    					//For each string, we need to create a new UIElement, adding to the panel is done on the other side
	    					//First remove the ~
	    					int elementID = Integer.parseInt(s.substring(1));
	    					recursiveFileRead(lines,objects,objectID,elementID);
	    					addUIElement(subject.getId(),objects.get(elementID).getId());
	    				}
	    				
	    			}
	    			//We do not have support for ArrayLists other than panels yet
	    			continue;
	    		}
	    		if(InteractableObject.class.isAssignableFrom(field.getType())){
	    			//We have an object!
	    			//We need to know if it's a panel
	    			if(Panel.class.isAssignableFrom(field.getType())){
	    				//Hmmm... it's a panel... do we happen to have an originobject on our hands?
	    				if(subject instanceof OriginObject&&fieldstr.equals("view")){
	    					//We do! Let's use the incubator's built in functions then
	    					recursiveFileRead(lines,objects,objectID,Integer.parseInt(fieldData.substring(1)));
	    					continue;
	    				}
	    			}
	    			//Oh it's not an origin object, we'll just create and put the object in the usual way then
	    		}
	    		if(fieldData.equals("~#")){
	    			writeParam(subject.getId(),fieldstr,null);
	    			continue;
	    		}
	    		//This is not a supported object,but we'll try to add the object in anyway...
	    		System.out.println("WARNING: Not an InteractableObject, attempting general object insert for "+fieldstr);
	    		writeParam(subject.getId(),fieldstr,recursiveFileRead(lines,objects,objectID,Integer.parseInt(fieldData.substring(1))));
	    		continue;
			}
			//It's not an object, it must be a primitive
			writeParam(subject.getId(),fieldstr,fieldData);
		}
		
		System.out.println("Done with object "+objectID);
		
		
		return subject;
	}

	/**
	 * Reads the file and changes behavior based on objectID
	 * @param fileName
	 * @param objectID
	 */
	public synchronized void readFileToObject(String fileName){
		
		//Create new hashtable for the lines
		Hashtable<Integer, String> lines = new Hashtable<Integer, String>();
		
		try {
			//Read the file and put each line in a hashtable
			BufferedReader reader = new BufferedReader(new FileReader("nursery/"+fileName+".tree"));

			ArrayList<Integer> baseKeys=new ArrayList<Integer>();
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
					 baseKeys.add(key);
					 System.out.println("Base key "+baseKeys.size()+" identified as "+key);
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
			if(baseKeys.isEmpty()){
			 	System.out.println("Error, file has no base object");
			 	return;
		 	}
			
		 	//Create the object hashtable
		 	Hashtable<Integer, InteractableObject> objects = new Hashtable<Integer, InteractableObject>();

		 	//Create the base object
		 	for(Integer i:baseKeys){
		 		//We have the base object, let's start the recursiveRead
			 	recursiveFileRead(lines,objects,-1,i);
		 	}
		 	
		 } catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	private boolean objectExists(int objectID) {
		return objects.containsKey(objectID) || panels.containsKey(objectID);
	}
	private boolean containsGameObject(int objectID){
		return objects.containsKey(objectID);
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
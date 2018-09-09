package Editor;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import aspenNetwork.AspenNode;
import focusObject.Incubator;
import focusObject.InteractableObject;
import smallGameObjects.HasOverlay;
import smallGameObjects.SmallGameObject;

/**
 * This item will store a gameobject on the first click and connect it to another object of the second click
 * It will be activated using Ctrl within the editor
 * @author Wesley Chiou
 *
 */
public class NodeConnector extends SmallGameObject implements EditorItem,HasOverlay{
	private InteractableObject stored;//Stores the gameobject so we can connect it
	private Incubator inc;
	public NodeConnector(Incubator inc){
		this.inc=inc;
	}
	
	public void applyGameObject(InteractableObject io){
		//If there is not stored object, get it
		if(stored==null){
			stored=io;
			return;
		}
		//It's the same node, remove it
		if(stored==io){
			stored=null;
			return;
		}
		//If there is already a connection, remove the connection
		if(stored.getNode().checkNeighbor(io.getNode())){
			stored.getNode().changeNetwork();
			stored.getNode().removeNeighbor(io.getNode());
			io.getNode().removeNeighbor(stored.getNode());
			stored=null;
		}
		else{
			stored.getNode().changeNetwork();
			stored.getNode().addNeighbor(io.getNode());
			io.getNode().addNeighbor(stored.getNode());
			stored=null;
		}
	}
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.blue);
		g.drawLine(x+2, y+2, x+9, y+9);//diagonal
		g.drawLine(x+9, y+2, x+1, y+10);//diagonal
		g.drawLine(x, y+5, x+10, y+5);//Hos
		g.drawLine(x+5, y, x+5, y+10);//Vert
	}

	@Override
	public void drawOverlay(Graphics g, int x, int y) {
		g.setColor(Color.blue);
		//Draw the line from the source object to the mouse
		if(stored!=null){
			g.drawLine(stored.getNode().getParent().getCenterX(), stored.getNode().getParent().getCenterY(), x, y);
		}
		LinkedList<InteractableObject> goList = inc.getManager().getGameObjects();
		for(InteractableObject io:goList){
			AspenNode temp = io.getNode();
			if(temp==null)
				continue;
			//The node exists, first get the x and y
			int originX = temp.getParent().getCenterX();
			int originY = temp.getParent().getCenterY();
			for(AspenNode an:temp.getNeighbors()){
				int destX = an.getParent().getCenterX();
				int destY = an.getParent().getCenterY();
				g.drawLine(originX, originY, destX, destY);
			}
		}
	}

	@Override
	public int getCenterX() {
		return 5;
	}

	@Override
	public int getCenterY() {
		return 5;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+10)
			if(y>=this.y&&y<=this.y+10){
				//System.out.println("ON");
				return true;
			}
		return false;
	}

	@Override
	protected int getWidth() {
		return 10;
	}

	@Override
	protected int getHeight() {
		return 10;
	}
	
}
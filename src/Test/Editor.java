package Test;


import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import DataLinkNetwork.DataNetwork;
import DataLinkNetwork.DataNetworkNode;
import FocusObject.OriginObject;
import FocusObject.Panel;
import FocusObject.TreeUIManager;
import FocusObject.Window;
import GameObjects.BasicPaneledGameObject;
import GameObjects.Generator;
import TreeUI.Button;
import TreeUI.Dial;
import TreeUI.Indicator;
import TreeUI.IndicatorBar;
import TreeUI.IndicatorDial;
import TreeUI.InventorySlot;
import TreeUI.Key;
import TreeUI.Keyhole;
import TreeUI.Keyrack;
import TreeUI.PowerMonitor;
import TreeUI.Slider;
import TreeUI.StateChangeButton;
import TreeUI.StaticText;
import TreeUI.TextBox;

public class Editor extends BasicGameState{

	int width;
	int height;
	TreeUIManager im;
	DataNetwork dn;
	BasicPaneledGameObject masterOO;
	StateBasedGame parent;
	public Editor(Shell parent){
		super();
		this.parent=parent;
	}
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		ArrayList<Integer> keys=new ArrayList<Integer>();
		keys.add(Input.KEY_E);
		keys.add(Input.KEY_R);
		width = container.getWidth();
		height = container.getHeight();
		dn=new DataNetwork();
		im=new TreeUIManager(container.getInput(),keys,10);
		im.addObject(new Window(100,100,50,50,Color.blue));
		im.addObject(new Window(50,50,50,50,Color.red));
		DataNetworkNode node1=new DataNetworkNode();
		
		masterOO=new BasicPaneledGameObject(300,400,10,10);
		Panel p=new Panel(-50,-100,100,100,masterOO,node1);
		
		StaticText st1=new StaticText(0,20,"Demo");

		p.addObject(st1);
		
		StateChangeButton scb1=new StateChangeButton(parent,30,80,20,"Demo");
		
		p.addObject(scb1);


		
		im.addObject(p);
		im.addObject(masterOO);
		
		
		
		
	}
	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(Color.white);
		im.draw(g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		im.update();
		dn.update();
		if(container.getInput().isKeyDown(Input.KEY_UP)){
			masterOO.dMoveTreeUI(0, -1);
		}
		
	}

	@Override
	public int getID() {
		return 2;
	}

}

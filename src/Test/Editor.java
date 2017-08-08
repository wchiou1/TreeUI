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
import TreeUI.Incubator;
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
	Incubator inc;
	StateBasedGame parent;
	public Editor(Shell parent){
		super();
		this.parent=parent;
	}
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		try{
			ArrayList<Integer> keys=new ArrayList<Integer>();
			keys.add(Input.KEY_E);
			keys.add(Input.KEY_R);
			width = container.getWidth();
			height = container.getHeight();
			dn=new DataNetwork();
			im=new TreeUIManager(container.getInput(),keys,10);
			inc = new Incubator(im,dn);
			
			
			//masterOO=new BasicPaneledGameObject(300,400,10,10);
			//im.addObject(masterOO);
			int masterOO = inc.addObject(BasicPaneledGameObject.class);
			inc.writeParam(masterOO, "x", 300);
			inc.writeParam(masterOO, "y", 400);
			inc.writeParam(masterOO, "width", 10);
			inc.writeParam(masterOO, "height", 10);
			
			//Panel p=new Panel(-50,-100,100,100);
			//im.addObject(p);
			int p = inc.addPanel();
			inc.writeParam(p, "x", -50);
			inc.writeParam(p, "y", -100);
			inc.writeParam(p, "width", 100);
			inc.writeParam(p, "height", 100);
			
			//p.setOrigin(masterOO);
			inc.setOrigin(p, masterOO);
			
			//StaticText st1=new StaticText(0,20,"Demo");
			//p.addObject(st1);
			int st1 = inc.addUIElement(p, StaticText.class);
			inc.writeParam(st1, "rx", -50);
			inc.writeParam(st1, "ry", -100);
			inc.writeParam(st1, "text", "Demo");
	
			//StateChangeButton scb1=new StateChangeButton(parent,30,80,20,"Demo");
			//p.addObject(scb1);
			int scb1 = inc.addUIElement(p, StateChangeButton.class);
			inc.writeParam(scb1, "rx", 30);
			inc.writeParam(scb1, "ry", 80);
			inc.writeParam(scb1, "radius", 20);
			inc.writeParam(scb1, "targetState", "Demo");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
		
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
		
	}

	@Override
	public int getID() {
		return 2;
	}

}

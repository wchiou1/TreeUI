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
import FocusObject.TreeUIManager;
import GameLogic.Incubator;
import GameObjects.BasicPaneledGameObject;
import TreeUI.StateChangeButton;
import TreeUI.StaticText;

public class MainMenu extends BasicGameState{

	int width;
	int height;
	TreeUIManager im;
	DataNetwork dn;
	Incubator inc;
	StateBasedGame parent;
	public MainMenu(Shell parent){
		super();
		this.parent=parent;
	}
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		try{
			ArrayList<Integer> keys=new ArrayList<Integer>();
			keys.add(Input.KEY_E);
			keys.add(Input.KEY_R);
			width = container.getWidth();
			height = container.getHeight();
			dn=new DataNetwork();
			im=new TreeUIManager(container.getInput(),keys,10,false);
			inc = new Incubator(im,dn);
			
			
			//masterOO=new BasicPaneledGameObject(300,400,10,10);
			int masterOO = inc.addObject(BasicPaneledGameObject.class);
			inc.writeParam(masterOO, "x", 300);
			inc.writeParam(masterOO, "y", 400);
			inc.writeParam(masterOO, "width", 10);
			inc.writeParam(masterOO, "height", 10);
			
			//Panel p=new Panel(-50,-100,100,100,true);
			//im.addObject(p);
			int p = inc.addPanel();
			inc.writeParam(p, "x", -50);
			inc.writeParam(p, "y", -100);
			inc.writeParam(p, "width", 100);
			inc.writeParam(p, "height", 100);
			
			//p.setOrigin(masterOO);
			//im.addObject(masterOO);
			inc.setOrigin(p, masterOO);
		
			//p.addObject(new StaticText(40,20,"Demo"));
			int st1 = inc.addUIElement(p, StaticText.class);
			inc.writeParam(st1, "rx", 40);
			inc.writeParam(st1, "ry", 20);
			inc.writeParam(st1, "text", "Demo");
			
			//p.addObject(new StaticText(40,38,"Editor"));
			int st2 = inc.addUIElement(p, StaticText.class);
			inc.writeParam(st2, "rx", 40);
			inc.writeParam(st2, "ry", 38);
			inc.writeParam(st2, "text", "Editor");
			
			//p.addObject(new StateChangeButton(parent,0,20,20,"Demo"));
			int scb1 = inc.addUIElement(p, StateChangeButton.class);
			inc.writeParam(scb1, "rx", 0);
			inc.writeParam(scb1, "ry", 20);
			inc.writeParam(scb1, "radius", 20);
			inc.writeParam(scb1, "targetState", "Demo");
			
			//p.addObject(new StateChangeButton(parent,0,38,20,"Editor"));
			int scb2 = inc.addUIElement(p, StateChangeButton.class);
			inc.writeParam(scb2, "rx", 0);
			inc.writeParam(scb2, "ry", 38);
			inc.writeParam(scb2, "radius", 20);
			inc.writeParam(scb2, "targetState", "Editor");
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
		return 0;
	}

}

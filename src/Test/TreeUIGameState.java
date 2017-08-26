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
import GameLogic.Incubator;
import focusObject.TreeUIManager;

public abstract class TreeUIGameState extends BasicGameState{
	TreeUIManager im;
	DataNetwork dn;
	Incubator inc;
	StateBasedGame parent;
	public TreeUIGameState(Shell parent){
		super();
		this.parent=parent;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		ArrayList<Integer> keys=new ArrayList<Integer>();
		keys.add(Input.KEY_E);
		keys.add(Input.KEY_R);
		dn=new DataNetwork();
		im=new TreeUIManager(container.getInput(),keys,10);
		inc = new Incubator(im,dn);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		im.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		im.update();
		dn.update();
	}
	
	@Override
	public void keyPressed(int key, char c) {
		im.keyPressed(key, c);
	}
	
}
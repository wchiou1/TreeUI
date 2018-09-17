package Test;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import aspenNetwork.AspenNetwork;
import focusObject.Incubator;
import focusObject.TreeUIManager;

public abstract class TreeUIGameState extends BasicGameState{
	TreeUIManager im;
	Incubator inc;
	AspenNetwork dn;
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
		dn=new AspenNetwork(0);
		im=new TreeUIManager(container,keys,dn,10);
		inc=im.getIncubator();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.white);
		im.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		im.update(delta);
		dn.update(delta);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		im.keyPressed(key, c);
	}
	@Override
	public void mouseWheelMoved(int arg0){
		im.mouseWheelMoved(arg0);
	}
	
}
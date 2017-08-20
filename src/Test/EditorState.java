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
import Editor.Editor;
import Editor.EditorBasePanel;
import FocusObject.TreeUIManager;
import GameLogic.Incubator;
import GameObjects.BasicPaneledGameObject;
import TreeUI.StateChangeButton;
import TreeUI.StaticText;

public class EditorState extends BasicGameState{

	int width;
	int height;
	TreeUIManager im;
	DataNetwork dn;
	Incubator inc;
	StateBasedGame parent;
	public EditorState(Shell parent){
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
			im=new TreeUIManager(container.getInput(),keys,10);
			inc = new Incubator(im,dn);
			
			Editor editor = new Editor(inc);
			
			
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

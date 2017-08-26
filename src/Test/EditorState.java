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
import GameLogic.Incubator;
import GameObjects.BasicPaneledGameObject;
import TreeUI.StateChangeButton;
import TreeUI.StaticText;
import focusObject.TreeUIManager;

public class EditorState extends TreeUIGameState{

	int width;
	int height;
	public EditorState(Shell parent){
		super(parent);
		this.parent=parent;
	}
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		super.init(container,arg1);
		try{
			width = container.getWidth();
			height = container.getHeight();
			im.enableEditor();
			
			Editor editor = new Editor(inc);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		
	}
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
	}

	@Override
	public int getID() {
		return 2;
	}

}

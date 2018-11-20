package Test;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import focusObject.Editor;
import focusObject.TreeUIMultiplayer;

public class EditorState extends TreeUIGameState{

	int width;
	int height;
	boolean server;
	public EditorState(Shell parent,boolean server){
		super(parent);
		this.parent=parent;
		this.server = server;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		super.init(container,arg1);
		try{
			System.out.println(this.getClass().getSimpleName());
			width = container.getWidth();
			height = container.getHeight();
			Editor editor = new Editor(im);
			if(server){
				TreeUIMultiplayer.startServer(im, 2004);
			}
			else{
				String ip = SuperGlobal.ip;
				TreeUIMultiplayer.startClient(im, ip, 2004);
			}
			
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
		if(server)
			return 2;
		return 3;
	}

}

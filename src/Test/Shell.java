package Test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Shell extends StateBasedGame {

	public static int menu = 0;

	public Shell(String name) {
		super(name);
		SuperGlobal.setGameState("MainMenu", new MainMenu(this));
		SuperGlobal.setGameState("Demo", new Demo(this));
		SuperGlobal.setGameState("Editor", new Editor(this));
		
		//int shipID=Integer.parseInt(JOptionPane.showInputDialog("Enter your shipID:"));
		//this.addState(new SBLobby());
		//this.addState(new SBBuild(shipID));
		this.addState((MainMenu)SuperGlobal.getGameState("MainMenu"));
		this.addState((Demo)SuperGlobal.getGameState("Demo"));
		this.addState((Editor)SuperGlobal.getGameState("Editor"));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.enterState(0);
	}
	
	public static void main(String Args[])
	{
		try{
			AppGameContainer app = new AppGameContainer(new Shell("Zoom and Drag Testing"));
			app.setIcon("res/icon1.png");
			SuperGlobal.setWindowSize(800, 600);
			app.setDisplayMode(800, 600, false);
			app.start();
		}catch(SlickException e)
		{
			//System.out.println("Slick Exception fired: Shell Main");
		}
	}
}

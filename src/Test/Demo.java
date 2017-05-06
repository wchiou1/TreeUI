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
import TreeUI.BasicPanelButton;
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
import TreeUI.TextBox;

public class Demo extends BasicGameState{

	int width;
	int height;
	TreeUIManager im;
	DataNetwork dn;
	BasicPaneledGameObject masterOO;
	public Demo(Shell parent){
		
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
		//im.addObject(new Window(100,100,50,50,Color.blue));
		//im.addObject(new Window(50,50,50,50,Color.red));
		Key key1=new Key(123,"key1");
		DataNetworkNode node1=new DataNetworkNode();
		DataNetworkNode node2=new DataNetworkNode();
		DataNetworkNode node3=new DataNetworkNode();
		DataNetworkNode node4=new DataNetworkNode();
		DataNetworkNode gen1node=new DataNetworkNode();
		
		
		node1.addNeighbor(node2);
		node1.addNeighbor(node3);
		node1.addNeighbor(node4);
		
		node2.addNeighbor(node1);
		node3.addNeighbor(node1);
		node4.addNeighbor(node1);
		
		gen1node.addNeighbor(node1);//Generator node is one way, generator only gives info
									//Power nodes must be separate from data(maybe make separate filtered DN Node?)
		
		dn.add(node1);
		dn.add(node2);
		dn.add(node3);
		dn.add(node4);
		dn.add(gen1node);
		
		
		
		masterOO=new BasicPaneledGameObject(300,400,10,10);
		Panel p=new Panel(-50,-100,100,100,masterOO,node1);
		
		BasicPanelButton oo2=new BasicPanelButton(10,10,10,10);
		BasicPanelButton oo3=new BasicPanelButton(24,10,10,10);
		BasicPanelButton oo4=new BasicPanelButton(38,10,10,10);
		
		p.addObject(oo2);
		p.addObject(oo3);
		p.addObject(oo4);
		
		Panel p2=new Panel(-50,-100,200,200,oo2,node2);
		Panel p3=new Panel(-50,-100,150,150,oo3,node3);
		Panel p4=new Panel(-50,-100,oo4,node4);
		
		Dial d1=new Dial(20,20,"dial1");
		TextBox tb1=new TextBox(0,20,"dial1");
		TextBox tb2=new TextBox(0,40,"slider1");
		IndicatorBar ib1=new IndicatorBar(80,10,80,100,"dial1");
		IndicatorDial id1=new IndicatorDial(20,30,360,"dial1");
		Keyhole kh1=new Keyhole(50,5,123,"key1");
		Keyrack kr1=new Keyrack(60,5,key1);
		InventorySlot is1=new InventorySlot(60,20);
		
		Slider s1=new Slider(10,10,70,100,true,"slider1");
		
		PowerMonitor pm=new PowerMonitor(0,60);
		
		p.addObject(pm);
		
		p2.addObject(d1);
		
		p3.addObject(tb1);
		p3.addObject(ib1);		
		p4.addObject(id1);
		p4.addObject(s1);
		
		Button b1=new Button(30,80,20,"data1");
		Button b2=new Button(30,80,20,"data2");
		Button b3=new Button(30,80,20,"data3");
		Button b4=new Button(30,80,20,"data4");
		
		Indicator i1=new Indicator(30,50,"data1");
		Indicator i2=new Indicator(40,50,"data2");
		Indicator i3=new Indicator(50,50,"data3");
		Indicator i4=new Indicator(60,50,"data4");
		Indicator i5=new Indicator(70,50,"key1");
		
		p.addObject(b1);
		p.addObject(kh1);
		p.addObject(kr1);
		p.addObject(is1);
		p2.addObject(b2);
		p3.addObject(b3);
		p4.addObject(b4);
		
		p.addObject(i1);
		p.addObject(i2);
		p.addObject(i3);
		p.addObject(i4);
		p.addObject(i5);
		
		im.addObject(p);
		im.addObject(masterOO);
		//mm.addObject(oo2);
		im.addObject(p2);
		im.addObject(p3);
		im.addObject(p4);
		
		Generator generate=new Generator(300,300,gen1node);
		generate.setView(p);
		
		im.addGameObject(generate);
		
		
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
		return 1;
	}

}

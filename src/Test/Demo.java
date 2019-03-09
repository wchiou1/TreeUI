package Test;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import TreeUI.BasicPanelButton;
import TreeUI.Button;
import TreeUI.Dial;
import TreeUI.Indicator;
import TreeUI.IndicatorBar;
import TreeUI.IndicatorDial;
import TreeUI.InputBox;
import TreeUI.InventorySlot;
import TreeUI.Keyhole;
import TreeUI.Keyrack;
import TreeUI.PanelExit;
import TreeUI.PowerMonitor;
import TreeUI.Slider;
import TreeUI.TextBox;
import gameObjects.Generator;
import smallGameObjects.Key;

public class Demo extends TreeUIGameState{

	int width;
	int height;
	public Demo(Shell parent){
		super(parent);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		try{
			width = container.getWidth();
			height = container.getHeight();
		
			//im.addObject(new Window(100,100,50,50,Color.blue));
			//im.addObject(new Window(50,50,50,50,Color.red));
			int key1 = inc.addObject(Key.class);
			inc.writeParam(key1, "pattern", 123);
			inc.writeParam(key1, "name", "key1");
			
			//Generator generate=new Generator(300,300);
			//generate.setView(p);
			//im.addGameObject(generate);
			int generate = inc.addObject(Generator.class);
			inc.writeParam(generate, "x", 300);
			inc.writeParam(generate, "y", 300);
			
			
			//masterOO=new BasicPaneledGameObject(300,400,10,10);
			//im.addObject(masterOO);
			/*int masterOO = inc.addObject(BasicPaneledGameObject.class);
			inc.writeParam(masterOO, "x", 300);
			inc.writeParam(masterOO, "y", 400);
			inc.writeParam(masterOO, "width", 10);
			inc.writeParam(masterOO, "height", 10);*/
			
			//Panel p=new Panel(-50,-100,100,100);
			//im.addObject(p);
			int p = inc.addPanel();
			inc.writeParam(p, "x", -50);
			inc.writeParam(p, "y", -100);
			inc.writeParam(p, "width", 100);
			inc.writeParam(p, "height", 100);
			
			//Add exit for panel 1
			int pe1 = inc.addUIElement(p, PanelExit.class);
			inc.writeParam(pe1, "x", 100-13);
			inc.writeParam(pe1, "y", 3);
			
			inc.setOrigin(p, generate);
			
			//p.setOrigin(masterOO);
			//inc.setOrigin(p, masterOO);
			
			//BasicPanelButton oo2=new BasicPanelButton();
			int oo2 = inc.addUIElement(p,BasicPanelButton.class);
			inc.writeParam(oo2, "x", 10);
			inc.writeParam(oo2, "y", 10);
			
			//BasicPanelButton oo3=new BasicPanelButton();
			int oo3 = inc.addUIElement(p,BasicPanelButton.class);
			inc.writeParam(oo3, "x", 24);
			inc.writeParam(oo3, "y", 10);
	
			//BasicPanelButton oo4=new BasicPanelButton();
			int oo4 = inc.addUIElement(p,BasicPanelButton.class);
			inc.writeParam(oo4, "x", 38);
			inc.writeParam(oo4, "y", 10);
			
			//AddUIElement automatically adds the object to the panel
			//p.addObject(oo2);
			//p.addObject(oo3);
			//p.addObject(oo4);
			
			//Panel p2=new Panel(-50,-100,200,200);
			//im.addObject(p2);
			int p2 = inc.addPanel();
			inc.writeParam(p2, "x", -50);
			inc.writeParam(p2, "y", -100);
			inc.writeParam(p2, "width", 200);
			inc.writeParam(p2, "height", 200);
			
			//Add exit for panel 2
			int pe2 = inc.addUIElement(p2, PanelExit.class);
			inc.writeParam(pe2, "x", 200-13);
			inc.writeParam(pe2, "y", 3);
			
			//p2.setOrigin(oo2);
			inc.setOrigin(p2, oo2);
			
			int ib = inc.addUIElement(p2, InputBox.class);
			inc.writeParam(ib, "x", 80);
			inc.writeParam(ib, "y", 50);
			
			//Panel p3=new Panel(-50,-100,150,150);
			//im.addObject(p3);
			int p3 = inc.addPanel();
			inc.writeParam(p3, "x", -50);
			inc.writeParam(p3, "y", -100);
			inc.writeParam(p3, "width", 150);
			inc.writeParam(p3, "height", 150);
			
			//Add exit for panel 3
			int pe3 = inc.addUIElement(p3, PanelExit.class);
			inc.writeParam(pe3, "x", 150-13);
			inc.writeParam(pe3, "y", 3);
			
			//p3.setOrigin(oo3);
			inc.setOrigin(p3, oo3);
			
			
			
			//Panel p4=new Panel(-50,-100);
			//im.addObject(p4);
			int p4 = inc.addPanel();
			inc.writeParam(p4, "x", -50);
			inc.writeParam(p4, "y", -100);
			inc.writeParam(p4, "width", 100);
			inc.writeParam(p4, "height", 100);
			
			//Add exit for panel 4
			int pe4 = inc.addUIElement(p4, PanelExit.class);
			inc.writeParam(pe4, "x", 100-13);
			inc.writeParam(pe4, "y", 3);
			
			//p4.setOrigin(oo4);
			inc.setOrigin(p4, oo4);
			
			
			
			//Dial d1=new Dial(20,20,"dial1");
			//p2.addObject(d1);
			int d1 = inc.addUIElement(p2, Dial.class);
			inc.writeParam(d1, "x", 20);
			inc.writeParam(d1, "y", 20);
			inc.writeParam(d1, "key", "dial1");
			
			//TextBox tb1=new TextBox(0,20,"dial1");
			//p3.addObject(tb1);
			int tb1 = inc.addUIElement(p3, TextBox.class);
			inc.writeParam(tb1, "x", 0);
			inc.writeParam(tb1, "y", 20);
			inc.writeParam(tb1, "key", "dial1");
			
			//TextBox tb2=new TextBox(0,40,"slider1");
			//p3.addObject(tb2);
			int tb2 = inc.addUIElement(p3, TextBox.class);
			inc.writeParam(tb2, "x", 0);
			inc.writeParam(tb2, "y", 40);
			inc.writeParam(tb2, "key", "slider1");
			
			//IndicatorBar ib1=new IndicatorBar(80,10,80,100,"dial1");
			//p3.addObject(ib1);
			int ib1 = inc.addUIElement(p3, IndicatorBar.class);
			inc.writeParam(ib1, "x", 80);
			inc.writeParam(ib1, "y", 10);
			inc.writeParam(ib1, "width", 9);
			inc.writeParam(ib1, "height", 80);
			inc.writeParam(ib1, "range", 100);
			inc.writeParam(ib1, "vertical", true);
			inc.writeParam(ib1, "key", "dial1");
			
			//IndicatorDial id1=new IndicatorDial(20,30,360,"dial1");
			//p4.addObject(id1);
			int id1 = inc.addUIElement(p4, IndicatorDial.class);
			inc.writeParam(id1, "x", 20);
			inc.writeParam(id1, "y", 30);
			inc.writeParam(id1, "range", 360);
			inc.writeParam(id1, "key", "dial1");
			
			//Keyhole kh1=new Keyhole(50,5,123,"key1");
			//p.addObject(kh1);
			int kh1 = inc.addUIElement(p, Keyhole.class);
			inc.writeParam(kh1, "x", 50);
			inc.writeParam(kh1, "y", 5);
			inc.writeParam(kh1, "pattern", 123);
			inc.writeParam(kh1, "key", "key1");
			
			//Keyrack kr1=new Keyrack(60,5,key1);
			//p.addObject(kr1);
			int kr1 = inc.addUIElement(p, Keyrack.class);
			inc.writeParam(kr1, "x", 60);
			inc.writeParam(kr1, "y", 5);
			//inc.writeParam(kr1, "key", key1);
			
			//InventoySlot is1=new InventoySlot(60,20);
			//p.addObject(is1);
			int is1 = inc.addUIElement(p, InventorySlot.class);
			inc.writeParam(is1, "x", 60);
			inc.writeParam(is1, "y", 20);
			
			//Slider s1=new Slider(10,10,70,100,true,"slider1");
			//p4.addObject(s1);
			int s1 = inc.addUIElement(p4, Slider.class);
			inc.writeParam(s1, "x", 10);
			inc.writeParam(s1, "y", 10);
			inc.writeParam(s1, "width", 10);
			inc.writeParam(s1, "height", 70);
			inc.writeParam(s1, "range", 100);
			inc.writeParam(s1, "vertical", true);
			inc.writeParam(s1, "key", "slider1");
			
			//PowerMonitor pm=new PowerMonitor(0,60);
			//p.addObject(pm);
			int pm = inc.addUIElement(p, PowerMonitor.class);
			inc.writeParam(pm, "x", 0);
			inc.writeParam(pm, "y", 60);
			
			//Button b1=new Button(30,80,20,"data1");
			//p.addObject(b1);
			int b1 = inc.addUIElement(p, Button.class);
			inc.writeParam(b1, "x", 30);
			inc.writeParam(b1, "y", 80);
			inc.writeParam(b1, "radius", 20);
			inc.writeParam(b1, "key", "data1");
			
			//Button b2=new Button(30,80,20,"data2");
			//p2.addObject(b2);
			int b2 = inc.addUIElement(p2, Button.class);
			inc.writeParam(b2, "x", 30);
			inc.writeParam(b2, "y", 80);
			inc.writeParam(b2, "radius", 20);
			inc.writeParam(b2, "key", "data2");
			
			//Button b3=new Button(30,80,20,"data3");
			//p3.addObject(b3);
			int b3 = inc.addUIElement(p3, Button.class);
			inc.writeParam(b3, "x", 30);
			inc.writeParam(b3, "y", 80);
			inc.writeParam(b3, "radius", 20);
			inc.writeParam(b3, "key", "data3");
			
			//Button b4=new Button(30,80,20,"data4");
			//p4.addObject(b4);
			int b4 = inc.addUIElement(p4, Button.class);
			inc.writeParam(b4, "x", 30);
			inc.writeParam(b4, "y", 80);
			inc.writeParam(b4, "radius", 20);
			inc.writeParam(b4, "key", "data4");
			
			//Indicator i1=new Indicator(30,50,"data1");
			//p.addObject(i1);
			int i1 = inc.addUIElement(p, Indicator.class);
			inc.writeParam(i1, "x", 30);
			inc.writeParam(i1, "y", 50);
			inc.writeParam(i1, "key", "data1");
			
			//Indicator i2=new Indicator(40,50,"data2");
			//p.addObject(i2);
			int i2 = inc.addUIElement(p, Indicator.class);
			inc.writeParam(i2, "x", 40);
			inc.writeParam(i2, "y", 50);
			inc.writeParam(i2, "key", "data2");
			
			//Indicator i3=new Indicator(50,50,"data3");
			//p.addObject(i3);
			int i3 = inc.addUIElement(p, Indicator.class);
			inc.writeParam(i3, "x", 50);
			inc.writeParam(i3, "y", 50);
			inc.writeParam(i3, "key", "data3");
			
			//Indicator i4=new Indicator(60,50,"data4");
			//p.addObject(i4);
			int i4 = inc.addUIElement(p, Indicator.class);
			inc.writeParam(i4, "x", 60);
			inc.writeParam(i4, "y", 50);
			inc.writeParam(i4, "key", "data4");
			
			//Indicator i5=new Indicator(70,50,"key1");
			//p.addObject(i5);
			int i5 = inc.addUIElement(p, Indicator.class);
			inc.writeParam(i5, "x", 70);
			inc.writeParam(i5, "y", 50);
			inc.writeParam(i5, "key", "key1");
			
			
			
			
			
			
			
			
			
			
			
			
			//DataNetworkNode node1=p.getNode();
			//DataNetworkNode node2=p2.getNode();
			//DataNetworkNode node3=p3.getNode();
			//DataNetworkNode node4=p4.getNode();
			//DataNetworkNode gen1node=generate.getNode();
			
			//node1.addNeighbor(node2);
			//node1.addNeighbor(node3);
			//node1.addNeighbor(node4);
			inc.connectNodes(p, p2);
			inc.connectNodes(p, p3);
			inc.connectNodes(p, p4);
			
			//node2.addNeighbor(node1);
			//node3.addNeighbor(node1);
			//node4.addNeighbor(node1);
			inc.connectNodes(p2, p);
			inc.connectNodes(p3, p);
			inc.connectNodes(p4, p);
			
			//gen1node.addNeighbor(node1);//Generator node is one way, generator only gives info
			//Power nodes must be separate from data(maybe make separate filtered DN Node?)
			inc.connectNodes(generate, p);
			
			//dn.add(node1);
			//dn.add(node2);
			//dn.add(node3);
			//dn.add(node4);
			//dn.add(gen1node);
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
	public int getID() {
		return 1;
	}

}

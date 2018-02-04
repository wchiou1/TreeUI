package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import aspenNetwork.ANKeyWrapper;
import uiItem.UIItem;

public abstract class PPoweredGO extends NonPaneledGameObject{
	public boolean powered=false;
	public boolean toggle=true;
	public int recordedPower;
	public int posPower;
	public int negPower;
	private ANKeyWrapper powerNode;
	public String toggleFreq="";
	private String powerFreq="";
	public double power_draw;
	public static int greedCo = 4;
	public abstract String powerId;
	public PPoweredGO(){
		powerFreq=":P Lightbulb "+id;
		powerNode = new ANKeyWrapper(getNode(),":P");
		power_draw = 0;
		posPower = 0;
		negPower = 0;
	}
	@Override
	public void update(int delta) {
		recordedPower = powerNode.getTotalValue();
		posPower = powerNode.getPositiveTotal();
		negPower = powerNode.getNegativeTotal();
		//Check if it has been ordered to turn off
		if(dataNode.getData(toggleFreq)==0)
			toggle=false;
		else if(dataNode.getData(toggleFreq)==1)
			toggle=true;
		if(recordedPower>0){//There is power on the network
			if(power_draw>-200){//If we are not at max power yet
				double ratio = Math.pow((1/(200.0/greedCo)), (1.0/(greedCo-1)));
				//System.out.println("Ratio:"+(1.0/(greedCo-1)+"+"+(1/(200.0/greedCo))));
				power_draw-=(200/greedCo)*Math.pow(1.0-ratio,Math.abs(power_draw/(200/greedCo)));//Increase consumption
			}
		}
		else if(recordedPower<0){//There is no power on the network
			if(power_draw<0)//If this is consuming power
				//power_draw-=Math.floor(1.0*power_draw/3);
				power_draw++;//Reduce consumption
		}
		else{
			power_draw-=1;
		}
		
		dataNode.changeData(powerFreq, (int) power_draw);
	}
	public abstract subUpdate()

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw the lightbulb sprite
		if(power_draw<-150){
			g.setColor(new Color((int)Math.round(Math.abs((power_draw*1.0)/150)*255),(int)Math.round(Math.abs((power_draw*1.0)/150)*255),0));
		}else{
			g.setColor(new Color((int)Math.round(Math.abs((power_draw*1.0)/150)*255),(int)Math.round(Math.abs((power_draw*1.0)/150)*255),0));
		}
		g.fillOval(x, y, 20, 20);
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		return item;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+10, this.y+10, x, y)<=10)
			return true;
		return false;
	}

	@Override
	public int getCenterX() {
		// TODO Auto-generated method stub
		return x+10;
	}

	@Override
	public int getCenterY() {
		// TODO Auto-generated method stub
		return y+10;
	}
}
package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import GameLogic.GameMath;
import focusObject.UIElement;
import uiItem.UIItem;
//		270
//	180		0
//		90
public class Dial extends UIElement{
	public int radius=20,angle=0,previousAngle=0,mouseAngle=0,lowerLimit=-180,upperLimit=180;
	public String key="";
	/*public Dial(int x, int y,String key){
		this.x=x;
		this.y=y;
		radius=20;
		angle=0;
		previousAngle=0;
		mouseAngle=0;
		this.key=key;
		lowerLimit=-90;
		upperLimit=90;
	}*/
	@Override
	public void update(int mouseX, int mouseY) {
		//super.update(mouseX, mouseY);TODO
		if(!locked)
			return;
		int tempAngle=(int)(Math.atan(1.0*(y+radius-mouseY)/(x+radius-mouseX))/Math.PI*180);
		if(x+radius-mouseX<0)
			tempAngle+=180;
		//Maths for multiple revolutions
		if(Math.abs(angle-(tempAngle+previousAngle-mouseAngle))>180){//Test if the difference is larger than 180
			if(angle>=(tempAngle+previousAngle-mouseAngle)){
				changeAngle(tempAngle+previousAngle-mouseAngle+360,mouseX,mouseY);
				previousAngle=previousAngle+360;
			}
			else{
				changeAngle(tempAngle+previousAngle-mouseAngle-360,mouseX,mouseY);
				previousAngle=previousAngle-360;
			}
		}
		else
			changeAngle(tempAngle+previousAngle-mouseAngle,mouseX,mouseY);
		
		
		dataNode.changeData(key, angle);
	}
	/**
	 * Takes the new angle and tests if it is within the bounds
	 * @param newAngle
	 */
	private void changeAngle(int newAngle,int mouseX, int mouseY){
		if(newAngle<upperLimit&&newAngle>lowerLimit){
			angle=newAngle;
			return;
		}
		if(newAngle>=upperLimit)
			angle=upperLimit;
		if(newAngle<=lowerLimit)
			angle=lowerLimit;
		click(mouseX,mouseY,null);
	}
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		int angleTemp=angle+270;
		g.setColor(Color.black);
		g.fillOval(x, y, radius*2, radius*2);
		g.setColor(Color.lightGray);
		g.fillOval(x+radius/2,y+radius/2,radius,radius);
		
		g.setColor(Color.red);
		int x1=(int)((radius/2)*Math.cos(1.0*angleTemp/180*Math.PI));
		int y1=(int)((radius/2)*Math.sin(1.0*angleTemp/180*Math.PI));
		int x2=(int)((1.0*radius-2)*Math.cos(1.0*angleTemp/180*Math.PI));
		int y2=(int)((1.0*radius-2)*Math.sin(1.0*angleTemp/180*Math.PI));
		
		g.drawLine(x+radius+x1, y+radius+y1, x2+x+radius, y2+y+radius);		
	}

	@Override
	public UIItem click(int mouseX, int mouseY,UIItem item) {
		previousAngle=angle;
		mouseAngle=(int)(Math.atan(1.0*(y+radius-mouseY)/(x+radius-mouseX))/Math.PI*180);
		if(x+radius-mouseX<0)
			mouseAngle+=180;
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+radius, this.y+radius, x, y)<=radius)
			return true;
		return false;
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Dials don't do anything on key presses
		
	}
	
}
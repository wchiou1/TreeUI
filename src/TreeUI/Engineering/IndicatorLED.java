package TreeUI.Engineering;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.Indicator;
import focusObject.UIElement;
import uiItem.UIItem;

public class IndicatorLED extends Indicator{
	public int width=6,height=8;
	public Wire link;

	@Override
	public void draw(Graphics g) {
		if(link!=null&&link.pulse)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
	}
}
package focusObject;

import org.newdawn.slick.Input;

import uiItem.UIItem;

public abstract class OriginObject extends UIElement{
	protected Panel view;
	protected boolean highlight;
	
	void setView(Panel v){
		view=v;
	}
	public void close(){
		if(view!=null)
			view.close();
	}
	public void open(){
		if(view!=null)
			view.open();
	}
	public void openAll(){
		view.openAll();
	}

	@Override
	public boolean isMoveable() {
		return false;
	}
	@Override
	public final void keyPress(int mouseX, int mouseY, int key) {
		if(!locked){
			if(key==Input.KEY_E){
				if(view==null){
					System.out.println("ERROR: panel not set in origin object");
					return;
				}
				view.toggle();
			}
			if(key==Input.KEY_R){
				if(view==null){
					System.out.println("ERROR: panel not set in origin object");
					return;
				}
				view.openAll();
			}
		}
		
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		if(view==null){
			System.out.println("ERROR: panel not set in origin object");
			return item;
		}
		view.toggle();
		return item;
	}
	
	public void dMoveTreeUI(int dx, int dy){
		dmove(dx,dy);
		view.dMoveTreeUI(dx,dy);
	}
	
	public Panel getView(){
		if(view==null)
			System.out.println("WARNING: Panel not set");
		return view;
	}
	public void unhighlight(){
		highlight=false;
	}
	public void highlight(){
		highlight=true;
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {

	}
}
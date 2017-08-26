package focusObject;

import org.newdawn.slick.Input;

import TreeUI.UIElement;

public abstract class OriginObject extends UIElement{
	protected Panel view;
	protected boolean highlight;
	
	public void setView(Panel v){
		view=v;
	}
	public void close(){
		view.close();
	}
	public void open(){
		System.out.println("Opening");
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
	public void update(int mouseX, int mouseY) {

	}
}
package focusObject;

import org.newdawn.slick.Input;

import smallGameObjects.SmallGameObject;

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
		else{
			System.out.println("No view");
		}
	}
	public void openAll(){
		view.openAll();
	}

	@Override
	public boolean isMoveable() {
		return false;
	}
	//Panels are not opened via keys anymore
	/*@Override
	public final void keyPress(int mouseX, int mouseY, int key) {
		testOpenPanel(key);
		objectKeyPress(mouseX,mouseY,key);
		
	}*/
	private final void testOpenPanel(int key){
		if(!locked){
			/*if(key==Input.KEY_E){
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
			}*/
		}
	}
	/**
	 * Default click event toggles the panel
	 * This is overwritten in smallGameObject to limit toggling only when the item is held(If the item that is given is itself)
	 * 
	 */
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		togglePanel(x,y,item);
		return item;
	}
	public boolean existsPanel(){
		return view==null;
	}
	public void togglePanel(int x, int y, SmallGameObject item){
		if(view==null){
			//If there is no panel, just ignore
			return;
		}
		if(!view.isActive()&&panelOpenCondition(x,y,item)){
			//The panel is open
			view.open();
		}
		else{
			view.close();
		}
	}
	/**
	 * Function which is called when attempting to open a panel
	 * Logic can be overrided to "lock" a panel
	 * @return
	 */
	protected boolean panelOpenCondition(int x, int y, SmallGameObject item){
		return true;
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
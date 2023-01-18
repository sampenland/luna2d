package theHunter.ui;

import java.awt.Color;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;
import luna2d.ui.UITextInput;

public class MapEditorMenu extends UIMenu
{	
	private boolean saving;
	
	private UITextInput mapNameInput;
	
	public MapEditorMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale) 
	{
		super(inScene, x, y, width, height, bkgColor, scale);
		
		this.saving = false;
		this.visible = false;
		
		TextDisplay title = new TextDisplay(inScene, "Editor Menu", x + 10, y + 20, Color.white);
		title.setInMenu(this);
		this.addTextDisplay(title);	
		
		TextDisplay lbMapName = new TextDisplay(inScene, "Map Name: ", x + 20, y + 40, Color.white);
		lbMapName.setInMenu(this);
		this.addTextDisplay(lbMapName);	
	}
	
	@Override
	protected void launch() 
	{
		mapNameInput = new UITextInput(inScene, "MAP00", this.screenX + 100, this.screenY + 25, 100, 20, 8);
		mapNameInput.visible = false;
		this.focusedTextInput = mapNameInput;
	}
	
	@Override
	public void update()
	{
		super.update();	
		
		if (!this.visible) return;
		
		if (this.focusedTextInput != null)
		{
			this.focusedTextInput.update();
			return;
		}
		
		checkKeys();
		
	}
	
	private void checkKeys()
	{
//		if (this.isKeyPressed(KeyEvent.VK_S) && !this.saving)
//		{
//			this.saving = true;
//			
//			MapEditor me = (MapEditor)this.inScene;
//			me.saveMap("map1");
//			
//			FadingTextDisplay saveStatusDisplay = new FadingTextDisplay(inScene, "Map Saved", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000);
//			saveStatusDisplay.setInMenu(this);
//			this.addTextDisplay(saveStatusDisplay);
//			
//			SceneTask task = new SceneTask(this.inScene)
//			{
//				@Override
//				public void run()
//				{
//					MapEditor mapEditor = (MapEditor)this.scene;
//					mapEditor.detailedMenu.saving = false;
//				}
//			};
//			
//			Timer t = new Timer();
//			t.schedule(task, 2100);
//			
//		}
	}

	@Override
	protected void close() 
	{
	}

}

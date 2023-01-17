package theHunter.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Timer;
import luna2d.Scene;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import luna2d.ui.UIMenu;
import theHunter.scenes.MapEditor;

public class MapEditorMenu extends UIMenu
{	
	private boolean saving;
	
	public MapEditorMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale) 
	{
		super(inScene, x, y, width, height, bkgColor, scale);
		
		this.saving = false;
		this.visible = false;
		
		TextDisplay title = new TextDisplay(inScene, "Editor Menu", x + 10, y + 20, Color.white);
		title.setInMenu(this);
		this.addTextDisplay(title);
	}
	
	@Override
	public void update()
	{
		super.update();	
		
		if (!this.visible) return;
		
		checkKeys();
		
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_S) && !this.saving)
		{
			this.saving = true;
			
			MapEditor me = (MapEditor)this.inScene;
			me.saveMap("map1");
			
			FadingTextDisplay saveStatusDisplay = new FadingTextDisplay(inScene, "Map Saved", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000);
			saveStatusDisplay.setInMenu(this);
			this.addTextDisplay(saveStatusDisplay);
			
			SceneTask task = new SceneTask(this.inScene)
			{
				@Override
				public void run()
				{
					MapEditor mapEditor = (MapEditor)this.scene;
					mapEditor.detailedMenu.saving = false;
				}
			};
			
			Timer t = new Timer();
			t.schedule(task, 2100);
			
		}
	}

}

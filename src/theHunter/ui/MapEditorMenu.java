package theHunter.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;

import luna2d.Scene;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import luna2d.ui.UIButton;
import luna2d.ui.UIMenu;
import luna2d.ui.UITextInput;
import theHunter.TheHunter;
import theHunter.scenes.MapEditor;

public class MapEditorMenu extends UIMenu
{	
	private boolean saving;
	private boolean loading;
	
	private UITextInput mapNameInput, mapNameLoadInput;
	private UIButton saveButton, loadButton, closeButton;
	
	public MapEditorMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale) 
	{
		super(inScene, x, y, width, height, bkgColor, scale);
		
		this.saving = false;
		this.loading = false;
		
		this.visible = false;
		
		TextDisplay title = new TextDisplay(inScene, "Editor Menu", x + 10, y + 20, Color.white);
		title.setInMenu(this);
		this.addTextDisplay(title);	
		
		TextDisplay lbMapName = new TextDisplay(inScene, "Save Map: ", x + 20, y + 40, Color.white);
		lbMapName.setInMenu(this);
		this.addTextDisplay(lbMapName);	
		
		TextDisplay lbMapNameLoad = new TextDisplay(inScene, "Load Map: ", x + 20, y + 65, Color.white);
		lbMapNameLoad.setInMenu(this);
		this.addTextDisplay(lbMapNameLoad);	
	}
	
	@Override
	public void launch() 
	{
		mapNameInput = new UITextInput(inScene, "MAP00", this.screenX + 100, this.screenY + 25, 100, 20, 8);
		this.focusedTextInput = mapNameInput;
		
		saveButton = new UIButton(inScene, "Save Map", this.screenX + 210, this.screenY + 25, 65, 20);
		
		// ----
		
		mapNameLoadInput = new UITextInput(inScene, "MAP00", this.screenX + 100, this.screenY + 50, 100, 20, 8);
		this.focusedTextInput = mapNameLoadInput;
		
		loadButton = new UIButton(inScene, "Load Map", this.screenX + 210, this.screenY + 50, 65, 20);
		closeButton = new UIButton(inScene, "Close", this.screenX + 240, this.screenY + this.getHeight() - 30, 45, 20);
		
		this.show();
	}
	
	@Override
	public void show()
	{
		super.show();
		
		if (this.saveButton != null)
		{		
			this.saveButton.visible = true;
		}
		
		if (this.loadButton != null)
		{			
			this.loadButton.visible = true;
		}
		
		if (this.closeButton != null)
		{		
			this.closeButton.visible = true;
		}
		
		if (this.mapNameInput != null)
		{
			this.mapNameInput.show();
		}
		
		if (this.mapNameLoadInput != null)
		{
			this.mapNameLoadInput.show();
		}
	}
	
	@Override 
	public void hide()
	{
		super.hide();

		if (this.saveButton != null)
		{			
			this.saveButton.visible = false;
		}
		
		if (this.loadButton != null)
		{		
			this.loadButton.visible = false;
		}
		
		if (this.closeButton != null)
		{		
			this.closeButton.visible = false;
		}
		
		if (this.mapNameInput != null)
		{
			this.mapNameInput.hide();
		}
		
		if (this.mapNameLoadInput != null)
		{
			this.mapNameLoadInput.hide();
		}
	}
	
	@Override
	public void update()
	{
		super.update();	
		
		if (!this.visible) return;
		
		checkMouseClicks();
		
		if (this.focusedTextInput != null)
		{
			this.focusedTextInput.update();
			return;
		}
		
		checkKeys();
		
	}
	
	private void checkMouseClicks()
	{
		if (this.closeButton.mouseClicked)
		{
			MapEditor me = (MapEditor)this.inScene;
			me.closeMenu();
			return;
		}
		else if (this.saveButton.mouseClicked && this.saving == false)
		{
			this.saving = true;
			
			MapEditor me = (MapEditor)this.inScene;
			me.saveMap("m_" + this.mapNameInput.getText());
			
			FadingTextDisplay saveStatusDisplay = new FadingTextDisplay(inScene, "Map Saved", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000);
			saveStatusDisplay.setInMenu(this);
			this.addTextDisplay(saveStatusDisplay);
			
			SceneTask task = new SceneTask(this.getScene())
			{
				@Override
				public void run()
				{
					MapEditor mapEditor = (MapEditor)this.scene;
					mapEditor.detailedMenu.focusedTextInput.setFocus(false);
					mapEditor.detailedMenu.focusedTextInput = null;
					mapEditor.detailedMenu.saving = false;
				}
			};
			
			Timer t = new Timer();
			t.schedule(task, 2100);
			
			this.saveButton.mouseClicked = false;
		}
		else if (this.loadButton.mouseClicked && this.loading == false)
		{
			this.loading = true;
			
			MapEditor me = (MapEditor)this.getScene();
			int[][] map = TheHunter.loadMapOrGrounds(("m_" + this.mapNameLoadInput.getText()), true);
			int[][] mapGrounds = TheHunter.loadMapOrGrounds(("m_" + this.mapNameLoadInput.getText()), false);
			
			me.injectMapData(map, mapGrounds);
			
			FadingTextDisplay loadStatusDisplay = new FadingTextDisplay(inScene, "Loading...", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000);
			loadStatusDisplay.setInMenu(this);
			this.addTextDisplay(loadStatusDisplay);
			
			SceneTask task = new SceneTask(this.inScene)
			{
				@Override
				public void run()
				{
					MapEditor mapEditor = (MapEditor)this.scene;
					mapEditor.detailedMenu.focusedTextInput.setFocus(false);
					mapEditor.detailedMenu.focusedTextInput = null;
					mapEditor.detailedMenu.loading = false;
					
					FadingTextDisplay loadStatusDisplay = new FadingTextDisplay(inScene, "Map Loaded", mapEditor.detailedMenu.screenX + 10, mapEditor.detailedMenu.screenY + mapEditor.detailedMenu.getHeight() - 15, Color.GREEN, 2000);
					loadStatusDisplay.setInMenu(mapEditor.detailedMenu);
					mapEditor.detailedMenu.addTextDisplay(loadStatusDisplay);
					
				}
			};
			
			Timer t = new Timer();
			t.schedule(task, 2100);
			
			this.loadButton.mouseClicked = false;
		}
		else if (this.mapNameInput.mouseClicked)
		{
			MapEditor mapEditor = (MapEditor)this.inScene;
			
			mapEditor.detailedMenu.focusedTextInput.setFocus(false);
			mapEditor.detailedMenu.focusedTextInput = this.mapNameInput;
			mapEditor.detailedMenu.focusedTextInput.setFocus(true);
			
			this.mapNameInput.mouseClicked = false;
		}
		else if (this.mapNameLoadInput.mouseClicked)
		{
			MapEditor mapEditor = (MapEditor)this.inScene;
			
			mapEditor.detailedMenu.focusedTextInput.setFocus(false);
			mapEditor.detailedMenu.focusedTextInput = this.mapNameLoadInput;
			mapEditor.detailedMenu.focusedTextInput.setFocus(true);
			
			this.mapNameLoadInput.mouseClicked = false;
		}
	}
	
	private void checkKeys()
	{
	}

	@Override
	public void close() 
	{
		this.hide();
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}

}

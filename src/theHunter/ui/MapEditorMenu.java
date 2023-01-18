package theHunter.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Timer;

import luna2d.Scene;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import luna2d.ui.UIButton;
import luna2d.ui.UIMenu;
import luna2d.ui.UITextInput;
import theHunter.scenes.MapEditor;

public class MapEditorMenu extends UIMenu
{	
	private boolean saving;
	
	private UITextInput mapNameInput;
	private UIButton saveButton;
	
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
		
		saveButton = new UIButton(inScene, "Save Map", this.screenX + 10, this.screenY + this.getHeight(), 65, 20);
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
		if (this.saveButton.mouseClicked && this.saving == false)
		{
			this.saving = true;
			
			MapEditor me = (MapEditor)this.inScene;
			me.saveMap("m_" + this.mapNameInput.getText());
			
			FadingTextDisplay saveStatusDisplay = new FadingTextDisplay(inScene, "Map Saved", this.screenX + 10, this.screenY + this.getHeight() - 15, Color.GREEN, 2000);
			saveStatusDisplay.setInMenu(this);
			this.addTextDisplay(saveStatusDisplay);
			
			SceneTask task = new SceneTask(this.inScene)
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
		else if (this.mapNameInput.mouseClicked)
		{
			MapEditor mapEditor = (MapEditor)this.inScene;
			
			mapEditor.detailedMenu.focusedTextInput.setFocus(false);
			mapEditor.detailedMenu.focusedTextInput = this.mapNameInput;
			mapEditor.detailedMenu.focusedTextInput.setFocus(true);
			
			this.mapNameInput.mouseClicked = false;
		}
	}
	
	private void checkKeys()
	{

	}

	@Override
	protected void close() 
	{
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

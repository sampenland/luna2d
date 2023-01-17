package theHunter.ui;

import java.awt.Color;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;

public class MapEditorMenu extends UIMenu
{

	public MapEditorMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale) 
	{
		super(inScene, x, y, width, height, bkgColor, scale);
		
		this.addTextDisplay(new TextDisplay(inScene, "Editor Menu", x + 10, y + 20, Color.white));
		
	}
	
	@Override
	public void update()
	{
		super.update();	
		
		
		
	}

}

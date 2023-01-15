package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;

public class MainMenu extends Scene
{		
	public MainMenu(String name) 
	{
		super(name);
	}

	public void start() 
	{
		Log.println("Main menu started.");
		
		new TextDisplay(this, "Main Menu", 200, 150, Color.GREEN);
		new TextDisplay(this, "(E) Map Editor", 200, 300, Color.GREEN);
		new TextDisplay(this, "(P) Map Player", 200, 350, Color.GREEN);
		new TextDisplay(this, "(Q) Quit", 200, 400, Color.GREEN);		
	}
	
	public void update()
	{
		checkKeys();
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_Q) || this.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			Log.println("Quitting the game");
			this.endGame();
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_P))
		{
			MapPlayer mapPlayer = (MapPlayer)this.openScene("MapPlayer");
			mapPlayer.loadAndStartMap("map1");
		}
		
		if (this.isKeyPressed(KeyEvent.VK_E))
		{
			this.openScene("MapEditor");
		}
	}
	
	public void end()
	{
		Log.println("Main menu ended.");
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

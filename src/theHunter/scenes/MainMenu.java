package theHunter.scenes;

import java.awt.event.KeyEvent;

import luna2d.Log;
import luna2d.Scene;

public class MainMenu extends Scene
{
		
	public MainMenu(String name) 
	{
		super(name);
	}

	public void start() 
	{
		Log.println("Main menu started.");
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
			this.openScene("MapPlayer");
		}
	}
	
	public void end()
	{
		Log.println("Main menu ended.");
	}

}

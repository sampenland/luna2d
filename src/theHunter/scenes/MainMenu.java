package theHunter.scenes;

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
		if (this.isKeyPressed((int)'Q'))
		{
			Log.println("Quitting the game");
			this.endGame();
			return;
		}
	}
	
	public void end()
	{
		Log.println("Main menu ended.");
	}

}

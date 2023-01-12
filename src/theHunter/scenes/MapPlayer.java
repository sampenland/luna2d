package theHunter.scenes;

import java.awt.event.KeyEvent;

import luna2d.Log;
import luna2d.Scene;

public class MapPlayer extends Scene
{

	public MapPlayer(String name) 
	{
		super(name);		
	}

	@Override
	public void start() 
	{
		Log.println("Started Map Player");
	}

	@Override
	public void end() 
	{
		Log.println("Ended Map Player");
	}

	@Override
	public void update() 
	{
		checkKeys();
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE)) 
		{
			this.openScene("MainMenu");
		}
	}

}

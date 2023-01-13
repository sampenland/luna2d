package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import luna2d.Game;
import luna2d.Log;
import luna2d.Maths;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import theHunter.TheHunter;

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
		
		loadImages();
	}
	
	private void loadImages()
	{
		ResourceHandler.addImage("Player", "player-idle_16x16_4-frames.png");
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
		
		if (this.isKeyPressed(KeyEvent.VK_E))
		{
			this.openScene("MapEditor");
		}
	}
	
	public void end()
	{
		Log.println("Main menu ended.");
	}

}

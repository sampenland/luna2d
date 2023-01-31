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
		
		new TextDisplay(this, "Main Menu", 200, 150, Color.GREEN, 1);
		new TextDisplay(this, "(E) Map Editor", 200, 250, Color.GREEN, 1);
		new TextDisplay(this, "(W) World Editor", 200, 300, Color.GREEN, 1);
		//new TextDisplay(this, "(P) Map Player", 200, 350, Color.GREEN, 1);
		new TextDisplay(this, "(L) Load Game", 200, 350, Color.GREEN, 1);
		new TextDisplay(this, "(P) World Player", 200, 400, Color.GREEN, 1);
		new TextDisplay(this, "(Q) Quit", 200, 500, Color.GREEN, 1);
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
		
//		if (this.isKeyPressed(KeyEvent.VK_P))
//		{
//			MapPlayer mapPlayer = (MapPlayer)this.openScene("MapPlayer");
//			mapPlayer.loadAndStartMap("MAP00");
//			return;
//		}
		
		if (this.isKeyPressed(KeyEvent.VK_E))
		{
			this.openScene("MapEditor");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_W))
		{
			this.openScene("WorldEditor");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_P))
		{
			WorldPlayer worldPlayer = (WorldPlayer)this.openScene("WorldPlayer");
			worldPlayer.loadAndStart("WORLD00");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_L))
		{
			WorldPlayer worldPlayer = (WorldPlayer)this.openScene("WorldPlayer");
			worldPlayer.loadGame("sam-world");
			return;
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

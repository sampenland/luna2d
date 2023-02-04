package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;

public class GameOver extends Scene
{
	private TextDisplay title;
	private TextDisplay contd;
	
	public GameOver(String name) 
	{
		super(name);
	}

	@Override
	public void start() 
	{
		title = new TextDisplay(this, "You died...", Game.WIDTH / 2, 
				Game.HEIGHT / 2 - 100, Color.white, 1);
		
		contd = new TextDisplay(this, "Press Space to Continue", Game.WIDTH / 2, 
				Game.HEIGHT / 2 + 100, Color.white, 1);
	}

	@Override
	public void end() 
	{
	}

	@Override
	public void update()
	{
		if (this.isKeyPressed(KeyEvent.VK_SPACE) || this.isKeyPressed(KeyEvent.VK_SPACE))
		{
			this.openScene("MainMenu");
		}
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	protected void onMousePressed(MouseEvent e)
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{
	}

}

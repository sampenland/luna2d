package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;
import luna2d.lights.GlowLight;
import luna2d.playerControllers.SimplePlayer;
import luna2d.renderables.FillBar;
import luna2d.renderables.TextDisplay;

public class Player extends SimplePlayer
{
	private FillBar healthBar;
	private GlowLight torch;
	
	private TextDisplay timeLabel;
	
	public Player(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames,
			int msBetweenFrames) 
	{
		super(inScene, imageName, x, y, scale, cellSize, frames, msBetweenFrames);
		
		healthBar = new FillBar(this.health, Game.WIDTH / 2 - 96, Game.HEIGHT / 2 - 96, 
				cellSize * 2, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		healthBar.setFixedScreenPosition(true);

		this.setZoomingEnabled(true);
		
		torch = new GlowLight(inScene, 0, 0, 2);
		
		this.timeLabel = new TextDisplay(inScene, inScene.getDaysAndTime(), 10, 20, Color.white);
		
	}

	@Override
	protected void render(Graphics g) 
	{	
		healthBar.render(g);
	}

	@Override
	protected void update() 
	{
		super.update();
		
		this.timeLabel.updateText(this.inScene.getDaysAndTime());
		
		healthBar.setValue(this.health);
		this.torch.updateScreenPosition(Game.WIDTH / 2 - this.torch.getWidth(), 
				Game.HEIGHT / 2 - this.torch.getHeight());
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

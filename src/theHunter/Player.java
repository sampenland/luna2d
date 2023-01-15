package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.Log;
import luna2d.Scene;
import luna2d.playerControllers.SimplePlayer;
import luna2d.renderables.FillBar;

public class Player extends SimplePlayer
{
	private FillBar healthBar;
	
	public Player(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames,
			int msBetweenFrames) 
	{
		super(inScene, imageName, x, y, scale, cellSize, frames, msBetweenFrames);
		
		healthBar = new FillBar(this.health, this.sprite.getScreenX(), this.sprite.getScreenY(), 
				this.sprite.getWidth() * 2, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		
	}

	@Override
	protected void render(Graphics g) 
	{	
		healthBar.render(g);
	}

	@Override
	protected void update() 
	{
		Log.println(sprite.getScreenX() + ", " + sprite.getScreenY());
		
		if (this.isKeyPressed(KeyEvent.VK_W) || this.isKeyPressed(KeyEvent.VK_UP))
		{
			this.worldY -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S) || this.isKeyPressed(KeyEvent.VK_DOWN))
		{
			this.worldY += this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_A) || this.isKeyPressed(KeyEvent.VK_LEFT))
		{
			this.worldX -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_D) || this.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			this.worldX += this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_1)) 
		{
			this.getGame().updateScale(1);
		}
		
		if (this.isKeyPressed(KeyEvent.VK_2)) 
		{
			this.getGame().updateScale(2);
		}
		
		if (this.isKeyPressed(KeyEvent.VK_3)) 
		{
			this.getGame().updateScale(3);
		}
		
		if (this.isKeyPressed(KeyEvent.VK_4)) 
		{
			this.getGame().updateScale(4);
		}
		
		if (this.isKeyPressed(KeyEvent.VK_5)) 
		{
			this.getGame().updateScale(5);
		}
		
		healthBar.updateScreenPosition(
				sprite.getScreenX() - this.sprite.getWidth(),  
				sprite.getScreenY() - this.sprite.getHeight());
		
		healthBar.setValue(this.health);
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

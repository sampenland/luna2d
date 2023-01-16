package luna2d.playerControllers;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import luna2d.Game;
import luna2d.GameObject;
import luna2d.Scene;
import luna2d.renderables.Sprite;

public class SimplePlayer extends GameObject
{
	private boolean zoomingEnabled = false;
	protected int moveSpeed = 5;
	protected int health = 100;
	
	protected Sprite sprite;
	
	public SimplePlayer(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames, int msBetweenFrames) 
	{
		super(0, 0, Game.PLAYER_ID, true, inScene);
		
		sprite = new Sprite(inScene, imageName, x, y, scale, cellSize, frames, msBetweenFrames);
		sprite.setFixedScreenPosition(true);
		sprite.updateScreenPosition(Game.WIDTH / 2 - sprite.getWidth(), Game.HEIGHT / 2 - sprite.getHeight());
	}
	
	public void setZoomingEnabled(boolean val)
	{
		this.zoomingEnabled = val;
	}
	
	public void setMoveSpeed(int speed)
	{
		this.moveSpeed = speed;
	}

	@Override
	protected void render(Graphics g) 
	{	
	}

	@Override
	protected void update()
	{
		this.playerUpdate();
	}
	
	public void playerUpdate() 
	{
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
		
		this.getGame().updateCameraOffset(this.worldX, this.worldY);
		
		if (this.zoomingEnabled)
		{
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

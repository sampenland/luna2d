package luna2d.playerControllers;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import luna2d.Game;
import luna2d.GameObject;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import theHunter.TheHunter;

public class SimplePlayer extends GameObject
{
	protected int moveSpeed = 5;
	protected float health = 100;
	protected int realWorldX, realWorldY;
	private int worldX, worldY;
	
	protected Sprite sprite;
	
	public SimplePlayer(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames, int msBetweenFrames) 
	{
		super(0, 0, Game.PLAYER_ID, true, inScene);
		
		this.inputEnabled = true;
		this.mouseEnabled = true;
		
		this.worldX = x;
		this.worldY = y;
		
		sprite = new Sprite(inScene, imageName, 0, 0, scale, Game.TOP_DRAW_LAYER -1, cellSize, frames, msBetweenFrames);
		sprite.setFixedScreenPosition(true);
		sprite.updateScreenPosition(Game.WIDTH / 2 - sprite.getWidth(), Game.HEIGHT / 2 - sprite.getHeight());
	}
	
	public int getScreenX()
	{
		return this.screenX;
	}
	
	public int getScreenY()
	{
		return this.screenY;
	}
	
	public int getInternalX()
	{
		return this.worldX;
	}
	
	public int getInternalY()
	{
		return this.worldY;
	}
	
	public int getWorldX()
	{
		return this.realWorldX;
	}
	
	public int getWorldY()
	{
		return this.realWorldY;
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public int getGridX()
	{
		return this.worldX / this.sprite.getWidth();
	}
	
	public int getGridY()
	{
		return this.worldY / this.sprite.getHeight();
	}
	
	public int getWidth()
	{
		return this.sprite.getWidth();
	}
	
	public int getHeight()
	{
		return this.sprite.getHeight();
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
		this.realWorldX = this.worldX + Game.WIDTH/2 - this.getWidth()/2; 
		this.realWorldY = this.worldY + Game.HEIGHT/2 - this.getHeight()/2;
		this.playerUpdate();
	}
	
	public void playerUpdate() 
	{
		int oX = this.worldX;
		int oY = this.worldY;
		int dX = 0;
		int dY = 0;
		
		if (this.isKeyPressed(KeyEvent.VK_W) || this.isKeyPressed(KeyEvent.VK_UP))
		{
			dY -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S) || this.isKeyPressed(KeyEvent.VK_DOWN))
		{
			dY += this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_A) || this.isKeyPressed(KeyEvent.VK_LEFT))
		{
			dX -= this.moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_D) || this.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			dX += this.moveSpeed;
		}
		
		if (this.getScene().getWorldData().getWorldHandler() != null)
		{
			int x = this.getWorldX() + dX;
			int y = this.getWorldY() + dY;
			
			x += this.getWidth() / 2 - this.getWidth() / 4;
			y += this.getHeight() / 2 + this.getHeight() / 4;
			
			Vector2 gPos = Maths.convertToGrid(x, y, TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
			WorldPosition movingToWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);
			
			if (!this.inScene.getWorldData().getWorldHandler().isSolid(movingToWP))
			{
				this.worldX += dX;
				this.worldY += dY;
			}
		}
		else
		{
			this.worldX = oX;
			this.worldY = oY;
		}
		
		this.getGame().updateCameraOffset(this.worldX, this.worldY);
		
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

	@Override
	public void onDestroy() 
	{
	}

	@Override
	protected void pauseTick() 
	{
	}
}

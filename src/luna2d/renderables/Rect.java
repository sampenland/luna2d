package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import luna2d.Game;
import luna2d.Scene;

public class Rect extends Renderable
{

	private Color color;
	private Rectangle rect;
	private boolean filled = true;
	
	public Rect(Scene inScene, int x, int y, int w, int h, boolean filled, Color c, int scale) 
	{
		super(inScene, scale);
		
		this.color = c;
		
		this.rect = new Rectangle();
		
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = w;
		this.rect.height = h;
		this.filled = filled;
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(this.color);
		
		if (this.filled)
		{
			g.fillRect(Game.CAMERA_X + this.rect.x, Game.CAMERA_Y + this.rect.y, 
					Math.round(this.rect.width * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.rect.height * this.scale * Game.CAMERA_SCALE));
		}
		else
		{
			g.drawRect(Game.CAMERA_X + this.rect.x, Game.CAMERA_Y + this.rect.y, 
					Math.round(this.rect.width * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.rect.height * this.scale * Game.CAMERA_SCALE));
		}
		
	}

	@Override
	public void update() 
	{

	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.rect.x = x;
		this.rect.y = y;
	}
	
	public void updateSize(int w, int h)
	{
		this.rect.width = w;
		this.rect.height = h;
	}
	
	public void updateColor(Color c)
	{
		this.color = c;
	}

}

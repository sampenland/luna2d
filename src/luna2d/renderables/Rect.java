package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import luna2d.Game;
import luna2d.Scene;

public class Rect extends Renderable
{

	private Color color;
	private boolean filled = true;
	
	public Rect(Scene inScene, int x, int y, int w, int h, boolean filled, Color c, int scale) 
	{
		super(inScene, x, y, scale);
		
		this.color = c;
		
		this.screenX = x;
		this.screenY = y;
		this.width = w;
		this.height = h;
		this.filled = filled;
	}
	
	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
	
	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		
		g.setColor(this.color);
		
		if (this.filled)
		{
			g.fillRect(Game.CAMERA_X + this.screenX, Game.CAMERA_Y + this.screenY, 
					Math.round(this.width * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.height * this.scale * Game.CAMERA_SCALE));
		}
		else
		{
			g.drawRect(Game.CAMERA_X + this.screenX, Game.CAMERA_Y + this.screenY, 
					Math.round(this.width * this.scale * Game.CAMERA_SCALE), 
					Math.round(this.height * this.scale * Game.CAMERA_SCALE));
		}
		
	}

	@Override
	public void update() 
	{

	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.screenX = x;
		this.screenY = y;
	}
	
	public void updateSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}
	
	public void updateColor(Color c)
	{
		this.color = c;
	}

}

package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import luna2d.Scene;

public class Rect extends Renderable
{

	private Color color;
	private Rectangle rect;
	private boolean filled = true;
	
	public Rect(Scene inScene, int x, int y, int w, int h, boolean filled, Color c) 
	{
		super(inScene);
		
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
			g.fillRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		}
		else
		{
			g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		}
		
	}

	@Override
	public void update() 
	{

	}

	@Override
	public void updatePosition(int x, int y) 
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

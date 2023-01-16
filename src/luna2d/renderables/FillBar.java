package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import luna2d.Game;
import luna2d.Scene;

public class FillBar extends Renderable
{

	private int x, y, w, h;
	protected boolean fixedScreenPosition = false;
	
	private Rectangle bkgRect;
	private Color bkgColor;
	
	private Rectangle outlineRect;
	private Color outlineColor;
	
	private Rectangle valueRect;
	private Color valueColor;
	
	private int outlineSize;
	private int value;
	private int max;
	
	public FillBar(int value, int x, int y, int w, int h, int outlineSize, int scale,
			Color bkgColor, Color outlineColor, Color valueColor, Scene inScene) 
	{
		super(inScene, scale);
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.max = 100;
		
		this.bkgColor = bkgColor;
		this.outlineColor = outlineColor;
		this.valueColor = valueColor;
		
		this.value = value;
		this.outlineSize = outlineSize;
		
		this.bkgRect = new Rectangle();
		this.outlineRect = new Rectangle();
		this.valueRect = new Rectangle();
		
		this.update();		
		
	}
	
	public void setFixedScreenPosition(boolean val)
	{
		this.fixedScreenPosition = val;
	}
	
	public int getWidth()
	{
		return this.bkgRect.width * Game.CAMERA_SCALE;
	}
	
	protected void setMax(int max)
	{
		this.max = max;
	}

	@Override
	public void render(Graphics g) 
	{
		int drawX, drawY;
		
		if(this.fixedScreenPosition)
		{
			drawX = 0;
			drawY = 0;
		}
		else
		{
			drawX = Game.CAMERA_X * Game.CAMERA_SCALE;
			drawY = Game.CAMERA_Y * Game.CAMERA_SCALE;
		}
		
		g.setColor(outlineColor);
		g.fillRect(drawX + this.outlineRect.x, drawY + this.outlineRect.y, 
				Math.round(this.outlineRect.width * this.scale * Game.CAMERA_SCALE), 
				Math.round(this.outlineRect.height * this.scale * Game.CAMERA_SCALE));
		
		g.setColor(bkgColor);
		g.fillRect(drawX + this.bkgRect.x, drawY + this.bkgRect.y, 
				this.bkgRect.width, this.bkgRect.height);
		
		g.setColor(valueColor);
		g.fillRect(drawX + this.valueRect.x, drawY + this.valueRect.y, 
				Math.round(this.valueRect.width * this.scale * Game.CAMERA_SCALE) + this.outlineSize, 
				Math.round(this.outlineRect.height * this.scale * Game.CAMERA_SCALE) - this.outlineSize * 2);
		}

	@Override
	public void update() 
	{
		this.bkgRect.x = this.outlineRect.x = this.valueRect.x = this.x;
		this.bkgRect.y = this.outlineRect.y = this.valueRect.y = this.y;
		this.bkgRect.width = this.outlineRect.width = this.w;
		this.bkgRect.height = this.outlineRect.height = this.valueRect.height = this.h;
		
		this.outlineRect.x -= this.outlineSize;
		this.outlineRect.width += 2 * this.outlineSize;
		this.outlineRect.y -= this.outlineSize;
		this.outlineRect.height += 2 * this.outlineSize;
			
		this.valueRect.width = Math.round(this.w * (float)((float)this.value / (float)this.max));
		
	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;		
	}
	
	protected void updateSize(int w, int h, int outlineSize)
	{
		this.w = w;
		this.h = h;
		this.outlineSize = outlineSize;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
}

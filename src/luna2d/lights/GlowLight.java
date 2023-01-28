package luna2d.lights;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.ResourceHandler;
import luna2d.Scene;

public class GlowLight extends Light
{	
	private int radius;
		
	public GlowLight(Scene inScene, int worldX, int worldY, int radius) 
	{
		super(inScene, worldX, worldY);
		this.radius = radius;		
	}	
	
	public int getRadius()
	{
		return this.radius;
	}
	
	public void setRadius(int r)
	{
		this.radius = r;
	}

	@Override
	public void update() 
	{
	}

	@Override
	public void render(Graphics g) 
	{	
	}
	
}

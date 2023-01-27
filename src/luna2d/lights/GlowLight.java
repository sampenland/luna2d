package luna2d.lights;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.ResourceHandler;
import luna2d.Scene;

public class GlowLight extends Light
{	
	private int radius;
	
	public Color color[] = new Color[5];
	public float fraction[] = new float[5];
	
	
	public GlowLight(Scene inScene, int worldX, int worldY, int radius) 
	{
		super(inScene, worldX, worldY);
		this.radius = radius;
		
		color[0] = new Color(0, 0, 0, 0f);
		color[1] = new Color(0, 0, 0, 0.25f);
		color[2] = new Color(0, 0, 0, 0.5f);
		color[3] = new Color(0, 0, 0, 0.75f);
		color[4] = new Color(0, 0, 0, 0.95f);
		
		fraction[0] = 0f;
		fraction[1] = 0.25f;
		fraction[2] = 0.5f;
		fraction[3] = 0.75f;
		fraction[4] = 1f;
		
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

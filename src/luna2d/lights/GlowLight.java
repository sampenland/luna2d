package luna2d.lights;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.ResourceHandler;
import luna2d.Scene;

public class GlowLight extends Light
{	
	private int radius;
	
	public Color color[] = new Color[11];
	public float fraction[] = new float[11];
	
	
	public GlowLight(Scene inScene, int worldX, int worldY, int radius) 
	{
		super(inScene, worldX, worldY);
		this.radius = radius;
		
		color[0] = new Color(0, 0, 0, 0f);
		color[1] = new Color(0, 0, 0, 0.1f);
		color[2] = new Color(0, 0, 0, 0.2f);
		color[3] = new Color(0, 0, 0, 0.3f);
		color[4] = new Color(0, 0, 0, 0.4f);
		color[5] = new Color(0, 0, 0, 0.5f);
		color[6] = new Color(0, 0, 0, 0.65f);
		color[7] = new Color(0, 0, 0, 0.7f);
		color[8] = new Color(0, 0, 0, 0.75f);
		color[9] = new Color(0, 0, 0, 0.8f);
		color[10] = new Color(0, 0, 0, 0.85f);
		
		fraction[0] = 0f;
		fraction[1] = 0.1f;
		fraction[2] = 0.2f;
		fraction[3] = 0.3f;
		fraction[4] = 0.4f;
		fraction[5] = 0.5f;
		fraction[6] = 0.6f;
		fraction[7] = 0.7f;
		fraction[8] = 0.8f;
		fraction[9] = 0.9f;
		fraction[10] = 1f;
		
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

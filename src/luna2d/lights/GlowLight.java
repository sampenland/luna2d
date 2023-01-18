package luna2d.lights;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import luna2d.ResourceHandler;
import luna2d.Scene;

public class GlowLight extends Light
{	
	public GlowLight(Scene inScene, int worldX, int worldY, int scale) 
	{
		super(inScene, worldX, worldY, scale);
		this.imgRef = ResourceHandler.getImage("GLOBAL_GlowLight");
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

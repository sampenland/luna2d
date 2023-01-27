package luna2d.lights;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;

public abstract class Light 
{
	private int screenX, screenY, worldX, worldY;
	protected boolean visible;
	
	private Scene inScene;
	
	public Light(Scene inScene, int worldX, int worldY)
	{
		this.inScene = inScene;
		this.worldX = worldX;
		this.worldY = worldY;
		
		if (this.inScene == null || this.inScene.getObjectHandler() == null)
		{
			Log.println("Light failed to construct. Null handler");
			return;
		}
		
		this.inScene.getObjectHandler().addLight(this);
	}
	
	public void updateWorldPosition(int x, int y)
	{
		this.worldX = x;
		this.worldY = y;
	}
	
	public int getWorldX()
	{
		return this.worldX;
	}
	
	public int getWorldY()
	{
		return this.worldY;
	}
	
	public void updateScreenPosition(int x, int y)
	{
		this.screenX = x;
		this.screenY = y;
	}
	
	public int getScreenX()
	{
		return this.screenX;
	}
	
	public int getScreenY()
	{
		return this.screenY;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
}

package luna2d.renderables;

import java.awt.Graphics;

import luna2d.Scene;

public abstract class Renderable 
{
	private Scene inScene;
	
	protected int worldX, worldY, screenX, screenY;
	protected int scale;
	
	public Renderable(Scene inScene, int scale)
	{
		this.inScene = inScene;
		this.inScene.getObjectHandler().addRenderable(this);
		this.scale = scale;
		this.worldX = this.worldY = this.screenX = this.screenY = 0;
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
	
	public abstract void render(Graphics g);
	
	public void gameUpdate()
	{
		update();
	}
	
	public abstract void update();
}

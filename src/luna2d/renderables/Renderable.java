package luna2d.renderables;

import java.awt.Graphics;

import luna2d.Scene;

public abstract class Renderable 
{
	private Scene inScene;
	
	public int worldX, worldY, screenX, screenY;
	protected int scale;
	public boolean visible;
	protected boolean customRender; // Means it will not be drawn from automatically but manually
	
	public Renderable(Scene inScene, int scale)
	{
		this.inScene = inScene;
		this.inScene.getObjectHandler().addRenderable(this);
		this.scale = scale;
		this.worldX = this.worldY = this.screenX = this.screenY = 0;
		this.visible = true;
		this.customRender = false;
	}
	
	public void setCustomRender(boolean val)
	{
		this.customRender = val;
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
	
	public void customRender(Graphics g)
	{
		boolean cRender = this.customRender;
		this.customRender = false;
		this.render(g);
		this.customRender = cRender;
	}
	
	public void show()
	{
		this.visible = true;
	}
	
	public void hide()
	{
		this.visible = false;
	}
	
	public void gameUpdate()
	{
		update();
	}
	
	public abstract void update();
}

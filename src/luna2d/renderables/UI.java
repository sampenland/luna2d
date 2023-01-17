package luna2d.renderables;

import java.awt.Graphics;

import luna2d.Scene;

public abstract class UI 
{
	private Scene inScene;
	
	public int worldX, worldY, screenX, screenY;
	protected int scale;
	public boolean visible;
	
	public UI(Scene inScene, int scale)
	{
		this.inScene = inScene;
		this.inScene.getObjectHandler().addUI(this);
		this.scale = scale;
		this.worldX = this.worldY = this.screenX = this.screenY = 0;
		this.visible = true;
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

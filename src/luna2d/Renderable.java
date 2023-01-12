package luna2d;

import java.awt.Graphics;

public abstract class Renderable 
{
	private Scene inScene;
	
	public Renderable(Scene inScene)
	{
		this.inScene = inScene;
		this.inScene.getObjectHandler().addRenderable(this);
	}
	
	public abstract void render(Graphics g);
	public abstract void update();
	public abstract void updatePosition(int x, int y);
}

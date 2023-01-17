package luna2d.lights;

import java.awt.Graphics;
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
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
}

package luna2d;

import java.awt.Graphics;
import java.util.HashMap;

public abstract class GameObject 
{

	public boolean inputEnabled = false;
	protected int x, y, objectType;
	protected HashMap<Integer, Boolean> keys;

	private Scene inScene;
	
	public GameObject(int x, int y, int objectType, boolean inputEnabled, Scene inScene)
	{
		this.inScene = inScene;
		this.keys = new HashMap<Integer, Boolean>();
		
		this.x = x;
		this.y = y;
		this.objectType = objectType;
		this.inputEnabled = inputEnabled;
		
		this.inScene.getObjectHandler().addObject(this);
	}
	
	protected void addObjectToHandler(GameObject o) 
	{
		this.inScene.getObjectHandler().addObject(o);
	}
	
	protected void addRenderableToHandler(Renderable r)
	{
		this.inScene.getObjectHandler().addRenderable(r);
	}
	
	protected abstract void render(Graphics g);
	protected abstract void update();
	
	protected void keyPressed(int keycode)
	{
		this.keys.put(keycode, true);
	}
	
	protected void keyReleased(int keycode)
	{
		this.keys.put(keycode, false);
	}
	
	public boolean isKeyPressed(int keycode)
	{
		if (this.keys.get(keycode) == null) return false;
		return this.keys.get(keycode);
	}
	
}

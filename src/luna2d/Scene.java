package luna2d;

import java.awt.Graphics;
import java.util.HashMap;

public abstract class Scene 
{

	public String name;

	protected ObjectHandler objHandler;
	protected HashMap<Integer, Boolean> keys;
	
	private Game game;
	
	public Scene(String name)
	{
		this.name = name;
		this.keys = new HashMap<Integer, Boolean>();
		this.objHandler = new ObjectHandler();
	}
	
	void setGame(Game g)
	{
		this.game = g;
	}
	
	public ObjectHandler getObjectHandler() { return this.objHandler; }
	
	public abstract void start();
	public abstract void end();
	
	public void endGame()
	{
		if (this.game == null)
		{
			Log.println("No game to end");
			return;
		}
		
		this.game.endGame();
	}
	
	public void render(Graphics g)
	{
		this.objHandler.renderAllObjects(g);
	}
	
	void backgroundUpdate()
	{
		this.objHandler.updateAllObjects();
		this.update();
	}
	
	public abstract void update();
	
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

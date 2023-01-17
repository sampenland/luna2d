package luna2d;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;

import luna2d.lights.Light;
import luna2d.renderables.Renderable;

public abstract class Scene
{

	public String name;

	protected ObjectHandler objHandler;
	protected HashMap<Integer, Boolean> keys;
	private boolean inputEnabled = false;
	private boolean mouseEnabled = false;
	
	private Game game;	
	private DayNightCycle dayNightCycle = null;
	
	public Scene(String name)
	{
		this.name = name;
		this.keys = new HashMap<Integer, Boolean>();
		this.objHandler = new ObjectHandler();
		this.dayNightCycle = null;
	}
	
	public void setDayNightCycle(DayNightCycle cycle, DayNightCycleTime time)
	{
		this.dayNightCycle = cycle;
		this.dayNightCycle.setTime(time);
	}
	
	public String getTime()
	{
		if (this.dayNightCycle != null)
		{
			return this.dayNightCycle.getTime();
		}
		
		return "No day/night cycle";
	}
	
	public String getDaysAndTime()
	{
		if (this.dayNightCycle != null)
		{
			return this.dayNightCycle.getDaysAndTime();
		}
		
		return "No day/night cycle";
	}
	
	public void setInputEnabled(boolean isEnabled)
	{
		this.inputEnabled = isEnabled;
	}
	
	public boolean getInputEnabled()
	{
		return this.inputEnabled;
	}
	
	public void setMouseEnabled(boolean isEnabled)
	{
		this.mouseEnabled = isEnabled;
	}
	
	public boolean getMouseEnabled()
	{
		return this.mouseEnabled;
	}
	
	void setGame(Game g)
	{
		this.game = g;
	}
	
	public Game getGame()
	{
		return this.game;
	}
	
	public ObjectHandler getObjectHandler() { return this.objHandler; }
	
	public abstract void start();
	public abstract void end();
	
	public void unload()
	{
		this.objHandler.getUIs().clear();
		this.objHandler.getObjects().clear();
		
		for(Renderable r : this.objHandler.getRenderables())
		{
			if (r.inMenu != null)
			{
				r.inMenu.clearAllRenderables();
			}
		}
		
		this.objHandler.getRenderables().clear();
	}
	
	public Scene openScene(String sceneName)
	{
		if (this.game == null)
		{
			Log.println("No game to end");
			return null;
		}
		
		SceneManager sceneManager = this.game.getSceneManager();
		Scene s = sceneManager.openScene(sceneName);
		return s;
	}
	
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
		this.objHandler.renderAllRenderables(g);
		this.objHandler.renderAllUIs(g);
		
		// Render lights with over-top Day/Night cycle background
		LinkedList<Light> lights = this.objHandler.getLights();
		
		
	}
	
	void backgroundUpdate()
	{
		this.objHandler.updateAllObjects();
		this.objHandler.updateAllRenderables();
		this.objHandler.updateAllUIs();		
		this.update();
	}
	
	public abstract void update();
	
	protected void keyPressed(int keycode)
	{
		if(!this.inputEnabled) return;
		this.keys.put(keycode, true);
	}
	
	protected void keyReleased(int keycode)
	{
		if(!this.inputEnabled) return;
		this.keys.put(keycode, false);
	}
	
	public boolean isKeyPressed(int keycode)
	{
		if(!this.inputEnabled) return false;
		if (this.keys.get(keycode) == null) return false;
		return this.keys.get(keycode);
	}
	
	protected abstract void onMouseClick(MouseEvent e);
	protected abstract void onMousePressed(MouseEvent e);
	protected abstract void onMouseReleased(MouseEvent e);
	
}

package luna2d;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

import luna2d.lights.Light;
import luna2d.renderables.Renderable;
import luna2d.renderables.Sprite;
import luna2d.ui.UI;

public abstract class Scene
{

	public String name;

	protected ObjectHandler objHandler;
	protected MouseHandler objMouseHandler;
	protected HashMap<Integer, Boolean> keys;
	private boolean inputEnabled = false;
	private boolean mouseEnabled = false;
	
	private Game game;	
	private DayNightCycle dayNightCycle = null;
	private Object player;
	
	public Scene(String name)
	{
		this.name = name;
		this.keys = new HashMap<Integer, Boolean>();
		this.objHandler = new ObjectHandler();
		this.dayNightCycle = null;
	}
	
	public void setPlayer(Object player)
	{
		this.player = player;
	}
	
	public Object getPlayer()
	{
		return this.player;
	}
	
	public void setDayNightCycle(DayNightCycle cycle, DayNightCycleTime time)
	{
		this.dayNightCycle = cycle;
		this.dayNightCycle.setTime(time);
	}
	
	public DayNightCycle getDayNightEngine()
	{
		return this.dayNightCycle;
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
	
	public boolean isDayTime()
	{
		if (this.dayNightCycle != null)
		{
			return this.dayNightCycle.isDayTime();
		}
		
		return false;
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
		this.objMouseHandler = g.getMouseHandler();
	}
	
	public MouseHandler getMouseHandler()
	{
		return this.objMouseHandler;
	}
	
	public Game getGame()
	{
		return this.game;
	}
	
	public void addSprite(Sprite sprite)
	{
		this.objHandler.addRenderable(sprite);
	}
	
	public ObjectHandler getObjectHandler() { return this.objHandler; }
	
	public abstract void start();
	public abstract void end();
	
	public void unload()
	{
		this.objHandler.getUIs().clear();
		this.objHandler.getObjects().clear();
		this.objHandler.getLights().clear();
		
		for (LinkedList<Renderable> renderLayer : this.objHandler.getRenderables())
		{
			for (Renderable r : renderLayer)
			{
				if (r.inMenu != null)
				{
					r.inMenu.clearAllRenderables();
				}
			}
		}
		
		this.objHandler.clearAllRenderables();
		
		Game.CAMERA_SCALE = 1;
		Game.CAMERA_X = 0;
		Game.CAMERA_Y = 0;
		this.game.updateCameraOffset(0, 0);
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
		
		if ((this.dayNightCycle != null && !this.dayNightCycle.isDayTime()) || WeatherSystem.isRaining)
		{
			// Render lights with over-top Day/Night cycle background
			LinkedList<Light> lights = this.objHandler.getLights();
			
			BufferedImage img = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = img.getGraphics();
			
			if (WeatherSystem.isRaining)
			{
				graphics.setColor(WeatherSystem.cloudColor);				
			}
			else
			{
				graphics.setColor(this.dayNightCycle.getCurrentColor());
			}
			
			
			graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			graphics.dispose();
			
			Graphics2D g2d = img.createGraphics();
			g2d.setComposite(AlphaComposite.DstOut);
			
			for (Light light : lights)
			{
				BufferedImage lightImg = light.getImageRef();
			    g2d.drawImage(lightImg, light.getScreenX(), light.getScreenY(), 
			    		lightImg.getWidth() * Game.CAMERA_SCALE * light.getScale(), 
			    		lightImg.getHeight() * Game.CAMERA_SCALE * light.getScale(), null);
			}
			
			g2d.dispose();
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, 0, 0, Game.WIDTH, Game.HEIGHT, null);	
		}
			
		this.objHandler.renderAllUIs(g);
	}
	
	void backgroundUpdate()
	{
		this.objHandler.updateAllObjects();
		if (!Game.paused)
		{
			this.objHandler.updateAllRenderables();
		}
		
		this.objHandler.updateAllUIs();
		
		if (!Game.paused)
		{
			this.update();
		}		
		
		for (GameObject temp : this.objHandler.getObjects())
		{
			temp.mouseClicked = false;
		}
		
		for (LinkedList<Renderable> renderLayer : this.objHandler.getRenderables())
		{
			for (Renderable temp : renderLayer)
			{
				temp.mouseClicked = false;
			}
		}
		
		for (UI temp : this.objHandler.getUIs())
		{
			temp.mouseClicked = false;
		}
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

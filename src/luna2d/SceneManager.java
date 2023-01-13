package luna2d;

import java.awt.Graphics;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import luna2d.timers.SceneTimer;

public class SceneManager 
{
	private Game game;
	private LinkedList<Scene> scenes;
	private Scene currentScene;
	
	private boolean running = false;
	
	public SceneManager(Game gameRef)
	{
		this.game = gameRef;
		this.scenes = new LinkedList<Scene>();
	}
	
	public void startEngine(String name)
	{
		this.running = this.openScene(name);
	}
	
	public void stopEngine()
	{
		if (this.currentScene != null)
		{
			this.currentScene.end();
			this.currentScene = null;
		}
		
		this.running = false;
	}
	
	public boolean openScene(String sceneName)
	{
		for(int i = 0; i < this.scenes.size(); i++)
		{
			Scene newScene = this.scenes.get(i); 
			if (newScene != null && newScene.name == sceneName)
			{
				if (this.currentScene != null)
				{
					this.currentScene.unload();
					this.currentScene.end();
				}
				
				this.currentScene = newScene;
				this.currentScene.keys.clear();
				this.currentScene.setInputEnabled(false);
				this.currentScene.setGame(this.game);
				this.currentScene.start();
				
				// Wait a 750 milliseconds before enabling input
				SceneTimer reenableInput = new SceneTimer(this.currentScene) {
					@Override
					public void run() 
					{
						this.scene.setInputEnabled(true);
					}
				};
				Timer ticker = new Timer("T-" + (new Date()).getTime()); 
				ticker.schedule(reenableInput, 750);
				
				return true;
			}
		}
		
		Log.println("Scene: " + sceneName + " NOT FOUND in Scene Manager scenes list");
		return false;
	}
	
	public Scene getCurrentScene() { return this.currentScene; }
	
	public void addScene(Scene s)
	{
		if (this.scenes.indexOf(s) == -1)
		{
			s.setGame(this.game);
			this.scenes.add(s);
		}
	}
	
	public void removeScene(Scene s)
	{
		if (this.scenes.indexOf(s) != -1)
		{
			this.scenes.remove(s);
		}
	}
	
	public void render(Graphics g)
	{
		if (this.running == false) return;
		if (this.currentScene == null) return;
		
		this.currentScene.render(g);
	}
	
	public void update()
	{
		if (this.running == false) return;
		if (this.currentScene == null) return;
		
		this.currentScene.backgroundUpdate();
	}
	
}

package luna2d;

import java.awt.Graphics;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
	
	public void startEngine(Scene startScene)
	{
		if (startScene == null) 
		{
			System.out.println("Invalid scene. Could not start.");
			return;
		}
		
		int foundIndex = this.scenes.indexOf(startScene);
		if (foundIndex == -1)
		{
			this.scenes.add(startScene);
			foundIndex = this.scenes.indexOf(startScene);
		}
		
		this.openScene(this.scenes.get(foundIndex).name);
		this.running = true;
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
	
	public void openScene(String sceneName)
	{
		for(int i = 0; i < this.scenes.size(); i++)
		{
			Scene newScene = this.scenes.get(i); 
			if (newScene != null && newScene.name == sceneName)
			{
				if (this.currentScene != null)
				{
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
				
				return;
			}
		}
		
		Log.println("Scene: " + sceneName + " NOT FOUND in Scene Manager scenes list");
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

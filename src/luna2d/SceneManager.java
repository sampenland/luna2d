package luna2d;

import java.awt.Graphics;
import java.util.LinkedList;

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
		
		this.currentScene = this.scenes.get(foundIndex);
		this.currentScene.setGame(this.game);
		this.currentScene.start();
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
	
	public void setCurrent(Scene s)
	{
		if (this.currentScene != s)
		{
			int foundIndex = this.scenes.indexOf(s);
			if (foundIndex == -1)
			{
				this.scenes.add(s);
			}
			else
			{
				this.currentScene = this.scenes.get(foundIndex);
			}
		}
		
		this.currentScene.setGame(this.game);
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

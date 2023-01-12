package luna2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2680723036795663013L;
	
	protected Window window;
	public SceneManager sceneManager;
	public InputHandler inputHandler;
	
	private Thread mainGameThread;
	private boolean gameRunning = false;
	
	private int width, height;
	private String title;
	private Color bkgColor;
	
	public void init(int width, int height, String title, Color bkgColor)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		this.bkgColor = bkgColor;
		
		sceneManager = new SceneManager(this);		
		window = new Window(this.width, this.height, this.title, this);
	}
	
	public SceneManager getSceneManager()
	{
		return sceneManager;
	}
	
	public ObjectHandler getObjectHandler()
	{
		if (this.sceneManager != null)
		{
			if (this.sceneManager.getCurrentScene() != null)
			{
				return this.sceneManager.getCurrentScene().getObjectHandler();
			}
		}
		
		return null;
	}
	
	private void tick()
	{
		if (this.sceneManager == null) return;
		
		if (this.inputHandler == null)
		{
			this.inputHandler = new InputHandler(this);
			this.addKeyListener(inputHandler);
			Log.println("Input Handler initialized.");
		}
		
		this.sceneManager.update();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(this.bkgColor);
		g.fillRect(0, 0, this.width, this.height);
		
		this.sceneManager.render(g);
		
		g.dispose();
		bs.show();
	}

	public void beginSceneEngine(Scene startingScene)
	{
		this.sceneManager.startEngine(startingScene);
	}
	
	public synchronized void start()
	{
		mainGameThread = new Thread(this);
		mainGameThread.start();
		this.gameRunning = true;
	}
	
	public void endGame()
	{
		this.gameRunning = false;
	}
	
	private synchronized void stop()
	{
		System.exit(0);
	}

	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (this.gameRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			
			if(this.gameRunning)
			{
				render();
			}
			
			frames++;
				
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		
		}
		
		stop();
		
	}
}

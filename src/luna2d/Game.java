package luna2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2680723036795663013L;
	
	protected Window window;
	public SceneManager sceneManager;
	public InputHandler inputHandler;
	
	private Thread mainGameThread;
	private boolean gameRunning = false;
	
	public static int WIDTH, HEIGHT;
	public static int mouseX, mouseY;
	private String title;
	private Color bkgColor;
	
	public static String resDir;
		
	public void init(int width, int height, String title, Color bkgColor, String resourceDir)
	{
		this.WIDTH = width;
		this.HEIGHT = height;
		this.title = title;
		this.bkgColor = bkgColor;
		this.resDir = resourceDir;
		
		sceneManager = new SceneManager(this);		
		window = new Window(this.WIDTH, this.HEIGHT, this.title, this);
	}
	
	public void setBkgColor(Color c)
	{
		this.bkgColor = c;
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
		g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		
		this.sceneManager.render(g);
		
		g.dispose();
		bs.show();
	}

	public void beginSceneEngine(String name)
	{
		this.sceneManager.startEngine(name);
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
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (this.gameRunning)
		{
			mouseX = MouseInfo.getPointerInfo().getLocation().x - this.window.getFrame().getLocationOnScreen().x;
			mouseY = MouseInfo.getPointerInfo().getLocation().y - this.window.getFrame().getLocationOnScreen().y;
			
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
				//Log.println("FPS: " + frames);
				frames = 0;
			}
		
		}
		
		stop();
		
	}
}

package luna2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2680723036795663013L;
	
	protected Window window;
	private Thread mainGameThread;
	private boolean gameRunning = false;
	
	private int width, height;
	private String title;
	
	public void init(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		
		window = new Window(width, height, title, this);
	}
	
	private void tick()
	{
		
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
		
		g.setColor(Color.blue);
		g.fillRect(0, 0, this.width, this.height);
		
		g.dispose();
		bs.show();
	}

	public synchronized void start()
	{
		mainGameThread = new Thread(this);
		mainGameThread.start();
		this.gameRunning = true;
	}
	
	public synchronized void stop()
	{
		try
		{
			this.mainGameThread.join();
			this.gameRunning = false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (this.gameRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta > 1)
			{
				tick();
				delta--;
			}
			
			if(this.gameRunning)
			{
				render();
				frames++;
				
				if(System.currentTimeMillis() - timer > 1000)
				{
					timer += 1000;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
		}
		
		stop();
	}
}

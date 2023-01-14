package luna2d.renderables;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.timers.SpriteTimer;

public class Sprite extends Renderable
{

	private BufferedImage imgRef;
	private Rectangle drawRect;
	private float scale;
	
	private boolean isAnimated;
	private int currentFrame;
	private int frames;
	private int frameWidth;
	private SpriteTimer nextFrameTask;
	private Timer nextFrameTimer;
	
	public boolean visible = true;
	
	public Sprite(Scene inScene, String name, int x, int y, float scale) 
	{
		super(inScene);
		
		this.isAnimated = false;
		this.currentFrame = 0;
		this.frames = 1;
		this.nextFrameTask = null;
		this.nextFrameTimer = null;
		
		this.scale = scale;
		this.drawRect = new Rectangle();
		
		imgRef = ResourceHandler.getImage(name);
		
		if (imgRef == null)
		{
			Log.println("Failed to load Sprite image: " + name);
			this.visible = false;
		}
		else
		{
			this.drawRect.width = imgRef.getWidth();
			this.drawRect.height = imgRef.getHeight();
		}

		this.drawRect.x = x;
		this.drawRect.y = y;
		
		this.frameWidth = this.drawRect.width;
		
	}
	
	public Sprite(Scene inScene, String name, int x, int y, float scale, int w, int h) 
	{
		super(inScene);
		
		this.isAnimated = false;
		this.currentFrame = 0;
		this.frames = 1;
		this.nextFrameTask = null;
		this.nextFrameTimer = null;
		
		this.scale = scale;
		this.drawRect = new Rectangle();
		
		imgRef = ResourceHandler.getImage(name);
		
		if (imgRef == null)
		{
			Log.println("Failed to load Sprite image: " + name);
			this.visible = false;
		}

		this.drawRect.x = x;
		this.drawRect.y = y;
		this.drawRect.width = w;
		this.drawRect.height = h;
		
		this.frameWidth = this.drawRect.width;
		
	}
	
	public Sprite(Scene inScene, String name, int x, int y, float scale, int frameWidth, int frames, int msBetweenFrames)
	{
		super(inScene);
		
		this.isAnimated = true;
		this.frames = frames - 1;
		this.currentFrame = 0;
		this.frameWidth = frameWidth;
		
		this.scale = scale;
		this.drawRect = new Rectangle();
		
		imgRef = ResourceHandler.getImage(name);
		
		if (imgRef == null)
		{
			Log.println("Failed to load Sprite image: " + name);
			this.visible = false;
		}
		else
		{
			this.drawRect.width = imgRef.getWidth();
			this.drawRect.height = imgRef.getHeight();
		}

		this.drawRect.x = x;
		this.drawRect.y = y;
		
		this.nextFrameTask = new SpriteTimer(this)
		{
			@Override
			public void run()
			{
				this.sprite.currentFrame++;
				if (this.sprite.currentFrame > this.sprite.frames) this.sprite.currentFrame = 0;
			}
		};
		
		this.nextFrameTimer = new Timer("Animator");
		this.nextFrameTimer.scheduleAtFixedRate(nextFrameTask, msBetweenFrames, msBetweenFrames);
		
	}
	
	public void updateImageRef(String name, boolean visible, boolean reset)
	{
		this.imgRef = ResourceHandler.getImage(name);
		this.visible = visible;
		
		if (reset)
		{
			this.drawRect.width = imgRef.getWidth();
			this.drawRect.height = imgRef.getHeight();
			this.frames = 1;
			this.frameWidth = this.drawRect.width;
			this.currentFrame = 0;
			
			if (this.nextFrameTimer != null)
			{
				this.nextFrameTimer.cancel();				
			}
		}
	}
	
	public void updateImageRef(String name, boolean visible, int w, int h)
	{
		this.imgRef = ResourceHandler.getImage(name);
		this.visible = visible;
		
		this.drawRect.width = w;
		this.drawRect.height = h;
		this.frames = 1;
		this.frameWidth = w;
		this.currentFrame = 0;
		
		if (this.nextFrameTimer != null)
		{
			this.nextFrameTimer.cancel();				
		}
	}

	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		if (this.imgRef == null) return;
		
		if (this.isAnimated)
		{
			int srcX = this.currentFrame * this.frameWidth;			
			g.drawImage(imgRef, 
					this.drawRect.x, this.drawRect.y, this.drawRect.x + Math.round(this.frameWidth * this.scale), this.drawRect.y + Math.round(this.drawRect.height * this.scale), 
					srcX, 0, srcX + this.frameWidth, this.drawRect.height, 
					null);
		}
		else
		{
			g.drawImage(imgRef, 
					this.drawRect.x, this.drawRect.y, this.drawRect.x + Math.round(this.drawRect.width * this.scale), this.drawRect.y + Math.round(this.drawRect.height * this.scale), 
					0, 0, this.drawRect.width, this.drawRect.height, 
					null);
		}		
	}

	@Override
	public void update() 
	{
		
	}

	@Override
	public void updatePosition(int x, int y) 
	{
		this.drawRect.x = x;
		this.drawRect.y = y;		
	}
	
}

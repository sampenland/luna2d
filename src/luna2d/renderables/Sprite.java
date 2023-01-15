package luna2d.renderables;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;

import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.timers.SpriteTimer;

public class Sprite extends Renderable
{

	private BufferedImage imgRef;
	private Rectangle drawRect;
	protected boolean fixedScreenPosition = false;

	private int scale;
	
	private boolean isAnimated;
	private int currentFrame;
	private int frames;
	private int frameWidth;
	private SpriteTimer nextFrameTask;
	private Timer nextFrameTimer;
	
	private int objectType = -1;
	
	public boolean visible = true;
	
	public Sprite(Scene inScene, String name, int x, int y, int scale) 
	{
		super(inScene, scale);
		
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

		this.worldX = x;
		this.worldY = y;
		
		this.frameWidth = this.drawRect.width;
		
	}
	
	public Sprite(Scene inScene, String name, int x, int y, int scale, int w, int h) 
	{
		super(inScene, scale);
		
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

		this.worldX = x;
		this.worldY = y;
		
		this.drawRect.width = w;
		this.drawRect.height = h;
		
		this.frameWidth = this.drawRect.width;
		
	}
	
	public Sprite(Scene inScene, String name, int x, int y, int scale, int frameWidth, int frames, int msBetweenFrames)
	{
		super(inScene, scale);
		
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

		this.worldX = x;
		this.worldY = y;
		
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
	
	public void setObjectType(int t)
	{
		this.objectType = t;
	}
	
	public int getObjectType()
	{
		return this.objectType;
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

	public int getScreenX()
	{
		return this.drawRect.x;
	}
	
	public int getScreenY()
	{
		return this.drawRect.y;
	}
	
	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		if (this.imgRef == null) return;
		
		int drawX, drawY, drawX2, drawY2;
		
		if (this.isAnimated)
		{
			int srcX = this.currentFrame * this.frameWidth;
			
			if (this.fixedScreenPosition)
			{
				drawX = this.drawRect.x - (Game.CAMERA_SCALE * this.frameWidth / 2);
				drawY = this.drawRect.y - (Game.CAMERA_SCALE * this.drawRect.height / 2);
				drawX2 = drawX + Math.round(this.frameWidth * this.scale * Game.CAMERA_SCALE);
				drawY2 = drawY + Math.round(this.drawRect.height * this.scale * Game.CAMERA_SCALE);
				
			}
			else 
			{
				drawX = Game.CAMERA_X + this.drawRect.x;
				drawY = Game.CAMERA_Y + this.drawRect.y;
				drawX2 = Game.CAMERA_X + this.drawRect.x + Math.round(this.frameWidth * this.scale * Game.CAMERA_SCALE);
				drawY2 = Game.CAMERA_Y + this.drawRect.y + Math.round(this.drawRect.height * this.scale * Game.CAMERA_SCALE);
			}
		
			g.drawImage(imgRef, 
					drawX, drawY, 
					drawX2, drawY2, 
					srcX, 0, srcX + this.frameWidth, this.drawRect.height, 
					null);
		}
		else
		{
			if (this.fixedScreenPosition)
			{
				drawX = this.drawRect.x - (Game.CAMERA_SCALE * this.frameWidth / 2);
				drawY = this.drawRect.y - (Game.CAMERA_SCALE * this.drawRect.height / 2);
				drawX2 = drawX + Math.round(this.frameWidth * this.scale * Game.CAMERA_SCALE);
				drawY2 = drawY + Math.round(this.drawRect.height * this.scale * Game.CAMERA_SCALE);
				
			}
			else 
			{
				drawX = Game.CAMERA_X + this.drawRect.x;
				drawY = Game.CAMERA_Y + this.drawRect.y;
				
				drawX2 = Game.CAMERA_X + this.drawRect.x +
						Math.round(this.drawRect.width * this.scale * Game.CAMERA_SCALE);
				
				drawY2 = Game.CAMERA_Y + this.drawRect.y + 
						Math.round(this.drawRect.height * this.scale* Game.CAMERA_SCALE);
			}
			
			g.drawImage(imgRef, 
					drawX, drawY, 
					drawX2, drawY2,
					0, 0, this.drawRect.width, this.drawRect.height, 
					null);
		}		
	}
	
	public void setFixedScreenPosition(boolean val)
	{
		this.fixedScreenPosition = val;
	}

	@Override
	public void update() 
	{		
		if (this.fixedScreenPosition)
		{
			this.drawRect.x = this.screenX;
			this.drawRect.y = this.screenY;
		}
		else
		{
			this.drawRect.x = Game.CAMERA_X + this.worldX + this.screenX;
			this.drawRect.y = Game.CAMERA_X + this.worldY + this.screenY;	
		}
	}
	
	public int getWidth()
	{
		return this.frameWidth * Game.CAMERA_SCALE;
	}
	
	public int getHeight()
	{
		return this.drawRect.height * Game.CAMERA_SCALE;
	}
	
}

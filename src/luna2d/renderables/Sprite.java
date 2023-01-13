package luna2d.renderables;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;

public class Sprite extends Renderable
{

	private BufferedImage imgRef;
	private Rectangle drawRect;
	private float scale;
	
	public Sprite(Scene inScene, String path, int x, int y, float scale) 
	{
		super(inScene);
		
		this.scale = scale;
		this.drawRect = new Rectangle();
		
		int idxStart = path.lastIndexOf("/");
		int idxEnd = path.indexOf(".");
		
		if (idxStart == -1) idxStart = 0;
		
		String name = path.substring(idxStart, idxEnd);
		
		imgRef = ResourceHandler.addImage(name, path);
		
		if (imgRef == null)
		{
			Log.println("Failed to load Sprite image: " + name);
			return;
		}

		this.drawRect.x = x;
		this.drawRect.y = y;
		this.drawRect.width = imgRef.getWidth();
		this.drawRect.height = imgRef.getHeight();
		
	}

	@Override
	public void render(Graphics g) 
	{
		g.drawImage(imgRef, 
				this.drawRect.x, this.drawRect.y, this.drawRect.x + Math.round(this.drawRect.width * this.scale), this.drawRect.y + Math.round(this.drawRect.height * this.scale), 
				0, 0, this.drawRect.width, this.drawRect.height, 
				null);
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

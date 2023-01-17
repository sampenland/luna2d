package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.renderables.UI;

public class UIMenu extends UI
{

	private LinkedList<Sprite> sprites;
	private LinkedList<TextDisplay> textDisplays;
	
	private Rectangle drawRect;
	private Color bkgColor;
	
	public UIMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale)
	{
		super(inScene, scale);
		
		this.sprites = new LinkedList<Sprite>();
		this.textDisplays = new LinkedList<TextDisplay>();
		this.visible = false;
		this.scale = scale;
		
		this.drawRect = new Rectangle(x, y, width, height);		
		this.bkgColor = bkgColor;
		this.visible = false;
	}
	
	protected void addSprite(Sprite sprite)
	{
		sprite.setCustomRender(true);
		sprites.add(sprite);
	}
	
	protected void removeSprite(Sprite sprite)
	{
		sprites.remove(sprite);
	}
	
	protected void addTextDisplay(TextDisplay textDisplay)
	{
		textDisplay.setCustomRender(true);
		textDisplays.add(textDisplay);
	}
	
	protected void removeTextDisplay(TextDisplay textDisplay)
	{
		textDisplays.remove(textDisplay);
	}
	
	public void toggleVisible()
	{
		if (this.visible)
		{
			this.hide();
		}
		else
		{
			this.show();
		}
	}

	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		
		g.setColor(bkgColor);
		g.fillRect(this.drawRect.x, this.drawRect.y, this.drawRect.width, this.drawRect.height);
		
		for (Sprite s : sprites)
		{
			s.customRender(g);
		}
		
		for (TextDisplay t : textDisplays)
		{
			t.customRender(g);
		}
	}

	@Override
	public void update() 
	{
		if(!this.visible) return;
		
		for (Sprite s : sprites)
		{
			s.update();
		}
		
		for (TextDisplay t : textDisplays)
		{
			t.update();
		}
	}
	
}

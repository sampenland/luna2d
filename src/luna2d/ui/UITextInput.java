package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Rect;
import luna2d.renderables.UI;

public class UITextInput extends UI 
{	
	private Rect drawRect;
	private Color bkgColor;
	private Color textColor;
	
	private String currentText;
	private int max;
	private int paddingX;
	private int paddingY;
	
	public UITextInput(Scene inScene, String placeHolderText, int x, int y, int w, int h, int max) 
	{
		super(inScene, 1);
	    this.destroyNow = false;
	    this.max = max;
	    
	    this.bkgColor = Color.white;
	    this.textColor = Color.black;
	    
	    this.drawRect = new Rect(inScene, x, y, w, h, true, Color.white, 1);
	    this.currentText = placeHolderText;
	    this.paddingX = 6;
	    this.paddingY = h / 2 + 6;
	    
	    this.inputEnabled = true;
	}
	
	public String getText()
	{
		return this.currentText;
	}
	
	public void setColor(Color bkg, Color text)
	{
		this.bkgColor = bkg;
		this.textColor = text;
	}

	@Override
	public void render(Graphics g) 
	{
		if(!this.visible) return;
		
		g.setColor(bkgColor);
		g.fillRect(drawRect.screenX, drawRect.screenY, drawRect.getWidth(), drawRect.getHeight());
		g.setColor(textColor);
		g.drawString(this.currentText, drawRect.screenX + this.paddingX, drawRect.screenY + this.paddingY);
	}
	
	@Override
	public void update() 
	{
	}

	@Override
	public void clean() 
	{
	}

	@Override
	public void keyPressed(int keycode) 
	{
	}

	@Override
	public void keyReleased(int keycode) 
	{	
		if (keycode == -1 || !Maths.characterIsAlphaNumeric(keycode, true)) return;

		if (this.currentText.length() >= this.max && keycode != KeyEvent.VK_BACK_SPACE) return;
		
		if (keycode == KeyEvent.VK_BACK_SPACE && this.currentText.length() > 0)
		{
			this.currentText = this.currentText.substring(0, this.currentText.length() - 1);
			return;
		}
		else if (keycode == KeyEvent.VK_BACK_SPACE)
		{
			return;
		}
		
		this.currentText += (char)keycode;
	}

}

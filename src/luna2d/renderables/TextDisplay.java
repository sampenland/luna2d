package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.Log;
import luna2d.Scene;

public class TextDisplay extends Renderable
{

	private String text;
	private Color textColor;
	private int x, y;
	
	public TextDisplay(Scene inScene, String text, int x, int y, Color c) 
	{
		super(inScene);
		
		this.text = text;
		this.textColor = c;
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(this.textColor);
		g.drawString(this.text, this.x, this.y);
	}

	@Override
	public void update() 
	{
		
	}

	@Override
	public void updatePosition(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

}

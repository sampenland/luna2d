package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.Scene;

public class TextDisplay extends Renderable
{

	private String text;
	private Color textColor;
	
	public TextDisplay(Scene inScene, String text, int x, int y, Color c) 
	{
		super(inScene, x, y, 1);
		
		this.text = text;
		this.textColor = c;
		this.screenX = x;
		this.screenY = y;
		this.visible = true;
		this.enableCulling = false;
	}
	
	public void updateText(String text)
	{
		this.text = text;
	}
	
	public void updateColor(Color c)
	{
		this.textColor = c;
	}

	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		if (this.customRender) return;
		
		g.setColor(this.textColor);
		g.drawString(this.text, this.screenX, this.screenY);
	}
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.screenX = x;
		this.screenY = y;
	}

	@Override
	public void onMouseClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

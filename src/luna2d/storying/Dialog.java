package luna2d.storying;

import java.awt.Color;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;

public class Dialog extends UIMenu
{
	protected String[] text;
	protected TextDisplay[] texts;
	protected TextDisplay continueText;
	
	public Dialog(Scene inScene, String text, int x, int y, int width, int height, 
			int xPad, int yPad, Color bkgColor, Color borderColor, String continueText) 
	{
		super(inScene, x, y, width, height, bkgColor, 1);
		this.text = text.split("\n");
		
		texts = new TextDisplay[this.text.length];
		
		int currentY = y + yPad + 5;
		for (int i = 0; i < texts.length; i++)
		{
			TextDisplay td = new TextDisplay(inScene, this.text[i], x + xPad, currentY, Color.white, Game.TOP_DRAW_LAYER);
			texts[i] = td;
			currentY += yPad;
			this.addTextDisplay(td);
		}
		
		this.continueText = new TextDisplay(inScene, continueText, x + width - 100, y + height - yPad, Color.white, Game.TOP_DRAW_LAYER);
		this.addTextDisplay(this.continueText);
	}

	@Override
	public void launch()
	{
	}

	@Override
	public void close() 
	{
	}

	@Override
	public void onMouseClick(MouseEvent e)
	{
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}

}

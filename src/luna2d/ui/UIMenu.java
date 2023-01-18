package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.renderables.UI;

public abstract class UIMenu extends UI
{

	private LinkedList<Sprite> sprites;
	private LinkedList<TextDisplay> textDisplays;
	
	private LinkedList<UITextInput> textInputs;
	protected UITextInput focusedTextInput;
	
	private Rectangle drawRect;
	private Color bkgColor;
	
	public UIMenu(Scene inScene, int x, int y, int width, int height, Color bkgColor, int scale)
	{
		super(inScene, scale);
		
		this.sprites = new LinkedList<Sprite>();
		this.textDisplays = new LinkedList<TextDisplay>();
		this.textInputs = new LinkedList<UITextInput>();
		
		this.visible = false;
		this.scale = scale;
		
		this.drawRect = new Rectangle(x, y, width, height);	
		
		this.screenX = x;
		this.screenY = y;
		
		this.bkgColor = bkgColor;
		this.visible = false;
	}
	
	protected abstract void launch();
	protected abstract void close();
	
	protected void setFocusedTextInput(UITextInput input)
	{
		this.focusedTextInput = input;
	}
	
	public int getWidth()
	{
		return this.drawRect.width;
	}
	
	public int getHeight()
	{
		return this.drawRect.height;
	}
	
	protected void addUITextInput(UITextInput tInput)
	{
		this.textInputs.add(tInput);
	}
	
	protected void removeUITextInput(UITextInput tInput)
	{
		this.textInputs.remove(tInput);
	}
	
	protected void addSprite(Sprite sprite)
	{
		sprite.setCustomRender(true);
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite)
	{
		sprites.remove(sprite);
	}
	
	protected void addTextDisplay(TextDisplay textDisplay)
	{
		textDisplay.setCustomRender(true);
		textDisplays.add(textDisplay);
	}
	
	public void removeTextDisplay(TextDisplay textDisplay)
	{
		textDisplays.remove(textDisplay);
	}
	
	public void clearAllRenderables()
	{
		textDisplays.clear();
		sprites.clear();
		textInputs.clear();
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
			this.launch();
		}
	}
	
	protected boolean isKeyPressed(int keycode)
	{
		return this.inScene.isKeyPressed(keycode);
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
		
		for (UITextInput t : textInputs)
		{
			t.render(g);
		}
	}

	@Override
	public void update() 
	{
		if (this.focusedTextInput != null)
		{
			this.focusedTextInput.visible = this.visible;
		}
		
		if(!this.visible) return;
		
		if (this.focusedTextInput != null)
		{
			this.focusedTextInput.update();
		}
		else
		{
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

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int keycode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int keycode) {
		// TODO Auto-generated method stub
		
	}
	
}

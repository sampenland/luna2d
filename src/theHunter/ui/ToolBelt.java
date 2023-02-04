package theHunter.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;

import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Scene;
import luna2d.ui.UIMenu;
import theHunter.ObjectTypes;

public class ToolBelt extends UIMenu
{
	private ToolBeltQuickAccess quickAccess;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 32;
	
	public static final int SLOTS = 6;
	
	private int x, y;
	
	public ToolBelt(Scene inScene) 
	{
		super(inScene, Game.WIDTH / 2 - WIDTH / 2, Game.HEIGHT - HEIGHT - 70, 
				WIDTH, HEIGHT, null, 1);
		
		this.x = Game.WIDTH / 2 - WIDTH / 2;
		this.y = Game.HEIGHT - HEIGHT - 70;
		
		this.quickAccess = new ToolBeltQuickAccess(inScene, x, y, 1, 6, 48, Color.white,
				ColorHandler.getColor("UIGray"), 1, Game.TOP_DRAW_LAYER, null);
		this.setBorder(null, 0);
		this.addRenderable(quickAccess);
		
	}
	
	private int getNextSlot()
	{
		return this.quickAccess.getNextSlot();
	}
	
	public void attachToQuickAccess(ObjectTypes type)
	{
		if (this.quickAccess.hasItem(type)) return;
		
		int slot = getNextSlot();
		if (slot < SLOTS && slot >= 0)
		{
			this.quickAccess.attachToQuickAccess(type, slot);
		}
	}
	
	public void removeFromQuickAccess(int slot)
	{
		if (slot < SLOTS && slot >= 0)
		{
			this.quickAccess.removeFromQuickAccess(slot);
		}
	}

	@Override
	public void launch() 
	{
		this.show();
	}

	@Override
	public void close() 
	{
		this.hide();
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

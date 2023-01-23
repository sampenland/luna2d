package theHunter.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;

public class Backpack extends UIMenu
{

	private final static int WIDTH = 300;
	private final static int HEIGHT = 450;
	
	private BackpackItems backpackItems;
	private int[][] items;
	private static final int BACKPACK_ROWS = 11;
	private static final int BACKPACK_COLUMNS = 9;
	
	private int x, y;
	
	private TextDisplay title;
	
	public Backpack(Scene inScene) 
	{
		super(inScene, Game.WIDTH/2 - (WIDTH/2) - 200, Game.HEIGHT/2 - (HEIGHT/2), WIDTH, HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.4f), 1);
		
		items = new int[BACKPACK_ROWS][BACKPACK_COLUMNS];
		
		this.x = Game.WIDTH/2 - (WIDTH/2) - 200;
		this.y = Game.HEIGHT/2 - (HEIGHT/2);
		
		title = new TextDisplay(inScene, "Backpack", this.x + 5, this.y + 15, Color.white);
		this.addTextDisplay(title);
		
		backpackItems = new BackpackItems(inScene, this.x + 5, this.y + 25, BACKPACK_ROWS, BACKPACK_COLUMNS, 32, new Color(1.f, 1.f, 1.f, 0.15f), 1, items);
		backpackItems.enableCulling = false;
		this.addRenderable(backpackItems);
		
	}
	
	public boolean addToBackpack(int type, int amount)
	{
		
		return true;
	}
	
	public boolean removeFromBackpack(int type, int amount)
	{
		
		return true;
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

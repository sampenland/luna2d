package theHunter.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;
import theHunter.InventoryItem;

public class Backpack extends UIMenu
{

	private final static int WIDTH = 300;
	private final static int HEIGHT = 450;
	
	private BackpackItems backpackItems;
	private LinkedList<InventoryItem> items;
	public static final int BACKPACK_ROWS = 11;
	public static final int BACKPACK_COLUMNS = 9;
	private static final int TOTAL_ITEMS = BACKPACK_ROWS * BACKPACK_COLUMNS;
	
	private int x, y;
	
	private TextDisplay title;
	
	public Backpack(Scene inScene) 
	{
		super(inScene, Game.WIDTH/2 - (WIDTH/2) - 200, Game.HEIGHT/2 - (HEIGHT/2), WIDTH, HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.4f), 1);
		
		items = new LinkedList<InventoryItem>();
		
		this.x = Game.WIDTH/2 - (WIDTH/2) - 200;
		this.y = Game.HEIGHT/2 - (HEIGHT/2);
		
		title = new TextDisplay(inScene, "Backpack", this.x + 5, this.y + 15, Color.white);
		this.addTextDisplay(title);
		
		backpackItems = new BackpackItems(this, inScene, this.x + 5, this.y + 25, BACKPACK_ROWS, BACKPACK_COLUMNS, 
				32, new Color(0.f, 0.f, 0.f, 0.5f), new Color(1.f, 1.f, 1.f, 0.25f), 1, null);
		backpackItems.enableCulling = false;
		this.addRenderable(backpackItems);
		
	}
	
	public boolean addToBackpack(InventoryItem item)
	{
		if (items.size() >= TOTAL_ITEMS) return false;
		
		items.push(item);
		compact();
		return true;
	}
	
	public boolean removeFromBackpack(int typeOfItem)
	{
		int foundIndex = -1;
		int cnt = 0;
		for (InventoryItem i : items)
		{
			if (typeOfItem == i.TYPE)
			{
				foundIndex = cnt;
				break;
			}
			cnt++;
		}
		
		if (foundIndex == -1) return false;
		
		items.remove(foundIndex);
		compact();
		return true;
	}
	
	private void compact()
	{
		// Compact all items
		
		// Update grid with images
		this.backpackItems.updateItems(items);
		
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

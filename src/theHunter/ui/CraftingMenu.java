package theHunter.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;

import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UIMenu;
import theHunter.ObjectTypes;

public class CraftingMenu extends UIMenu
{
	private final static int WIDTH = 170;
	private final static int HEIGHT = 400;
	
	public static final int CRAFTER_ROWS = 11;
	public static final int CRAFTER_COLUMNS = 5;
	private static final int TOTAL_ITEMS = CRAFTER_ROWS * CRAFTER_COLUMNS;
	
	private CraftingMenuItems crafterItems;
	
	private TextDisplay title;
	private int x, y;
	
	public CraftingMenu(Scene inScene) 
	{
		super(inScene, Game.WIDTH - 460, Game.HEIGHT/2 - (HEIGHT/2), WIDTH, HEIGHT, ColorHandler.getColor("UIGray"), 1);
		
		this.x = Game.WIDTH - 460;
		this.y = Game.HEIGHT/2 - (HEIGHT/2);
		
		title = new TextDisplay(inScene, "Crafter", this.x + 5, this.y + 15, Color.white, Game.TOP_DRAW_LAYER);
		this.addTextDisplay(title);
		
		crafterItems = new CraftingMenuItems(this, inScene, this.x + 5, this.y + 25, CRAFTER_ROWS, CRAFTER_COLUMNS, 
				32, new Color(0.f, 0.f, 0.f, 0.5f), new Color(1.f, 1.f, 1.f, 0.25f), 1, null);
		crafterItems.enableCulling = false;
		this.addRenderable(crafterItems);
	}
	
	public int itemQty(ObjectTypes type)
	{
		return this.crafterItems.itemQty(type);
	}
	
	public boolean isFull()
	{
		return this.crafterItems.totalItems() == TOTAL_ITEMS;
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

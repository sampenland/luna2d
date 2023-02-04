package theHunter.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.Utilites;
import luna2d.ui.UIGrid;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.TheHunter;
import theHunter.inventoryItems.InvBerries;
import theHunter.inventoryItems.InvFence;
import theHunter.inventoryItems.InvFire;
import theHunter.inventoryItems.InvRock;
import theHunter.inventoryItems.InvTorch;

public class CraftingMenuItems extends UIGrid
{
	private CraftingMenu crafter;
	private InventoryItem[][] items;
	
	public CraftingMenuItems(CraftingMenu crafter, Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, Color bkgColor, int scale,
			int[][] fillTypes) 
	{
		super(inScene, x, y, rows, columns, cellSize, gridColor, bkgColor, scale, Game.TOP_DRAW_LAYER, null);
		this.crafter = crafter;
		this.mouseEnabled = true;
		this.enableCulling = false;
		this.items = new InventoryItem[CraftingMenu.CRAFTER_ROWS][CraftingMenu.CRAFTER_COLUMNS];
	}
	
	public boolean load(String worldName)
	{
		String path = TheHunter.GAME_SAVE_DIR + worldName + "/crafter.thc";
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   String[] cols = line.split(","); 
			   int col = 0;
			   for(String  c : cols)
			   {
				   String[] parts = c.split("-");
				   
				   items[row][col] = this.loadInventoryItem(parts);
				   col++;
			   }
			   
			   row++;
			   
			}
			
			reader.close();
			
			return true;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
			Log.println("Problem loading backpack.");
			return false;
		}
	}
	
	private InventoryItem loadInventoryItem(String[] item)
	{
		ObjectTypes type = ObjectTypes.values()[Integer.parseInt(item[0])];
		int qty = Integer.parseInt(item[1]);
		
		switch(type)
		{
		case Empty:
			return null;
		case InvBerries:
			return new InvBerries(this.getScene(), qty);
		case InvFence:
			return new InvFence(this.getScene(), qty);
		case InvFire:
			return new InvFire(this.getScene());
		case InvRock:
			return new InvRock(this.getScene(), qty);
		case InvTorch:
			return new InvTorch(this.getScene());
		default:
			Log.println("NOT A INVENTORY ITEM");
			return null;
		
		}
	}
	
	public boolean save(String worldName)
	{
		String path = TheHunter.GAME_SAVE_DIR + worldName + "/crafter.thc";
		
		StringBuilder builder = new StringBuilder();
		
		for (int r = 0; r < CraftingMenu.CRAFTER_ROWS; r++)
		{
			for (int c = 0; c < CraftingMenu.CRAFTER_COLUMNS; c++)
			{
				String item = "";
				if (this.items[r][c] == null)
				{
					 item = ObjectTypes.Empty.intValue + "-0";
				}
				else
				{
					 item = this.items[r][c].TYPE.intValue + "-" + this.items[r][c].AMOUNT;
				}
				
				builder.append(item);

			   if(c < CraftingMenu.CRAFTER_COLUMNS - 1)
			   {
				   builder.append(",");
			   }
		   }
		   
		   builder.append("\n");
		
		}
		
		return Utilites.saveStringToFile(builder.toString(), path);
		
	}
	
	public LinkedList<InventoryItem> getItems()
	{
		LinkedList<InventoryItem> l = new LinkedList<InventoryItem>();
		for (int r = 0; r < CraftingMenu.CRAFTER_ROWS; r++)
		{
			for (int c = 0; c < CraftingMenu.CRAFTER_COLUMNS; c++)
			{
				if (items[r][c] != null)
				{
					l.add(this.loadInventoryItem(
							new String[] {
									items[r][c].TYPE.intValue + "", 
									items[r][c].AMOUNT + ""
							}
					));
				}
			}
		}
		
		return l;
	}
	
	public int totalItems()
	{
		int cnt = 0;
		for (int r = 0; r < Backpack.BACKPACK_ROWS; r++)
		{
			for (int c = 0; c < Backpack.BACKPACK_COLUMNS; c++)
			{
				if (items[r][c] == null)
				{
					return cnt;
				}
				
				cnt++;
			}
		}
		
		return cnt;
	}
	
	public int itemQty(ObjectTypes type)
	{
		int cnt = 0;
		for (int r = 0; r < Backpack.BACKPACK_ROWS; r++)
		{
			for (int c = 0; c < Backpack.BACKPACK_COLUMNS; c++)
			{
				if (items[r][c] == null)
				{
					return cnt;
				}
				
				if (items[r][c].TYPE == type)
				{
					cnt++;					
				}				
			}
		}
		
		return cnt;
	}
	
	public void updateItems(LinkedList<InventoryItem> inItems)
	{
		items = new InventoryItem[Backpack.BACKPACK_ROWS][Backpack.BACKPACK_COLUMNS];
				
		for (int r = 0; r < Backpack.BACKPACK_ROWS; r++)
		{
			for (int c = 0; c < Backpack.BACKPACK_COLUMNS; c++)
			{
				int i = r * Backpack.BACKPACK_COLUMNS + c;
				
				if (i >= inItems.size())
				{
					items[r][c] = null;
					continue;
				}
				
				items[r][c] = inItems.get(i);				
			}
		}
	}
	
	private void checkMouseClicks()
	{
		if (!this.crafter.visible) return;
		
		if (this.mouseClicked && this.mouseClickEvent != null && (this.mouseClickEvent.getButton() == 3 || this.mouseClickEvent.getButton() == 1)) 
		{
			if (items != null && items[this.clickedRow][this.clickedColumn] != null)
			{				
//				items[this.clickedRow][this.clickedColumn].use(this.mouseClickEvent.getButton());
//				if (this.backpack != null)
//				{
//					this.backpack.removeFromBackpack(items[this.clickedRow][this.clickedColumn]);
//					this.mouseClicked = false;
//					this.clickedColumn = -1;
//					this.clickedRow = -1;
//				}
			}
		}
	}
	
	@Override
	public void update() 
	{
		super.update();
		
		checkMouseClicks();
	}
	
	@Override
	public void render(Graphics g)
	{
		if (!this.visible) return;
		if (this.customRender) return;
			
		super.renderBackground(g);

		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = this.x + col * cellSize * this.scale;
				int cy = this.y + row * cellSize * this.scale;
				
				if (items != null && items[row][col] != null)
				{
					String imgName = ObjectTypes.values()[items[row][col].TYPE.intValue].imgName;
					BufferedImage img = ResourceHandler.getImage(imgName);
					g.drawImage(img, 
							cx, cy, cx + Math.round(cellSize * this.scale), cy + Math.round(cellSize * this.scale), 
							0, 0, img.getWidth(), img.getHeight(), null);					
				}
			}
		}
		
		super.renderGrid(g);
	}

}

package theHunter.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.ui.UIGrid;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;

public class BackpackItems extends UIGrid
{
	private InventoryItem[][] items;
	
	public BackpackItems(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, Color bkgColor, int scale,
			int[][] fillTypes) 
	{
		super(inScene, x, y, rows, columns, cellSize, gridColor, bkgColor, scale, null);
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
	
	@Override
	public void render(Graphics g)
	{
		if (!this.visible) return;
		if (this.customRender) return;
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = this.x + col * cellSize * this.scale;
				int cy = this.y + row * cellSize * this.scale;
								
				g.setColor(bkgColor);
				
				g.fillRect(cx, cy, 
						Math.round(cellSize * this.scale), 
						Math.round(cellSize * this.scale));
				
				g.setColor(gridColor);
				
				g.drawRect(cx, cy, 
						Math.round(cellSize * this.scale), 
						Math.round(cellSize * this.scale));
				
				if (items[row][col] != null)
				{
					String imgName = ObjectTypes.values()[items[row][col].TYPE].imgName;
					BufferedImage img = ResourceHandler.getImage(imgName);
					g.drawImage(img, 
							cx, cy, cx + Math.round(cellSize * this.scale), cy + Math.round(cellSize * this.scale), 
							0, 0, img.getWidth(), img.getHeight(), null);					
				}
			}
		}
	}

}

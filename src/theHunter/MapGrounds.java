package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.Grid;

public class MapGrounds extends Grid
{	
	public MapGrounds(Scene inScene, int x, int y, Color gridColor, int scale, int[][] fillTypes) 
	{
		super(inScene, x, y, TheHunter.ROWS, TheHunter.COLUMNS, TheHunter.CELL_SIZE, gridColor, scale, 0, fillTypes);
		this.setColors(gridColor, ColorHandler.getColor("GrassGreen"));
		this.mouseEnabled = true;
		this.enableCulling = false;
		//this.hideGrid = true;
	}
	
	@Override
	public void render(Graphics g)
	{		
		super.render(g);
		
		if (this.fillTypes == null) return;
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				if (this.fillTypes[row][col] == ObjectTypes.Empty.intValue) continue;
				
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
				
				int drawX = Game.CAMERA_X + this.x + cx;
				int drawY = Game.CAMERA_Y + this.y + cy;
				
				// Culling				
				if (this.enableCulling && !Game.getScreenBounds().contains(
						new Point(drawX - Game.CAMERA_X + (cellSize), drawY - Game.CAMERA_Y + (cellSize))))
				{
					continue;
				}
					
				Color groundColor = Color.black;
				
				ObjectTypes gndType = ObjectTypes.values()[this.fillTypes[row][col]];
				
				switch(gndType)
				{
				case Bush:
					break;
				case Empty:
					break;
				case GndDirt:
					break;
				case GndGrass:
					groundColor = ColorHandler.getColor("GrassGreen");
					break;
				case GndRock:
					break;
				case GndWater:
					groundColor = ColorHandler.getColor("WaterBlue");
					break;
				case Player:
					break;
				case Tree:
					break;
				case Water:
					break;
				case Wolf:
					break;
				default:
					break;
				
				}
				
				g.setColor(groundColor);
				
				g.fillRect(drawX + 1, drawY + 1, 
						Math.round(cellSize * this.scale) - 1, 
						Math.round(cellSize * this.scale) - 1);
			}
		}	
	}

	@Override
	public void update() 
	{
	}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
}

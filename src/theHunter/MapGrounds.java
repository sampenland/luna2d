package theHunter;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Scene;
import luna2d.renderables.Grid;

public class MapGrounds extends Grid
{	
	public MapGrounds(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale, int[][] fillTypes) 
	{
		super(inScene, x, y, rows, columns, cellSize, gridColor, scale, fillTypes);
	}
	
	@Override
	public void render(Graphics g) 
	{		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				if (this.fillTypes != null)
				{
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
				}
				
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
				
				if (this.fillTypes != null)
				{					
					g.fillRect(Game.CAMERA_X + this.x + cx, Game.CAMERA_Y + this.y + cy, 
							Math.round(cellSize * this.scale * Game.CAMERA_SCALE), 
							Math.round(cellSize * this.scale * Game.CAMERA_SCALE));	
				}
				
				g.setColor(gridColor);
				
				g.drawRect(Game.CAMERA_X + this.x + cx, Game.CAMERA_Y + this.y + cy, 
						Math.round(cellSize * this.scale * Game.CAMERA_SCALE), 
						Math.round(cellSize * this.scale * Game.CAMERA_SCALE));
			}
		}
	}

	@Override
	public void update() 
	{}

	@Override
	public void updateScreenPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
}

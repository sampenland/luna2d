package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.Game;
import luna2d.Scene;

public class Grid extends Renderable
{

	private int x, y, rows, columns, cellSize;
	private Color gridColor;
	
	public Grid(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale) 
	{
		super(inScene, scale);
		
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.gridColor = gridColor;
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(gridColor);
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = col * cellSize;
				int cy = row * cellSize;
				
				g.drawRect(this.x + cx, this.y + cy, 
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

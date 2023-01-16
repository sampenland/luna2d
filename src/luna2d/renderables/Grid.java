package luna2d.renderables;

import java.awt.Color;
import java.awt.Graphics;
import luna2d.Game;
import luna2d.Scene;

public class Grid extends Renderable
{

	protected int x, y, rows, columns, cellSize;
	protected Color gridColor;
	
	protected int[][] fillTypes;
	
	public Grid(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale, int[][] fillTypes) 
	{
		super(inScene, scale);
		
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.gridColor = gridColor;
		this.scale = scale;
		this.fillTypes = fillTypes;
	}
	
	@Override
	public void render(Graphics g) 
	{
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
								
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
	}

}

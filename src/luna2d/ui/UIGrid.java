package luna2d.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import luna2d.Scene;
import luna2d.renderables.Renderable;

public class UIGrid extends Renderable
{

	protected int x, y, rows, columns, cellSize;
	protected Color gridColor;
	
	protected int[][] fillTypes;
	
	public UIGrid(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale, int[][] fillTypes) 
	{
		super(inScene, x, y, scale);
		
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
		if (!this.visible) return;
		if (this.customRender) return;
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < columns; col++)
			{
				int cx = col * cellSize * this.scale;
				int cy = row * cellSize * this.scale;
								
				g.setColor(gridColor);
				
				g.drawRect(this.x + cx, this.y + cy, 
						Math.round(cellSize * this.scale), 
						Math.round(cellSize * this.scale));
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

package theHunter.ui;

import java.awt.Color;

import luna2d.Scene;
import luna2d.ui.UIGrid;

public class BackpackItems extends UIGrid
{

	public BackpackItems(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor, int scale,
			int[][] fillTypes) 
	{
		super(inScene, x, y, rows, columns, cellSize, gridColor, scale, fillTypes);
	}

}

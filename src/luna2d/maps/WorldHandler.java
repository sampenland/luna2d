package luna2d.maps;

import luna2d.Log;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;

public abstract class WorldHandler 
{

	/*
	 * 
	 * 
	 *    World handler has one (or more) layers of cells which keeps
	 *    track of all object in the game. 
	 *    
	 *    When saved it is parsed to MapStruct[][] up to WorldStruct then saved.
	 * 
	 * 
	 */	
	
	protected Scene inScene;
	public static WorldCell[][] entireWorldCells;
	
	public static int worldRows;
	public static int worldColumns;
	
	public static int ROWS;
	public static int COLUMNS;
	
	public WorldHandler(Scene inScene, int totalRows, int totalColumns)
	{
		this.inScene = inScene;
		
		ROWS = totalRows;
		COLUMNS = totalColumns;
		
		worldRows = totalRows * totalRows;
		worldColumns = totalColumns * totalColumns;
		
		entireWorldCells = new WorldCell[worldRows][worldColumns];
		
	}
	
	public int getRows()
	{
		return ROWS;
	}
	
	public int getColumns()
	{
		return COLUMNS;
	}
	
	public void updateTypeAt(WorldCell cell, WorldPosition wp)
	{
		Vector2 p = getCellPosition(wp);
		if (p.y > this.worldRows || p.y < 0 || p.x > this.worldColumns || p.x < 0)
		{
			Log.println("Could not update object onto worldcells.");
			return;
		}
		
		entireWorldCells[p.y][p.x].type = cell.type;
		entireWorldCells[p.y][p.x].groundType = cell.groundType;
		
	}
	
	// will get a square chunk of entire array :: can do A* on this relative to object grid
	public static WorldCell[][] getSubArray(int row, int column, int surroundingCells)
	{
		int minRow = row - surroundingCells;
		int maxRow = row + surroundingCells;
		int minCol = column + surroundingCells;
		int maxCol = column + surroundingCells;
		
		minRow = Maths.clamp(minRow, worldRows, 0);
		maxRow = Maths.clamp(maxRow, worldRows, 0);
		minCol = Maths.clamp(minCol, worldColumns, 0);
		maxCol = Maths.clamp(maxCol, worldColumns, 0);
		
		WorldCell[][] rtn = new WorldCell[maxRow - minRow][maxCol - minCol];
		for (int r = minRow; r < maxRow; r++)
		{
			for (int c = minCol; c < maxCol; c++)
			{
				rtn[r - minRow][c - minCol] = entireWorldCells[r][c];
			}
		}
		
		return rtn;
		
	}
	
	public static char[][] getSubCharArray(int row, int column, int surroundingCells)
	{
		int minRow = row - surroundingCells;
		int maxRow = row + surroundingCells;
		int minCol = column - surroundingCells;
		int maxCol = column + surroundingCells;
		
		minRow = Maths.clamp(minRow, worldRows, 0);
		maxRow = Maths.clamp(maxRow, worldRows, 0);
		minCol = Maths.clamp(minCol, worldColumns, 0);
		maxCol = Maths.clamp(maxCol, worldColumns, 0);
		
		char[][] rtn = new char[maxRow - minRow][maxCol - minCol];
		for (int r = minRow; r < maxRow; r++)
		{
			for (int c = minCol; c < maxCol; c++)
			{
				if (entireWorldCells[r][c].isSolid())
				{					
					rtn[r - minRow][c - minCol] = '1';
				}
				else
				{
					rtn[r - minRow][c - minCol] = '0';
				}
			}
		}
		
		return rtn;
		
	}
	
	public boolean isSolid(WorldPosition wp)
	{
		Vector2 c = getCellPosition(wp);
		WorldCell cell = entireWorldCells[c.y][c.x];
		return cell.isSolid();
		
	}
	
	public boolean isFree(WorldPosition wp)
	{
		return !isSolid(wp);
	}
	
	public static WorldPosition convertFromCellToWorldPosition(int cellRow, int cellCol)
	{
		int worldRow = 0;
		int worldColumn = 0;
		int mapRow = cellRow;
		int mapColumn = cellCol;
		
		if (cellRow >= ROWS)
		{
			worldRow = cellRow / ROWS;
			mapRow = cellRow % ROWS;
		}
		
		if (cellCol >= COLUMNS)
		{
			worldColumn = cellCol / COLUMNS;
			mapColumn = cellCol % COLUMNS;
		}
		
		return new WorldPosition(worldRow, worldColumn, mapRow, mapColumn);
	}
	
	public static Vector2 getCellPosition(WorldPosition wp)
	{
		int worldRow = (wp.worldRow * ROWS) + wp.mapRow;
		int worldCol = (wp.worldColumn * COLUMNS) + wp.mapColumn;
		return new Vector2(worldCol, worldRow);
	}
	
	/*
	 *  Return position if available to move OR returns same position if can't move
	 */
	public WorldPosition tryMove(WorldPosition wp, int deltaRow, int deltaColumn)
	{
		Vector2 pos = getCellPosition(wp);
		
		int newRow = pos.y + deltaRow;
		int newCol = pos.x + deltaColumn;
		
		newRow = Maths.clamp(newRow, worldRows - 1, 0);
		newCol = Maths.clamp(newCol, worldColumns - 1, 0);
		
		if (entireWorldCells[newRow][newCol].isSolid())
		{
			return wp;
		}
		
		return convertFromCellToWorldPosition(newRow, newCol);
		
	}
	
}

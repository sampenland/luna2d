package theHunter;

import luna2d.Log;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;

public class WorldHandler 
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
	
	private Scene inScene;
	private WorldCell[][] entireWorldCells;
	
	private final int worldRows;
	private final int worldColumns;
	
	public WorldHandler(Scene inScene)
	{
		this.inScene = inScene;
		
		this.worldRows = TheHunter.ROWS * TheHunter.ROWS;
		this.worldColumns = TheHunter.COLUMNS * TheHunter.COLUMNS;
		
		this.entireWorldCells = new WorldCell[this.worldRows][this.worldColumns];
		
	}
	
	/*
	 *     Load from file, to map structure then into entire world array
	 * 	   using this function
	 */
	public void loadFromMapStruct(MapStruct[][] worldMaps)
	{
		this.entireWorldCells = new WorldCell[this.worldRows][this.worldColumns];
				
		for (int currentWorldRow = 0; currentWorldRow < TheHunter.ROWS; currentWorldRow++)
		{
			for (int currentWorldColumn = 0; currentWorldColumn < TheHunter.COLUMNS; currentWorldColumn++)
			{
				MapStruct map = worldMaps[currentWorldRow][currentWorldColumn];
				
				int[][] mapObjects = map.getMapData();
				int[][] mapGrounds = map.getMapGroundsData();
				
				for (int mapRow = 0; mapRow < TheHunter.ROWS; mapRow++)
				{
					for (int mapCol = 0; mapCol < TheHunter.COLUMNS; mapCol++)
					{
						ObjectTypes type = ObjectTypes.values()[mapObjects[mapRow][mapCol]];
						ObjectTypes gndType = ObjectTypes.values()[mapGrounds[mapRow][mapCol]];
												
						int worldCol = (currentWorldColumn * TheHunter.COLUMNS) + mapCol;						
						int worldRow = (currentWorldRow * TheHunter.ROWS) + mapRow;
												
						this.entireWorldCells[worldRow][worldCol] = new WorldCell(type, gndType);
						
					}
				}				
			}
		}
	}
	
	public void updateTypeAt(WorldCell cell, int cellRow, int cellColumn)
	{
		if (cellRow > this.worldRows || cellRow < 0 || cellColumn > this.worldColumns || cellColumn < 0)
		{
			Log.println("Could not update object onto worldcells.");
			return;
		}
		
		this.entireWorldCells[cellRow][cellColumn].type = cell.type;
		this.entireWorldCells[cellRow][cellColumn].groundType = cell.groundType;
		
	}
	
	// will get a square chunk of entire array :: can do A* on this relative to object grid
	public WorldCell[][] getSubArray(int row, int column, int surroundingCells)
	{
		int minRow = row - surroundingCells;
		int maxRow = row + surroundingCells;
		int minCol = column + surroundingCells;
		int maxCol = column + surroundingCells;
		
		minRow = Maths.clamp(minRow, this.worldRows, 0);
		maxRow = Maths.clamp(maxRow, this.worldRows, 0);
		minCol = Maths.clamp(minCol, this.worldColumns, 0);
		maxCol = Maths.clamp(maxCol, this.worldColumns, 0);
		
		WorldCell[][] rtn = new WorldCell[maxRow - minRow][maxCol - minCol];
		for (int r = minRow; r < maxRow; r++)
		{
			for (int c = minCol; c < maxCol; c++)
			{
				rtn[r - minRow][c - minCol] = this.entireWorldCells[r][c];
			}
		}
		
		return rtn;
		
	}
	
	public boolean isSolid(WorldPosition wp)
	{
		Vector2 c = getCellPosition(wp);
		WorldCell cell = this.entireWorldCells[c.y][c.x];
		return cell.isSolid();
		
	}
	
	public static WorldPosition convertFromCellToWorldPosition(int cellRow, int cellCol)
	{
		int worldRow = 0;
		int worldColumn = 0;
		int mapRow = cellRow;
		int mapColumn = cellCol;
		
		if (cellRow >= TheHunter.ROWS)
		{
			worldRow = cellRow / TheHunter.ROWS;
			mapRow = cellRow % TheHunter.ROWS;
		}
		
		if (cellCol >= TheHunter.COLUMNS)
		{
			worldColumn = cellCol / TheHunter.COLUMNS;
			mapColumn = cellCol % TheHunter.COLUMNS;
		}
		
		return new WorldPosition(worldRow, worldColumn, mapRow, mapColumn);
	}
	
	public Vector2 getCellPosition(WorldPosition wp)
	{
		int worldRow = (wp.worldRow * TheHunter.ROWS) + wp.mapRow;
		int worldCol = (wp.worldColumn * TheHunter.COLUMNS) + wp.mapColumn;
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
		
		newRow = Maths.clamp(newRow, this.worldRows - 1, 0);
		newCol = Maths.clamp(newCol, this.worldColumns - 1, 0);
		
		if (this.entireWorldCells[newRow][newCol].isSolid())
		{
			return wp;
		}
		
		return convertFromCellToWorldPosition(newRow, newCol);
		
	}
	
}

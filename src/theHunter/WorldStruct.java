package theHunter;

import luna2d.Log;
import luna2d.Scene;
import luna2d.Vector2;

public class WorldStruct 
{
	private MapStruct[][] worldMaps;
	private Vector2 playerWorldPosition;
	private Vector2 playerMapPosition;
	
	private String worldName;
	private Scene inScene;
	
	public WorldStruct(String worldName, Scene inScene)
	{
		this.inScene = inScene;
		
		this.worldName = worldName;
		worldName = "w_" + worldName;
		
		this.worldMaps = new MapStruct[TheHunter.ROWS][TheHunter.COLUMNS];
		
		String[][] mapsInWorld = TheHunter.loadCSVstrings(worldName, LoadDataType.WORLD_NAMES);
		
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				String mapName = mapsInWorld[r][c];
				this.worldMaps[r][c] = new MapStruct(mapName, this.inScene, r, c);
				this.worldMaps[r][c].load();
				
				if (this.worldMaps[r][c].hasPlayer())
				{
					playerWorldPosition = new Vector2(c, r);
					playerMapPosition = this.worldMaps[r][c].getPlayerMapPosition();
				}
			}
		}
		
		Log.println("Loading Complete: Player @ W[" + 
			playerWorldPosition.x + ", " + playerWorldPosition.y + "] :: M[" + 
			playerMapPosition.x + ", " + playerMapPosition.y + "]");
	}
	
	public void updateMap(int r, int c)
	{
		this.worldMaps[r][c].update();
	}
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	/*
	 * Returns Vector2 (COLUMN, ROW)    NOT r, c
	 */
	public Vector2 getPlayerOnMapRC()
	{
		return this.playerWorldPosition;
	}
}

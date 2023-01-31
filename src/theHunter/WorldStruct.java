package theHunter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import luna2d.Log;
import luna2d.Scene;
import luna2d.Utilites;
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
	
	public MapStruct[][] getWorldMaps()
	{
		return this.worldMaps;
	}
	
	public void updateMap(int r, int c)
	{
		this.worldMaps[r][c].update();
	}
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	public void setPlayerWorldPosition(WorldPosition wp)
	{
		this.playerWorldPosition = wp.getWorldPos();
		this.playerMapPosition = wp.getMapPos();
	}
	
	public WorldPosition getPlayerWorldPosition()
	{
		return new WorldPosition(this.playerWorldPosition, this.playerMapPosition);
		
	}
	
	/*
	 * Returns Vector2 (COLUMN, ROW)    NOT r, c
	 */
	public Vector2 getPlayerOnMapRC()
	{
		return this.playerWorldPosition;
	}
	
	// --------------------------------------------------------------------------------------------------
	//                                  SAVING AND LOADING
	// --------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------
	//                 LOADING
	// --------------------------------------------------------------------
	public static void loadEntireWorld(String gameName)
	{
		
	}
	
	// --------------------------------------------------------------------
	//                 SAVING
	// --------------------------------------------------------------------
	public static void saveEntireWorld(String gameName, MapStruct[][] worldMaps)
	{
		Log.println("Saving world: " + gameName);
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   Log.println("Saving [" + gameName + "] :: " + worldMaps[r][c].getMapName());
			   saveWorldMap(gameName, worldMaps[r][c]);
			   Log.println("Saved");
		   }
		}
		Log.println(gameName + " World Saved");
	}
	
	public static void saveWorldMap(String gameName, MapStruct map)
	{
		String path = TheHunter.GAME_SAVE_DIR + "/" + gameName + "/";
		Utilites.createDirectory(path);
		path = path + "s_" + gameName + "_" + map.getWorldPosition().worldRow + "-" +  map.getWorldPosition().worldColumn + ".ths";
		
		StringBuilder builder = new StringBuilder();
		
		int[][] mapData = map.getMapData();
		
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   int oType = mapData[r][c];
			   builder.append(oType + "");

			   if(c < TheHunter.COLUMNS - 1)
			   {
				   builder.append(",");
			   }
		   }
		   
		   builder.append("\n");
		
		}
		
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(builder.toString());
			writer.close();	
			
			saveWorldGround(gameName, map);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void saveWorldGround(String gameName, MapStruct map)
	{
		
		String path = TheHunter.GAME_SAVE_DIR + "/" + gameName + "/";
		Utilites.createDirectory(path);
		path = path + "s_" + gameName + "_" + map.getWorldPosition().worldRow + "-" +  map.getWorldPosition().worldColumn + ".thsg";
		
		int[][] mapGrounds = map.getMapGroundsData();
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)
		   {
			   int oType = mapGrounds[r][c];
			   builder.append(oType + "");

			   if(c < TheHunter.COLUMNS - 1)
			   {
				   builder.append(",");
			   }
		   }
		   
		   builder.append("\n");
		
		}
		
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(builder.toString());
			writer.close();			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}

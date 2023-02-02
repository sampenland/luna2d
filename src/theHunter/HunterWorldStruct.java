package theHunter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import luna2d.Log;
import luna2d.Scene;
import luna2d.Utilites;
import luna2d.Vector2;
import luna2d.enums.LoadDataType;
import luna2d.maps.WorldHandler;
import luna2d.maps.WorldPosition;
import luna2d.maps.WorldStruct;

public class HunterWorldStruct extends WorldStruct 
{	
	private static HunterMapStruct[][] worldMaps;
	private HunterWorldHandler worldHandler;
	
	public HunterWorldStruct(String worldName, Scene inScene, boolean gameLoad)
	{
		super(worldName, inScene);

		worldMaps = new HunterMapStruct[TheHunter.ROWS][TheHunter.COLUMNS];
		
		if (gameLoad) return;
		
		this.worldName = worldName;
		worldName = "w_" + worldName;		
		
		String[][] mapsInWorld = TheHunter.loadCSVstrings(worldName, LoadDataType.WORLD_NAMES);
		
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				String mapName = mapsInWorld[r][c];
				worldMaps[r][c] = new HunterMapStruct(mapName, this.inScene, r, c, false);
				worldMaps[r][c].load();
				
				if (worldMaps[r][c].hasPlayer())
				{
					playerWorldPosition = new WorldPosition(new Vector2(c, r), worldMaps[r][c].getPlayerMapPosition());
				}
			}
		}
		
		Log.println("Loading Complete: Player @ ", this.playerWorldPosition);
	}
	
	public WorldHandler getWorldHandler()
	{
		return this.worldHandler;
	}
	
	public void startWorldHanlder()
	{
		this.worldHandler = new HunterWorldHandler(this.inScene);
		this.worldHandler.loadFromMapStruct(worldMaps);
	}
	
	public void updateMap(int r, int c)
	{
		worldMaps[r][c].update();
	}
	
	public void updatePlayerWorldPosition(WorldPosition wp)
	{
		this.addObjectToWorld(ObjectTypes.Player, wp);
	}
	
	public WorldPosition getPlayerWorldPosition()
	{
		return this.playerWorldPosition;
		
	}
	
	public void addObjectToWorld(ObjectTypes type, WorldPosition wp)
	{
		if (worldMaps == null) return;
		if (!this.isActive()) return;
		
		HunterMapStruct map = (HunterMapStruct)worldMaps[wp.worldRow][wp.worldColumn];
		map.addObjectToWorld(type, wp.mapRow, wp.mapColumn);
	}
	
	/*
	 * Returns Vector2 (COLUMN, ROW)    NOT r, c
	 */
	public Vector2 getPlayerOnMapRC()
	{
		return this.playerWorldPosition.getMapPos();
	}
	
	// --------------------------------------------------------------------------------------------------
	//                                  SAVING AND LOADING
	// --------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------
	//                 LOADING
	// --------------------------------------------------------------------
	public void injectData(HunterMapStruct[][] data)
	{
		worldMaps = new HunterMapStruct[TheHunter.ROWS][TheHunter.COLUMNS];
		worldMaps = data;
	}
	
	// --------------------------------------------------------------------
	//                 SAVING
	// --------------------------------------------------------------------
	public static void saveEntireWorld(String gameName, Player p)
	{
		saveGameFile(gameName, p);
		
		// Save world
		Log.println("Saving world: " + gameName);
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   saveWorldMap(gameName, worldMaps[r][c]);
		   }
		}
		Log.println(gameName + " World Saved");
	}
	
	private static void saveGameFile(String gameName, Player p)
	{
		String path = TheHunter.GAME_SAVE_DIR + "/" + gameName + "/" + gameName + ".thdat";
		
		StringBuilder builder = new StringBuilder();
		
		// player position
		WorldPosition wp = p.getWorldPosition();
		builder.append(wp.worldRow + "," + wp.worldColumn + "," + wp.mapRow + "," + wp.mapColumn);
		
		Utilites.saveStringToFile(builder.toString(), path);
		
		// Backpack and player stats
		p.save(gameName);		
		
	}
	
	public static void saveWorldMap(String gameName, HunterMapStruct map)
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
	
	public static void saveWorldGround(String gameName, HunterMapStruct map)
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

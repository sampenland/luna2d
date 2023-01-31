package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.DayNightCycleTime;
import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.Utilites;
import luna2d.Vector2;
import theHunter.DayNightCycleEngine;
import theHunter.LoadDataType;
import theHunter.MapStruct;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.WorldPosition;
import theHunter.WorldStruct;

public class WorldPlayer extends Scene
{
	public static final int WORLD_RENDER_DISTANCE = 1;
	public static final int WORLD_UPDATE_DISTANCE = 2;
	
	private boolean savingLock;
	
	public WorldPlayer(String name) 
	{
		super(name);
	}

	@Override
	public void start() 
	{
		Log.println("World Player started.");		
	}
	
	public void loadGame(String gameName)
	{
		this.savingLock = false;
		
		Log.println("Loading save data: " + gameName);
		this.worldData = new WorldStruct(gameName, this, true);
		
		Log.println(gameName + " game finished. Starting...");
		
		String pathBase = TheHunter.GAME_SAVE_DIR + "/" + gameName + "/";
		
		if(Utilites.directoryExists(pathBase))
		{
			MapStruct[][] worldMaps = new MapStruct[TheHunter.ROWS][TheHunter.COLUMNS];
			for (int r = 0; r < TheHunter.ROWS; r++)
			{
				for (int c = 0; c < TheHunter.COLUMNS; c++)
				{
					worldMaps[r][c] = this.loadMap(gameName, r, c);
					
					if (worldMaps[r][c].hasPlayer())
					{
						this.worldData.setPlayerWorldPosition(new WorldPosition(new Vector2(c, r), worldMaps[r][c].getPlayerMapPosition()));
					}					
				}
			}
			
			this.worldData.injectData(worldMaps);
			configGame();
		}		
	}
	
	private MapStruct loadMap(String gameName, int worldRow, int worldColumn)
	{
		String mapName = gameName + "_" + worldRow + "-" +  worldColumn;
		
		String pathBase = TheHunter.GAME_SAVE_DIR + "/" + gameName + "/";
		String mapPath = pathBase + "s_" + gameName + "_" + worldRow + "-" +  worldColumn + ".ths";
		String groundPath = pathBase + "s_" + gameName + "_" + worldRow + "-" +  worldColumn + ".thsg";

		int[][] mapData = TheHunter.loadCSVints(mapPath, LoadDataType.GAME_MAP);
		int[][] mapGrounds = TheHunter.loadCSVints(groundPath, LoadDataType.GAME_GROUNDS);
		
		MapStruct mapStruct = new MapStruct(mapName, this, worldRow, worldColumn, true);
		mapStruct.injectData(mapName, mapData, mapGrounds);
		
		return mapStruct;
		
	}
	
	public void loadAndStart(String worldName)
	{
		this.savingLock = false;
		
		Log.println("Loading world...");
		this.worldData = new WorldStruct(worldName, this, false);
		
		configGame();
		
		Log.println(worldName + " finished. Starting...");
	}

	public void configGame()
	{
		// Zoom in
		this.getGame().updateScale(MapStruct.MAP_SCALE);
		
		// Startup day and night
		this.setDayNightCycle(new DayNightCycleEngine(80, 8, 20, Color.orange, Color.white, Color.orange, 
				new Color(0, 0, 0, 0.8f)), new DayNightCycleTime(8, 0, 0));
		
		ResourceHandler.addRain("Rain", "RainComing", 800, 10, 50, 
				4 * 60, 3 * 24 * 60, // 4 hrs - 3 days between rain
				60, 2 * 24 * 60, // 1 hours - 2 days of rain
				this.getDayNightEngine().getMilliSecondsOfInGameMinute() / 2
				);
	}
	
	@Override
	public void end() 
	{
		if (this.getPlayer() != null) 
		{
			Player p = (Player)this.getPlayer();
			p.destroy();			
		}
		
		Game.getWeatherSystem().disableRain();
		
		Log.println("World Player ended.");	
	}
	
	private void updateWorld()
	{
		Vector2 playerMap = this.worldData.getPlayerOnMapRC();
		
		if (playerMap == null)
		{
			return;
		}
		
		int playerRow = playerMap.y;
		int playerColumn = playerMap.x;
		
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (playerColumn - WORLD_UPDATE_DISTANCE >= 0 && 
						playerColumn + WORLD_UPDATE_DISTANCE < TheHunter.COLUMNS &&
						playerRow - WORLD_UPDATE_DISTANCE >= 0 &&
						playerRow + WORLD_UPDATE_DISTANCE < TheHunter.ROWS)
					{
						this.worldData.updateMap(r, c);
					}
			}
		}
	}

	@Override
	public void update() 
	{
		this.updateWorld();
		this.checkKeys();
	}

	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE)) 
		{
			this.openScene("MainMenu");
		}
		
		if (!this.savingLock && this.isKeyPressed(KeyEvent.VK_U)) 
		{
			this.savingLock = true;
			WorldStruct.saveEntireWorld("sam-world", this.worldData.getWorldMaps());
		}
	}
	
	@Override
	public void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}

}

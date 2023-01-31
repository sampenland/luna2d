package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.DayNightCycleTime;
import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.Vector2;
import theHunter.DayNightCycleEngine;
import theHunter.MapStruct;
import theHunter.Player;
import theHunter.TheHunter;
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
	
	public void loadAndStart(String worldName)
	{
		this.savingLock = false;
		
		Log.println("Loading world...");
		this.worldData = new WorldStruct(worldName, this);
		
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
		
		Log.println(worldName + " finished. Starting...");
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

package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.DayNightCycleTime;
import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import luna2d.Scene;
import theHunter.DayNightCycleEngine;
import theHunter.LoadDataType;
import theHunter.MapGrounds;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.objects.BerryBush;
import theHunter.objects.Fence;
import theHunter.objects.Fire;
import theHunter.objects.Rock;
import theHunter.objects.Tree;
import theHunter.objects.WaterSource;
import theHunter.objects.Wolf;

public class MapPlayer extends Scene
{
	private final int MAP_SCALE = 5;
	private static int[][] mapData;
	private static int[][] mapDataGrounds;
		
	public MapPlayer(String name) 
	{
		super(name);	
		mapData = new int[TheHunter.ROWS][TheHunter.COLUMNS];
	}

	@Override
	public void start() 
	{
		Log.println("Started Map Player"); 
	}
	
	public void loadAndStartMap(String name)
	{
		Log.println("Loading Map: " + name);
		
		name = "m_" + name;
		
		mapData = TheHunter.loadCSVints(name, LoadDataType.MAP);
		mapDataGrounds = TheHunter.loadCSVints(name, LoadDataType.GROUNDS);
		
		MapGrounds grounds = new MapGrounds(this, 0, 0, MAP_SCALE, mapDataGrounds);
		grounds.enableCulling = true;
		
		// Populate objects
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				int x = c * TheHunter.CELL_SIZE * MAP_SCALE;
				int y = (r * TheHunter.CELL_SIZE) * MAP_SCALE;
				
				ObjectTypes objectType = ObjectTypes.values()[mapData[r][c]];
				
				switch(objectType)
				{
				case Empty:
					break;
					
				case Bush:
					new BerryBush(this, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
					
				case GndDirt:
					break;
					
				case GndGrass:
					break;
					
				case GndRock:
					break;
					
				case GndWater:
					break;
					
				case Player:
					Player p = new Player(this, "Player", 0, 0, 1, 16, 4, 250);
					x = x - Game.WIDTH / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
					y = y - Game.HEIGHT / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
					p.updateWorldPosition(x, y);
					break;
					
				case Tree:
					new Tree(this, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
					
				case Water:
					new WaterSource(this, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
					
				case Wolf:
					new Wolf(this, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
				case InvBerries:
					break;
				case InvRock:
					break;
				case Rock:
					new Rock(this, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
				case Fire:
					Fire f = new Fire(this, false);
					f.placeOnGround(x, y);
					break;
				case FenceHorz:
					Fence fenH = new Fence(this);
					fenH.placeOnGround(x, y);
					fenH.rotate();
					break;
				case FenceVert:
					Fence fenV = new Fence(this);
					fenV.placeOnGround(x, y);
					break;
				case InvFence:
					break;
				case InvFire:
					break;
				case ThrownRock:
					break;
				default:
					break;
				}
			}
		}
		
		// Zoom in
		this.getGame().updateScale(MAP_SCALE);
		
		// Startup day and night
		this.setDayNightCycle(new DayNightCycleEngine(50, 8, 20, Color.orange, Color.white, Color.orange, new Color(0, 0, 0, 0.8f)), new DayNightCycleTime(8, 0, 0));
		
		ResourceHandler.addRain("Rain", "RainComing", 800, 10, 50, 
				4 * 60, 3 * 24 * 60, // 4 hrs - 3 days between rain
				60, 2 * 24 * 60, // 1 hours - 2 days of rain
				this.getDayNightEngine().getMilliSecondsOfInGameMinute() / 2
				);
		
	}

	@Override
	public void end() 
	{
		Player p = (Player)this.getPlayer();
		p.destroy();
		
		Game.getWeatherSystem().disableRain();
		
		Log.println("Ended Map Player");
	}

	@Override
	public void update() 
	{
		checkKeys();
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE)) 
		{
			this.openScene("MainMenu");
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

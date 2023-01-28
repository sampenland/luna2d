package theHunter;

import java.awt.Color;
import luna2d.Game;
import luna2d.Log;
import luna2d.Scene;
import luna2d.Vector2;
import theHunter.objects.BerryBush;
import theHunter.objects.Fire;
import theHunter.objects.Rock;
import theHunter.objects.Tree;
import theHunter.objects.WaterSource;
import theHunter.objects.Wolf;

public class MapStruct 
{
	public static final int MAP_SCALE = 5;
	private int[][] mapData;
	private int[][] mapDataGrounds;
	private String mapName;
	
	private Vector2 mapPosition;
	
	MapGrounds grounds;
	Scene inScene;
	
	public MapStruct(String mapName, Scene inScene, int r, int c) // r, c is row and col of world
	{
		this.inScene = inScene;
		
		this.mapName = mapName;
		mapName = "m_" + mapName;
		
		this.mapPosition = new Vector2(c, r);
		
		this.mapData = TheHunter.loadCSVints(mapName, LoadDataType.MAP);
		this.mapDataGrounds = TheHunter.loadCSVints(mapName, LoadDataType.GROUNDS);
	}
	
	public void load()
	{
		Log.println("Loading Map: " + this.mapName);
		
		int mX = this.mapPosition.y + (MAP_SCALE * TheHunter.CELL_SIZE * TheHunter.COLUMNS);
		int mY = this.mapPosition.x + (MAP_SCALE * TheHunter.CELL_SIZE * TheHunter.ROWS);
		
		grounds = new MapGrounds(this.inScene, mX, mY, new Color(0, 0, 0, 0.2f), MAP_SCALE, this.mapDataGrounds);
		grounds.enableCulling = true;
		
		// Populate objects
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				int x = mX + c * TheHunter.CELL_SIZE * MAP_SCALE;
				int y = mY + (r * TheHunter.CELL_SIZE) * MAP_SCALE;
				
				ObjectTypes objectType = ObjectTypes.values()[mapData[r][c]];
				
				switch(objectType)
				{
				case Empty:
					break;
					
				case Bush:
					new BerryBush(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
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
					Player p = new Player(this.inScene, "Player", 0, 0, 1, 16, 4, 250);
					x = x - Game.WIDTH / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
					y = y - Game.HEIGHT / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
					p.updateWorldPosition(x, y);
					break;
					
				case Tree:
					new Tree(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
					
				case Water:
					new WaterSource(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
					
				case Wolf:
					new Wolf(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
				case InvBerries:
					break;
				case InvRock:
					break;
				case Rock:
					new Rock(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
				case Fire:
					new Fire(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void update()
	{
		
	}
	
	public Vector2 getPlayerMapPosition()
	{
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					return new Vector2(c, r);
				}
			}
		}
		
		return new Vector2(-1, -1);
	}
	
	public boolean hasPlayer()
	{
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int[][] getMapData()
	{
		return this.mapData;
	}
	
	public int[][] getMapGroundsData()
	{
		return this.mapDataGrounds;
	}
	
}

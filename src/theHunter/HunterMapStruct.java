package theHunter;

import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.enums.LoadDataType;
import luna2d.maps.MapStruct;
import luna2d.maps.WorldPosition;
import theHunter.objects.BerryBush;
import theHunter.objects.Fire;
import theHunter.objects.GrowingBerryBush;
import theHunter.objects.Rock;
import theHunter.objects.Torch;
import theHunter.objects.Tree;
import theHunter.objects.WaterSource;
import theHunter.objects.Wolf;

public class HunterMapStruct extends MapStruct
{
	public static final int MAP_SCALE = 5;
	private MapGrounds grounds;
	
	public HunterMapStruct(String mapName, Scene inScene, int r, int c, boolean gameLoad) // r, c is row and col of world
	{
		super(mapName, inScene, r, c, gameLoad, TheHunter.ROWS, TheHunter.COLUMNS, TheHunter.CELL_SIZE, MAP_SCALE);
		
		if (gameLoad)
		{
			this.worldPosition = new WorldPosition(new Vector2(c, r), new Vector2(0, 0));
			return;
		}
		
		this.mapName = mapName;
		mapName = "m_" + mapName;
		
		this.worldPosition = new WorldPosition(new Vector2(c, r), new Vector2(0, 0));
		
		this.mapData = TheHunter.loadCSVints(mapName, LoadDataType.MAP);
		this.mapDataGrounds = TheHunter.loadCSVints(mapName, LoadDataType.GROUNDS);
		
	}
	
	public void clearPlayer()
	{
		for (int r = 0; r < this.totalRows; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (this.mapData[r][c] == ObjectTypes.Player.intValue)
				{
					this.mapData[r][c] = ObjectTypes.Empty.intValue;
					return;
				}
			}
		}
	}
	
	@Override
	public void load()
	{
		grounds = new MapGrounds(this.inScene, this.worldPosition.worldColumn * (TheHunter.CELL_SIZE * TheHunter.COLUMNS), 
				this.worldPosition.worldRow * (TheHunter.CELL_SIZE * TheHunter.ROWS), MAP_SCALE, this.mapDataGrounds);
		
		grounds.setWorldRender(true);
		grounds.updateWorldPosition(this.worldPosition);
		grounds.setColors(ColorHandler.getColor("GrassGridYellow"), ColorHandler.getColor("GrassGreen"));
		grounds.enableCulling = true;
		
		// Populate objects
		Vector2 wp = this.worldPosition.getWorldPos();
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				WorldPosition thisWorldPosition = new WorldPosition(wp, new Vector2(c, r));
				int x = c * TheHunter.CELL_SIZE * MAP_SCALE;
				int y = (r * TheHunter.CELL_SIZE) * MAP_SCALE;
						
				x += wp.x * TheHunter.CELL_SIZE * TheHunter.COLUMNS * MAP_SCALE;
				y += wp.y * TheHunter.CELL_SIZE * TheHunter.ROWS * MAP_SCALE;
				
				ObjectTypes objectType = ObjectTypes.values()[mapData[r][c]];
				
				switch(objectType)
				{
				case Empty:
					break;
					
				case Bush:
					BerryBush bush = new BerryBush(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					bush.updateWorldPosition(thisWorldPosition);
					break;
				case GrowingBerryBush:
					GrowingBerryBush growingBush = new GrowingBerryBush(inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					growingBush.updateWorldPosition(thisWorldPosition);
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
					p.updateWorldPosition(thisWorldPosition);
					break;
					
				case Tree:
					Tree t = new Tree(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					t.updateWorldPosition(thisWorldPosition);
					break;
					
				case Water:
					WaterSource ws = new WaterSource(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					ws.updateWorldPosition(thisWorldPosition);
					break;
					
				case Wolf:
					Wolf w = new Wolf(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					w.updateWorldPosition(thisWorldPosition);
					break;
				case InvBerries:
					break;
				case InvRock:
					break;
				case Rock:
					Rock rock = new Rock(this.inScene, x, y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					rock.updateWorldPosition(thisWorldPosition);
					break;
				case Fire:
					Fire f = new Fire(this.inScene, false);
					f.placeOnGround(x, y);
					f.updateWorldPosition(thisWorldPosition);
					break;
				case Torch:
					Torch torch = new Torch(this.inScene, false);
					torch.placeOnGround(x, y);
					torch.updateWorldPosition(thisWorldPosition);
					break;
				default:
					break;
				}
			}
		}
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
	
	public void addObjectToWorld(ObjectTypes type, int r, int c)
	{
		if (this.mapData != null)
		{
			this.mapData[r][c] = type.intValue;
		}
	}
	
	public void createPlayer(WorldPosition wp)
	{
		Vector2 xyPos = Maths.convertWorldPositionToXY(wp, TheHunter.CELL_SIZE, TheHunter.ROWS, TheHunter.COLUMNS, MAP_SCALE);
		
		int x = xyPos.x;
		int y = xyPos.y;
		
		Player p = new Player(this.inScene, "Player", 0, 0, 1, 16, 4, 250);
		x = x - Game.WIDTH / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
		y = y - Game.HEIGHT / 2 + (TheHunter.CELL_SIZE * Game.CAMERA_SCALE) + (TheHunter.CELL_SIZE * MAP_SCALE / 2);
		p.updateWorldPosition(x, y);
		p.updateWorldPosition(wp);
	}

	@Override
	public void update() 
	{
		
	}
	
}

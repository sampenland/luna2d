package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Log;
import luna2d.Scene;
import theHunter.MapGrounds;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.objects.BerryBush;
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
		mapData = TheHunter.loadMapOrGrounds("map1", true);
		mapDataGrounds = TheHunter.loadMapOrGrounds("map1", false);
		
		new MapGrounds(this, 0, 0, new Color(0, 0, 0, 0.2f), MAP_SCALE, mapDataGrounds);
		
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
					new BerryBush(this, x, y, 1);
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
					new Tree(this, x, y, 1);
					break;
					
				case Water:
					new WaterSource(this, x, y, 1);
					break;
					
				case Wolf:
					new Wolf(this, x, y, 1);
					break;
					
				default:
					break;
				}
			}
		}
		
		// Zoom in
		this.getGame().updateScale(MAP_SCALE);
	}

	@Override
	public void end() 
	{
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

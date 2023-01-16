package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.Grid;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.objects.Tree;

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
		
		new Grid(this, 0, 0, TheHunter.ROWS, TheHunter.COLUMNS, 16, new Color(0, 0, 0, 0.2f), MAP_SCALE, mapDataGrounds);
		
		// Populate objects
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				int x = c * 16;
				int y = TheHunter.GRIDY_OFFSET + r * 16;
				
				ObjectTypes objectType = ObjectTypes.values()[mapData[r][c]];
				
				switch(objectType)
				{
				case Empty:
					break;
				case Bush:
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
					new Player(this, "Player", x, y, 1, 16, 4, 250);
					break;
				case Tree:
					new Tree(this, x, y, 1);
					break;
				case Water:
					break;
				case Wolf:
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

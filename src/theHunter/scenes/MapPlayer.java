package theHunter.scenes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.Log;
import luna2d.Scene;
import theHunter.Ground;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.objects.Tree;

public class MapPlayer extends Scene
{

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
		
		// Populate grounds
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				int x = c * 16;
				int y = TheHunter.GRIDY_OFFSET + r * 16;
				Ground g = new Ground(this, x, y, 1);
				g.updateGroundType(ObjectTypes.values()[mapDataGrounds[r][c]]);
			}
		}
		
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
					new Tree(x, y, this);
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

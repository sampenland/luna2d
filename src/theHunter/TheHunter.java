package theHunter;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import luna2d.ColorHandler;
import luna2d.Game;
import luna2d.Log;
import luna2d.ResourceHandler;
import theHunter.scenes.MainMenu;
import theHunter.scenes.MapEditor;
import theHunter.scenes.MapPlayer;
import theHunter.scenes.WorldEditor;
import theHunter.scenes.WorldPlayer;

public class TheHunter extends Game 
{

	private static final long serialVersionUID = -8234717309381689045L;

	public static final String DATA_DIR = System.getProperty("user.dir") + "/data/";
	public static final int GRIDY_OFFSET = 5;
	public static final int ROWS = 37;
	public static final int COLUMNS = 49;
	public static final int CELL_SIZE = 16;
	public static final int WORLD_WIDTH = CELL_SIZE * COLUMNS;
	public static final int WORLD_HEIGHT = CELL_SIZE * ROWS;
	
	public static final int RESOURCE_ACTION_DISTANCE = 50;
	
	// -------- LAYERS ----------------------
	public static final int ENVIRONMENT_DRAW_LAYER = 2; 
	//---------------------------------------
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
		
	public static void main(String[] args)
	{
		Game g = new Game();
		g.init(WIDTH, HEIGHT, "The Hunter", Color.black, "src/theHunter/res/");
		
		MainMenu menu = new MainMenu("MainMenu");		
		g.sceneManager.addScene(menu);
		
		MapPlayer mapPlayer = new MapPlayer("MapPlayer");
		g.sceneManager.addScene(mapPlayer);
		
		MapEditor mapEditor = new MapEditor("MapEditor");
		g.sceneManager.addScene(mapEditor);
		
		WorldEditor worldEditor = new WorldEditor("WorldEditor");
		g.sceneManager.addScene(worldEditor);
		
		WorldPlayer worldPlayer = new WorldPlayer("WorldPlayer");
		g.sceneManager.addScene(worldPlayer);
		
		loadImages();
		createColors();
		
		g.beginSceneEngine("MainMenu");
		
	}	
	
	private static void createColors()
	{
		ColorHandler.addColor("GrassGreen", new Color(52, 122, 115));
		ColorHandler.addColor("WaterBlue", new Color(118, 135, 171));
	}
	
	private static void loadImages()
	{
		ResourceHandler.addImage("Player", "player-idle_16x16_4-frames.png");
		ResourceHandler.addImage("BerryBush", "berry-bush.png");
		ResourceHandler.addImage("Tree", "tree.png");
		ResourceHandler.addImage("Water", "water.png");
		ResourceHandler.addImage("Wolf", "wolf.png");
		ResourceHandler.addImage("INV_Berries", "inventory/inv-berries.png");
		ResourceHandler.addImage("GrowingBerryBush", "growing-berry-bush_16x16_5-frames.png");
		ResourceHandler.addImage("Rock", "rock.png");
		ResourceHandler.addImage("INV_Rock", "inventory/inv-rock.png");
		ResourceHandler.addImage("Rain", "rain.png"); 
		ResourceHandler.addImage("RainComing", "rain-coming.png");
		ResourceHandler.addImage("Fire", "fire_16x16_5-frames.png");
		ResourceHandler.addImage("InvFence","inv-fence.png");
		ResourceHandler.addImage("Fence","fence_16x16-6-colors.png");
	}
	
	public static int[][] loadCSVints(String name, LoadDataType dataType)
	{
		String path = TheHunter.DATA_DIR + "/" + name;
		
		switch(dataType)
		{
		case GROUNDS:
			path += ".thmg";
			break;
		case MAP:
			path += ".thm";
			break;
		case WORLD:
			path += ".thw";
			break;
		default:
			Log.println(name + " not a world, map, or grounds name.");
			return null;	
		}
		
		int[][] data = new int[ROWS][COLUMNS];
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   String[] cols = line.split(","); 
			   int col = 0;
			   for(String  c : cols)
			   {
				   data[row][col] = Integer.parseInt(c);
				   col++;
			   }
			   
			   row++;
			   
			}
			
			reader.close();
			
			return data;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String[][] loadCSVstrings(String name, LoadDataType dataType)
	{
		String path = TheHunter.DATA_DIR + "/" + name;
		
		switch(dataType)
		{
		case WORLD_NAMES:
			path += ".thwn";
			break;
		default:
			Log.println(name + " not a world names file.");
			return null;
		}
		
		String[][] data = new String[ROWS][COLUMNS];
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(path));
			
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   String[] cols = line.split(","); 
			   int col = 0;
			   for(String  c : cols)
			   {
				   data[row][col] = c;
				   col++;
			   }
			   
			   row++;
			   
			}
			
			reader.close();
			
			return data;
			
		} 
		catch (IOException | NumberFormatException e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
}

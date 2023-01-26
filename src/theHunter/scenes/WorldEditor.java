package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import luna2d.Game;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.TextDisplay;
import luna2d.ui.UITextInput;
import theHunter.MapGrounds;
import theHunter.ObjectTypes;
import theHunter.TheHunter;
import theHunter.ui.WorldEditorMenu;

public class WorldEditor extends Scene
{

	private MapGrounds grounds;
	private int[][] maps;
	private String[][] mapNames;
	
	private TextDisplay status;
	private UITextInput mapNameInput;
	
	public WorldEditorMenu detailedMenu;
	
	public WorldEditor(String name) 
	{
		super(name);
		this.setMouseEnabled(true);
	}
	
	public void closeMenu()
	{		
		this.detailedMenu.close();
	}
	
	public void injectMapData(int[][] data, String[][] names)
	{
		clearMaps();
		clearMapNames();
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{		
				maps[r][c] = data[r][c];
				mapNames[r][c] = names[r][c];
			}
		}
		
		grounds.updateFillTypes(maps);
		this.closeMenu();
	}
	
	private void clearMapNames()
	{
		mapNames = new String[TheHunter.ROWS][TheHunter.COLUMNS];
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{		
				mapNames[r][c] = "Water";
			}
		}
		
	}
	
	private void clearMaps()
	{
		maps = new int[TheHunter.ROWS][TheHunter.COLUMNS];
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{		
				maps[r][c] = ObjectTypes.GndWater.intValue;
			}
		}
		
	}
	
	public void saveMap(String name)
	{
		String path = TheHunter.DATA_DIR + "/" + name + ".thw";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   int oType = this.maps[r][c];
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
			
			this.saveNames(name);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void checkKeys()
	{
		if (this.detailedMenu != null && this.detailedMenu.visible)
		{
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.openScene("MainMenu");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_M)) 
		{			
			this.detailedMenu.toggleVisible();
		}
	}

	private void saveNames(String name)
	{
		String path = TheHunter.DATA_DIR + "/" + name + ".thwn";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)
		   {
			   String mapName = this.mapNames[r][c];
			   builder.append(mapName);

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
	
	@Override
	public void start() 
	{
		Log.println("World Editor started.");
		
		grounds = new MapGrounds(this, 0, TheHunter.GRIDY_OFFSET, new Color(1.0f, 1.0f, 0.0f, 0.1f), 1, null);
		grounds.inputEnabled = true;
		grounds.enableCulling = false;
		
		clearMaps();
		clearMapNames();
		
		grounds.updateFillTypes(maps);
		
		status = new TextDisplay(this, "Left Click to place map. Right click to name it.", Game.WIDTH / 2 - 150, 
				Game.HEIGHT - 60, Color.white, Game.TOP_DRAW_LAYER);
		
		mapNameInput = new UITextInput(this, "MAP00", 0, 0, 100, 20, 8);
		mapNameInput.inputEnabled = true;
		mapNameInput.visible = false;
		
		this.detailedMenu = new WorldEditorMenu(this, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 300, 200, new Color(1, 1, 1, 0.45f), 1);
		
	}

	@Override
	public void end() 
	{
		Log.println("World Editor ended.");
	}

	@Override
	public void update() 
	{
		this.checkKeys();
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
		if (this.detailedMenu != null && this.detailedMenu.visible) return;
		
		if (this.grounds != null && this.grounds.mouseClicked)
		{
			if (this.grounds.mouseClickEvent != null && this.grounds.mouseClickEvent.getButton() == 1)
			{
				// left click - place map
				if (this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] == ObjectTypes.GndGrass.intValue)
				{
					this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] = ObjectTypes.Empty.intValue;
				}
				else
				{
					this.maps[this.grounds.clickedRow][this.grounds.clickedColumn] = ObjectTypes.GndGrass.intValue;
				}				
			}
			else if (this.grounds.mouseClickEvent != null && this.grounds.mouseClickEvent.getButton() == 3)
			{
				// Right click - name map
			}
			
			
			grounds.updateFillTypes(maps);
		}
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

}

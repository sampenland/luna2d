package theHunter.scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;

import luna2d.Game;
import luna2d.Log;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Grid;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTimer;
import theHunter.Ground;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public class MapEditor extends Scene
{	
	private int playerRow = 0;
	private int playerCol = 0;
	
	private enum MOUSE_STATUS
	{
		IDLE,
		SELECTING,
		PLACING
	}
	
	private MOUSE_STATUS mouseStatus;
	private boolean disableSwitching = false;
	private SceneTimer resetInputTask;
	private Timer resetInputTimer;
	
	private ObjectTypes currentSelection;
	private ObjectTypes lastSelection;
	private Sprite currentSelectionSprite;
	
	private Sprite[][] mapDataSprites;
	private Ground[][] mapDataGrounds;
	
	private TextDisplay statusLabel;
	
	public MapEditor(String name) 
	{
		super(name);
		mouseStatus = MOUSE_STATUS.IDLE;
		this.currentSelection = ObjectTypes.Player;
		this.lastSelection = ObjectTypes.Empty;
	}

	@Override
	public void start() 
	{		
		this.setMouseEnabled(true);
		this.mapDataSprites = new Sprite[TheHunter.ROWS][TheHunter.COLUMNS];
		this.mapDataGrounds = new Ground[TheHunter.ROWS][TheHunter.COLUMNS];
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{				
				mapDataGrounds[r][c] = new Ground(this, c * 16, TheHunter.GRIDY_OFFSET + r * 16);
				mapDataSprites[r][c] = new Sprite(this, "", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1.0f);
				mapDataSprites[r][c].setObjectType(ObjectTypes.Empty.intValue);
			}
		}
		
		new Grid(this, 0, TheHunter.GRIDY_OFFSET, TheHunter.ROWS, TheHunter.COLUMNS, 16, new Color(1.0f, 1.0f, 0.0f, 0.5f));
		
		this.setInputEnabled(false);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE);
		createSelectionSprites();
		
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1.0f, 16, 16);
		this.currentSelectionSprite.visible = false;
		
		mapDataSprites[this.playerRow][this.playerCol].updateImageRef("Player", true, 16, 16);
		mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Player.intValue);
	}
	
	private void createSelectionSprites()
	{
		
	}

	@Override
	public void end() 
	{
		
	}

	@Override
	public void update() 
	{
		checkKeys();
		populateGrid();
	}
	
	private void populateGrid()
	{
		switch(this.mouseStatus)
		{
		
			case IDLE:
				
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = false;	
				}
				
				break;
				
			case SELECTING:
				displaySelection();
				break;
				
			case PLACING:
				placeSelection();
				break;
				
		}
	}
	
	private void displaySelection()
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.visible = false;
	}
	
	private void placeSelection()
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.visible = true;
		this.currentSelectionSprite.updatePosition(Game.mouseX, Game.mouseY);
		
		if (this.currentSelection != this.lastSelection)
		{
			this.currentSelectionSprite.updateImageRef(getSelectionName(), true, false);
			this.lastSelection = this.currentSelection;
			Log.println(getSelectionName());
		}
	}
	
	private String getSelectionName()
	{
		switch(this.currentSelection)
		{
		case Empty:
			return "n/a";
		case Bush:
			return "Bush";
		case Player:
			return "Player";
		case Tree:
			return "Tree";
		case Water:
			return "Water";
		case Wolf:
			return "Wolf";
		default:
			return "n/a";
		}
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.openScene("MainMenu");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_Q))
		{
			userSwitching();
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S))
		{
			this.saveMap("map1");
		}
	}
	
	private void userSwitching()
	{
		if (!this.disableSwitching)
		{
			switch(this.mouseStatus)
			{
			
				case IDLE:
					this.mouseStatus = MOUSE_STATUS.SELECTING;
					this.statusLabel.updateText("Selecting");
					break;
					
				case SELECTING:
					this.mouseStatus = MOUSE_STATUS.PLACING;
					this.statusLabel.updateText("Placing");
					break;
					
				case PLACING:
					this.mouseStatus = MOUSE_STATUS.IDLE;
					this.statusLabel.updateText("Idle");
					break;
					
			}
			
			// Wait 500 before you can tab again
			this.disableSwitching = true;

			resetInputTask = new SceneTimer(this)
			{
				@Override
				public void run() 
				{
					MapEditor s = (MapEditor)this.scene;
					s.disableSwitching = false;
				}
			};
			resetInputTimer = new Timer("ResetInputTimer");				
			this.resetInputTimer.schedule(resetInputTask, 500);
		}
	}

	@Override
	public void onMouseClick(MouseEvent e) 
	{
		int x = Game.mouseX;
		int y = Game.mouseY;
		
		Point gPos = Maths.convertToGrid(x, y, 16, 0, TheHunter.GRIDY_OFFSET);
		
		switch(this.mouseStatus)
		{
		
			case IDLE:				
				break;
				
			case SELECTING:
				break;
				
			case PLACING:
				
				if (this.currentSelection == ObjectTypes.Player)
				{
					this.mapDataSprites[gPos.y][gPos.x].updateImageRef("Player", true, true);
					this.mapDataSprites[gPos.y][gPos.x].setObjectType(ObjectTypes.Player.intValue);
					
					this.mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, false);
					this.mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
					
					this.playerCol = gPos.x;
					this.playerRow = gPos.y;
				}
				
				break;
				
		}
	}

	@Override
	public void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
	}
	
	private void saveMap(String name)
	{
		String path = TheHunter.MAP_DIR + "/" + name + ".thm";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   int oType = this.mapDataSprites[r][c].getObjectType();
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
			
			this.saveGrounds(name);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void saveGrounds(String name)
	{
		String path = TheHunter.MAP_DIR + "/" + name + ".thmg";
		
		StringBuilder builder = new StringBuilder();
		
		for(int r = 0; r < TheHunter.ROWS; r++)//for each row
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)//for each column
		   {
			   int oType = this.mapDataGrounds[r][c].getObjectType();
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}

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
import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTask;
import theHunter.MapGrounds;
import theHunter.ObjectTypes;
import theHunter.TheHunter;
import theHunter.ui.MapEditorMenu;

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
	private SceneTask resetInputTask;
	private Timer resetInputTimer;
	
	private ObjectTypes currentSelection;
	private ObjectTypes lastSelection;
	private Sprite currentSelectionSprite;
	
	private Sprite[][] mapDataSprites;
	private int[][] mapDataGrounds;
	private Sprite[][] selectionSprites;
	
	private TextDisplay statusLabel;
	public MapEditorMenu detailedMenu;
	
	public MapEditor(String name) 
	{
		super(name);
		mouseStatus = MOUSE_STATUS.IDLE;
		this.currentSelection = ObjectTypes.Player;
		this.lastSelection = ObjectTypes.Empty;
	}
	
	public void injectMapData(int[][] data, boolean isMap)
	{
		if (isMap)
		{
			for(int r = 0; r < TheHunter.ROWS; r++)
			{
				for(int c = 0; c < TheHunter.COLUMNS; c++)
				{		
					
					ObjectTypes objectType = ObjectTypes.values()[data[r][c]];
					
					switch(objectType)
					{
					case Empty:
						break;
						
					case Bush:
						this.mapDataSprites[r][c] = new Sprite(this, "BerryBush", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1, 16, 2, 0);
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
						this.mapDataSprites[r][c] = new Sprite(this, "Player", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1, 16, 4, 0);
						this.currentSelectionSprite = this.mapDataSprites[r][c];
						this.currentSelection = ObjectTypes.Player;
						break;
						
					case Tree:
						this.mapDataSprites[r][c] = new Sprite(this, "Tree", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);
						break;
						
					case Water:
						this.mapDataSprites[r][c] = new Sprite(this, "Water", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);
						break;
						
					case Wolf:
						this.mapDataSprites[r][c] = new Sprite(this, "Wolf", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);
						break;
						
					default:
						break;
					}
					
					mapDataSprites[r][c] = new Sprite(this, "", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);
					mapDataSprites[r][c].setObjectType(ObjectTypes.Empty.intValue);
				}
			}
		}
		else
		{
			this.mapDataGrounds = new int[TheHunter.ROWS][TheHunter.COLUMNS];
			
			for(int r = 0; r < TheHunter.ROWS; r++)
			{
				for(int c = 0; c < TheHunter.COLUMNS; c++)
				{
					mapDataGrounds[r][c] = data[r][c];
				}
			}
		}
		
		this.closeMenu();
	}
	
	public void closeMenu()
	{
		this.mouseStatus = MOUSE_STATUS.IDLE;
		
		this.currentSelectionSprite.updateImageRef("Player", true, false);
		this.currentSelectionSprite.setFixedScreenPosition(true);
		
		this.detailedMenu.close();
	}

	@Override
	public void start() 
	{		
		this.setMouseEnabled(true);
		this.mapDataSprites = new Sprite[TheHunter.ROWS][TheHunter.COLUMNS];
		this.mapDataGrounds = new int[TheHunter.ROWS][TheHunter.COLUMNS];
		this.selectionSprites = new Sprite[TheHunter.ROWS][TheHunter.COLUMNS];
		
		MapGrounds grounds = new MapGrounds(this, 0, TheHunter.GRIDY_OFFSET, new Color(1.0f, 1.0f, 0.0f, 0.1f), 1, null);
		
		int count = 0;
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{				
				mapDataGrounds[r][c] = ObjectTypes.GndGrass.intValue;
				mapDataSprites[r][c] = new Sprite(this, "", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);
				mapDataSprites[r][c].setObjectType(ObjectTypes.Empty.intValue);
				
				if (count < ObjectTypes.values().length)
				{
					String imgName = ObjectTypes.values()[count].imgName;
					int objType = ObjectTypes.values()[count].intValue;
					
					if (imgName == "Player")
					{
						this.selectionSprites[r][c] = new Sprite(this, imgName, c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1, 16, 16);
					}
					else
					{
						this.selectionSprites[r][c] = new Sprite(this, imgName, c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1);	
					}
					
					this.selectionSprites[r][c].setObjectType(objType);
					this.selectionSprites[r][c].visible = false;
				}
				count++;
			}
		}
		
		grounds.updateFillTypes(mapDataGrounds);
		
		this.setInputEnabled(false);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE);
		
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1, 16, 16);
		this.currentSelectionSprite.setFixedScreenPosition(true);
		
		mapDataSprites[this.playerRow][this.playerCol].updateImageRef("Player", true, 16, 16);
		mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Player.intValue);
		
		this.detailedMenu = new MapEditorMenu(this, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 300, 200, new Color(0, 0, 0, 0.45f), 1);
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
				
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = false;	
				}
				
				displaySelection(true);
				break;
				
			case PLACING:
				
				displaySelection(false);
				if( this.currentSelectionSprite != null)
				{
					this.currentSelectionSprite.visible = true;	
				}
				
				placeSelection();
				break;
				
		}
	}
	
	private void displaySelection(boolean isVisible)
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.visible = !isVisible;
		
		// Hide all placed map items
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (this.mapDataSprites[r][c] == null) continue;
				this.mapDataSprites[r][c].visible = !isVisible;
			}	
		}
		
		// Show possible selections
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{
				if (this.selectionSprites[r][c] == null) continue;
				this.selectionSprites[r][c].visible = isVisible;
			}	
		}
	}
	
	private void placeSelection()
	{
		if (this.currentSelectionSprite == null) return;
		
		this.currentSelectionSprite.updateScreenPosition(Game.mouseX, Game.mouseY);
		
		if (this.currentSelection != this.lastSelection)
		{
			this.currentSelectionSprite.updateImageRef(getSelectionName(), true, false);
			this.lastSelection = this.currentSelection;
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
		if (this.detailedMenu != null && this.detailedMenu.visible)
		{
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			this.openScene("MainMenu");
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_Q))
		{
			userSwitching();
		}
		
		if (this.isKeyPressed(KeyEvent.VK_M)) 
		{			
			this.detailedMenu.toggleVisible();
		}
	}
	
	private void setMapSpritesVisibility(boolean v)
	{
		for (int r = 0; r < TheHunter.ROWS; r++)
		{
			for (int c = 0; c < TheHunter.COLUMNS; c++)
			{
				this.mapDataSprites[r][c].visible = v;
			}
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
					this.setMapSpritesVisibility(false);
					break;
					
				case SELECTING:
					this.mouseStatus = MOUSE_STATUS.PLACING;
					this.statusLabel.updateText("Placing");
					this.setMapSpritesVisibility(true);
					break;
					
				case PLACING:
					this.mouseStatus = MOUSE_STATUS.IDLE;
					this.statusLabel.updateText("Idle");
					this.setMapSpritesVisibility(true);
					break;
					
			}
			
			// Wait 500 before you can tab again
			this.disableSwitching = true;

			resetInputTask = new SceneTask(this)
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

		if (gPos.x == -1 || gPos.y == -1) return;
		
		switch(this.mouseStatus)
		{
		
			case IDLE:				
				break;
				
			case SELECTING:
				
				if (this.selectionSprites.length > gPos.y && this.selectionSprites[gPos.y].length > gPos.x)
				{
				
					if (this.selectionSprites[gPos.y][gPos.x] != null)
					{		
						int objType = this.selectionSprites[gPos.y][gPos.x].getObjectType();
						this.currentSelection = ObjectTypes.values()[objType];
						String img = ObjectTypes.values()[this.selectionSprites[gPos.y][gPos.x].getObjectType()].imgName;
						this.currentSelectionSprite.updateImageRef(img, false, true);
						this.currentSelectionSprite.setObjectType(objType);
					}
					
				}	
				
				break;
				
			case PLACING:
				
				if (this.currentSelection == ObjectTypes.Player)
				{
					if (!(gPos.x == this.playerCol && gPos.y == this.playerRow))
					{
						this.mapDataSprites[gPos.y][gPos.x].updateImageRef("Player", true, 16, 16);
						this.mapDataSprites[gPos.y][gPos.x].setObjectType(ObjectTypes.Player.intValue);
						
						this.mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, false);
						this.mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
						
						this.playerCol = gPos.x;
						this.playerRow = gPos.y;	
					}
				}
				else
				{
					if (!(gPos.x == this.playerCol && gPos.y == this.playerRow))
					{
						String imgName = this.currentSelectionSprite.getImageName();
						int objType = this.currentSelectionSprite.getObjectType();
						this.mapDataSprites[gPos.y][gPos.x].updateImageRef(imgName, true, 16, 16);
						this.mapDataSprites[gPos.y][gPos.x].setObjectType(objType);
					}
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
	
	public void saveMap(String name)
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
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
		   for(int c = 0; c < TheHunter.COLUMNS; c++)
		   {
			   int oType = this.mapDataGrounds[r][c];
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

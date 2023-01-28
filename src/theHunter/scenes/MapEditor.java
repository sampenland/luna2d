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
import luna2d.renderables.Grid;
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
		mouseStatus = MOUSE_STATUS.IDLE;
		this.currentSelection = ObjectTypes.Player;
		this.lastSelection = ObjectTypes.Empty;
		
		this.setMouseEnabled(true);
		
		this.mouseStatus = MOUSE_STATUS.IDLE;
		
		this.playerRow = 0;
		this.playerCol = 0;
		
		this.mapDataSprites = new Sprite[TheHunter.ROWS][TheHunter.COLUMNS];
		this.mapDataGrounds = new int[TheHunter.ROWS][TheHunter.COLUMNS];
		this.selectionSprites = new Sprite[TheHunter.ROWS][TheHunter.COLUMNS];
		
		MapGrounds grounds = new MapGrounds(this, 0, TheHunter.GRIDY_OFFSET, new Color(1.0f, 1.0f, 0.0f, 0.1f), 1, null);
		grounds.enableCulling = false;
		
		int count = 0;
		int countRow = 0;
		int countColumn = 0;
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{				
				mapDataGrounds[r][c] = ObjectTypes.GndGrass.intValue;
				mapDataSprites[r][c] = new Sprite(this, "", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
				mapDataSprites[r][c].setObjectType(ObjectTypes.Empty.intValue);
				mapDataSprites[r][c].visible = false;
				
				if (count < ObjectTypes.values().length)
				{
					if (ObjectTypes.values()[count].showInMapEditor)
					{
						String imgName = ObjectTypes.values()[count].imgName;
						int objType = ObjectTypes.values()[count].intValue;
						
						if (imgName == "Player")
						{
							this.selectionSprites[countRow][countColumn] = new Sprite(this, imgName, countColumn * 16, TheHunter.GRIDY_OFFSET + countRow * 16, 1, Game.TOP_DRAW_LAYER, 16, 16);
						}
						else
						{
							this.selectionSprites[countRow][countColumn] = new Sprite(this, imgName, countColumn * 16, TheHunter.GRIDY_OFFSET + countRow * 16, 1, Game.TOP_DRAW_LAYER);	
						}
						
						this.selectionSprites[countRow][countColumn].setObjectType(objType);
						this.selectionSprites[countRow][countColumn].visible = false;
						
						countColumn++;
						
						if (countColumn >= TheHunter.COLUMNS)
						{
							countColumn = 0;
							countRow++;
						}
						
					}
					
					count++;
					
				}
			}
		}
		
		grounds.updateFillTypes(mapDataGrounds);
		
		this.setInputEnabled(false);
		
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1, 16, 16, Game.TOP_DRAW_LAYER);
		this.currentSelectionSprite.setFixedScreenPosition(true);
		
		mapDataSprites[this.playerRow][this.playerCol].updateImageRef("Player", true, 16, 16);
		mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Player.intValue);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE, Game.TOP_DRAW_LAYER);
		
		this.detailedMenu = new MapEditorMenu(this, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 100, 300, 200, new Color(0, 0, 0, 0.45f), 1);
	}
	
	public void injectMapData(int[][] data, int[][] grounds)
	{
		mapDataSprites[this.playerRow][this.playerCol].updateImageRef("", false, true);
		mapDataSprites[this.playerRow][this.playerCol].setObjectType(ObjectTypes.Empty.intValue);
		
		for(int r = 0; r < TheHunter.ROWS; r++)
		{
			for(int c = 0; c < TheHunter.COLUMNS; c++)
			{		
				this.mapDataSprites[r][c] = new Sprite(this, "", c * 16, TheHunter.GRIDY_OFFSET + r * 16, 1, 2);
				
				ObjectTypes objectType = ObjectTypes.values()[data[r][c]];
				
				switch(objectType)
				{
				case Empty:
					this.mapDataSprites[r][c].updateImageRef("", true, true);
					break;
					
				case Bush:
					this.mapDataSprites[r][c].updateImageRef("BerryBush", true, true);
					this.mapDataSprites[r][c].setFrameWidth(16);
					this.mapDataSprites[r][c].setFrameCount(1, true);
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
					this.playerRow = r;
					this.playerCol = c;
					this.mapDataSprites[r][c].updateImageRef("Player", true, 16, 16);
					break;
					
				case Tree:
					this.mapDataSprites[r][c].updateImageRef("Tree", true, true);
					break;
					
				case Water:
					this.mapDataSprites[r][c].updateImageRef("Water", true, true);
					break;
					
				case Wolf:
					this.mapDataSprites[r][c].updateImageRef("Wolf", true, true);
					break;
					
				case Fire:
					this.mapDataSprites[r][c].updateImageRef("Fire", true, true);
					this.mapDataSprites[r][c].setFrameWidth(16);
					this.mapDataSprites[r][c].setFrameCount(1, true);
					
				default:
					break;
				}
				
				mapDataSprites[r][c].setObjectType(objectType.intValue);
				mapDataSprites[r][c].visible = true;
			}
		}
		
		this.closeMenu();
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
				
				break;
				
			case PLACING:
				
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
			return "";
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
					this.displaySelection(true);
					break;
					
				case SELECTING:
					this.mouseStatus = MOUSE_STATUS.PLACING;
					this.statusLabel.updateText("Placing");
					this.setMapSpritesVisibility(true);
					this.displaySelection(false);
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
						
						if (objType == ObjectTypes.Player.intValue)
						{
							this.currentSelectionSprite.updateImageRef(img, false, 16, 16);
						}
						else if (objType == ObjectTypes.Bush.intValue)
						{
							this.currentSelectionSprite.updateImageRef(img, false, 16, 16);
						}
						else
						{
							this.currentSelectionSprite.updateImageRef(img, false, true);	
						}
						
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
						if (this.mapDataSprites[gPos.y][gPos.x].visible)
						{
							String imgName = "NONE";
							int objType = ObjectTypes.Empty.intValue;
							this.mapDataSprites[gPos.y][gPos.x].updateImageRef(imgName, true, 16, 16);
							this.mapDataSprites[gPos.y][gPos.x].setObjectType(objType);	
							this.mapDataSprites[gPos.y][gPos.x].visible = false;
						}
						else
						{
							String imgName = this.currentSelectionSprite.getImageName();
							int objType = this.currentSelectionSprite.getObjectType();
							this.mapDataSprites[gPos.y][gPos.x].updateImageRef(imgName, true, 16, 16);
							this.mapDataSprites[gPos.y][gPos.x].setObjectType(objType);	
							this.mapDataSprites[gPos.y][gPos.x].visible = true;
						}
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
		String path = TheHunter.DATA_DIR + "/" + name + ".thm";
		
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
		String path = TheHunter.DATA_DIR + "/" + name + ".thmg";
		
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

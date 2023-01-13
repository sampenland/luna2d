package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;

import luna2d.Game;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.Grid;
import luna2d.renderables.Rect;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTimer;
import theHunter.Ground;
import theHunter.ObjectTypes;

public class MapEditor extends Scene
{

	private final int ROWS = 37;
	private final int COLUMNS = 49;
	
	private enum MOUSE_STATUS
	{
		IDLE,
		SELECTING,
		PLACING
	}
	
	private static final int TYPE_LAYER = 0;
	private static final int SPRITE_LAYER = 1;
	
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
		this.mapDataSprites = new Sprite[ROWS][COLUMNS];
		this.mapDataGrounds = new Ground[ROWS][COLUMNS];
		
		for(int r = 0; r < this.ROWS; r++)
		{
			for(int c = 0; c < this.COLUMNS; c++)
			{				
				mapDataGrounds[r][c] = new Ground(this, c * 16, 5 + r * 16, ObjectTypes.GndGrass);
				mapDataSprites[r][c] = new Sprite(this, "Empty", c * 16, 5 + r * 16, 1.0f);
			}
		}
		
		new Grid(this, 0, 5, ROWS, COLUMNS, 16, new Color(1.0f, 1.0f, 0.0f, 0.5f));
		
		this.setInputEnabled(false);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE);
		createSelectionSprites();
		
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1.0f, 16, 16);
		this.currentSelectionSprite.visible = false;
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
			this.currentSelectionSprite.updateImageRef(getSelectionName());
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
	
}

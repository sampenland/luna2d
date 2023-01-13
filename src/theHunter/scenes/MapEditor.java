package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;

import luna2d.Game;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.Grid;
import luna2d.renderables.Sprite;
import luna2d.renderables.TextDisplay;
import luna2d.timers.SceneTimer;
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
	
	private MOUSE_STATUS mouseStatus;
	private boolean disableSwitching = false;
	private SceneTimer resetInputTask;
	private Timer resetInputTimer;
	
	private ObjectTypes currentSelection;
	private ObjectTypes lastSelection;
	private Sprite currentSelectionSprite;
	
	private HashMap<Integer, int[][]> mapData;
	
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
		new Grid(this, 0, 5, ROWS, COLUMNS, 16, new Color(1.0f, 1.0f, 0.0f, 0.2f));
		this.currentSelectionSprite = new Sprite(this, "Player", 0, 0, 1.0f, 16, 16);
		this.currentSelectionSprite.visible = false;
		
		this.mapData = new HashMap<Integer, int[][]>();
		for(int i = 0; i < 4; i++)
		{
			mapData.put(i, new int[ROWS][COLUMNS]);
		}
		
		this.setInputEnabled(false);
		
		this.statusLabel = new TextDisplay(this, "Idle", Game.WIDTH / 2 - 64, Game.HEIGHT - 64, Color.WHITE);
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

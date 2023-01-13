package theHunter.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;

import luna2d.Scene;
import luna2d.renderables.Grid;
import luna2d.timers.SceneTimer;

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
	private SceneTimer resetInputTask;
	private Timer resetInputTimer;
	
	private HashMap<Integer, int[][]> mapData;
	
	public MapEditor(String name) 
	{
		super(name);
		mouseStatus = MOUSE_STATUS.IDLE;
		resetInputTask = new SceneTimer(this)
		{
			@Override
			public void run() 
			{
				this.scene.setInputEnabled(true);
			}
		};
		resetInputTimer = new Timer("ResetInputTimer");
	}

	@Override
	public void start() 
	{
		new Grid(this, 0, 5, ROWS, COLUMNS, 16, new Color(1.0f, 1.0f, 0.0f, 0.2f));
		
		this.mapData = new HashMap<Integer, int[][]>();
		for(int i = 0; i < 4; i++)
		{
			mapData.put(i, new int[ROWS][COLUMNS]);
		}
		
		this.setInputEnabled(false);
	}

	@Override
	public void end() 
	{
		
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
			return;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_TAB))
		{
			switch(this.mouseStatus)
			{
			
				case IDLE:
					this.mouseStatus = MOUSE_STATUS.SELECTING;
					break;
					
				case SELECTING:
					this.mouseStatus = MOUSE_STATUS.PLACING;
					break;
					
				case PLACING:
					this.mouseStatus = MOUSE_STATUS.IDLE;
					break;
					
			}
			
			// Wait 750 before you can tab again
			this.resetInputTimer.schedule(resetInputTask, 750);
		}
	}
	
}

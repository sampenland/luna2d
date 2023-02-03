package theHunter.objects;

import java.util.Timer;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.WeatherSystem;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import luna2d.timers.SpriteTask;
import theHunter.HunterWorldStruct;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public class GrowingBerryBush extends Sprite
{

	private final static int GROW_SPEED = 2; // in seconds, the duration between frames
	
	private final static int DELAY_MIN = 40000; // Delay variables in seconds
	private final static int DELAY_MAX = 60000; //    this controls how long to wait in milliseconds before growing
	
	private SpriteTask growTask;
	private SpriteTask delayGrowth;
	private Timer growTimer;
	private Timer delayGrowthTimer;
	
	public boolean hasWater;
	
	public GrowingBerryBush(Scene inScene, int x, int y, int scale, int depth, boolean activate) 
	{
		super(inScene, "GrowingBerryBush", x, y, scale, depth, 16, 5, 0);
		
		this.hasWater = false;
		
		Vector2 gPos = Maths.convertToGrid(this.getWorldX(), this.getWorldY(), TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		WorldPosition pWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);

		this.updateWorldPosition(pWP);
		
		if (this.getScene().getWorldData() != null)
		{			
			HunterWorldStruct world = (HunterWorldStruct)this.getScene().getWorldData();
			world.addObjectToWorld(ObjectTypes.GrowingBerryBush, pWP);
		}
		
		if (activate) 
		{
			this.activate();
		}
		else
		{
			this.visible = false;
			delayGrowth = new SpriteTask(this)
			{
				@Override
				public void run()
				{
					GrowingBerryBush bush = (GrowingBerryBush)this.sprite;
					bush.activate();
				}
			};
			
			delayGrowthTimer = new Timer();
			delayGrowthTimer.schedule(delayGrowth, Maths.random(DELAY_MIN, DELAY_MAX));
		}
		
	}	
	
	private void activate()
	{
		this.visible = true;
		
		growTask = new SpriteTask(this)
		{
			@Override
			public void run()
			{
				if (Game.paused) return;
				
				GrowingBerryBush bush = (GrowingBerryBush)this.sprite;
				
				if (!bush.hasWater && !WeatherSystem.isRaining)
				{
					return;
				}
				
				bush.hasWater = false;
				
				int currentFrame = bush.getCurrentFrame();
				if (currentFrame >= bush.getFrameCount())
				{
					// Done growing
					bush.growTimer.cancel();
					
					BerryBush b = new BerryBush(this.sprite.getScene(), this.sprite.getWorldX(), this.sprite.getWorldY(), 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
					b.updateWorldPosition(this.sprite.getWorldPosition());
					
					HunterWorldStruct world = (HunterWorldStruct)this.sprite.getScene().getWorldData();
					world.addObjectToWorld(ObjectTypes.Bush, this.sprite.getWorldPosition());
					
					this.sprite.getScene().addSprite(b);
					
					this.sprite.destroy();
				}
				else
				{
					bush.setFrame(currentFrame + 1, true);	
				}					
			}
		};
		
		growTimer = new Timer("Growing");
		growTimer.scheduleAtFixedRate(growTask, GROW_SPEED * 1000 * this.getFrameCount(), GROW_SPEED * 1000 * this.getFrameCount());
	}

}

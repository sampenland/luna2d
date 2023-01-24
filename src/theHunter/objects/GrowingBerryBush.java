package theHunter.objects;

import java.util.Timer;

import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.timers.SpriteTask;

public class GrowingBerryBush extends Sprite
{

	private final static int GROW_SPEED = 2; // in seconds, the duration between frames
	private SpriteTask growTask;
	private Timer growTimer;
	
	public GrowingBerryBush(Scene inScene, int x, int y, int scale) 
	{
		super(inScene, "GrowingBerryBush", x, y, scale, 16, 5, GROW_SPEED * 1000);
		
		growTask = new SpriteTask(this)
		{
			@Override
			public void run()
			{
				this.sprite.getScene().addSprite(new BerryBush(this.sprite.getScene(), this.sprite.getWorldX(), this.sprite.getWorldY(), 1));
				this.sprite.destroy();
			}
		};
		
		growTimer = new Timer("Growing");
		growTimer.schedule(growTask, GROW_SPEED * 1000 * this.getFrameCount());
		
	}	

}

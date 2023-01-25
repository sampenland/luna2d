package theHunter.objects;

import java.util.Timer;
import luna2d.Scene;
import luna2d.WeatherSystem;
import luna2d.renderables.Sprite;
import luna2d.timers.SpriteTask;

public class GrowingBerryBush extends Sprite
{

	private final static int GROW_SPEED = 2; // in seconds, the duration between frames
	private SpriteTask growTask;
	private Timer growTimer;
	
	public boolean hasWater;
	
	public GrowingBerryBush(Scene inScene, int x, int y, int scale) 
	{
		super(inScene, "GrowingBerryBush", x, y, scale, 16, 5, 0);
		
		this.hasWater = false;
		
		growTask = new SpriteTask(this)
		{
			@Override
			public void run()
			{
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
					this.sprite.getScene().addSprite(new BerryBush(this.sprite.getScene(), this.sprite.getWorldX(), this.sprite.getWorldY(), 1));
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

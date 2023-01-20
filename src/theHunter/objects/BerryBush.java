package theHunter.objects;

import java.awt.Point;
import java.util.Timer;

import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.timers.SpriteTask;
import theHunter.Player;
import theHunter.TheHunter;

public class BerryBush extends Sprite
{
	private boolean hasBerries;
	
	private SpriteTask regrowBerriesTask;
	private Timer regrowBerriesTimer;
	
	public BerryBush(Scene inScene, int x, int y, int scale)
	{
		super(inScene, "BerryBush", x, y, scale, 16, 1000);
		this.hasBerries = true;
		this.setFrame(0);
//		
//		this.regrowBerriesTask = new SpriteTask(this)
//		{
//			@Override
//			public void run()
//			{
//				((BerryBush)this.sprite).hasBerries = true;
//				((BerryBush)this.sprite).setFrame(0);
//			}
//		};
//		this.regrowBerriesTimer = new Timer("RegrowBerries");
	}
	
	public void pickBerries()
	{
		if (!this.hasBerries) return;
		
		this.hasBerries = false;
		this.regrowBerriesTimer.schedule(regrowBerriesTask, 10000);
		this.setFrame(1);
	}
	
	private void mouseClicks()
	{
		if (this.mouseClicked)
		{
			Player player = (Player)this.getScene().getPlayer();
			if (Maths.distanceBetweenPlayerAndPoint(this.getScene().getPlayer(), new Point(this.screenX, this.screenY)) < TheHunter.RESOURCE_ACTION_DISTANCE)
			{
				player.pickup(this, 5);
				this.pickBerries();
			}
		}
	}
	
	@Override
	public void update()
	{
		super.update();	
		mouseClicks();
	}
	
}

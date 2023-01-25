package theHunter.objects;

import java.awt.Point;
import java.util.Timer;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import luna2d.timers.SpriteTask;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;

public class BerryBush extends Sprite
{
	private final int BERRY_REGROW_TIME = 1000;//10000;
	private boolean hasBerries;
	
	private SpriteTask regrowBerriesTask;
	private Timer regrowBerriesTimer;
	
	public BerryBush(Scene inScene, int x, int y, int scale)
	{
		super(inScene, "BerryBush", x, y, scale, 16, 2, 0);
		this.inputEnabled = true;
		this.hasBerries = true;
	}
	
	public void growBerries()
	{
		if (this.hasBerries) return;
		
		this.regrowBerriesTask = new SpriteTask(this)
		{
			@Override
			public void run()
			{
				((BerryBush)this.sprite).hasBerries = true;
				((BerryBush)this.sprite).setFrame(0, false);
			}
		};
		this.regrowBerriesTimer = new Timer("RegrowBerries");
		this.regrowBerriesTimer.schedule(regrowBerriesTask, BERRY_REGROW_TIME);
	}
	
	public boolean pickBerries()
	{
		if (!this.hasBerries) return false;
		
		this.hasBerries = false;
		this.growBerries();
		this.setFrame(1, false);
		
		return true;
	}
	
	private void mouseClicks()
	{
		if (this.mouseClicked && this.hasBerries)
		{
			Player player = (Player)this.getScene().getPlayer();
			if (Maths.distanceBetweenPlayerAndPoint(this.getScene().getPlayer(), new Point(this.worldX, this.worldY)) < TheHunter.RESOURCE_ACTION_DISTANCE)
			{
				player.pickup(ObjectTypes.InvBerries, 2 * InventoryItem.PICKUP_MULTIPLIER);
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

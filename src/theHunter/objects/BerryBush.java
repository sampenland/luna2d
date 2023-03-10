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
	private static final int PICKUP_AMOUNT = 3 * InventoryItem.PICKUP_MULTIPLIER;
	private final int BERRY_REGROW_TIME = 10000;
	private boolean hasBerries;
	
	private SpriteTask regrowBerriesTask;
	private Timer regrowBerriesTimer;
	
	public BerryBush(Scene inScene, int x, int y, int scale, int depth)
	{
		super(inScene, "BerryBush", x, y, scale, depth, 16, 2, 0);
		this.mouseEnabled = true;
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
			int dist = Maths.distanceBetweenPlayerAndPoint(this.getScene().getPlayer(), new Point(this.worldX, this.worldY));
			if (dist  != -1 && dist < TheHunter.RESOURCE_ACTION_DISTANCE)
			{
				player.pickup(ObjectTypes.InvBerries, PICKUP_AMOUNT);
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

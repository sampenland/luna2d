package theHunter.objects;

import java.awt.Point;

import luna2d.Log;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;

public class Rock extends Sprite 
{

	public Rock(Scene inScene, int x, int y, int scale, int depth) 
	{
		super(inScene, "Rock", x, y, scale, depth);
		this.inputEnabled = true;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		checkMouseClicks();
	}
	
	private void playerPickedUp()
	{
		this.destroy();
	}
	
	private void checkMouseClicks()
	{
		if (this.mouseClicked && this.mouseClickEvent != null && this.mouseClickEvent.getButton() == 1)
		{
			Player player = (Player)this.getScene().getPlayer();
			int dist = Maths.distanceBetweenPlayerAndPoint(this.getScene().getPlayer(), new Point(this.worldX, this.worldY));
			if (dist  != -1 && dist < TheHunter.RESOURCE_ACTION_DISTANCE)
			{
				player.pickup(ObjectTypes.InvRock, 1 * InventoryItem.PICKUP_MULTIPLIER);
				this.playerPickedUp();
			}
		}
	}

}

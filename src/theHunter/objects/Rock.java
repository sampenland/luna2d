package theHunter.objects;

import java.awt.Point;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.renderables.Sprite;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.WorldPosition;

public class Rock extends Sprite 
{

	public Rock(Scene inScene, int x, int y, int scale, int depth) 
	{
		super(inScene, "Rock", x, y, scale, depth);
		this.mouseEnabled = true;
		
		Vector2 gPos = Maths.convertToGrid(this.getWorldX(), this.getWorldY(), TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		WorldPosition pWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);

		this.updateWorldPosition(pWP);
		
	}
	
	@Override
	public void update()
	{
		super.update();
		
		checkMouseClicks();
	}
	
	private void playerPickedUp()
	{
		this.getScene().getWorldData().addObjectToWorld(ObjectTypes.Empty, this.getWorldPosition());
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

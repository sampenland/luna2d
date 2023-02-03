package theHunter.inventoryItems;

import luna2d.Game;
import luna2d.Log;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.maps.WorldPosition;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;
import theHunter.TheHunter;
import theHunter.objects.GrowingBerryBush;

public class InvBerries extends InventoryItem
{
	public static final int BERRY_HUNGER_SUBTRACTION = 10;
	
	public InvBerries(Scene inScene, int amount) 
	{
		super(inScene, ObjectTypes.InvBerries, amount);
	}

	@Override
	public void use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		if (flag == 3)
		{
			player.useItem(ObjectTypes.InvBerries);	
			
			if (Maths.random(1, 10) <= 3)
			{
				Vector2 gPos = Maths.convertToGrid(player.getWorldX(), player.getWorldY(), TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
				WorldPosition wp = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);
				
				if (this.getScene().getWorldData().getWorldHandler().isFree(wp))
				{		
					Log.println("Droppings will spawn berrys: ", wp);
					Vector2 xy = Maths.convertWorldPositionToXY(wp, TheHunter.CELL_SIZE, TheHunter.ROWS, TheHunter.COLUMNS, Game.CAMERA_SCALE);
					new GrowingBerryBush(this.getScene(), xy.x, xy.y, 1, TheHunter.ENVIRONMENT_DRAW_LAYER, false);
				}				
			}			
		}
		else
		{
			player.readyHoldItem(ObjectTypes.InvBerries);
		}
	}

	@Override
	public void repair() 
	{
		
	}

	@Override
	public void craft() 
	{
		
	}

}

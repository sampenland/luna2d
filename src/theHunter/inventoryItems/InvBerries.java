package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;

public class InvBerries extends InventoryItem
{
	public static final int BERRY_HUNGER_SUBTRACTION = 10;
	
	public InvBerries(Scene inScene, int amount) 
	{
		super(inScene, ObjectTypes.InvBerries.intValue, amount);
	}

	@Override
	public void use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		if (flag == 3)
		{
			player.useItem(ObjectTypes.InvBerries);			
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

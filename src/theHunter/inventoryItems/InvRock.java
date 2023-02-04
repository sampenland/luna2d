package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;

public class InvRock extends InventoryItem
{

	public InvRock(Scene inScene, int amount) 
	{
		super(inScene, ObjectTypes.InvRock, amount);
	}

	@Override
	public boolean use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvRock);
		
		if (flag == 1)
		{
			return true;
		}
		
		return false;
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

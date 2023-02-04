package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;

public class InvFence extends InventoryItem
{
	public InvFence(Scene inScene, int amount) 
	{
		super(inScene, ObjectTypes.InvFence, amount);
	}

	@Override
	public boolean use(int flag)
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvFence);
		
		return true;
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

package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;

public class InvTorch extends InventoryItem
{

	public InvTorch(Scene inScene) 
	{
		super(inScene, ObjectTypes.InvTorch, 1);
	}

	@Override
	public boolean use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		if (flag == 1)
		{
			player.readyHoldItem(ObjectTypes.InvTorch);			
			return true;
		}
		else
		{
			player.toolbelt.attachToQuickAccess(ObjectTypes.InvTorch);
			return false;
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

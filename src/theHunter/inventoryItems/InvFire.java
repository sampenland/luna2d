package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;
import theHunter.Player;

public class InvFire extends InventoryItem
{

	public InvFire(Scene inScene) 
	{
		super(inScene, ObjectTypes.InvFire, 1);
	}

	@Override
	public boolean use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvFire);
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

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
	public void use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvTorch);
		
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

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
	public void use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvRock);
		
	}

	@Override
	public void repair() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void craft() {
		// TODO Auto-generated method stub
		
	}

}

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
	public void use(int flag) 
	{
		Player player = (Player)this.getScene().getPlayer();
		player.readyHoldItem(ObjectTypes.InvFire);
		
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

package theHunter.inventoryItems;

import luna2d.Scene;
import theHunter.InventoryItem;
import theHunter.ObjectTypes;

public class InvRock extends InventoryItem
{

	public InvRock(Scene inScene, int amount) 
	{
		super(inScene, ObjectTypes.InvRock.intValue, amount);
	}

	@Override
	public void use(int flag) 
	{
		// Flag == 1 for left click, 3 for right click
		//Player player = (Player)this.getScene().getPlayer();
		
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

package theHunter;

import luna2d.Scene;

public abstract class InventoryItem 
{
	public int TYPE;
	public int AMOUNT;
	private Scene inScene;
	
	public InventoryItem(Scene inScene, int type, int amount)
	{
		this.inScene = inScene;
		this.TYPE = type;
		this.AMOUNT = amount;
	}
	
	public Scene getScene()
	{
		return this.inScene;
	}
	
	public abstract void use();
	public abstract void repair();
	public abstract void craft();	
	
}

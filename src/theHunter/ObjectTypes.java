package theHunter;

public enum ObjectTypes 
{	
	Empty(0, "NONE"),
	Player(1, "Player"),
	Tree(2, "Tree"),
	Bush(3, "BerryBush"),
	Water(4, "Water"),
	Wolf(5, "Wolf"),
	GndGrass(6, ""),
	GndDirt(7, ""),
	GndRock(8, ""),
	GndWater(9, "");
	
	public final int intValue;
	public final String imgName;
	private ObjectTypes(int v, String imgName)
	{
		this.intValue = v;
		this.imgName = imgName;
	}
}

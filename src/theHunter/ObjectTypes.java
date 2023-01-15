package theHunter;

public enum ObjectTypes 
{	
	Empty(0),
	Player(1),
	Tree(2),
	Bush(3),
	Water(4),
	Wolf(5),
	GndGrass(6),
	GndDirt(7),
	GndRock(8),
	GndWater(9);
	
	public final int intValue;
	private ObjectTypes(int v)
	{
		this.intValue = v;
	}
}

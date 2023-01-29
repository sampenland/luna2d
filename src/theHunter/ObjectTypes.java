package theHunter;

public enum ObjectTypes 
{	
	Empty(0, "NONE", false),
	Player(1, "Player", true),
	Tree(2, "Tree", true),
	Bush(3, "BerryBush", true),
	Water(4, "Water", true),
	Wolf(5, "Wolf", true),
	GndGrass(6, "", false),
	GndDirt(7, "", false),
	GndRock(8, "", false),
	GndWater(9, "", false),
	InvBerries(10, "INV_Berries", false),
	Rock(11, "Rock", true),
	InvRock(12, "INV_Rock", false),
	ThrownRock(13, "Rock", false),
	Fire(14, "Fire", true),
	FenceVert(15, "Fence", true),
	FenceHorz(16, "Fence", true),
	InvFence(17, "InvFence", false),
	InvFire(18, "InvFire", false);
	
	public final int intValue;
	public final String imgName;
	public final boolean showInMapEditor;
	private ObjectTypes(int v, String imgName, boolean showInMapEditor)
	{
		this.intValue = v;
		this.imgName = imgName;
		this.showInMapEditor = showInMapEditor;
	}
}

package theHunter;

public enum ObjectTypes 
{	
	Empty(0, "NONE", false, false),
	Player(1, "Player", true, false),
	Tree(2, "Tree", true, true),
	Bush(3, "BerryBush", true, false),
	Water(4, "Water", true, false),
	Wolf(5, "Wolf", true, true),
	GndGrass(6, "", false, false),
	GndDirt(7, "", false, false),
	GndRock(8, "", false, false),
	GndWater(9, "", false, false),
	InvBerries(10, "INV_Berries", false, false),
	Rock(11, "Rock", true, false),
	InvRock(12, "INV_Rock", false, false),
	ThrownRock(13, "Rock", false, false),
	Fire(14, "Fire", true, true),
	FenceVert(15, "Fence", true, true),
	FenceHorz(16, "Fence", true, true),
	InvFence(17, "InvFence", false, false),
	InvFire(18, "InvFire", false, false),
	InvTorch(19, "InvTorch", false, false),
	Torch(20, "Torch", true, true),
	GrowingBerryBush(21, "BerryBush", false, false),
	FenceTopRight(22, "Fence", true, true),
	FenceTopLeft(23, "Fence", true, true),
	FenceBottomRight(24, "Fence", true, true),
	FenceBottomLeft(25, "Fence", true, true);
	
	public final int intValue;
	public final String imgName;
	public final boolean showInMapEditor;
	public final boolean solid;
	private ObjectTypes(int v, String imgName, boolean showInMapEditor, boolean solid)
	{
		this.intValue = v;
		this.imgName = imgName;
		this.showInMapEditor = showInMapEditor;
		this.solid = solid;
	}
}

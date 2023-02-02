package theHunter;

public class WorldCell 
{
	
	public ObjectTypes type;
	public ObjectTypes groundType;

	public WorldCell(ObjectTypes type, ObjectTypes groundType)
	{
		this.type = type;
		this.groundType = groundType;
	}	
	
	public boolean isSolid()
	{
		return type.solid;
	}
}

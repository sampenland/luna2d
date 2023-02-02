package luna2d.maps;

import luna2d.Scene;
import luna2d.Vector2;

public abstract class WorldStruct 
{
	protected WorldPosition playerWorldPosition;
	
	protected String worldName;
	protected Scene inScene;
	
	protected boolean active;
	
	public WorldStruct(String worldName, Scene inScene)
	{
		this.inScene = inScene;
	}
	
	public void setActive(boolean val)
	{
		this.active = val;
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public abstract WorldHandler getWorldHandler();
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	public void setPlayerWorldPosition(WorldPosition wp)
	{
		this.playerWorldPosition = wp;
	}
	
	public WorldPosition getPlayerWorldPosition()
	{
		return this.playerWorldPosition;
	}
	
	/*
	 * Returns Vector2 (COLUMN, ROW)    NOT r, c
	 */
	public Vector2 getPlayerOnMapRC()
	{
		return this.playerWorldPosition.getMapPos();
	}
}

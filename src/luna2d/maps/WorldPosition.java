package luna2d.maps;

import luna2d.Log;
import luna2d.Vector2;

public class WorldPosition 
{
	public int worldRow, worldColumn, mapRow, mapColumn;
	
	public static WorldPosition zero()
	{
		return new WorldPosition(0, 0, 0, 0);
	}
	
	public WorldPosition(int worldRow, int worldCol, int mapRow, int mapCol)
	{
		this.worldRow = worldRow;
		this.worldColumn = worldCol;
		this.mapRow = mapRow;
		this.mapColumn = mapCol;
	}
	
	public WorldPosition(Vector2 worldPos, Vector2 mapPos)
	{
		this.worldRow = worldPos.y;
		this.worldColumn = worldPos.x;
		this.mapRow = mapPos.y;
		this.mapColumn = mapPos.x;
	}
	
	public static Vector2 distanceFromWPs(WorldPosition p1, WorldPosition p2)
	{
		int oRow = p1.getWorldPos().y;
		int oCol = p1.getWorldPos().x;
		
		int pRow = p2.getWorldPos().y;
		int pCol = p2.getWorldPos().x;
		
		int rDist = Math.abs(pRow - oRow);
		int cDist = Math.abs(pCol - oCol);
		
		return new Vector2(cDist, rDist);
	}
	
	public static boolean withinDistance(WorldPosition p1, WorldPosition p2, int worldDist, int mapDist)
	{
		WorldPosition wp = new WorldPosition(
				Math.abs(p1.worldRow - p2.worldRow), Math.abs(p1.worldRow - p2.worldRow),
				Math.abs(p1.mapRow - p2.mapRow), Math.abs(p1.mapColumn - p2.mapColumn));
		
		Log.println("Distance: ", wp);
		
		int worldDistX = wp.worldColumn;
		int worldDistY = wp.worldRow;
		int mapDistX = wp.mapColumn;
		int mapDistY = wp.mapRow;
		
		if (worldDistX > worldDist || worldDistY > worldDist) return false;
		if (mapDistX > worldDist || mapDistY > worldDist) return false;
		
		return true;
	}
	
	public Vector2 getMapPos()
	{
		return new Vector2(this.mapColumn, this.mapRow);
	}
	
	public Vector2 getWorldPos()
	{
		return new Vector2(this.worldColumn, this.worldRow);
	}
}

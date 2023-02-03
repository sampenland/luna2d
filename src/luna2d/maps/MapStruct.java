package luna2d.maps;

import luna2d.Scene;

public abstract class MapStruct 
{
	public static int MAP_SCALE = 1;
	protected int[][] mapData;
	protected int[][] mapDataGrounds;
	protected String mapName;
	
	protected WorldPosition worldPosition;
	protected Scene inScene;
	
	protected final int totalRows;
	protected final int totalColumns;
	protected final int cellSize;
	
	public MapStruct(String mapName, Scene inScene, int r, int c, boolean gameLoad, int totalRows, int totalColumns, int cellSize, int mapScale) // r, c is row and col of world
	{
		this.inScene = inScene;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.cellSize = cellSize;
		MAP_SCALE = mapScale;
	}
	
	public Scene getScene()
	{
		return this.inScene;
	}
	
	public String getMapName()
	{
		return this.mapName;
	}
	
	public WorldPosition getWorldPosition()
	{
		return this.worldPosition;
	}
	
	public int[][] getMapData()
	{
		return this.mapData;
	}
	
	public int[][] getMapGroundsData()
	{
		return this.mapDataGrounds;
	}
	
	public void injectData(String mapName, int[][] mapData, int[][] groundData)
	{
		this.mapName = mapName;
		
		this.mapData = new int[this.totalRows][this.totalColumns];
		this.mapData = mapData;
		
		this.mapDataGrounds = new int[this.totalRows][this.totalColumns];
		this.mapDataGrounds = groundData;
		
		this.load();
	}
	
	public abstract void load();
	public abstract void update();
	
}

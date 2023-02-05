package luna2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import luna2d.maps.WorldHandler;
import luna2d.maps.WorldPosition;

public class AStar 
{
	public static int rows;
	public static int columns;
	
	private static Vector2 offset;
	
	public static LinkedList<WorldPosition> getWonderPath(WorldPosition wp, int gridWonderDistance)
	{
		LinkedList<Vector2> path = getWonderPathVects(wp, gridWonderDistance);
		LinkedList<WorldPosition> pathWP = new LinkedList<WorldPosition>();
		
		if (path == null)
			return null;
		
		for(Vector2 v : path)
		{
			pathWP.add(Maths.convertToWorldPosition(v, 1, WorldHandler.ROWS, WorldHandler.COLUMNS));
		}
		
		return pathWP;
		
	}
	public static LinkedList<Vector2> getWonderPathVects(WorldPosition wp, int gridWonderDistance)
	{
		if (wp == null) return null;
		
		Vector2 gp = WorldHandler.getCellPosition(wp);
		char[][] matrix = WorldHandler.getSubCharArray(gp.x, gp.y, gridWonderDistance);
		return getPathWithWonderDistance(matrix, new Vector2(gp.x, gp.y), gridWonderDistance);
	}
	
	
	public static LinkedList<Vector2> getPathFromTo(WorldPosition from, WorldPosition to)
	{
		Vector2 fromV = WorldHandler.getCellPosition(from);
		Vector2 toV = WorldHandler.getCellPosition(to);
		
		// Have to figure out a square part of the massive game array
		// we can take as our matrix chunk
		//
		// Below function will give back max distance (whether X or Y)
		int max = Vector2.compareXYdistanceReturnGreatest(fromV, toV);
		
		int row = fromV.y;
		if (fromV.x - toV.x < 0) row = max;
		
		int col = fromV.x;
		if (fromV.y - toV.y < 0) col = max;
		
		// Use that max to only pathfind within that square chunk of larger array
		char[][] matrix = WorldHandler.getSubCharArray(row, col, max);
		
		offset = new Vector2(col, row);
		return getPath(matrix, fromV, toV);
	}
	
	private static LinkedList<Vector2> getPathWithWonderDistance(char[][] matrix, Vector2 start, int cellWonderDisance)
	{
		int x = start.x;
		int y = start.y;

		int magX = Maths.random(1, cellWonderDisance);
		int magY = Maths.random(1, cellWonderDisance);
		
		if (Maths.coinFlip())
		{
			magX *= -1;
		}
		
		if (Maths.coinFlip())
		{
			magY *= -1;
		}

		x += magX;
		y += magY;
		
		Vector2 end = new Vector2(x, y);
		
		for (int i = 0; i < 3; i++)
		{
			offset = new Vector2(x - magX, y - magY);
			LinkedList<Vector2> p = getPath(matrix, start, end);
			if (p == null) continue;
			return p;
		}
		
		return null;
	}
	
	
	/*
	 *  Returns list of Vector2 steps of path or NULL if not path
	 */
	private static LinkedList<Vector2> getPath(char[][] matrix, Vector2 start, Vector2 end) 
	{

		LinkedList<Vector2> path = new LinkedList<Vector2>();
		
		Node source = new Node(start.x, start.y, 0);
		Queue<Node> queue = new LinkedList<Node>();

		if (matrix == null || matrix.length == 0) return null;
		
		int numOfRows = matrix.length;
		int numOfColumns = matrix[0].length;

		queue.add(source);

		while(!queue.isEmpty()) 
		{
			Node poped = queue.poll();

			if (poped.x == end.x && poped.y == end.y) 
			{
				return path;
			}
			else 
			{
				matrix[poped.x - offset.x][poped.y - offset.y] = 'S';

				path.add(new Vector2(poped.x, poped.y));
				
				List<Node> neighbourList = addNeighbours(poped, matrix, numOfRows, numOfColumns);
				queue.addAll(neighbourList);
			}
		}
		
		return null;
	}
	
	private static List<Node> addNeighbours(Node poped, char[][] matrix, final int numOfRows, final int numOfColumns) 
	{

		List<Node> list = new LinkedList<Node>();

		poped.x -= offset.x;
		poped.y -= offset.y;
		
		if((poped.x-1 >= 0 && poped.x-1 < numOfRows) && matrix[poped.x-1][poped.y] != 'S') 
		{
			list.add(new Node(poped.x-1, poped.y, poped.distanceFromSource+1));
		}
		
		if((poped.x+1 >= 0 && poped.x+1 < numOfRows) && matrix[poped.x+1][poped.y] != 'S') 
		{
			list.add(new Node(poped.x+1, poped.y, poped.distanceFromSource+1));
		}
		
		if((poped.y-1 >= 0 && poped.y-1 < numOfColumns) && matrix[poped.x][poped.y-1] != 'S') 
		{
			list.add(new Node(poped.x, poped.y-1, poped.distanceFromSource+1));
		}
		
		if((poped.y+1 >= 0 && poped.y+1 < numOfColumns) && matrix[poped.x][poped.y+1] != 'S') 
		{
			list.add(new Node(poped.x, poped.y+1, poped.distanceFromSource+1));
		}
		
		return list;
	}
	
}

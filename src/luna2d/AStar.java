package luna2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AStar 
{
	/*
	 *  Returns list of Vector2 steps of path or NULL if not path
	 */
	public static LinkedList<Vector2> getPath(char[][] matrix, Vector2 start, Vector2 end) 
	{

		LinkedList<Vector2> path = new LinkedList<Vector2>();
		
		Node source = new Node(start.x, start.y, 0);
		Queue<Node> queue = new LinkedList<Node>();

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
				matrix[poped.x][poped.y] = 'S';

				List<Node> neighbourList = addNeighbours(poped, matrix, numOfRows, numOfColumns);
				queue.addAll(neighbourList);
			}
		}
		
		return null;
	}
	
	private static List<Node> addNeighbours(Node poped, char[][] matrix, final int numOfRows, final int numOfColumns) {

		List<Node> list = new LinkedList<Node>();

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

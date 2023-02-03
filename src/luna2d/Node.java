package luna2d;

class Node
{
	int x;
	int y;
	int distanceFromSource;

	Node(int x, int y, int dis) 
	{
		this.x = x;
		this.y = y;
		this.distanceFromSource = dis;
	}
}
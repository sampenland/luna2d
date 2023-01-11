package luna2d;

import java.awt.Graphics;
import java.util.LinkedList;

public class ObjectHandler 
{

	private static LinkedList<GameObject> objects;
	
	public ObjectHandler()
	{
		objects = new LinkedList<GameObject>();
	}
	
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	
	public void addObject(GameObject o)
	{
		objects.add(o);
	}
	
	public void removeObject(GameObject o)
	{
		objects.remove(o);
	}
	
	public void renderAllObjects(Graphics g)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			temp.render(g);
		}
	}
	
	public void updateAllObjects()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			temp.update();
		}
	}
	
}

package luna2d;

import java.awt.Graphics;
import java.util.LinkedList;

import luna2d.renderables.Renderable;

public class ObjectHandler 
{

	private static LinkedList<GameObject> objects;
	private static LinkedList<Renderable> renderables;
	
	public ObjectHandler()
	{
		objects = new LinkedList<GameObject>();
		renderables = new LinkedList<Renderable>();
	}
	
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	
	public LinkedList<Renderable> getRenderables()
	{
		return renderables;
	}
	
	public void addObject(GameObject o)
	{
		objects.add(o);
	}
	
	public void removeObject(GameObject o)
	{
		objects.remove(o);
	}
	
	public void addRenderable(Renderable r)
	{
		renderables.add(r);
	}
	
	public void removeRenderable(Renderable r)
	{
		renderables.remove(r);
	}
	
	public void renderAllObjects(Graphics g)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			temp.render(g);
		}
	}
	
	public void renderAllRenderables(Graphics g)
	{
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
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
	
	public void updateAllRenderables()
	{
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
			temp.update();
		}
	}
	
}

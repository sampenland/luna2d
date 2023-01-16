package luna2d;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import luna2d.renderables.Renderable;

public class ObjectHandler 
{	
	private static LinkedList<GameObject> objects;
	private static LinkedList<Renderable> renderables;
	
	private Rectangle screenBounds = new Rectangle(-16, -16, Game.WIDTH, Game.HEIGHT);
	
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
			if (!screenBounds.contains(new Point(temp.screenX, temp.screenY))) continue;
			temp.render(g);
		}
	}
	
	public void renderAllRenderables(Graphics g)
	{
		int j = 0;
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
			if (!screenBounds.contains(new Point(temp.screenX, temp.screenY))) continue;
			j++;
			temp.render(g);
		}
		Log.println("Objects displayed: " + j);
	}
	
	public void updateAllObjects()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			temp.gameUpdate();
		}
	}
	
	public void updateAllRenderables()
	{
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
			temp.gameUpdate();
		}
	}
	
	public void onMouseClick(MouseEvent e)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			if (temp.inputEnabled)
			{				
				temp.onMouseClick(e);
			}
		}
	}
	
	public void onMousePressed(MouseEvent e)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			if (temp.inputEnabled)
			{				
				temp.onMousePressed(e);
			}
		}
	}
	
	public void onMouseReleased(MouseEvent e)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			if (temp.inputEnabled)
			{				
				temp.onMouseReleased(e);
			}
		}
	}
}

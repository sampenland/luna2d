package luna2d;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import luna2d.lights.Light;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.Renderable;
import luna2d.ui.UI;

public class ObjectHandler 
{	
	private static LinkedList<GameObject> objects;
	private static LinkedList<Renderable> renderables;
	private static LinkedList<UI> uis;
	private static LinkedList<Light> lights;
		
	public ObjectHandler()
	{
		objects = new LinkedList<GameObject>();
		renderables = new LinkedList<Renderable>();
		uis = new LinkedList<UI>();
		lights = new LinkedList<Light>();
	}
	
	public LinkedList<Light> getLights()
	{
		return lights;
	}
	
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	
	public LinkedList<Renderable> getRenderables()
	{
		return renderables;
	}
	
	public LinkedList<UI> getUIs()
	{
		return uis;
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
	
	public void addLight(Light l)
	{
		lights.add(l);
	}
	
	public void removeLight(Light l)
	{
		lights.remove(l);
	}
	
	public void addUI(UI u)
	{
		uis.add(u);
	}
	
	public void removeUI(UI u)
	{
		uis.remove(u);
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
		int count = 0;
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
			
			// Culling			
			if (temp.enableCulling && !Game.getScreenBounds().contains(new Point(temp.screenX, temp.screenY)))
			{
				continue;
			}
			
			count++;
			temp.render(g);
		}
		Log.println("Renderables: " + count + "    FPS: " + Game.FPS);
	}
	
	public void renderAllUIs(Graphics g)
	{
		for(int i = 0; i < uis.size(); i++)
		{
			UI temp = uis.get(i);
			temp.render(g);
		}
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
		LinkedList<Renderable> removes = new LinkedList<Renderable>();
		
		for(int i = 0; i < renderables.size(); i++)
		{
			Renderable temp = renderables.get(i);
			if (temp.getDestroyNow()) 
			{
				removes.add(temp);
				continue;
			}
			
			temp.gameUpdate();
		}
		
		for(Renderable remove : removes)
		{
			if (remove.inMenu != null)
			{
				if (remove instanceof FadingTextDisplay)
				{
					remove.inMenu.removeTextDisplay((FadingTextDisplay)remove);
				}
			}
		}
	}
	
	public void updateAllUIs()
	{
		LinkedList<UI> removes = new LinkedList<UI>();
		
		for(int i = 0; i < uis.size(); i++)
		{
			UI temp = uis.get(i);
			if (temp.getDestroyNow())
			{
				removes.add(temp);
				continue;
			}
			
			temp.gameUpdate();
		}
		
		for(UI remove : removes)
		{
			if (remove != null)
			{
				remove.clean();
				uis.remove(remove);
			}
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
		
		for(int i = 0; i < uis.size(); i++)
		{
			UI temp = uis.get(i);
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
		
		for(int i = 0; i < uis.size(); i++)
		{
			UI temp = uis.get(i);
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
		
		for(int i = 0; i < uis.size(); i++)
		{
			UI temp = uis.get(i);
			if (temp.inputEnabled)
			{				
				temp.onMouseReleased(e);
			}
		}
	}
}

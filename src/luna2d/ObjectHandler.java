package luna2d;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import luna2d.lights.Light;
import luna2d.renderables.FadingTextDisplay;
import luna2d.renderables.Renderable;
import luna2d.renderables.Sprite;
import luna2d.ui.UI;

public class ObjectHandler 
{	
	private static LinkedList<GameObject> objects;
	private static List<LinkedList<Renderable>> renderables;
	private static LinkedList<UI> uis;
	private static LinkedList<Light> lights;
		
	public ObjectHandler()
	{
		objects = new LinkedList<GameObject>();
		
		renderables = new ArrayList<LinkedList<Renderable>>();
		for (int i = 0; i < Game.DRAW_LAYERS; i++)
		{
			renderables.add(new LinkedList<Renderable>());
		}
		
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
	
	public List<LinkedList<Renderable>> getRenderables()
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
		renderables.get(r.getDepth()).add(r);
	}
	
	public void removeRenderable(Renderable r)
	{
		renderables.get(r.getDepth()).remove(r);
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
	
	public void clearAllRenderables()
	{
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			renderables.get(layer).clear();
		}
	}
	
	public void renderAllRenderables(Graphics g)
	{
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			LinkedList<Renderable> renderLayer = renderables.get(layer);
		
			for(int i = 0; i < renderLayer.size(); i++)
			{
				Renderable temp = renderLayer.get(i);
				
				// Culling			
				if (temp.enableCulling)
				{
					if (temp instanceof Sprite)
					{
						temp = (Sprite)temp;
						if(!Game.getScreenBounds().contains(new Point(temp.worldX, temp.worldY)))
						{
							continue;
						}
					}
				}

				temp.render(g);
			}
			
		}
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
		LinkedList<GameObject> removes = new LinkedList<GameObject>();

		for(int i = 0; i < objects.size(); i++)
		{
			GameObject temp = objects.get(i);
			if (temp.getDestroyNow()) 
			{
				removes.add(temp);
				continue;
			}
			
			if (Game.paused)
			{
				temp.pauseTick();
			}
			else
			{				
				temp.gameUpdate();
			}
		}
		
		for(GameObject remove : removes)
		{
			objects.remove(remove);
		}
	}
	
	public void updateAllRenderables()
	{
		LinkedList<Renderable> removes = new LinkedList<Renderable>();
		
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			LinkedList<Renderable> renderLayer = renderables.get(layer);
		
			for(int i = 0; i < renderLayer.size(); i++)
			{
				Renderable temp = renderLayer.get(i);			
				if (temp.getDestroyNow()) 
				{
					removes.add(temp);
					continue;
				}
				
				temp.gameUpdate();
			}
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
			else
			{
				renderables.get(remove.getDepth()).remove(remove);
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
		
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			LinkedList<Renderable> renderLayer = renderables.get(layer);
		
			for(int i = 0; i < renderLayer.size(); i++)
			{
				Renderable temp = renderLayer.get(i);
				if (temp.inputEnabled)
				{				
					temp.onMouseClick(e);
				}
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
		
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			LinkedList<Renderable> renderLayer = renderables.get(layer);
		
			for(int i = 0; i < renderLayer.size(); i++)
			{
				Renderable temp = renderLayer.get(i);
				if (temp.inputEnabled)
				{				
					temp.onMousePressed(e);
				}
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
		
		for (int layer = 0; layer < Game.DRAW_LAYERS; layer++)
		{
			LinkedList<Renderable> renderLayer = renderables.get(layer);
		
			for(int i = 0; i < renderLayer.size(); i++)
			{
				Renderable temp = renderLayer.get(i);
				if (temp.inputEnabled)
				{				
					temp.onMouseReleased(e);
				}
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

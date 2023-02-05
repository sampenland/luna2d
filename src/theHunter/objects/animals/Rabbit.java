package theHunter.objects.animals;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.AStar;
import luna2d.Log;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.maps.WorldHandler;
import theHunter.ObjectTypes;

public class Rabbit extends Animal
{
	private static final int WONDER_DISTANCE = 10;
	
	public Rabbit(Scene inScene, int worldX, int worldY) 
	{
		super(inScene, worldX, worldY, ObjectTypes.Rabbit, "Rabbit");
		
		this.init(ANIMAL_STATES.Wondering, 4000, 0, 2500);
	}
	
	@Override
	protected void onWonderLooking() 
	{
		this.path = AStar.getWonderPath(this.getWorldPosition(), WONDER_DISTANCE);
		this.pathIndex = 0;
		Log.println("Rabbit wonder");
		Log.println(this.path);
	}

	@Override
	protected void onChaseUpdate()
	{
	}

	@Override
	protected void onFleeUpdate() 
	{
	}
	
	@Override
	protected void wonder() 
	{
		if (this.path == null || this.path.isEmpty()) return;
		
		if (this.getWorldPosition() == this.path.get(this.pathIndex))
		{
			return;
		}
		
		this.pathIndex++;
	}

	@Override
	protected void chase() 
	{
	}

	@Override
	protected void flee() 
	{
	}
	
	@Override
	protected void render(Graphics g) 
	{
	}

	@Override
	protected void onUpdate() 
	{
	}

	@Override
	protected void pauseTick()
	{
	}

	@Override
	public void onDestroy() 
	{
	}
	
	@Override
	protected void onMouseClick(MouseEvent e)
	{
	}

	@Override
	protected void onMousePressed(MouseEvent e)
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e)
	{
	}	
}

package theHunter.objects.animals;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import luna2d.Scene;
import theHunter.ObjectTypes;

public class Rabbit extends Animal
{
	
	public Rabbit(Scene inScene, int worldX, int worldY) 
	{
		super(inScene, worldX, worldY, ObjectTypes.Rabbit, "Rabbit");
	}
	
	@Override
	protected void render(Graphics g) 
	{

	}

	@Override
	protected void update() 
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

	@Override
	protected void pauseTick()
	{
	}

	@Override
	public void onDestroy() 
	{
	}

}

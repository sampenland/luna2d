package theHunter.objects.animals;

import java.awt.event.MouseEvent;
import luna2d.GameObject;
import luna2d.Scene;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public abstract class Animal extends GameObject
{
	protected float hunger, temperature, anger;
	protected int age;
	
	protected Sprite sprite;
	
	public Animal(Scene inScene, int worldX, int worldY, ObjectTypes type, String spriteImage) 
	{
		super(worldX, worldY, type.intValue, false, inScene);
		
		this.sprite = new Sprite(inScene, spriteImage, worldX, worldY, 1, TheHunter.ANIMAL_DRAW_LAYER);
		
	}
	
	@Override
	public void updateWorldPosition(WorldPosition wp)
	{
		super.updateWorldPosition(wp);
		this.sprite.updateWorldPosition(wp);
	}

	protected abstract void onMouseClick(MouseEvent e);
	protected abstract void onMousePressed(MouseEvent e);
	protected abstract void onMouseReleased(MouseEvent e);
	protected abstract void pauseTick();
	public abstract void onDestroy();

}

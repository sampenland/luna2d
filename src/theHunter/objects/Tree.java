package theHunter.objects;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.GameObject;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import theHunter.ObjectTypes;

public class Tree extends GameObject
{

	Sprite sprite;
	
	public Tree(int x, int y, Scene inScene) 
	{
		super(x, y, ObjectTypes.Tree.intValue, false, inScene);
		sprite = new Sprite(inScene, "Tree", x, y, 1.0f);
	}

	@Override
	protected void render(Graphics g) 
	{
		
	}

	@Override
	protected void update() 
	{
		sprite.updatePosition(this.x, this.y);
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

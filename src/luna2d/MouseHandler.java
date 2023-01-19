package luna2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener
{

	private Game game;
	private ObjectHandler objHandler;
	private SceneManager sceneManager;
	
	public boolean mouseClicked;
	
	public MouseHandler(Game g)
	{
		this.game = g;
		if (this.game == null || this.game.getObjectHandler() == null || this.game.getSceneManager() == null) 
		{
			Log.println("Could not initialize mouse handler");
			return;
		}
		
		this.objHandler = this.game.getObjectHandler();
		this.sceneManager = this.game.getSceneManager();
		this.mouseClicked = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		this.objHandler.onMouseClick(e);
		this.sceneManager.onMouseClick(e);
		this.mouseClicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		this.objHandler.onMousePressed(e);
		this.sceneManager.onMousePressed(e);
		this.mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		this.objHandler.onMouseReleased(e);
		this.sceneManager.onMouseReleased(e);
		this.mouseClicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

}

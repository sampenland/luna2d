package luna2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener
{

	private Game game;
	private ObjectHandler objHandler;
	private SceneManager sceneManager;
	
	public MouseHandler(Game g)
	{
		this.game = g;
		if (this.game == null || this.game.getObjectHandler() == null || this.game.getSceneManager() == null) return;
		this.objHandler = this.game.getObjectHandler();
		this.sceneManager = this.game.getSceneManager();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		this.objHandler.onMouseClick(e);
		this.sceneManager.onMouseClick(e);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		this.objHandler.onMousePressed(e);
		this.sceneManager.onMousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		this.objHandler.onMouseReleased(e);
		this.sceneManager.onMouseReleased(e);
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

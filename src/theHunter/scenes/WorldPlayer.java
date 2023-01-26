package theHunter.scenes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.Log;
import luna2d.Scene;

public class WorldPlayer extends Scene
{

	
	/*
	 * 
	 * 
	 *    THIS CLASS WILL BE THE WORLD RENDER/PLAYER MAIN STORY world engine
	 * 
	 * 
	 * 
	 */
	
	
	
	public WorldPlayer(String name) 
	{
		super(name);
	}

	@Override
	public void start() 
	{
		Log.println("World Player started.");		
	}

	@Override
	public void end() 
	{
		Log.println("World Player ended.");	
	}

	@Override
	public void update() 
	{
		this.checkKeys();
	}

	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_ESCAPE)) 
		{
			this.openScene("MainMenu");
		}
	}
	
	@Override
	protected void onMouseClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

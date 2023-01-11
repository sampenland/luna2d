package luna2d;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter 
{
	private ObjectHandler objHandler;
	
	public InputHandler(ObjectHandler objHandler)
	{
		this.objHandler = objHandler;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for (int i = 0; i < objHandler.getObjects().size(); i++) 
		{
			GameObject temp = objHandler.getObjects().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyPressed(key);
		}
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for (int i = 0; i < objHandler.getObjects().size(); i++) 
		{
			GameObject temp = objHandler.getObjects().get(i);
			if(temp.inputEnabled == false) continue;
			
			temp.keyReleased(key);
		}
	}

}

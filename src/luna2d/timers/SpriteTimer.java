package luna2d.timers;

import java.util.TimerTask;

import luna2d.renderables.Sprite;

public class SpriteTimer extends TimerTask
{
	protected Sprite sprite;
	
	public SpriteTimer(Sprite scene)
	{
		this.sprite = scene;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
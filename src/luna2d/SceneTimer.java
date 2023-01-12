package luna2d;

import java.util.TimerTask;

public class SceneTimer extends TimerTask
{
	protected Scene scene;
	
	public SceneTimer(Scene scene)
	{
		this.scene = scene;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
package luna2d.timers;

import java.util.TimerTask;

import theHunter.objects.animals.Animal;

public class AnimalTask extends TimerTask
{
	protected Animal animal;
	
	public AnimalTask(Animal animal)
	{
		this.animal = animal;
	}

	@Override
	public void run() 
	{ 
		// Override this	
	}
}
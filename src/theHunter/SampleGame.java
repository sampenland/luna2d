package theHunter;

import java.awt.Color;

import luna2d.Game;
import theHunter.scenes.MainMenu;
import theHunter.scenes.MapPlayer;

public class SampleGame extends Game 
{

	private static final long serialVersionUID = -8234717309381689045L;

	private static final int WIDTH = 800, HEIGHT = 640;
		
	public static void main(String[] args)
	{
		Game g = new Game();
		g.init(WIDTH, HEIGHT, "The Hunter", Color.black);
		
		MainMenu menu = new MainMenu("MainMenu");		
		g.beginSceneEngine(menu);
		
		MapPlayer mapPlayer = new MapPlayer("MapPlayer");
		g.sceneManager.addScene(mapPlayer);
		
	}	
}

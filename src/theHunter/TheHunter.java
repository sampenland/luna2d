package theHunter;

import java.awt.Color;

import luna2d.Game;
import theHunter.scenes.MainMenu;
import theHunter.scenes.MapEditor;
import theHunter.scenes.MapPlayer;

public class TheHunter extends Game 
{

	private static final long serialVersionUID = -8234717309381689045L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
		
	public static void main(String[] args)
	{
		Game g = new Game();
		g.init(WIDTH, HEIGHT, "The Hunter", Color.black, "src/theHunter/res/");
		
		MainMenu menu = new MainMenu("MainMenu");		
		g.sceneManager.addScene(menu);
		
		MapPlayer mapPlayer = new MapPlayer("MapPlayer");
		g.sceneManager.addScene(mapPlayer);
		
		MapEditor mapEditor = new MapEditor("MapEditor");
		g.sceneManager.addScene(mapEditor);
		
		g.beginSceneEngine("MainMenu");
		
	}	
}

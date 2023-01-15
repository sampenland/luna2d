package luna2d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceHandler 
{

	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static BufferedImage addImage(String name, String path)
	{
		if (name == "")
		{
			name = "NONE";
			images.put(name, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
			return images.get(name);
		}
		
		path = Game.resDir + path;
		
		BufferedImage img = null;
		try 
		{
		    img = ImageIO.read(new File(path));
		} 
		catch (IOException e) 
		{
			Log.println("Load err: " + e.toString());
			return null;
		}
		
		if (img != null)
		{
			images.put(name, img);
		}
		
		return images.get(name);
		
	}
	
	public static BufferedImage getImage(String name)
	{
		if(name == "") name = "NONE";
		return images.get(name);
	}
	
}

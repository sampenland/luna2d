package luna2d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilites
{

	public static boolean createDirectory(String fullpath)
	{
		try 
		{
		
			Path path = Paths.get(fullpath);
			
			//java.nio.file.Files;
			Files.createDirectories(path);
			
			return true;
		
		} 
		catch (IOException e) 
		{
		
		    System.err.println("Failed to create directory: " + e.getMessage());
		    return false;		
		}
	}
	
}

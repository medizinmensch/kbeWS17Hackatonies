package htw.ii.kbe.hackatonies.runMeRunner;


import java.lang.annotation.*;
import java.util.Properties;
/**
 * Hello world!
 *
 */
public class App 
{
	@Target({ElementType.METHOD})
    public @interface RunMe
    {
        String input();
    }
	
	public static void main(String[] args) {
		String configPath = args[0];
		
		Properties props = ReadConfigFile.GetProperties(configPath);
		
		try {
			Class<?> c = Class.forName(props.getProperty("classWithRunMeAnnos"));
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	
}

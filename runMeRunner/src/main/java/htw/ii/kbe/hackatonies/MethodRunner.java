package htw.ii.kbe.hackatonies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

public class MethodRunner {

	public static void main(String[] args) {
		
		if(args == null || args.length == 0) {
			System.out.println("No Filepath given.\n");
			System.exit(1);
		}
		String configPath = args[0];
		
		Properties myProperties = null;
		try {
			myProperties = readConfigFile.readIn(configPath);
		} catch(IOException e) {
			System.out.println(".." + e);
			System.exit(1);
		}
		
		String PropToLookFor = "classWithRunMeAnnos";
		String className = null;
		try {
			className = myProperties.getProperty(PropToLookFor);
			if ( className.equals(null)|| className == "") {
				System.out.println("could not find...");
				System.exit(1);
			}
		} catch (NullPointerException e) {
			System.out.println("Cannot look for empty properties.\n" + e);
			System.exit(1); 
		}

		Class<?> myClass;

		try {
			myClass = Class.forName(className);
			for (Method method : myClass.getDeclaredMethods()) {
				runMeMethods.RunMe mXY = method.getAnnotation(runMeMethods.RunMe.class);
				try {
					System.out.println(method.getName());
				} catch (Exception e) {
					System.out.println("No Method found with Annotation: <" + method.getName() + ">.");
				}

				/*
				 * another way for (Annotation anno: method.getDeclaredAnnotations()) { if (anno
				 * instanceof runMeMethods.RunMe){ }
				 */
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Property file enthält keinen Klassennamen." + e);
		} catch (NullPointerException e) {
			System.out.println("Property File enthält nicht die richtige Property.\n" + e);
		}
	}
}
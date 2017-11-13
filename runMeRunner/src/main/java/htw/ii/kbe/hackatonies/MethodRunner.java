package htw.ii.kbe.hackatonies;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class MethodRunner {

	public static void main(String[] args) {
		
		if(args == null || args.length == 0) {
			System.out.println("No Filepath given.");
			System.out.println("System shutdown.\n");
			System.exit(1);
		}
		String configPath = args[0];
		
		Properties myProperties = null;
		try {
			myProperties = ReadConfigFile.readIn(configPath);
		} catch(IOException e) {
			System.out.println(".." + e);
			System.exit(1);
		}
		
		String PropToLookFor = "classWithRunMeAnnos";
		String className = myProperties.getProperty(PropToLookFor);

		if (className == null || className.equals("")) {
			System.out.println("Could not find right class.");
			System.out.println("System shutdown.\n");
			System.exit(1);
		}

		Class<?> myClass;

		try {
			//RunMeMethods myRunner = RunMeMethods.create();
			myClass = Class.forName(className);
			Object myRunner = myClass.newInstance();

			for (Method method : myClass.getDeclaredMethods()) {
				RunMeAnnotation.RunMe mXY = method.getAnnotation(RunMeAnnotation.RunMe.class);

				// if method has annotation it gets printed out (and should be invoked...)
				if (mXY != null) {
					try {
						//System.out.print(method.getName() + ": ");
						//first parameter is instance of class, second is normal parameter for method, got out of annotation
						method.invoke(myRunner, mXY.input());
					} catch (Exception e) {
						System.out.println("No Method found with Annotation: <" + method.getName() + ">.");
					}
				}


				/*
				 * another way for (Annotation anno: method.getDeclaredAnnotations()) { if (anno
				 * instanceof RunMeMethods.RunMeAnnotation){ }
				 */
			}
		} catch (InstantiationException e) {
			System.out.println("." + e);
		} catch (ClassNotFoundException e) {
			System.out.println("." + e);
		} catch (IllegalAccessException e) {
			System.out.println("." + e);
		}
	}
}

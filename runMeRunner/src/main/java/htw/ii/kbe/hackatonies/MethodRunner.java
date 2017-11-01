package htw.ii.kbe.hackatonies;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

public class MethodRunner {

    public static void main( String[] args ) {
        //TODO: check if argument exists, else do ???
        String configPath = args[0];
        Properties myProperties = readConfigFile.readIn(configPath);


        String className = myProperties.getProperty("classWithRunMeAnnos");
        Class<?> myClass;

        try {
            myClass = Class.forName(className);
            for (Method method : myClass.getDeclaredMethods()) {
                runMeMethods.RunMe mXY = method.getAnnotation(runMeMethods.RunMe.class);
                if (mXY != null)
                    System.out.println(method.getName());
                /* another way
                for (Annotation anno: method.getDeclaredAnnotations()) {
                    if (anno instanceof runMeMethods.RunMe){
                    }
                */
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Property file enthält keinen Klassennamen." + e);
        } catch (NullPointerException e) {
            System.out.println("Property File enthält nicht die richtige Property.\n" + e);
        }
    }
}

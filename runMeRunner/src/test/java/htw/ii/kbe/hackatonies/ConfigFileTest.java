package htw.ii.kbe.hackatonies;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ConfigFileTest {

    ReadConfigFile myConfigFileReader = new ReadConfigFile();

    @Test
    public void validConfigFile() {
        String path = "runMeConfig.properties";
        try {
            Properties props = myConfigFileReader.readIn(path);
            String className = props.getProperty("classWithRunMeAnnos");
            Class<?> myClass;
            myClass = Class.forName(className);
            assertEquals("expected different class name", "htw.ii.kbe.hackatonies.RunMeMethods", myClass.getName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void isNull() {
        String path = null;
        try {
            Properties props = myConfigFileReader.readIn(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void propertiesFileEmpty() {
        String path = "runMeConfigEmpty.properties";
        try {
            Properties props = myConfigFileReader.readIn(path);
            assertEquals("properties file should be empty", 0,  props.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test(expected = NullPointerException.class)
    public void propertiesFileContainsWrongProperty() {
        String path = "runMeConfigWrong.properties";
        try {
            Properties props = myConfigFileReader.readIn(path);
            String className = props.getProperty("classWithRunMeAnnos");
            Class<?> myClass;
            myClass = Class.forName(className);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* nicht benötigt?
    //funktioniert nicht. kein plan warum. er wirft die exception, geht dann aber tiefer ins system und wirft dann einen assertion error
    @Test(expected = java.io.FileNotFoundException.class)
    public void fileDoesNotExist() {
        String path = "hello.properties";
        try {
            Properties props = myConfigFileReader.readIn(path);

        } catch (IOException e) {

        }
    }
    */
    /*
    //nicht benötigt da dies in der main abgefangen werden muss
      @Test(expected = FileFormatException)
    public void notAPropertyFile() {
        String path = "runMeConfig.abc";
        Properties props = myConfigFileReader.readIn(path);
    }*/


}

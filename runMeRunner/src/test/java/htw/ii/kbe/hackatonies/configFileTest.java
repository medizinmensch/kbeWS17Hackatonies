package htw.ii.kbe.hackatonies;

import org.junit.Test;
import java.io.*;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class configFileTest {

    readConfigFile myConfigFileReader = new readConfigFile();

    @Test(expected = NullPointerException.class)
    public void isNull() {
        String path = null;
        Properties props = myConfigFileReader.readIn(path);
    }

    //funktioniert nicht. kein plan warum. er wirft die exception, geht dann aber tiefer ins system und wirft dann einen assertion error
    @Test(expected = FileNotFoundException.class)
    public void fileDoesNotExist() {
        String path = "hello.properties";
        Properties props = myConfigFileReader.readIn(path);
    }

    @Test
    public void propertiesFileEmpty() {
        String path = "runMeConfigEmpty.properties";
        Properties props = myConfigFileReader.readIn(path);
        assertEquals("properties file should be empty", 0,  props.size());
    }

    @Test(expected = NullPointerException.class)
    public void propertiesFileContainsWrongProperty() {
        String path = "runMeConfigWrong.properties";
        Properties props = myConfigFileReader.readIn(path);
        String className = props.getProperty("classWithRunMeAnnos");
        Class<?> myClass;
        try {
            myClass = Class.forName(className);
        } catch (ClassNotFoundException e) {

        }
    }


    //TODO: richtige exception einfügen
    @Test(expected = FileFormatException)
    public void notAPropertyFile() {
        String path = "runMeConfig.abc";
        Properties props = myConfigFileReader.readIn(path);
    }

}

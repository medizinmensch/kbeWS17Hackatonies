package htw.ii.kbe.hackatonies;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.Properties;
import org.apache.commons.io.FilenameUtils;

public class readConfigFile {

    public static Properties readIn(String path) {

        //TODO: throw right exception. youri das musst du iwie try-catchen oder so
        String extension = FilenameUtils.getExtension(path);
        //if (!extension.equals("properties"))
            //throw new FileFormatException();
        //throw new IncorrectFileTypeError

        Properties prop = new Properties();
        File file = new File(path);
        InputStream input = null;

        try {
			if (path.equals(null)) {
				throw new NullPointerException();
			}
			else if(!file.exists()) {
				throw new FileNotFoundException();
			}
            input = new FileInputStream(path);
            prop.load(input);
            return prop;

        } catch (FileNotFoundException ex) {
            System.out.println("Datei existiert nicht.\n" + ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.out.println("Pfad wurde nicht übermittelt");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        /*



        try {



            if (prop == null) {
                throw new NullPointerException("bla");
            }

            return prop;

        } catch (IOException ex) {
            throw new FileNotFoundException("Datei existiert nicht.");
        }
        */
        return null;
    }
}

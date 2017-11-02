package htw.ii.kbe.hackatonies;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.Properties;
import org.apache.commons.io.FilenameUtils;

public class readConfigFile {

    public static Properties readIn(String path) throws IOException{
        String extension = FilenameUtils.getExtension(path);
        if (!extension.equals("properties"))
            throw new IOException("File is not a property file");


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
            throw new IOException("...");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("...");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

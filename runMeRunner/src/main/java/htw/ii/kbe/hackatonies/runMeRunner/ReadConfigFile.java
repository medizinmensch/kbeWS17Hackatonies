package htw.ii.kbe.hackatonies.runMeRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigFile {

	public static Properties GetProperties(String filePath) {

		File file = new File(filePath);
		Properties prop = new Properties();
		InputStream input = null;

		try {
			
			if (filePath.equals(null)) {
				throw new NullPointerException();
			}
			else if(!file.exists()) {
				throw new FileNotFoundException();
			}
			else {
				input = new FileInputStream(filePath);
				
				prop.load(input);
				
				if(prop.size() == 0) {
					throw new NullPointerException("Given filepath is not a valid property file!");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}

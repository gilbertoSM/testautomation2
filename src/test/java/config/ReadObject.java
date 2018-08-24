package config;

import utility.DriverException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObject{
	Properties p = new Properties();
    Properties prop = new Properties();
    
    /**
     * Method: getObjectProperties() --> Extract all the properties
     * from the config.properties file
     * 
     * @return
     * @throws IOException
     */
    public Properties getObjectProperties(){
        //Read object repository file
    	InputStream is;
		try {
			is = new FileInputStream(Constants.ConfigPath);
			prop.load(is);
		} catch (Exception e){
			if(e instanceof FileNotFoundException || e instanceof IOException){
				new DriverException("Class ReadObject | Method getObjectProperties | Problems Reading the File, Exception desc: " + e.getMessage());
			}else{
				new DriverException("Class ReadObject | Method getObjectProperties | Exception desc: " + e.getMessage());
			}	
		} 
        return prop;
    }
    
    /**
     * Method: getObjectProperties() --> Extract all the objects
     * from the Object Repository
     * 
     * @return
     * @throws IOException
     */
    public Properties getObjectRepository(){
        //Read object repository file
        InputStream stream;
		try {
			stream = new FileInputStream(new File(Constants.OBJECT_REPOSITORY));
	        //load all objects
	        p.load(stream);
		}  catch (Exception e){
			if(e instanceof FileNotFoundException || e instanceof IOException){
				new DriverException("Class ReadObject | Method getObjectProperties | Problems Reading the File, Exception desc: " + e.getMessage());
			}else{
				new DriverException("Class ReadObject | Method getObjectProperties | Exception desc: " + e.getMessage());
			}	
		} 
         return p;
    }
}

package utility;

import java.util.Properties;

import config.ReadObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import properties.General;
import testCasesWeb.DriverScript;

public class DriverException extends Exception{

	public static final long serialVersionUID = 700L;
	public static General objProp = new General();
    public static String file;
    public static ReadObject object = new ReadObject();
    private static Properties allProperties = object.getObjectProperties();
    private static String fromProperty = allProperties.getProperty("From");
	private static String passwordProperty = allProperties.getProperty("Password");
	private static String toProperty = allProperties.getProperty("To");
	
	private void errorInfo(String message, String fileName){
		if(DriverScript.EmailProperty.equals("true")){
			SeleniumUtils.sendEmail(fromProperty, passwordProperty, toProperty, message, message, fileName);
			Log.error("Email sent to " + toProperty);
		}
		objProp.setScreenShot(fileName);
		Log.error("Screenshot created in: "+fileName);
		
	}
	
	public DriverException(){}
	
	public DriverException(String message){
		super(message);
		DriverScript.bResult = false;
		Log.error(message);
		objProp.setRemark(message);
	}
	
	public DriverException(String message, Exception e){
		this(message);
		Log.error(e.getMessage());
	}
	
	public DriverException(WebDriver driver, String snapshotError,String message){
		this(message);
		file = SeleniumUtils.takeSnapShot(driver, snapshotError);
		errorInfo(message,file);
	}
	
	public DriverException(WebDriver driver, String snapshotError,String message, Exception e){
		this(driver, snapshotError, message);
		Log.error(e.getMessage());
	}
	
	public DriverException(WebDriver driver, WebElement element, String snapshotError,String message){
		this(message);
		file = SeleniumUtils.takeSnapShot(driver,element, snapshotError);
		errorInfo(message,file);
	}
	
	public DriverException(WebDriver driver, WebElement element, String snapshotError,String message, Exception e){
		this(driver, element, snapshotError, message);
		Log.error(e.getMessage());
	}
}

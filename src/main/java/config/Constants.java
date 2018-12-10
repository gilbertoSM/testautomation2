package config;

public class Constants {
	// Modified 01/03/2018 'config.properties path variable was created', Gilberto Sánchez Mares
	public static final String ConfigPath = System.getProperty("user.dir")+ "/src/test/resources/configuration/config.properties";
	public static final String CHROME_DRIVER = System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver";
	public static final String OBJECT_REPOSITORY = System.getProperty("user.dir") + "/src/test/resources/configuration/ObjRep.txt";
	public static final String CHROME_MOBILE_DRIVER_PATH = System.getProperty("user.dir") + "/src/test/resources/drivers/";
	public static final String FIREFOX_DRIVER = System.getProperty("user.dir") + "/src/test/resources/drivers/gecko";
	public static final String IE_DRIVER = System.getProperty("user.dir") + "/src/test/resources/drivers/IEDriverServer";
	public static final String EDGE_DRIVER = System.getProperty("user.dir") + "/src/test/resources/drivers/MicrosoftWebDriver";

	//DataEngine
	public static final String DATA_ENGINE = System.getProperty("user.dir")+ "/src/test/resources/configuration/CoursekeyTest.xlsx";
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	//Resources directory
	// Modified 27/10/2017,    'LogXMLDir war replaced by LogPropDir and dir was modified', Gilberto Sánchez Mares
	public static final String LogPropDir = System.getProperty("user.dir")+ "/src/test/resources/configuration/log4j.properties";
	public static final String TestCase_Template = System.getProperty("user.dir")+ "/src/test/resources/configuration/TestCase_report_template.html";
	public static final String TestSteps_Template = System.getProperty("user.dir")+ "/src/test/resources/configuration/TestSteps_report_template.html";

	//Results directory
	public static final String Results_Folder = System.getProperty("user.dir")+"/results/";
	public static final String ScreenShots_Folder = System.getProperty("user.dir")+"/results/screenshots/";
	public static final String Logs_Result = System.getProperty("user.dir")+"/results/";

	//System Variables
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String KEYWORD_RESULT = "<!-- INSERT_RESULTS -->";
	public static final String KEYWORD_DATETIME = "<!-- DATETIME  -->";
	public static final String KEYWORD_TCRESULT = "<!-- TESTCASEID -->";
	public static final String KEYWORD_TCDESC = "<!-- TCDESCRIPTION -->";
	public static final String KEYWORD_STARTTIME = "<!-- STARTTIME -->";
	public static final String KEYWORD_DURATION = "<!-- DURATION -->";
	public static final String KEYWORD_PASSED = "<!-- PASSED -->";
	public static final String KEYWORD_FAILED = "<!-- FAILED -->";
	public static final String KEYWORD_TOTALTC = "<!-- TOTAL -->";
	public static final String KEYWORD_DATA = "<!-- DATA -->";
	public static final String KEYWORD_NOTRUN = "<!-- NOTRUN -->";
	public static final String KEYWORD_BLANK = "";
	//public final int BYTES_TO_READ = sizeof(Int64);

	//Data Sheet Column Numbers
	//Sheet: Test Cases
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TCDescription = 1;
	public static final int Col_RunMode = 2;
	public static final int Col_DDTMode = 3;
	public static final int Col_Result = 4;
	//Sheet: Test Steps
	public static final int Col_TestScenarioID = 1;
	public static final int Col_TSDescription = 2;
	public static final int Col_PageObject = 4;
	//	public static final int Col_ObjectType = 5;
	public static final int Col_ActionKeyword = 5;
	public static final int Col_DataSet = 6;
	public static final int Col_TestStepResult = 7;
}
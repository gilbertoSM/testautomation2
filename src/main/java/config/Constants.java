package config;

public class Constants {
	// Modified 01/03/2018 'config.properties path variable was created', Gilberto Sánchez Mares
	public static final String BASE_PATH = System.getProperty("user.dir");
    public static final String MAIN_PATH = BASE_PATH + "/src/main";
    public static final String RESOURCES_PATH = MAIN_PATH + "/resources/";
    public static final String RESULTS_DIR = BASE_PATH +"/results/";
	public static final String CONFIG_PATH = RESOURCES_PATH + "configuration/config.properties";
	public static final String CHROME_DRIVER = RESOURCES_PATH + "drivers/chromedriver";
	public static final String OBJECT_REPOSITORY = RESOURCES_PATH + "configuration/ObjRep.txt";
	public static final String CHROME_MOBILE_DRIVER_PATH = RESOURCES_PATH + "drivers/";
	public static final String FIREFOX_DRIVER = RESOURCES_PATH + "drivers/gecko";
	public static final String IE_DRIVER = RESOURCES_PATH + "drivers/IEDriverServer";
	public static final String EDGE_DRIVER = RESOURCES_PATH + "drivers/MicrosoftWebDriver";

	//DataEngine
	public static final String DATA_ENGINE = RESOURCES_PATH + "configuration/CoursekeyTest.xlsx";
	public static final String SHEET_TEST_STEPS = "Test Steps";
	public static final String SHEET_TEST_CASES = "Test Cases";
	//Resources directory
	// Modified 27/10/2017,    'LogXMLDir war replaced by LogPropDir and dir was modified', Gilberto Sánchez Mares
	public static final String LOG_DIR = RESOURCES_PATH + "configuration/log4j.properties";
	public static final String TEST_CASE_TEMPLATE = RESOURCES_PATH + "configuration/TestCase_report_template.html";
	public static final String TEST_STEPS_TEMPLATE = RESOURCES_PATH + "configuration/TestSteps_report_template.html";

	//Results directory
	public static final String SCREENSHOT_DIR = RESULTS_DIR + "screenshots/";


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
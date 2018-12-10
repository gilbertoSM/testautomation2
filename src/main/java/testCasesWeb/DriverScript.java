package testCasesWeb;

import config.ActionKeywords;
import config.Constants;
import config.ReadObject;
import config.Reporter;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import properties.General;
import properties.TestCase;
import properties.TestStep;
import utility.ExcelUtils;
import utility.Log;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverScript extends ExcelUtils {

    private static Properties allObjects;
    private static ReadObject object;
    private static Properties allProperties;
    private static ActionKeywords actionKeywords;
    private static Method method[];
    private static XSSFWorkbook ExcelWBook;
    private static XSSFSheet ExcelWSheet;
    private static TestCase _testCase;
    private static TestStep _testStep;
    public static General objProp;
    private static General _testCaseReporter;
    private static General _testStepReporter;
    static SoftAssert softAssertion = new SoftAssert();
    private static int iTestStep;
    private static int iTestLastStep;
    private static int iTestData;
    private static int iTestLastData;
    private int iTotalTestCases;
    private int iTestcase;
    private int startCol;
    private int totalCols;
    public static boolean bResult;
    private static boolean ddtResult;
    private static String sParam;
    private static String TCSheetProperty;
    private static String TSSheetProperty;
    private static String BrowserProperty;
    public static String EmailProperty;

    /**
     * This method is used to load all the methods of the class 'ActionKeywords'
     *
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public DriverScript() throws NoSuchMethodException, SecurityException {
        actionKeywords = new ActionKeywords();
        object = new ReadObject();
        objProp = new General();
        _testCaseReporter = new General();
        _testStepReporter = new General();
        allProperties = object.getObjectProperties();
        allObjects = object.getObjectRepository();
        method = actionKeywords.getClass().getMethods();
        TCSheetProperty = Constants.Sheet_TestCases;
        TSSheetProperty = Constants.Sheet_TestSteps;
        BrowserProperty = allProperties.getProperty("Browser");
        EmailProperty = allProperties.getProperty("SendEmail");
    }

    /**
     * The main script is divided in to three parts now
     * First is main method, execution starts from here
     */
    //public static void main(String[] args) throws Exception {
    @Test
    public void DriverScript() throws Exception {
        // Modified 27/10/2017,    'DOMConfigurator.configure(u); deleted', Gilberto Sánchez Mares
        PropertyConfigurator.configure(Constants.LogPropDir);
        DriverScript startEngine = new DriverScript();
        startEngine.execute_TestCase();

        Assert.assertTrue(ActionKeywords.closeBrowser(null, Constants.KEYWORD_BLANK, Constants.KEYWORD_BLANK, Constants.KEYWORD_BLANK)); //--> Uncomment
        if (allProperties.getProperty("Open_Report").toLowerCase().equals("true")) {
            Desktop.getDesktop().open(new File(Reporter.objProp.getTestCaseReporter()));
        }
        if (allProperties.getProperty("Open_DataEngine").toLowerCase().equals("true")) {
            Desktop.getDesktop().open(new File(Constants.DATA_ENGINE));
        }
        if (allProperties.getProperty("Open_Logs").toLowerCase().equals("true")) {
            Desktop.getDesktop().open(new File(Constants.Logs_Result + "Logs.log"));
        }
    }

    /**
     * Second method, this is to figure out the test cases execution one by one
     * And to figure out test step execution one by one
     */
    private void execute_TestCase() throws Exception {
        long startTime = System.nanoTime();
        setExcelFile(Constants.DATA_ENGINE);
        objProp.setBrowserDriver(BrowserProperty);
        iTotalTestCases = getRowCount(TCSheetProperty);
        if(allProperties.getProperty("Mobile").toLowerCase().equals("true")){
            ActionKeywords.mobileBrowser(null, Constants.KEYWORD_BLANK, allProperties.getProperty("Capabilities"), Constants.KEYWORD_BLANK);
        }else {
            Assert.assertTrue(ActionKeywords.openBrowser(null, Constants.KEYWORD_BLANK, objProp.getBrowserDriver(), Constants.KEYWORD_BLANK));
        }

        for (iTestcase = 1; iTestcase < iTotalTestCases; iTestcase++) {
            _testCaseReporter.setTestCaseReporter(Constants.TestCase_Template);
            _testStepReporter.setTestStepReporter(Constants.TestSteps_Template);
            setTestCaseValues();
            bResult = true;

            if (_testCase.getRunMode().equals("Yes")) {
                Log.startTestCase(_testCase.getId());
                if (!_testCase.getDDTMode().equals("")) {
                    iTestLastData = getRowCount(_testCase.getDDTMode());
                    ddtResult = true;
                } else {
                    iTestLastData = 2;
                    ddtResult = false;
                }
                bResult = true;

                for (iTestData = 1; iTestData < iTestLastData; iTestData++) {
                    iTestStep = getRowContains(_testCase.getId(), Constants.Col_TestCaseID, TSSheetProperty);
                    iTestLastStep = getTestStepsCount(TSSheetProperty, _testCase.getId(), iTestStep);
                    bResult = true;

                    for (; iTestStep < iTestLastStep; iTestStep++) {
                        setTestStepsValues();
                        if (!_testStep.getData().equals("") && !_testCase.getDDTMode().equals("")) {
                            FileInputStream ExcelFile = new FileInputStream(Constants.DATA_ENGINE);
                            ExcelWBook = new XSSFWorkbook(ExcelFile);
                            ExcelWSheet = ExcelWBook.getSheet(_testCase.getDDTMode());
                            startCol = 0;
                            totalCols = ExcelWSheet.getRow(0).getLastCellNum();
                            for (; startCol < totalCols; startCol++) {
                                sParam = getCellData(0, startCol, _testCase.getDDTMode());
                                if (_testStep.getData().equals(sParam)) {
                                    sParam = getCellData(iTestData, startCol, _testCase.getDDTMode());
                                    _testStep.setData(sParam);
                                    break;
                                }
                            }
                        }

                        _testStep.setLink(getCellData(iTestStep, Constants.Col_TestStepResult, TSSheetProperty));
                        execute_Actions();

                        if (ddtResult == true) {
                            if (bResult == false) {
                                setCellData(Constants.KEYWORD_FAIL, iTestData, totalCols, _testCase.getDDTMode(), Constants.DATA_ENGINE);
                                Log.endTestCase();
                                break;
                            }
                        } else if (bResult == false) {
                            break;
                        }
                    }
                    if (ddtResult == true) {
                        if (bResult == true) {
                            setCellData(Constants.KEYWORD_PASS, iTestData, totalCols, _testCase.getDDTMode(), Constants.DATA_ENGINE);
                            Log.endTestCase();
                        }
                        if (bResult == false) {
                            setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result, TCSheetProperty, Constants.DATA_ENGINE);
                            break;
                        }
                    }
                }
                Reporter.WriteTestStepsResults(_testStepReporter.getTestStepReporter(), "TestStepReport" + iTestcase, _testCase.getId(), _testCase.getDescription());
                if (bResult == true) {
                    setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result, TCSheetProperty, Constants.DATA_ENGINE);
                    Reporter.TestCaseReport(_testCase.getId(), _testCase.getDescription(), Constants.KEYWORD_PASS);
                } else if (bResult == false) {
                    setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result, TCSheetProperty, Constants.DATA_ENGINE);
                    Reporter.TestCaseReport(_testCase.getId(), _testCase.getDescription(), Constants.KEYWORD_FAIL);
                }

                Log.endTestCase();
            } else if (_testCase.getRunMode().equals("No")) {
                Reporter.TestCaseReport(_testCase.getId(), _testCase.getDescription(), "");
            }
        }
        long difference = System.nanoTime() - startTime;
        objProp.setDuration(String.format("%d hrs, %d min, %d sec",
                TimeUnit.NANOSECONDS.toHours(difference),
                TimeUnit.NANOSECONDS.toMinutes(difference),
                TimeUnit.NANOSECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference)))
        );
        Reporter.WriteTestCaseResults(_testCaseReporter.getTestCaseReporter(), "TestCaseReport");
    }

    private void setTestStepsValues() throws Exception {
        _testStep = new TestStep();
        _testStep.setDescription(getCellData(iTestStep, Constants.Col_TSDescription, TSSheetProperty));
        _testStep.setId(getCellData(iTestStep, Constants.Col_TestScenarioID, TSSheetProperty));
        _testStep.setActionKeyword(getCellData(iTestStep, Constants.Col_ActionKeyword, TSSheetProperty));
        _testStep.setPageObject(getCellData(iTestStep, Constants.Col_PageObject, TSSheetProperty));
        _testStep.setData(getCellData(iTestStep, Constants.Col_DataSet, TSSheetProperty));
    }

    private void setTestCaseValues() throws Exception {
        _testCase = new TestCase();
        _testCase.setId(getCellData(iTestcase, Constants.Col_TestCaseID, TCSheetProperty));
        _testCase.setDescription(getCellData(iTestcase, Constants.Col_TCDescription, TCSheetProperty));
        _testCase.setRunMode(getCellData(iTestcase, Constants.Col_RunMode, TCSheetProperty));
        _testCase.setDDTMode(getCellData(iTestcase, Constants.Col_DDTMode, TCSheetProperty));
    }

    /**
     * This method contains the code to perform some action
     * As it is completely different set of logic, which revolves around the action only,
     * It makes sense to keep it separate from the main driver script
     * This is to execute test step (Action)
     */
    private static void execute_Actions() throws Exception {
        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().equals(_testStep.getActionKeyword())) {
                method[i].invoke(actionKeywords, allObjects, _testStep.getPageObject(), _testStep.getData(), _testStep.getLink());

                if (bResult == true) {
                    setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, TSSheetProperty, Constants.DATA_ENGINE);
                    Reporter.TestStepReport(_testStep.getId(), _testStep.getDescription(), Constants.KEYWORD_PASS);
                    softAssertion.assertTrue(true);
                    break;
                } else {
                    setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, TSSheetProperty, Constants.DATA_ENGINE);
                    Reporter.TestStepReport(_testStep.getId(), _testStep.getDescription(), Constants.KEYWORD_FAIL);
                    softAssertion.assertTrue(false);
                    break;
                }
            }
        }
    }

}
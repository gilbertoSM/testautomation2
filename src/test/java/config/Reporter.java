package config;

import properties.General;
import properties.Results;
import testCasesWeb.DriverScript;
import utility.DriverException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class Reporter{
	private static List<Results> details;
	public static List<Results> detailsSteps=new ArrayList<Results>();
	public static General objProp = new General();
	private static ReadObject objects = new ReadObject();
	public static Properties allProperties = objects.getObjectProperties();
    private static String ResultPlaceholder = Constants.KEYWORD_RESULT;
    private static String sDataTime = Constants.KEYWORD_DATETIME;
    private static int i = 0, j = 0, k = 0, l = 0;
    
    public static void TestStepReport(String tsId, String description, String result)
    {
    	Results rS=null;
    	if(DriverException.objProp.getRemark()!=null){
    		rS = new Results(tsId, description, DriverException.objProp.getRemark(), result, "");
    	}else if(ActionKeywords.objProps.getRemark()!=null){
    		rS = new Results(tsId, description, ActionKeywords.objProps.getRemark(), result, "");
    	}
        
        detailsSteps.add(rS);
    }

    public static void TestCaseReport(String tcId, String description, String result)
    {
        if (details == null) details = new ArrayList<Results>();
        Results r = new Results(tcId, description, result, objProp.getTestStepReporter());
        details.add(r);
    }
    
    public static void WriteTestCaseResults(String fileTemplate, String reportname)
    {
    	String reportIn = "";

        try
        {
            reportIn = new String(Files.readAllBytes(Paths.get(fileTemplate)));

            reportIn = reportIn.replace(sDataTime, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            reportIn = reportIn.replace(Constants.KEYWORD_STARTTIME, new SimpleDateFormat("HH:mm:ss").format(new Date()));
            reportIn = reportIn.replace(Constants.KEYWORD_DURATION, DriverScript.objProp.getDuration());

            for (; i < details.size(); i++)
            {
            	//Number of test cases passed
                if (details.get(i).getResultText() != null && details.get(i).getResultText().equals("PASS"))
                {
                    j++;
                }
              //Number of test cases failed
                else if (details.get(i).getResultText() != null && details.get(i).getResultText().equals("FAIL"))
                {
                    k++;
                }
                //Number of test cases not ran
                else if (details.get(i).getResultText().equals(""))
                {
                    l++;
                }
            }

//            reportIn = reportIn.replace(Constants.KEYWORD_TOTALTC, Integer.toString(i));
            reportIn = reportIn.replace(Constants.KEYWORD_TOTALTC, Integer.toString(j+k));
            objProp.setTotalTestCasesExecuted(Integer.toString(j+k));
            reportIn = reportIn.replace(Constants.KEYWORD_PASSED, Integer.toString(j));
            objProp.setTestCasePassed(Integer.toString(j));
            reportIn = reportIn.replace(Constants.KEYWORD_FAILED, Integer.toString(k));
            objProp.setTestCaseFailed(Integer.toString(k));
            reportIn = reportIn.replace(Constants.KEYWORD_NOTRUN, Integer.toString(l));
            objProp.setTestCaseSkipped(Integer.toString(l));
            reportIn = reportIn.replace(Constants.KEYWORD_DATA, "[" + Integer.toString(j) + "," + Integer.toString(k) + "]");
            objProp.setDetails(details);
            
            for (i=0; i < details.size(); i++)
            {
                if (details.get(i).getResultText() != null && details.get(i).getResultText().equals("PASS"))
                {
                    reportIn = reportIn.replaceFirst(ResultPlaceholder, "<tr><td><font color=\"black\">" + details.get(i).getTestId() + "</font></td><td><font color=\"black\">" + details.get(i).getResult() + "</font></td><td><font color=\"green\">" + "<a href=\"" + details.get(i).getLink().replace("\\", "/") + "\">" + details.get(i).getResultText() + "</font></td></tr>" + ResultPlaceholder);
                }
                else if (details.get(i).getResultText() != null && details.get(i).getResultText().equals("FAIL"))
                {
                    reportIn = reportIn.replaceFirst(ResultPlaceholder, "<tr><td><font color=\"black\">" + details.get(i).getTestId() + "</font></td><td><font color=\"black\">" + details.get(i).getResult() + "</font></td><td><font color=\"red\">" + "<a href=\"" + details.get(i).getLink().replace("\\", "/") + "\">" + details.get(i).getResultText() + "</font></td></tr>" + ResultPlaceholder);
                }

            }

            objProp.setTestCaseReporter(Constants.Results_Folder + reportname + ".html");
            Files.write(Paths.get(objProp.getTestCaseReporter()),reportIn.getBytes(),StandardOpenOption.CREATE);

        }
        catch (Exception e)
        {
            new DriverException("Class Reporter | Method WriteTestCaseResults | Exception desc: Unable to Write HTML Report" + e.getMessage());
        }
    }

    public static void WriteTestStepsResults(String fileTemplate, String reportname, String tcId, String tcDesc)
    {
        String reportIn = "";

        try
        {
        	reportIn = new String(Files.readAllBytes(Paths.get(fileTemplate)));
            
            reportIn = reportIn.replace(sDataTime, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            reportIn = reportIn.replace(Constants.KEYWORD_TCRESULT, tcId);
            reportIn = reportIn.replace(Constants.KEYWORD_TCDESC, tcDesc);

            objProp.setDetailsSteps(detailsSteps);
            
            for (int i = 0; i < detailsSteps.size(); i++)
            {
                if (detailsSteps.get(i).getStepResultText() != null && detailsSteps.get(i).getStepResultText().equals("PASS"))
                {
                	if(detailsSteps.get(i).getRemarks().contains("Custom Snapshot took!")){
                		reportIn = reportIn.replace(ResultPlaceholder, "<tr><td><font color=\"black\">" + detailsSteps.get(i).getTestStepId() + "</font></td>" +
                                "<td><font color=\"black\">" + detailsSteps.get(i).getStepResult() + "</font></td>" +
                                "<td><font color=\"black\">" + detailsSteps.get(i).getRemarks() + "</font></td>" +
                                "<td><font color=\"green\"><a href=\""  + ActionKeywords.objProps.getCustomScreenShot() + "\">" + detailsSteps.get(i).getStepResultText() + "</font></td></tr>" + ResultPlaceholder);
                	}else{
	                    reportIn = reportIn.replace(ResultPlaceholder, "<tr><td><font color=\"black\">" + detailsSteps.get(i).getTestStepId() + "</font></td>" +
	                        "<td><font color=\"black\">" + detailsSteps.get(i).getStepResult() + "</font></td>" +
	                        "<td><font color=\"black\">" + detailsSteps.get(i).getRemarks() + "</font></td>" +
	                        "<td><font color=\"green\">" + detailsSteps.get(i).getStepResultText() + "</font></td></tr>" + ResultPlaceholder);
                	}
                }
                else if (detailsSteps.get(i).getStepResultText() != null && detailsSteps.get(i).getStepResultText().equals("FAIL"))
                {
                    reportIn = reportIn.replace(ResultPlaceholder, "<tr><td><font color=\"black\">" + detailsSteps.get(i).getTestStepId() + "</font></td>" +
                        "<td><font color=\"black\">" + detailsSteps.get(i).getStepResult() + "</font></td>" +
                        "<td><font color=\"red\">" + detailsSteps.get(i).getRemarks() + "</font></td>" +
                        "<td><a href=\"" + DriverException.objProp.getScreenShot() + "\">" + detailsSteps.get(i).getStepResultText() + "</td></tr>" + ResultPlaceholder);
                }
            }

            objProp.setTestStepReporter(Constants.Results_Folder + reportname + ".html");
            Files.write(Paths.get(objProp.getTestStepReporter()),reportIn.getBytes(),StandardOpenOption.CREATE);
            detailsSteps.clear();
        }
        catch (Exception e)
        {
            new DriverException("Class Reporter | Method WriteTestStepsResults | Exception desc: Unable to Write HTML Report" + e.getMessage());
        }
    }
}

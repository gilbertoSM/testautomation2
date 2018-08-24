package properties;

import java.util.List;

public class General {
	private String browserDriver;
	private String testStepReporter;
	private String testCaseReporter;
	private String screenShot;
	private String customScreenShot;
	private String remark;
	private String videoPath;
	private String video;
	private String duration;
	private String testCasePassed;
	private String testCaseFailed;
	private String testCaseSkipped;
	private String totalTestCasesExecuted;
	private List<Results> details;
	private List<Results> detailsSteps;
	
	public String getBrowserDriver() {return browserDriver;}
	public void setBrowserDriver(String browserDriver) {this.browserDriver = browserDriver;}
	
	public String getTestStepReporter(){return this.testStepReporter;}
	public void setTestStepReporter(String sStepRepo){this.testStepReporter = sStepRepo;}
	
	public String getTestCaseReporter(){return this.testCaseReporter;}
	public void setTestCaseReporter(String sCaseRepo){this.testCaseReporter = sCaseRepo;}
	
	public String getScreenShot(){return this.screenShot;}
	public void setScreenShot(String sScreenShot){this.screenShot = sScreenShot;}
	
	public String getCustomScreenShot(){return this.customScreenShot;}
	public void setCustomScreenShot(String sCustomScreenShot){this.customScreenShot = sCustomScreenShot;}
	
	public String getRemark(){return this.remark;}
	public void setRemark(String sRemark){this.remark = sRemark;}

	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the video
	 */
	/**
	 * @return the testCasePassed
	 */
	public String getTestCasePassed() {
		return testCasePassed;
	}
	/**
	 * @param testCasePassed the testCasePassed to set
	 */
	public void setTestCasePassed(String testCasePassed) {
		this.testCasePassed = testCasePassed;
	}
	/**
	 * @return the testCaseFailed
	 */
	public String getTestCaseFailed() {
		return testCaseFailed;
	}
	/**
	 * @param testCaseFailed the testCaseFailed to set
	 */
	public void setTestCaseFailed(String testCaseFailed) {
		this.testCaseFailed = testCaseFailed;
	}
	/**
	 * @return the testCaseSkipped
	 */
	public String getTestCaseSkipped() {
		return testCaseSkipped;
	}
	/**
	 * @param testCaseSkipped the testCaseSkipped to set
	 */
	public void setTestCaseSkipped(String testCaseSkipped) {
		this.testCaseSkipped = testCaseSkipped;
	}
	/**
	 * @return the totalTestCasesExecuted
	 */
	public String getTotalTestCasesExecuted() {
		return totalTestCasesExecuted;
	}
	/**
	 * @param totalTestCasesExecuted the totalTestCasesExecuted to set
	 */
	public void setTotalTestCasesExecuted(String totalTestCasesExecuted) {
		this.totalTestCasesExecuted = totalTestCasesExecuted;
	}
	/**
	 * @return the details
	 */
	public List<Results> getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(List<Results> details) {
		this.details = details;
	}
	/**
	 * @return the detailsSteps
	 */
	public List<Results> getDetailsSteps() {
		return detailsSteps;
	}
	/**
	 * @param detailsSteps the detailsSteps to set
	 */
	public void setDetailsSteps(List<Results> detailsSteps) {
		this.detailsSteps = detailsSteps;
	}
}

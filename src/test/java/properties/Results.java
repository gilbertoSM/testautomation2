package properties;

public class Results {
	private String result;
	private String resultText;
	private String Link;
    private String tcId;
    private String stepResult;
    private String stepResultText;
	@SuppressWarnings("unused")
	private String stepResultScreenshot;
    private String tsId;
    private String remarks;
	
	public Results(String tcId, String result,String resultText, String Link) {
		this.tcId = tcId;
		this.result = result;
		this.resultText = resultText;
		this.Link = Link;
	}
	
	public Results(String tsId, String stepResult, String remarks, String stepResultText, String stepResultScreenshot)
    {
        this.tsId = tsId;
        this.stepResult = stepResult;
        this.remarks = remarks;
        this.stepResultText = stepResultText;
        this.stepResultScreenshot = stepResultScreenshot;
    }
	
	public void setTestId(String tcId){this.tcId = tcId;}
	
	public String getTestId(){return this.tcId;}
	
	public void setResult(String result){this.result = result;}
	
	public String getResult(){return this.result;}
	
	public void setResultText(String resultText){this.resultText = resultText;}
	
	public String getResultText(){return this.resultText;}	
	
	public void setLink(String Link){this.Link = Link;}

    public String getLink(){return this.Link;}
    
	public void setTestStepId(String tsId){this.tsId = tsId;}

    public String getTestStepId(){return this.tsId;}

    public void setStepResult(String stepResult){this.stepResult = stepResult;}

    public String getStepResult(){return this.stepResult;}

    public void setRemarks(String remarks){this.remarks = remarks;}

    public String getRemarks(){return this.remarks;}

    public void setStepResultText(String stepResultText){this.stepResultText = stepResultText;}

    public String getStepResultText(){return this.stepResultText;}
}

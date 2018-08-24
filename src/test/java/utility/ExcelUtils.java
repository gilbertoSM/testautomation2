package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import config.Constants;
import config.ReadObject;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import testCasesWeb.DriverScript;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	private static XSSFRow Row;
	public static Properties allProperties;
	public static ReadObject object;

	/**
	 * This method is to set the File path and to open the Excel file
	 * 
	 * @param Path
	 * @return
	 * @throws Exception
	 */
	public static void setExcelFile(String Path) throws DriverException {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e){
			DriverScript.bResult = false;
			throw new DriverException("Class ExcelUtils | Method setExcelFile | Exception desc: "+e.getMessage());
		}
	}

	/**
	 * This method is to read the test data from the Excel cell
	 * 
	 * @param RowNum
	 * @param ColNum
	 * @param SheetName
	 * @return
	 * @throws Exception
	 */
	public static String getCellData(int RowNum, int ColNum, String SheetName ) throws DriverException{
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			Cell.setCellType(CellType.STRING);
			if(Cell == null){
				return "";
			}else{
				String CellData = Cell.getStringCellValue();
				return CellData;
			}
		}catch (Exception e){
			if(e.getMessage() != null){
				DriverScript.bResult = false;
				throw new DriverException("Class ExcelUtils | Method getCellData | Exception desc: "+e.getMessage());
			}
			return"";
		}
	}

	/**
	 * This method is used to get the row count used of the excel sheet
	 * 
	 * @param SheetName
	 * @throws DriverException 
	 */
	public static int getRowCount(String SheetName) throws DriverException{
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getLastRowNum()+1;
		} catch (Exception e){
			if(e.getMessage() != null){
				DriverScript.bResult = false;
				throw new DriverException("Class ExcelUtils | Method getRowCount | Exception desc: "+e.getMessage());
			}
		}
		return iNumber;
	}

	/**
	 * This method is to get the Row number of the test case
	 *
	 *@param sTestCaseName
	 *@param colNum
	 *@param SheetName
	 *@return
	 *@throws DriverExeption
	 */
	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws DriverException{
		int iRowNum=0;	
		try {
			//ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtils.getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++){
				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}       			
		} catch (Exception e){
			if(e.getMessage() != null){
				DriverScript.bResult = false;
				throw new DriverException("Class ExcelUtils | Method getRowContains | Exception desc: "+e.getMessage());
			}
		}
		return iRowNum;
	}

	/**
	 * This method is to get the count of the test steps of test case
	 * 
	 * @param SheetName
	 * @param sTestCaseID
	 * @param iTestCaseStart
	 * @return
	 * @throws DriverException
	 */
	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws DriverException{
		try {
			for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
				if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
					int number = i;
					return number;      				
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum()+1;
			return number;
		} catch (Exception e){
			if(e.getMessage() != null){
				DriverScript.bResult = false;
				throw new DriverException("Class ExcelUtils | Method getTestStepsCount | Exception desc: "+e.getMessage());
			}
			return 0;
		}
	}

	/**
	 * This method is to set the results of the test steps and test cases
	 * 
	 * @param Result
	 * @param RowNum
	 * @param ColNum
	 * @param SheetName
	 * @param destFile 
	 * @return
	 * @throws IOException 
	 * @throws Exeption
	 */
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName, String File) throws DriverException, IOException    {
		object = new ReadObject();
		allProperties = object.getObjectProperties();   
		try{

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);

			/*
			 * If you want to set the background color of excel cell
			 * uncomment the statements below
			 * 
			 * XSSFCellStyle style6 = ExcelWBook.createCellStyle();
			 * style6.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
			 */
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);

				//addig color green to your cell
				//Cell.setCellStyle(style6);

			} else {
				Cell.setCellValue(Result);

				//create other style for failures
				//Cell.setCellStyle(style6);
			}
			FileOutputStream fileOut = new FileOutputStream(File);//allProperties.getProperty("DataEngine2_WorkBook"));
			ExcelWBook.write(fileOut);
			//fileOut.flush();
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(File));//allProperties.getProperty("DataEngine2_WorkBook")));
		}catch(Exception e){
			DriverScript.bResult = false;
			throw new DriverException("Class ExcelUtils | Method setCellData | Exception desc: "+e.getMessage());
		}
	}

	/**
	 * This method is to create hyper links in the results of the test steps
	 * 
	 * @param destFile 
	 * @param ColNum 
	 * 
	 * @throws Exeption
	 */
	public static void hyperlinkScreenshot(String destFile) {
		try{
			CreationHelper createHelper = ExcelWBook.getCreationHelper();
			CellStyle hlink_style = ExcelWBook.createCellStyle();
			Font hlink_font = ExcelWBook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			Hyperlink hp = createHelper.createHyperlink(HyperlinkType.FILE);//createHyperlink(Hyperlink.LINK_FILE);
			destFile=destFile.replace("\\", "/");
			//				destFile = URLEncoder.encode(destFile, "UTF-8"); 
			hp.setAddress(destFile);
			Cell.setCellValue("FAIL");
			Cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hp);
			Cell.setCellStyle(hlink_style);
		}catch(Exception e){
			Log.error("Class ExcelUtils | Method hyperlinkScreenshot | Exception desc: Not able to create hyperlink: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * This method is to create an Object[][] for DDT using DataProvider
	 * 
	 * @param FilePath
	 * @param SheetName
	 * @return
	 * @throws Exeption
	 */
	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0; 
			int ci,cj; 
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			System.out.println(totalRows + "," + totalCols);
			tabArray=new String[totalRows][totalCols];
			ci=0; 
			for (int i=startRow;i<=totalRows;i++, ci++) {
				cj=0;
				for (int j=startCol;j<totalCols;j++, cj++){
					tabArray[ci][cj]=getCellDataDDT(i,j);  
					System.out.println(tabArray[ci][cj]);
				}
			}
		}catch (FileNotFoundException e){
			throw new Exception("Class ExcelUtils | Method getTableArray | Exception desc: Excel not found: "+e.getMessage());
		}catch (IOException e){
			throw new Exception("Class ExcelUtils | Method getTableArray | Exception desc: Could not read the Excel sheet: "+e.getMessage());  
		} 
		return(tabArray);  
	} 

	@SuppressWarnings("deprecation")
	public static String getCellDataDDT(int RowNum, int ColNum) throws Exception { 
		try{ 
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = Cell.getCellType(); 
			
			if  (dataType == 3) {
				return "";  
			}else if(dataType == 0){
				  return String.valueOf(Cell.getNumericCellValue());
			}else{
				return Cell.getStringCellValue(); 
			}
		}catch (Exception e){
			throw new Exception("Class ExcelUtils | Method getCellDataDDT | Exception desc: " + e.getMessage());  
		}
	}
}
package QAD.Automation;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import ObjectRepository.LoginObjects;
import ObjectRepository.PlantObjects;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Testmethods {
	
  /* Description - To set up browser to test
      Parameters - Browser type (Chrome/Firefox/IE/Edge)
      Return - WebDriver
  */  
  public WebDriver setupbrowser(String browsertype) {	
	WebDriver drivers=null;
	if (browsertype.equals("Chrome")) {
		ChromeDriverManager.getInstance().setup();
		drivers = new ChromeDriver();
	} else if (browsertype.equals("IE")) {
		InternetExplorerDriverManager.getInstance().setup();
		drivers = new InternetExplorerDriver();
	} else if (browsertype.equals("Firefox")) {
		FirefoxDriverManager.getInstance().setup();
		drivers = new FirefoxDriver();
	} else if (browsertype.equals("Edge")) {
		EdgeDriverManager.getInstance().setup();
		drivers = new EdgeDriver();
	}
    return drivers;
  }
    
  /* Description - To get excel row count
     Parameters - Excel file path, Excel file name, Excel sheet name
     Return - Row count
  */
  public int rowcount(String filePath,String fileName,String sheetName){
	File file =    new File(filePath+"\\"+fileName);
    FileInputStream inputStream = null;
	try {
		inputStream = new FileInputStream(file);
    	} catch (FileNotFoundException e) {
	  e.printStackTrace();
	}
	Workbook dataWorkbook = null;
	try {
		  dataWorkbook = new HSSFWorkbook(inputStream);
		} catch (IOException e) {
		  e.printStackTrace();
	}
	Sheet dataWorkSheet = dataWorkbook.getSheet(sheetName);
	return dataWorkSheet.getPhysicalNumberOfRows();
  }
  
  /* Description - Method to Login
     Parameters - App URL, User name, Password, Webdriver
     Return - void
  */
  public void login(String baseUrlF,String usernameF, String passwordF, WebDriver driver) throws Exception  {
	try {
		driver.get(baseUrlF + "/");
		forsync(LoginObjects.txtbx_UserName(driver), driver);
		LoginObjects.txtbx_UserName(driver).sendKeys(usernameF);
		LoginObjects.txtbx_Password(driver).sendKeys(passwordF);
		LoginObjects.btn_LogIn(driver).click();
	}
	  catch (Exception e){
	        throw(e);
	}
  }
  
  /* Description - Method to Create a Plant
     Parameters - Plant details, Webdriver
     Return - void
  */
  public void CreatePlant(String plantname,String plantnumber, String address1, String address2,String city,String state, String zip,String phone, String fax, WebDriver driver) throws Exception  {
	try {
		Thread.sleep(1000);
		boolean syncstate=PlantObjects.navigationPlant(driver).isDisplayed();
		if (!syncstate) {
			PlantObjects.navigationMasterData(driver).click();
			Thread.sleep(1000);
			PlantObjects.navigationPlant(driver).click();
		}
		forsync(PlantObjects.addplant(driver), driver);
		PlantObjects.addplant(driver).click();
		forsync(PlantObjects.plantname(driver), driver);
		PlantObjects.plantname(driver).sendKeys(plantname);
		PlantObjects.plantnumber(driver).sendKeys(plantnumber);
		PlantObjects.plantaddress1(driver).sendKeys(address1);
		PlantObjects.plantaddress2(driver).sendKeys(address2);
		PlantObjects.plantcity(driver).sendKeys(city);
		new Select(PlantObjects.plantstate(driver)).selectByVisibleText(state);
		PlantObjects.plantzip(driver).sendKeys(zip);
		PlantObjects.plantphone(driver).sendKeys(phone);
		PlantObjects.plantfax(driver).sendKeys(fax);
		PlantObjects.plantcancel(driver).click();
	}
	catch (Exception e){
	    throw(e);
	}
  }
 
  /* Description - Method to Sync
     Parameters - WebElement, Webdriver
     Return - void
  */
 public void forsync(WebElement element, WebDriver driver) {
	 WebDriverWait wait = new WebDriverWait(driver, 20);
     wait.until(ExpectedConditions.elementToBeClickable(element));
 }
 
 /* Description - Method to check text in app and return 1 if present
  * Return - 0/1 (int)
 */
 public int checkresult (WebDriver driver, String checktext) {
	 if(driver.getPageSource().contains(checktext)) {return 1;}
	 else {return 0;}
 }
 
 /* Description - Method for test reporting using extend reports
    Parameters - Report file path, Result check digit (1- Pass, 0- Fail), Clear the report flag (Yes/No), Result text for report
    Return - void
 */
 public void reporting (String filePath,int check,String resultdesc,String resultdescfail, String clearR, int rowiterate) {
	 ExtentReports extent = null;
	 if (clearR.equals("Yes") && rowiterate <=2 ) {
		extent = new ExtentReports(filePath,true);
	 }
	 else {
	    extent = new ExtentReports(filePath,false);
	 }
	 if (check==1) {
		 ExtentTest test = extent.startTest(resultdesc, resultdesc);
		 test.log(LogStatus.PASS, resultdesc);
		 extent.endTest(test);
		 extent.flush();
		 System.err.printf(resultdesc);
	 } else 
	 {	 
		 ExtentTest test = extent.startTest(resultdescfail, resultdescfail);
		 test.log(LogStatus.FAIL, resultdescfail);
		 extent.endTest(test);
		 extent.flush();
		 System.err.printf(resultdescfail);
     }
 }
 
 /* Description - Method to read excel data   
    Parameters - Excel Path, Excel file name, Excel sheet name, Column Header, Row number
    Return - excel sheet cell data
 */
 public String readExcel(String filePath,String fileName,String sheetName,String col_header,int row_position) throws IOException{
	   File file =    new File(filePath+"\\"+fileName);
	   FileInputStream inputStream = new FileInputStream(file);
	   Workbook dataWorkbook = null;
	   String fileExtensionName = fileName.substring(fileName.indexOf("."));
	   if(fileExtensionName.equals(".xls")){
	   	  dataWorkbook = new HSSFWorkbook(inputStream);
	   }
	  Sheet dataWorkSheet = dataWorkbook.getSheet(sheetName);
	  int column_num = 0;
	  for (int i = 0; i < 1; i++) {
		     Row row = dataWorkSheet.getRow(i);
		     for (int j = 0; j < row.getLastCellNum(); j++) {
		       	String cell_data=row.getCell(j).getStringCellValue();
		        if (cell_data.equals(col_header)) {
		        	column_num=j;
		        }
		     }
		}
	  String cell_data = null;
	  for (int i = row_position; i < row_position+1; i++) {
	     Row row = dataWorkSheet.getRow(i);
	     for (int j = column_num; j < column_num+1; j++) {
	       	 cell_data=row.getCell(j).getStringCellValue();
	     }
	  }
	  return cell_data;
   }
 
 /* Description - Method to take page screenshot  
    Parameters - png image Path, Webdriver
    Return - void
 */
 public void screenshot(String screenpath,WebDriver driver) throws IOException {
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scrFile, new File(screenpath));
 }
}
 
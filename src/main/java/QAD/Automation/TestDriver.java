package QAD.Automation;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.openqa.selenium.*;

public class TestDriver {
  WebDriver driver;
  /* Path of input sheet and report html */
  private static String filePath= System.getProperty("user.dir") + "\\DataInput" ;;
  private String resultPath=System.getProperty("user.dir") + "\\Results\\Test_Report.html" ;
  private StringBuffer verificationErrors = new StringBuffer();
  /* Initialising class object */
  Testmethods testclassobj=new Testmethods();
  
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	// Setting up browser to run the test
	String browsertype = testclassobj.readExcel(filePath,"Input.xls","Driver","Browser",1);
	driver=testclassobj.setupbrowser(browsertype);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void QADTest() throws Exception {
	  int rowcnt= testclassobj.rowcount(filePath,"Input.xls","Driver");
	  for (int row_iterate = 1; row_iterate < rowcnt; row_iterate++) { 
		  // Reading data-driver sheet
		  String baseUrl = testclassobj.readExcel(filePath,"Input.xls","Driver","URL",1);
		  String str_execute=testclassobj.readExcel(filePath,"Input.xls","Driver","Execute",row_iterate);
		  String testcase = testclassobj.readExcel(filePath,"Input.xls","Driver","TestCase",row_iterate);
		  String clearreport = testclassobj.readExcel(filePath,"Input.xls","Driver","ReportClear",row_iterate);
		  
		  /* To execute the test case based on input (Yes/No)
		     Each Test Case is a method */
		  switch (testcase) {
			case "TestCase1": if(str_execute.equals("Yes")) {
			  int rows= testclassobj.rowcount(filePath,"Input.xls",testcase);
			  for (int row_test = 1; row_test < rows; row_test++) { 
				  // Reading test case data
				  String username = testclassobj.readExcel(filePath,"Input.xls",testcase,"User",row_test);
				  String password = testclassobj.readExcel(filePath,"Input.xls",testcase,"Password",row_test);
				  String testsuccess = testclassobj.readExcel(filePath,"Input.xls",testcase,"Success Message",row_test);
				  String testfail = testclassobj.readExcel(filePath,"Input.xls",testcase,"Fail Message",row_test);
				  String screenshots=System.getProperty("user.dir") + testclassobj.readExcel(filePath,"Input.xls",testcase,"Screenshot",row_test);
				  // Calling the method to run test case
				  testclassobj.login(baseUrl,username,password,driver);
				  // Test Reporting - Extend reports, Page screenshot
				  int checkresult= testclassobj.checkresult(driver,"Quality Assurance Database");
				  testclassobj.reporting (resultPath,checkresult,testsuccess, testfail,clearreport,row_iterate);
				  testclassobj.screenshot(screenshots,driver);
			  } 
			}
			break;
			case "TestCase2": if(str_execute.equals("Yes")) {
			  int rows= testclassobj.rowcount(filePath,"Input.xls",testcase);
			  for (int row_test = 1; row_test < rows; row_test++) { 
				  // Reading test case data
				  String plantname = testclassobj.readExcel(filePath,"Input.xls",testcase,"PlantName",row_test);
				  String plantnumber= testclassobj.readExcel(filePath,"Input.xls",testcase,"PlantNumber",row_test);
				  String address1 = testclassobj.readExcel(filePath,"Input.xls",testcase,"Address1",row_test);
				  String address2 = testclassobj.readExcel(filePath,"Input.xls",testcase,"Address2",row_test);
				  String city = testclassobj.readExcel(filePath,"Input.xls",testcase,"City",row_test);
				  String state = testclassobj.readExcel(filePath,"Input.xls",testcase,"State",row_test);
				  String zip = testclassobj.readExcel(filePath,"Input.xls",testcase,"ZIP",row_test);
				  String phone = testclassobj.readExcel(filePath,"Input.xls",testcase,"Phone",row_test);
				  String fax = testclassobj.readExcel(filePath,"Input.xls",testcase,"Fax",row_test);
				  String testsuccess = testclassobj.readExcel(filePath,"Input.xls",testcase,"Success Message",row_test);
				  String testfail = testclassobj.readExcel(filePath,"Input.xls",testcase,"Fail Message",row_test);
				  String screenshots=System.getProperty("user.dir") + testclassobj.readExcel(filePath,"Input.xls",testcase,"Screenshot",row_test);
				  // Calling the method to run test case
				  testclassobj.CreatePlant(plantname,plantnumber,address1,address2,city,state,zip,phone,fax,driver);
				  // Test Reporting - Extend reports, Page screenshot
				  int checkresult= testclassobj.checkresult(driver,"XXXXXXX");
				  testclassobj.reporting (resultPath,checkresult,testsuccess,testfail,clearreport,row_iterate);
				  testclassobj.screenshot(screenshots,driver);
			  }
			}
			break;
		  }
	  }
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}


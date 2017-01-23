package ObjectRepository;

import org.openqa.selenium.*;

public class PlantObjects {
		private static WebElement element = null;
		public static WebElement navigationMasterData(WebDriver driver){
			element = driver.findElement(By.cssSelector("a.accordion-toggle.collapsed > div.menu-box > h3"));
			return element;
		}
			 
		public static WebElement navigationPlant(WebDriver driver){
			element = driver.findElement(By.xpath("//div[@id='collapse2']/div/div/a[3]/div/h3"));
			return element;
		}
			 
		public static WebElement addplant(WebDriver driver){
			element = driver.findElement(By.cssSelector("h4.ng-scope > img"));
			return element;
		}
		
		public static WebElement plantname(WebDriver driver){
			element = driver.findElement(By.name("txt_plantName"));
			return element;
		}
		
		public static WebElement plantnumber(WebDriver driver){
			element = driver.findElement(By.name("txt_plantNo"));
			return element;
		}
		
		public static WebElement plantaddress1(WebDriver driver){
			element = driver.findElement(By.name("txt_plantAddr1"));
			return element;
		}
		
		public static WebElement plantaddress2(WebDriver driver){
			element = driver.findElement(By.xpath("(//input[@type='text'])[5]"));
			return element;
		}
		
		public static WebElement plantcity(WebDriver driver){
			element = driver.findElement(By.name("txt_plantCity"));
			return element;
		}
		
		public static WebElement plantstate(WebDriver driver){
			element = driver.findElement(By.id("drp_State"));
			return element;
		}
		
		public static WebElement plantzip(WebDriver driver){
			element = driver.findElement(By.name("txt_plantZip"));
			return element;
		}
		
		public static WebElement plantphone(WebDriver driver){
			element = driver.findElement(By.name("txt_plantPhone"));
			return element;
		}
		
		public static WebElement plantfax(WebDriver driver){
			element = driver.findElement(By.name("txt_plantFax"));
			return element;
		}
		
		public static WebElement plantcancel(WebDriver driver){
			element = driver.findElement(By.name("cancel"));
			return element;
		}
		
		public static WebElement plantsubmit(WebDriver driver){
			element = driver.findElement(By.name("submit"));
			return element;
		}
		
	}

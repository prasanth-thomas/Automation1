package ObjectRepository;
import org.openqa.selenium.*;

public class LoginObjects {
		private static WebElement element = null;
		public static WebElement txtbx_UserName(WebDriver driver){
			element = driver.findElement(By.id("UserName"));
			return element;
		}
			 
		public static WebElement txtbx_Password(WebDriver driver){
			element = driver.findElement(By.id("Password"));
			return element;
		}
			 
		public static WebElement btn_LogIn(WebDriver driver){
			element = driver.findElement(By.cssSelector("input[type=\"submit\"]"));
			return element;
		}
	}

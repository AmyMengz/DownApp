import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class WebDriverTest {
	public static void main(String[] args) {
//		WebDriver driver = new InternetExplorerDriver ();
		System.setProperty("webdriver.chrome.driver", "F:/soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
//		driver=new AndroidDriver();
//		driver=new HtmlUnitDriver();
		driver.get("http://www.baidu.com");
		WebElement element = driver.findElement(By.id("kw"));
		element.sendKeys("android");
		System.out.println(element.getText());
		WebElement saveButton = driver.findElement(By.id("su"));
		saveButton.click();
	}

}

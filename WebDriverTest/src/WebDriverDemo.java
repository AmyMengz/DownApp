import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverDemo {
	public static void main(String[] args) {
		// 创建一个 firefox driver 实例
//		WebDriver driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "F:/soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		// 打开测试网址
		driver.get("https://apps.na.collabserv.com/");
		// 定义用户名和密码文本框
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		// 输入用户名和密码
		username.sendKeys("autouser01@e3yunmail.mail.lotuslive.com");
		password.sendKeys("test");
		// 点击 login 登录
		WebElement login = driver.findElement(By.id("continue"/*"submit_form"*/));
		login.click();
		// 设置页面等待直到出现 Mail 链接
		(new WebDriverWait(driver, 500))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver dr) {
						return dr.findElement(By.linkText("Mail"));
					}
				});
		// 登出
		WebElement logout = driver.findElement(By.linkText("Log Out"));
		logout.click();
		// 关闭浏览器
		driver.quit();
	}
}
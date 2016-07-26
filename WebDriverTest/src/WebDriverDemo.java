import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverDemo {
	public static void main(String[] args) {
		// ����һ�� firefox driver ʵ��
//		WebDriver driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "F:/soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		// �򿪲�����ַ
		driver.get("https://apps.na.collabserv.com/");
		// �����û����������ı���
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		// �����û���������
		username.sendKeys("autouser01@e3yunmail.mail.lotuslive.com");
		password.sendKeys("test");
		// ��� login ��¼
		WebElement login = driver.findElement(By.id("continue"/*"submit_form"*/));
		login.click();
		// ����ҳ��ȴ�ֱ������ Mail ����
		(new WebDriverWait(driver, 500))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver dr) {
						return dr.findElement(By.linkText("Mail"));
					}
				});
		// �ǳ�
		WebElement logout = driver.findElement(By.linkText("Log Out"));
		logout.click();
		// �ر������
		driver.quit();
	}
}
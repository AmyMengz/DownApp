import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


public class GoogleWebDriverTest {
	static String urlGoogleAccount="http://accounts.google.com";
	static String newPassword="hdj123123";
	static String first="   ";
	static String second="----";
	static String Email="ElinorMcconnell2828818135@gmail.com";
	static String password="uby79mu7803";
	static String assEmail="5yvkh61cyfzsk8@aol.com";
	static int index=12;
//	173   --------
//	177   --------
	
//	 --------
//	175   --------
//	--------
	public static void readAccount(){
		File file=new File("G:\\account.txt");
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(file));
			
//			String tmp=reader.readLine();
			String tmp=null;
			int count=0;
			while(count<=index&& (tmp=reader.readLine())!=null){
				count++;
			}
			System.out.println(tmp+"   "+count);
			if(tmp!=null){
				int start=tmp.indexOf(first);
				int end=tmp.indexOf(second);
				int end2=tmp.lastIndexOf(second);
				Email=tmp.substring(start+first.length(),end);
				password=tmp.substring(end+second.length(),end2);
				assEmail=tmp.substring(end2+second.length(),tmp.length());
				System.out.println(Email+":"+password+":"+assEmail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null)
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		while(index<=21){
			readAccount();
			doSetAccount();
			System.out.println("-------------------------------------------------------------index="+index+"-------------------------------");
		}
		
		
	}
	
	private static void doSetAccount() {
		System.setProperty("webdriver.chrome.driver", "F:/soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(urlGoogleAccount);
		doInputById(driver, "Email", Email, "Email");
		doClickById(driver, "next", "next");
		
//-----------------------------------------------
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		System.out.println("title===="+driver.getCurrentUrl());
		doInputById(driver, "Passwd", password, "���������");
		doClickById(driver, "signIn", "signIn");
		
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		try{
			doInputByName(driver, "email", assEmail, "��������");
			doClickById(driver, "submit", "submit");
		}catch(Exception e){
//			System.out.println(e);
		}
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		System.out.println("���===="+driver.getTitle());
		if("�ָ�ѡ��".equals(driver.getTitle())){
		try{
			doClickByClassName(driver, "c-sa-ra", "���", "���","Done","DONE");
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
//-----------------------------------------------
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		while(!("�ҵ��ʻ�".equals(driver.getTitle())||"My Account".equals(driver.getTitle()))) {
			driver.switchTo().defaultContent();
			waitForDropDownListLoaded(driver);
		}
		System.out.println("title===elementI1======"+driver.getTitle());
		doClickById(driver, "i1", "elementI1");
		
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		doClickByClassName(driver, "upRLX", "upRLX", "����","Password");

		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		doInputById(driver, "Passwd", password, "OLDPasswd");
		doClickById(driver, "signIn", "signIn");
		
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		
		
		inputNewPass(driver, "Hj", "Hj");
		
		doClickByClassName(driver, "RveJvd", "��������", "��������","Change password","CHANGE PASSWORD");
		
		driver.switchTo().defaultContent();
		waitForDropDownListLoaded(driver);
		try {
			Thread.sleep(8000);
			System.out.println("---------------------------title--"+driver.getTitle()+"=url=="+driver.getCurrentUrl());
			driver.close();
			driver.quit();
			index++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected static Function<WebDriver, Boolean> isPageLoaded() {
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
    }
	 public static void waitForDropDownListLoaded(WebDriver webDriver) {
	        WebDriverWait wait = new WebDriverWait(webDriver, 30);
	        wait.until(isPageLoaded());
	    }
	 /**
	  * ����ID���ҽڵ㣬����
	  * @param driver
	  * @param elementID
	  * @param InputKey
	  * @param log
	  */
	 public static void doInputById(WebDriver driver,String elementID,String InputKey,String log){
		 WebElement element = driver.findElement(By.id(elementID));
		 	System.out.println(log+"====input:::"+element+"   "+element.isEnabled());
		 	
		 	element.sendKeys(InputKey);
	 }
	 /**
	  * ����ID���ҽڵ㣬����¼�
	  * @param driver
	  * @param elementID
	  * @param log
	  */
	 public static void doClickById(WebDriver driver,String elementID,String log){
		 WebElement element = driver.findElement(By.id(elementID));
		 	System.out.println(log+"===click:::"+element);
		 	element.click();
	 }
	 /**
	  * ����NAME���ҽڵ㣬����
	  * @param driver
	  * @param elementName
	  * @param InputKey
	  * @param log
	  */
	 public static void doInputByName(WebDriver driver,String elementName,String InputKey,String log){
		 WebElement element = driver.findElement(By.name(elementName));
		 	System.out.println(log+"====input:::"+element);
		 	element.sendKeys(InputKey);
	 }
	 
	 
	 /**
	  * ����ClassName���ҽڵ㣬����¼�
	  * @param driver
	  * @param elementID
	  * @param log
	  */
	 public static void doClickByClassName(WebDriver driver,String className,String log,String... args){
		 List<WebElement> elements= driver.findElements(By.className(className));
		 while (elements.size()<=0) {
			 elements= driver.findElements(By.className(className));
			 System.out.println(log+"====ClickByClassName:::elements:"+elements.size()+className);
			 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		 System.out.println(log+"====ClickByClassName:::elements:-----"+elements.size()+className);
			OK:
			for (int i = 0; i <elements.size(); i++) {
				for (int j = 0; j < args.length; j++) {
					if(args[j].equals(elements.get(i).getText())){
						System.out.println(log+"====ClickByClassName::click:"+elements.get(i).getText()+"  "+args[j]);
						elements.get(i).click();
						break OK;
					}
				}
			}
	 }
	 
	 /**
	  * ����ClassName���ҽڵ㣬����
	  * @param driver
	  * @param elementID
	  * @param log
	  */
	 public static void inputNewPass(WebDriver driver,String className,String log){
		 List<WebElement> elements= driver.findElements(By.className(className));
			while (elements.size()<2) {
				elements= driver.findElements(By.className(className));
				System.out.println(log+"====inputNewPass:::elements:"+elements.size());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(elements.size()==2){
				for (int i = 0; i < elements.size(); i++) {
					System.out.println(log+"====inputNewPass:::"+elements.get(i).getText()+"----------  ");
					elements.get(i).sendKeys(newPassword);
					
				}
			}
	 }
	 
	 

}

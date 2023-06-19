package Logintest.DataDrivenTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EnteringData {

	WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		

	}

	@Test(dataProvider="LoginData")
	public void datatest(String user,String pass,String exp) throws InterruptedException {
		
		driver.get("https://admin-demo.nopcommerce.com/login");
		org.openqa.selenium.WebElement txtEmail = driver.findElement(By.id("Email"));
		txtEmail.clear();
		txtEmail.sendKeys(user);
		
		org.openqa.selenium.WebElement txtPassword = driver.findElement(By.id("Password"));
		txtPassword.clear();
		txtPassword.sendKeys(pass);
		
		driver.findElement(By.xpath("//input[@value='Log in']")).click(); // Login button

		String exp_title = "Dashboard / nopCommerce administration";
		String act_title = driver.getTitle();
		
		
		
		if (exp.equals("Valid")) {
			if (exp_title.equals(act_title)) {
				Thread.sleep(10);


				driver.findElement(By.xpath("//ul[@class='sidebar-menu tree']/li[5]/a/span")).click();
				driver.findElement(By.linkText("Logout")).click();
				Thread.sleep(10);
				//
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
			
		} else if (exp.equals("Invalid")) {
			if (exp_title.equals(act_title)) {
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}}
	}

	@DataProvider(name ="LoginData")
	public String[][] getData() throws IOException {

		String path = "D:\\Excel Data\\Book2.xlsx";
		com.excel.utility.XLUtility xlutil = new com.excel.utility.XLUtility(path);

		int totalrows = xlutil.getRowCount("TestData");
		int totalcols = xlutil.getCellCount("TestData", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) // 1
		{
			for (int j = 0; j < totalcols; j++) // 0
			{
				loginData[i - 1][j] = xlutil.getCellData("TestData", i, j);// avoiding header part in excell
			}
			
		}
		return loginData;
	}
	

	@AfterTest
	public void closebrowser() {

		driver.close();
	}

}

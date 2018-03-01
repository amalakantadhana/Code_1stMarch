package com.wipro.chcare.ccc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;

public class openCart006_performingActions_Totalamount {
	
	
private static final String depends= null;
private static final String dependsonMethods=null;
private WebDriver driver;
private Properties prop;
private String value;
private static int WAIT_TIME = 10000 ;
/*
This is method for setting path for Chrome driver and implicit wait and reading properties file
*/
	@BeforeClass
	public void intialize()
	{	
	System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe" );
	   driver=new ChromeDriver();
	   driver.manage().window().maximize();
	   driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
	   prop= new Properties();
		InputStream input;
		try {
			input = new FileInputStream("D:\\SELENIUM_PRACTISE\\Assignment1\\src\\Assignment1\\config.properties");
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	/* Step:001  Launch  Open Cart application http://10.207.182.108:81/opencart/*/
@Test(description = "Launch openCart application")
public void openApp() throws InterruptedException
{		
	  driver.get(prop.getProperty("url"));
	  	  	  System.out.println("opencart app launched");
			//Thread.sleep(5000);
	  	  	  
	 /* Step 002: Click on "Login" Link*/
	driver.findElement(By.xpath("(//a[text()='login'])")).click();
	//Thread.sleep(3000);
			} 
/* This method is for opening the opencart application
 * Enter Email Address and Password and click on "Login" Button.
 * Step:003
 * */
//Data Provider
@DataProvider(name="Mydatprovide")
public static  Object[][] ReadingData() throws IOException{
Object[][] obj=ReadingExcel.readExcel("LoginPage","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
return obj;	
}	
@DataProvider(name="TC006readdata")
public static  Object[][] TC006() throws IOException{
Object[][] obj=ReadingExcel.readExcel("TC006","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
return obj;	
}	

@Test (dataProvider="Mydatprovide",dependsOnMethods = "openApp") 	 
public void loginApp(String Username,String Password, String changequantity, String Faultycomment) throws IOException
{
	   driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(Username);
	  System.out.println(Username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
		System.out.println(Password);
		driver.findElement(By.xpath("//input[@class='button']")).click();
		   
		   System.out.println("Logged in successfully");
		/*=====================
		 * Check point: User's first name as a link
		 */
			String e=driver.findElement(By.linkText("dhana")).getText();
				Assert.assertEquals("dhana", e);
		System.out.println("Validation is passed");

}

@Test(dataProvider="TC006readdata",dependsOnMethods="loginApp")
public void performingActions(String word, String quantity) throws InterruptedException
{
	driver.findElement(By.name("search")).click();
	driver.findElement(By.name("search")).sendKeys(word);
    driver.findElement(By.className("button-search")).click();
    driver.findElement(By.xpath("//*[@id='content']/div[6]/div/div[2]/div[1]/a/img")).click();
    driver.findElement(By.xpath("//div[@id='content']/div[2]/div[2]/div[3]/div/input[1]")).clear();
    driver.findElement(By.xpath("//div[@id='content']/div[2]/div[2]/div[3]/div/input[1]")).sendKeys(quantity);
    driver.findElement(By.id("button-cart")).click();
    driver.findElement(By.xpath("//div[@id='notification']/div/img")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText("Shopping Cart")).click();
    String totalprice =driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr/td[6]")).getText();
    System.out.println("The total price is :" + totalprice);
    String price = totalprice.substring(1);
    System.out.println( "The price is " + price);
    double dprice=Double.parseDouble(price);  
    
    System.out.println("The Value of the price is " + dprice);
    if(dprice<1000.0)
    {
    	driver.findElement(By.linkText("Continue Shopping")).click();
    	System.out.println("Clicked on the Continue Shopping button");
    }
    else
    {
    	driver.findElement(By.linkText("Logout")).click();
    	System.out.println("Logged out successfully");
    }
    System.out.println("Test is completed");
}

}

package com.wipro.chcare.ccc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;

public class openCart005_GiftVoucher {

	private static final String depends =null;
	private static final String dependsOnMethods=null;
	private WebDriver driver;
	 private Properties prop;
	 private String value;
	 private static int WAIT_TIME = 10000 ;
	 
	@BeforeClass
	public void initialize()
	{
	System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
	prop=new Properties();
	InputStream input;
	
	try
	{
		input = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
		prop.load(input);
	}
	catch (IOException e)
	{
		
		e.printStackTrace();
	}
			}

	/* Step:001  Launch  Open Cart application http://10.207.182.108:81/opencart/*/
	
	@Test
	public void openApp()
	{
		driver.get(prop.getProperty("url"));
		System.out.println("opencart app launched");
		 /* Step 002: Click on "Login" Link*/
		driver.findElement(By.xpath("(//a[text()='login'])")).click();
	}
	
	 /*---------------------------------------------------------------*/
	
	//Data Provider
	
	@DataProvider(name="Mydatprovide")
	public static Object[][] ReadingData() throws IOException
	{
		Object[][] obj= ReadingExcel.readExcel("LoginPage","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;
			
	}
	
	@DataProvider(name="giftvocherdata")
	public static Object[][] Giftvocherdataread() throws IOException
	{
		Object[][] obj= ReadingExcel.readExcel("TC005_giftvocher","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;
			
	}
	
	 /*---------------------------------------------------------------*/
	  /* This method is for opening the opencart application
	   * Enter Email Address and Password and click on "Login" Button.
	   * Step:003
	   * */
	
   @Test(dataProvider="Mydatprovide",dependsOnMethods = "openApp")
   public void loginapp(String Username,String Password, String changequantity, String Faultycomment)
   {
	   driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(Username);
	   System.out.println(Username);
	   driver.findElement(By.xpath(prop.getProperty("password"))).sendKeys(Password);
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
   
	@Test(dependsOnMethods="loginapp")
	public void Gift_Vouchers() throws InterruptedException, IOException
	{
		List<String> data=new ArrayList<String>();
		FileReader fr=new FileReader("D:\\SELENIUM_PRACTISE\\giftvocher_testdata.txt");
		BufferedReader br= new BufferedReader(fr);
		String x="";
		while ((x=br.readLine()) != null)
		{
	    data.add(x);	
		System.out.println(x);
		}
		System.out.println("List is" +data);
		br.close();
		//Click on "Gift Vouchers" from the extras - footer of the page.
		driver.findElement(By.linkText("Gift Vouchers")).click();
		System.out.println("Purchase a Gift Certificate Page is displayed.");
		driver.findElement(By.name("to_name")).sendKeys(data.get(0));
		driver.findElement(By.name("to_email")).sendKeys(data.get(1));
		driver.findElement(By.name("from_name")).sendKeys(data.get(2));
		driver.findElement(By.name("from_email")).sendKeys(data.get(3));
		driver.findElement(By.id("voucher-7")).click();
		driver.findElement(By.name("message")).sendKeys(data.get(4));
		driver.findElement(By.name("amount")).sendKeys(data.get(5));
		
		/*==================Step005 validation==============*/
		
		
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.className("button")).click();
		System.out.println("Thank you for purchasing a gift certificate");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println("Shopping Cart Page is displayed.");
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//div[@id='content']/form/div/table/tbody/tr/td[4]/a/img")).click();
		//System.out.println("The message displayed is: Your shopping cart is sempty");
		List<WebElement> shoppingcartprods= driver.findElements(By.xpath(".//*[@id='content']/form/div/table"));
		System.out.println("The no of products in the shopping cart is : " + shoppingcartprods.size());
		driver.findElement(By.xpath("//*[@id='content']/div[5]/div[2]/a")).click();
		System.out.println("Home page is displayed--1");
		driver.findElement(By.linkText("Contact Us")).click();
		driver.findElement(By.name("enquiry")).sendKeys(data.get(6));
		System.out.println("Please enter the Captcha : ");
		Scanner input=new Scanner(System.in);
		String Captcha=input.nextLine();
		driver.findElement(By.name("captcha")).clear();
		driver.findElement(By.name("captcha")).sendKeys(Captcha);
	    //driver.findElement(By.xpath(".//*[@name='captcha']")).sendKeys(Captcha);
		driver.findElement(By.xpath("//div[@id='content']/form/div[3]/div/input")).click();
		System.out.println("Contact us page is displayed");
		
		driver.findElement(By.xpath("//*[@id='content']/div[2]/div/a")).click();
		System.out.println("Home page is dispalyed--2");
		driver.findElement(By.linkText("Wish List")).click();
		System.out.println(" My Wish list is opened ");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println(" My account page is displayed--1 ");
		driver.findElement(By.linkText("Edit your account information")).click();
		System.out.println("My account information page is displayed");
		driver.findElement(By.name("telephone")).clear();
		driver.findElement(By.name("telephone")).sendKeys(data.get(7));
		driver.findElement(By.className("button")).click();
		System.out.println("Your account has been successfully updated.");
		driver.findElement(By.linkText("View your return requests")).click();
		System.out.println("Product Returns page is displayed");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='content']/div[2]/div[3]/div[3]/a/img")).click();
		System.out.println("Return Information page is displayed");
		String orderid = driver.findElement(By.xpath("//div[@id='content']/table[1]/tbody/tr/td[2]")).getText();
		System.out.println("The order id is : " + orderid);
		driver.findElement(By.linkText("Continue")).click();
		System.out.println("First continue button is clicked");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println("My Account Page is displayed--2");
		driver.findElement(By.linkText("Modify your address book entries")).click();
		System.out.println("Address Book page is displayed");
		driver.findElement(By.linkText("Edit")).click();
		System.out.println("Edit address tab is displayed.");
		//Edit address tab
		driver.findElement(By.name("firstname")).sendKeys(data.get(8));
		System.out.println("First name is changed");
		driver.findElement(By.name("lastname")).sendKeys(data.get(9));
		System.out.println("last name is changed");
		driver.findElement(By.name("company")).sendKeys(data.get(10));
		System.out.println("company name is changed");
		driver.findElement(By.name("company_id")).sendKeys(data.get(11));
		System.out.println("company id name is changed");
		driver.findElement(By.name("address_1")).sendKeys(data.get(1));
		System.out.println("address1 name is changed");
		driver.findElement(By.name("city")).sendKeys(data.get(1));
		System.out.println("city name is changed");
		driver.findElement(By.className("button")).click();
		System.out.println("Your address has been successfully updated");
		//Compare the address displayed and and the file contents.
		driver.findElement(By.linkText("Logout")).click();
		System.out.println("Account Logout page is displayed");
	}
	
   
   
   
    
   
   
   
   
   
   
	
}

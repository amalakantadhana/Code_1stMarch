package com.wipro.chcare.ccc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;

public class openCart007_Addingallproductsfromcategory
{
private static final long TIME_UNIT = 0;
private WebDriver driver;
private static int WAIT_TIME = 10000 ;
RegistrationPage RP;
WebDriverWait wait;

@BeforeClass
public void initialize()
{
	System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe");
	driver= new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
}

@Test(description = "Launch openCart application")
public void openApp()
{
	 driver.get("http://10.207.182.108:81/opencart/");
	  	  System.out.println("opencart app launched");

}

//Data Provider
@DataProvider(name="Mydatprovide")
public static  Object[][] ReadingData() throws IOException{
Object[][] obj=ReadingExcel.readExcel("LoginPage","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
return obj;	
}	
@DataProvider(name="Billingdetails")
public static  Object[][] ReadingBillingData() throws IOException{
Object[][] obj=ReadingExcel.readExcel("TC04_billing","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
return obj;	
}	

@Test (dataProvider="Mydatprovide",dependsOnMethods = "openApp") 	 
public void loginApp(String Username,String Password, String changequantity, String Faultycomment) throws IOException
{
	 RP = new RegistrationPage(driver);
	 RP.login.click();
	  // driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(Username);
	   RP.username.sendKeys(Username);
	  System.out.println(Username);
		//driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
		RP.password.sendKeys(Password);
		System.out.println(Password);
		RP.loginbutton.click();
		//driver.findElement(By.xpath("//input[@class='button']")).click();
		   
		   System.out.println("Logged in successfully");
		/*=====================
		 * Check point: User's first name as a link
		 */
			String e=driver.findElement(By.linkText("dhana")).getText();
				Assert.assertEquals("dhana", e);
		System.out.println("Validation is passed");
}
   @Test(dependsOnMethods = "loginApp")
   public void addingAllProducts() throws InterruptedException
   {
	   WebDriverWait wait = new WebDriverWait(driver, TIME_UNIT);
	   ArrayList<String> prodMessage = new ArrayList<String>();
	   driver.findElement(By.linkText("Phones & PDAs")).click();
	 List<WebElement> Prod = driver.findElements(By.xpath("//div[@id='content']/div[4]/div"));
	   		  int psize= Prod.size();
	   		  System.out.println(psize);
	   		  
	   		  driver.findElement(By.linkText("iPhone")).click();	   	   		  
	   		  driver.findElement(By.id("button-cart")).click();
	   		     		
			WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notification']/div")));
	   		// String succmsg= driver.findElement(By.xpath("//*[@id='notification']/div")).getText();
    		 		  System.out.println("The message is : " + successMsg);
    		 		 // Assert.assertEquals(successMsg, "Success: You have added iPhone to your shopping cart!");
    		 		 Assert.assertTrue(successMsg.getText().contains("Success"), "Product is not added to Cart");
    		   		driver.findElement(By.linkText("Shopping Cart")).click();
	   		System.out.println(" Shopping cart link is clicked");
	   		
	   		  
	   		 /*for(int i=1;i<=psize;i++)
	   		 {
	   			 driver.findElement(By.xpath("//*[@id='content']/div[4]/div["+ i+ "]/div[1]/div[1]/input")).click();
	   			 System.out.println(" Clicked on the product" + i );
	   			 Thread.sleep(2000);
	   			prodMessage.add(driver.findElement(By.xpath(".//*[@id='notification']/div")).getText());
	   			Thread.sleep(2000);
	   			System.out.println("The success message is : " + prodMessage);
	   		 	driver.findElement(By.xpath("//div[@id='notification']/div/img")).click();
	   		 }
	   		System.out.println("The success message is : " + prodMessage.get(0));
	   		
	   		if(driver.findElement(By.linkText("Palm Treo Pro")).isDisplayed());
	   				{
	   					driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr[1]/td[4]/a/img")).click();
	   		   		 	System.out.println("The Product with name HTC Touch HD is deleted");
	   		   		driver.findElement(By.xpath("//*[@id='container']/div[4]/img")).click();
	   			  	   
	   				}
	   				elseif(driver.findElement(By.linkText("HTC Touch HD")).isDisplayed());
	   		{
	   			driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr[1]/td[4]/a/img")).click();
			  	   	System.out.println("The Product with name Palm Treo Pro is deleted");
			  		driver.findElement(By.xpath("//*[@id='container']/div[4]/img")).click();
	   		}*/
	   		driver.findElement(By.linkText("Checkout")).click();
	   		System.out.println(" Checkout button is clicked");
 		   	driver.findElement(By.linkText("Checkout")).click();
	   		System.out.println(" Checkout button is clicked");
	   		
	   		try{
	   		Alert alert = driver.switchTo().alert();
	   		alert.accept();
	   		}
	   		catch (Exception e) {}
	   		// alert.getText();
	   			   		Thread.sleep(3000);
	   		driver.findElement(By.id("button-payment-address")).click();
	   		System.out.println(" Billing details Continue button is clicked");
	   		Thread.sleep(3000);
	   		driver.findElement(By.id("button-shipping-address")).click();
	   		System.out.println("Deliver details continue button is clicked");
	   		Thread.sleep(3000);
	   		driver.findElement(By.id("button-shipping-method")).click();
	   		System.out.println("Delivry method continue button is clicked");
	   		Thread.sleep(3000);
	   		driver.findElement(By.name("agree")).click();
	   		driver.findElement(By.id("button-payment-method")).click();
	   		Thread.sleep(3000);
	   		System.out.println("Payment method is clicked");
	   		driver.findElement(By.id("button-confirm")).click();
	   		Thread.sleep(3000);
	   		System.out.println("Confirm order is clicked");
	   		String message=driver.findElement(By.xpath(".//*[@id='content']/h1")).getText();
	   		System.out.println("the message is :" + message );
	   		driver.findElement(By.linkText("Logout")).click();
	   		
   }
 
   
}

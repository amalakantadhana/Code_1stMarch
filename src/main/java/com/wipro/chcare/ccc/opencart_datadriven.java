package com.wipro.chcare.ccc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;

public class opencart_datadriven {
	 private static final String depends = null;
	  private static final String dependsonMethods = null;
	  private WebDriver driver;
	    private Properties prop;
	    private static int WAIT_TIME = 10000 ;
	     
	    RegistrationPage RP;
	    GenericMethods GM;
	    //String url;
	    	  		
	   
	 	@SuppressWarnings("resource")
		@BeforeClass
	 	public void intialize()
	 	{	
	 System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/Utils/chromedriver.exe" );
	 	   driver=new ChromeDriver();
	 	   
	 	             driver.manage().window().maximize();
	 	 	   driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
	 	 	   prop= new Properties();
	 	 		InputStream input;
	 	 		try {
	 	 			//input = new FileInputStream("D:\\SELENIUM_PRACTISE\\Assignment1\\src\\Assignment1\\config.properties");
	 	 			input= new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
	 	 			prop.load(input);
	 	 		} catch (IOException e) {
	 	 			// TODO Auto-generated catch block
	 	 			e.printStackTrace();
	 	 				 	 		}
	 	 			        }
	 		
	 	
	 	/* This is for launching the web applo*/
	   @Test(description = "Launch openCart application")
	   public void openApp() throws InterruptedException
	   {	
		 //  logger = extent.startTest("openApp");
		   //Test Step :001
	 	  driver.get(prop.getProperty("url"));
	 	  	  System.out.println("opencart app launched for TC01");
	 	 // 	logger.log(LogStatus.PASS, "Method \"openApp\" is passed");
	 			//Thread.sleep(5000);
	 	
	 			} 
	    /*---------------------------------------------------------------*/
	   
	
	     /* This method is for registration*/
	   
	   @DataProvider(name="datadriven")
	    public static  Object[][] ReadingReviewData1() throws IOException{
		Object[][] obj=ReadingExcel.readExcel("datadriven","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;	
	}
	   @Test(dataProvider="datadriven",dependsOnMethods= "openApp" )
	   public void registration(String fname, String lname,String email,String Telephone,String fax, String Company, String companyid
			   ,String add1, String add2, String city, String Postcode, String Country, 
			   String Region, String Password, String confirmpassword) throws InterruptedException
	   {
		   		   
		  RP = new RegistrationPage(driver);
		  GM = new GenericMethods();
		   String emailadd= System.nanoTime()+email;
		   System.out.println("The changed email is " + emailadd);
		  
		   // //Test Step :002
  
	     RP.Register.click();		   
	 	RP.fnameR.sendKeys(fname);
	 	driver.findElement(By.name("lastname")).sendKeys(lname);
	 	
	 	driver.findElement(By.name("email")).sendKeys(emailadd);
	 	driver.findElement(By.name("telephone")).sendKeys(Telephone);
	 	driver.findElement(By.name("fax")).sendKeys(fax);
	 	driver.findElement(By.name("company")).sendKeys(Company);
	 	driver.findElement(By.name("company_id")).sendKeys(companyid);
	 	driver.findElement(By.name("address_1")).sendKeys(add1);
	 	driver.findElement(By.name("address_2")).sendKeys(add2);
	 	driver.findElement(By.name("city")).sendKeys(city);
	 	driver.findElement(By.name("postcode")).sendKeys(Postcode);
	 	GM.DropDwnText(RP.CountryIdR, Country);
	 		GM.DropDwnText(RP.zone_idRR, Region);
	 	driver.findElement(By.name("password")).sendKeys(Password);
	 	driver.findElement(By.name("confirm")).sendKeys(confirmpassword);	 	
	 	driver.findElement(By.xpath(".//*[@id='content']/form/div[4]/table/tbody/tr/td[2]/input[2]"));
	 	// Test Step: 004
	 	driver.findElement(By.name("agree")).click();
	 	//driver.findElement(By.xpath(".//*[@id='content']/form/div[5]/div/input[2]")).click();
	 	driver.findElement(By.className("button")).click();
	 	driver.findElement(By.linkText("Logout")).click();
	
	 	 	
	 	
	 		 		   }
	   
	  	 
}

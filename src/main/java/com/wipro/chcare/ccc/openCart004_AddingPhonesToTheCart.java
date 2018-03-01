package com.wipro.chcare.ccc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

public class openCart004_AddingPhonesToTheCart extends ExtentReportsBaseClass {
	private static final String depends = null;
	private static final String dependsonMethods = null;
	private WebDriver driver;
	  private Properties prop;
	private String value;
	  private static int WAIT_TIME = 10000 ;
	  List<String> ShoppingCart;
	  List<String> Checkout;
	  String orderidp;
	  String currentorderid;
	  String url;
	  /*
	   This is method for setting path for Chrome driver and implicit wait and reading properties file
	   */
		@BeforeClass
		public void intialize()
		{	
		/*System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe" );
		   driver=new ChromeDriver();*/
		   url = "http://10.159.34.131:4444/wd/hub";//http://10.159.34.131:4444/
	        try {
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            capabilities.setBrowserName("chrome");
	            capabilities.setPlatform(Platform.WINDOWS);
	            driver = new RemoteWebDriver(new URL(url), capabilities);
		   driver.manage().window().maximize();
		   driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
		   prop= new Properties();
			InputStream input;
			try {
				input = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
				prop.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(Exception e)
        {
            e.printStackTrace();
        }
			}
		
		/* Step:001  Launch  Open Cart application http://10.207.182.108:81/opencart/*/
	  @Test(description = "Launch openCart application")
	  public void openApp() throws InterruptedException
	  {		
		  logger = extent.startTest("productComparison");
		  driver.get(prop.getProperty("url"));
		  	  	  System.out.println("opencart app launched");
				//Thread.sleep(5000);
		  	  	  
		 /* Step 002: Click on "Login" Link*/
		driver.findElement(By.xpath("(//a[text()='login'])")).click();
		//Thread.sleep(3000);
		logger.log(LogStatus.PASS, "Method \"openApp\" is passed");
				} 
	  
	    /*---------------------------------------------------------------*/
	  
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
			
	  /*---------------------------------------------------------------*/
	  /* This method is for opening the opencart application
	   * Enter Email Address and Password and click on "Login" Button.
	   * Step:003
	   * */
	  
	  @Test (dataProvider="Mydatprovide",dependsOnMethods = "openApp")
	  public void loginApp(String Username,String Password, String changequantity, String Faultycomment) throws IOException
	  {
		  logger = extent.startTest("loginApp");
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
			logger.log(LogStatus.PASS, "Method \"loginApp\" is passed");

	  }
	    @Test (dependsOnMethods ="loginApp")
	  public void homePageNavigation() throws InterruptedException
	  {
	    	logger = extent.startTest("homePageNavigation");
		  driver.findElement(By.xpath(prop.getProperty("homepage"))).click();
		  System.out.println("Clicked on Home Page");
		  logger.log(LogStatus.PASS, "Method \"homePageNavigation\" is passed");
	  }
	    @Test(dataProvider="Billingdetails" ,dependsOnMethods="homePageNavigation")
	public void AddingPhonesToTheCart(String firstname, String lastname, String company, String company_id, String address_1, String address_2,
			String city, String postcode , String country_id, String zone_id, String comment,
			String couponcode) throws InterruptedException, IOException
	{
	    	logger = extent.startTest("AddingPhonesToTheCart");
		   //driver.findElement(By.linkText("Home")).click();
		   driver.findElement(By.xpath("(//a[contains(.,'Home')])[1]")).click();
		   System.out.println("Click on home page link");
		   driver.findElement(By.id("slideshow0")).click();
		   System.out.println("Slideshow image is clicked");
		   Thread.sleep(5000);
		   driver.findElement(By.id("image")).click();
		   System.out.println("Image is opened");
		   Thread.sleep(5000);
		   /*==============================Step006====================*/
		   String currenimagecount= driver.findElement(By.xpath("//div[@id='cboxCurrent']")).getText();
		   System.out.println("The text on the image window is " + currenimagecount );
		 String imagecount= currenimagecount.substring(11);
		  int countint= Integer.parseInt(imagecount);
		   System.out.println("The no of images are "  + countint);
		   
		   File myfileimage = new File("D:\\Output\\TC004_step06_output.txt");
			BufferedWriter pwimage = new BufferedWriter(new FileWriter(myfileimage));
			pwimage.write("The no of images is : " + countint);
			pwimage.close();
		   
		   Thread.sleep(5000);
		   		    for(int i = 1;i<=countint;i++)
		   {
		    	driver.findElement(By.xpath("//div[@id='cboxNext']")).click();
			    System.out.println("Image is " + i);
		   } 
		   driver.findElement(By.id("cboxClose")).click();
		   System.out.println("Window is closed");
		   Thread.sleep(4000);
		   driver.findElement(By.id("button-cart")).click();
		   System.out.println("You have added the phone must be displayed.");
		   driver.findElement(By.xpath("//div[@id='notification']/div/img")).click();
		   driver.findElement(By.xpath("//div[@class='links']/a[4]")).click();
		   Thread.sleep(5000);
		   driver.findElement(By.id("shipping_estimate")).click();
		   System.out.println("Estimate Shipping and Taxes are clicked");
		   driver.findElement(By.id("button-quote")).click();
		   Thread.sleep(3000);
		   driver.findElement(By.id("flat.flat")).click();
		   System.out.println(" Radio button is clicked");
		   driver.findElement(By.id("button-shipping")).click();
		   System.out.println("Clicked on Apply shopping");
		   System.out.println("Your shipping estimate has been applied! and Total amoutn is displayed");
		   /*
		    * Step 12-----Total amount should be saved in the file.
		    */
		   String totalcost =driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr/td[6]")).getText();

		   File myfile = new File("D:\\Output\\TC004_step12_output.txt");
		   String totalcost1 =driver.findElement(By.xpath("//*[@id='content']/form/div/table/tbody/tr/td[6]")).getText();
		 	System.out.println("Total coste is " +totalcost1);
	        BufferedWriter pw = new BufferedWriter(new FileWriter(myfile));
	        pw.write("Total cost is " + totalcost1);
	        pw.close();
		   
		   
		   driver.findElement(By.id("use_coupon")).click();
		   System.out.println("use coupon radio button is clicked");
		   driver.findElement(By.name("coupon")).sendKeys(couponcode);
		   System.out.println("Coupon is entered");
		   driver.findElement(By.xpath("//*[@id='coupon']/form/input[3]")).click();
		   System.out.println("Clicked on Apply Coupon button");
		   String Warning=driver.findElement(By.xpath("//*[@id='container']/div[4]")).getText();
		   System.out.println("The Warning mesage is "+ Warning);
		   File myfilewarning = new File("D:\\Output\\TC004_step14_output.txt");
		   BufferedWriter pwwarn = new BufferedWriter(new FileWriter(myfilewarning));
		   pwwarn.write("The warning message is " + Warning);
		   pwwarn.close();
		   driver.findElement(By.xpath("//div[@class='warning']/img")).click();
		   System.out.println("Notification is closed");
		   Thread.sleep(5000);
		    //driver.findElement(By.xpath("(//a[text()='Checkout'])")).click();
		    driver.findElement(By.linkText("Checkout")).click();
		    System.out.println("Clicked on Checkout button");
		    Thread.sleep(5000);
		    logger.log(LogStatus.PASS, "Method \"AddingPhonesToTheCart\" is passed");
	}
		//Billing details -2
	
	@Test(dataProvider="Billingdetails",dependsOnMethods = "AddingPhonesToTheCart")
	public void billingDetails(String firstname, String lastname, String company, String company_id, String address_1, String address_2,
			String city, String postcode , String country_id, String zone_id, String comment, String couponcode ) throws InterruptedException, IOException

	{
		logger = extent.startTest("billingDetails");
		    driver.findElement(By.id("payment-address-new")).click();
		    System.out.println("New address is selected");
		    driver.findElement(By.name("firstname")).sendKeys(firstname); 
		    driver.findElement(By.name("lastname")).sendKeys(lastname);
		    driver.findElement(By.name("company")).sendKeys(company);
		    driver.findElement(By.name("company_id")).sendKeys(company_id);
		  //  driver.findElement(By.name("company_id")).sendKeys("568552"); 
		    driver.findElement(By.name("address_1")).sendKeys(address_1);
		    driver.findElement(By.name("address_2")).sendKeys(address_2);
		    driver.findElement(By.name("city")).sendKeys(city);
		    driver.findElement(By.name("postcode")).sendKeys(postcode);
		    
		    WebElement country=driver.findElement(By.name("country_id"));
		    Select dropdown= new Select(country);
		    dropdown.selectByVisibleText(country_id);
		    System.out.println("Country is selected");
		    WebElement state= driver.findElement(By.name("zone_id"));
		    Select dropdown1 = new Select(state);
			   dropdown1.selectByVisibleText(zone_id);
			   System.out.println("State is selected");
		driver.findElement(By.id("button-payment-address")).click();
		System.out.println("2nd is Displayed");
		Thread.sleep(5000);
		//Delivery Details -3
		//driver.findElement(By.id("shipping-address-new")).click();
		
		WebElement listadd=driver.findElement(By.xpath("//div[@id='shipping-existing']//select[@name='address_id']"));
		Select dropdown2=new Select(listadd);
		dropdown2.selectByIndex(dropdown2.getOptions().size()-1);
		
	   driver.findElement(By.id("button-shipping-address")).click();
		System.out.println("3rd is Displayed");
		Thread.sleep(5000);
		
		//Delivery method -4
		driver.findElement(By.id("button-shipping-method")).click();
		System.out.println("4th is Displayed");
		Thread.sleep(5000);
		//Payment method
	//	driver.findElement(By.xpath("//textarea[@name='comment']")).sendKeys("It is processing");
		//driver.findElement(By.name("comment")).click();
		driver.findElement(By.xpath("(//textarea[@name='comment'])[2]")).sendKeys(comment);
		//driver.findElement(By.name("comment")).sendKeys("It is processing");
		System.out.println("Comments added");
		driver.findElement(By.linkText("Terms & Conditions")).click();
		System.out.println("Clicked on the Terms & Conditions link");
		
	/*
	 * ====================================Step 20=========================
	 */
		Thread.sleep(5000);
		String terms=driver.findElement(By.xpath("//div[@id='cboxLoadedContent']/h1")).getText();
		//System.out.println(terms.length());
		System.out.println(terms);
		   File myfileterm = new File("D:\\Output\\TC004_step20_output.txt");
		   	System.out.println("The message is " +terms);
	        BufferedWriter pwterm = new BufferedWriter(new FileWriter(myfileterm));
	        pwterm.write("The count is  " + terms.length());
	        pwterm.close();
		
		driver.findElement(By.id("cboxClose")).click();
		System.out.println("Window Closed");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		System.out.println("Check box checked");
	     driver.findElement(By.id("button-payment-method")).click();
	     Thread.sleep(5000);
	     /*=========Step21 Validation-================*/
	     
	     List<String> Checkouttotalprice= new ArrayList<String>();
	       String Price=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[4]")).getText();
	   String Total=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[5]")).getText();
	   Checkouttotalprice.add(Price);
	   Checkouttotalprice.add(Total);
	   FileOutputStream ExcelOutPrice= new FileOutputStream("D:\\Output\\TC004_step21_output.xlsx");
		 XSSFWorkbook workbook = new XSSFWorkbook();
		 XSSFSheet sheet=workbook.createSheet();
		System.out.println("The Size of the List is : " + Checkouttotalprice.size());  
		 for(int i=0;i<Checkouttotalprice.size();i++)
		 {
		         sheet.createRow(i).createCell(0).setCellValue(Checkouttotalprice.get(i));
		 }
		    
	   
	      driver.findElement(By.xpath("//*[@id='payment-address']/div[1]/a")).click();
	     Thread.sleep(3000);
	     WebElement listadd1=driver.findElement(By.xpath("//div[@id='payment-existing']//select[@name='address_id']"));
			Select dropdown3=new Select(listadd1);
			dropdown3.selectByIndex(0);
				     //Second time
			driver.findElement(By.id("button-payment-address")).click();
			System.out.println("2nd is Displayed");
			Thread.sleep(5000);
			
			//Delivery Details -3
			driver.findElement(By.id("button-shipping-address")).click();
			System.out.println("3rd is Displayed");
			Thread.sleep(5000);
			//Delivery method -4
			driver.findElement(By.id("button-shipping-method")).click();
			System.out.println("4th is Displayed");
			Thread.sleep(5000);
			//Payment method
			driver.findElement(By.linkText("Terms & Conditions")).click();
			System.out.println("Clicked on the Terms & Conditions link");
			driver.findElement(By.id("cboxClose")).click();
			System.out.println("Window Closed");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@name='agree']")).click();
			System.out.println("Check box checked");
		     driver.findElement(By.id("button-payment-method")).click();
		     Thread.sleep(5000);
						
	     //Confirm order
	     driver.findElement(By.id("button-confirm")).click();
	     driver.findElement(By.xpath("//div[@id='welcome']/a[2]")).click();
		   System.out.println("Logged out successfully.");
		   String logout=driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
	       System.out.println(logout);
		   if(logout.contains("Account Logout"));
	       {
	    	   System.out.println("Validation is passed"); 
	       } 
	       
	       driver.close();
	       logger.log(LogStatus.PASS, "Method \"billingDetails\" is passed");
	}

}

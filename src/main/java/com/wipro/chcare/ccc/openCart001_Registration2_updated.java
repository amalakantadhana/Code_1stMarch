package com.wipro.chcare.ccc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;


public class openCart001_Registration2_updated extends ExtentReportsBaseClass{
	  private static final String depends = null;
	  private static final String dependsonMethods = null;
	  private WebDriver driver;
	    private Properties prop;
	    private static int WAIT_TIME = 10000 ;
	     
	    RegistrationPage RP;
	    GenericMethods GM;
	    String url;
	    
	    /*
	    This is method for setting path for Chrome driver and implicit wait and reading properties file
	    */
	 	@SuppressWarnings("resource")
		@BeforeClass
	 	public void intialize()
	 	{	
	 /*	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/Utils/chromedriver.exe" );
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
	 	 			//input = new FileInputStream("D:\\SELENIUM_PRACTISE\\Assignment1\\src\\Assignment1\\config.properties");
	 	 			input= new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
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
	 	
	 	/* This is for launching the web applo*/
	   @Test(description = "Launch openCart application")
	   public void openApp() throws InterruptedException
	   {	
		   logger = extent.startTest("openApp");
		   //Test Step :001
	 	  driver.get(prop.getProperty("url"));
	 	  	  System.out.println("opencart app launched for TC01");
	 	  	logger.log(LogStatus.PASS, "Method \"openApp\" is passed");
	 			//Thread.sleep(5000);
	 	//driver.findElement(By.xpath("(//a[text()='login'])")).click();
	 	//Thread.sleep(3000);
	 			} 
	    /*---------------------------------------------------------------*/
	   
	//Data Provider
			@DataProvider(name="Mydatprovide")
		    public static  Object[][] ReadingData() throws IOException{
			Object[][] obj=ReadingExcel.readExcel("testcase001","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
			return obj;	
		}	
			@DataProvider(name="Reviewproduct")
		    public static  Object[][] ReadingReviewData() throws IOException{
			Object[][] obj=ReadingExcel.readExcel("review","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
			return obj;	
		}			
	     
	     /* This method is for registration*/
	   @Test(dataProvider="Mydatprovide",dependsOnMethods= "openApp" )
	   public void registration(String fname, String lname,String email,String Telephone,String fax, String Company, String companyid
			   ,String add1, String add2, String city, String Postcode, String Country, 
			   String Region, String Password, String confirmpassword) throws InterruptedException
	   {
		   
		  RP = new RegistrationPage(driver);
		  GM = new GenericMethods();
		   String emailadd= System.nanoTime()+email;
		   System.out.println("The changed email is " + emailadd);
		   logger = extent.startTest("registration");
		   // //Test Step :002
     //driver.findElement(By.xpath("//a[@href='http://10.207.182.108:81/opencart/index.php?route=account/register']")).click();
	     RP.Register.click();		   
		   //Validation
	     driver.findElement(By.xpath("//*[contains(text(),'account')]")).click();
	 	WebDriverWait wdw=new WebDriverWait(driver,30);
	 	wdw.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='logo']/a/img")));
	 	// Test Step : 003
	 //	driver.findElement(By.name("firstname")).sendKeys(fname);
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
	 	
	 	//Validation Parts- 1st checkpoint
		   String  fname1=driver.findElement(By.name("firstname")).getAttribute("value");
		 	System.out.println("attribute data is"+fname1);
		 	Assert.assertEquals(fname, fname1, "first name validation unSuccessful");
		 	 String  lname1=driver.findElement(By.name("lastname")).getAttribute("value");
			 	System.out.println("attribute data is"+lname1);
			 	Assert.assertEquals(lname, lname1, "Last name validation unSuccessful");
			String  eml=driver.findElement(By.name("email")).getAttribute("value");
				System.out.println("attribute data is"+eml);
				Assert.assertEquals(emailadd, eml, "Email validation unSuccessful");
				String tphone=driver.findElement(By.name("telephone")).getAttribute("value");
				System.out.println("attribute data is"+eml);
				Assert.assertEquals(Telephone, tphone, "Telephone No validation unSuccessful");
				String faxno=driver.findElement(By.name("fax")).getAttribute("value");
				System.out.println("attribute data is"+faxno);
				Assert.assertEquals(fax, faxno, "Fax No validation unSuccessful");
				
	 	
	 /*	Select ctry=new Select(driver.findElement(By.name("country_id")));
	 	ctry.selectByVisibleText(Country);
	 	Select reg=new Select(driver.findElement(By.name("zone_id")));
	 	reg.selectByVisibleText(Region);*/
	 	/*============Implemented the page object Model ==============*/
	 		GM.DropDwnText(RP.CountryIdR, Country);
	 		GM.DropDwnText(RP.zone_idRR, Region);
	 	driver.findElement(By.name("password")).sendKeys(Password);
	 	driver.findElement(By.name("confirm")).sendKeys(confirmpassword);
		driver.findElement(By.xpath(".//*[@id='content']/form/div[4]/table/tbody/tr/td[2]/input[2]"));
	 	driver.findElement(By.name("agree")).click();
	 	//driver.findElement(By.xpath(".//*[@id='content']/form/div[5]/div/input[2]")).click();
	 	driver.findElement(By.className("button")).click();   		 
	   	 		
	 	//Validation of 2nd Checkpoint
	 	WebDriverWait wdw1=new WebDriverWait(driver,30);
	 	wdw1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Congratulations')]")));
	 	driver.findElement(By.xpath("//*[@id='header']/div[5]/a[1]")).click();
	 	driver.findElement(By.xpath("//*[contains(text(),'Home')]")).click();
	 	 //Clicking on samsung Galaxy tab of home page
	 		 	Thread.sleep(2000);
	 		 	//Test Step: 005
	 	
	 	
	 	/*----------------------------------------------------*/
	 	//logger.log(LogStatus.PASS, "Method \"registration\" is passed");
	   }
	   
	   // Test Step: 006 : Test Step: 007 : // Test Step: 008
	 	@Test(dataProvider="Reviewproduct",dependsOnMethods="registration")
	 	public void reviewProd(String Yourname,String Rating, String Review1) throws InterruptedException
	 	{
	 		 logger = extent.startTest("reviewProd");

	 		 driver.findElement(By.xpath("//*[contains(text(),'Reviews')]")).click();
	 	//passing the data from excel
	 	driver.findElement(By.name("name")).clear();
	 	driver.findElement(By.name("name")).sendKeys(Yourname);
	 	driver.findElement(By.xpath(".//*[@id='tab-review']/textarea")).clear();
	 	driver.findElement(By.xpath(".//*[@id='tab-review']/textarea")).sendKeys(Review1);
	 	int RatingInt = Integer.parseInt(Rating);
	 	driver.findElement(By.xpath(".//*[@id='tab-review']/input["+(RatingInt+1)+"]")).click();
	 	Thread.sleep(10000);
	 	/*System.out.println("Please Enter Captha:");
	 	Thread.sleep(5000);
	    Scanner sc=new Scanner(System.in);
	 	String captcha=sc.nextLine();
	 	driver.findElement(By.xpath(".//*[@name='captcha']")).clear();
	 	driver.findElement(By.xpath(".//*[@name='captcha']")).sendKeys(captcha);*/
	 	 	driver.findElement(By.id("button-review")).click();
	 	 	Thread.sleep(3000);
	 	 	System.out.println("length of the review1 is "  +Review1.length());
	 	 	if(Review1.length()<20)
	 	 	{
	 	    String Warningmsg=driver.findElement(By.xpath(".//div[@id='tab-review']/div[2]")).getText();
	 		System.out.println("The message is " + Warningmsg);
	 		Assert.assertEquals("Warning: Review Text must be between 25 and 1000 characters!", Warningmsg);
	 		System.out.println("validation is not successsfull");
	 		 	}else
	 		 	{
	 		 	  String Warningmsg=driver.findElement(By.xpath(".//div[@id='tab-review']/div[2]")).getText();
	 		 		System.out.println("The message is " + Warningmsg);
	 		 		Assert.assertEquals("Thank you for your review. It has been submitted to the webmaster for approval.", Warningmsg);
	 		 		System.out.println("validation is successsfull");
	 		 	}
	 	 	logger.log(LogStatus.PASS, "Method \"reviewProd\" is passed");
	 		 	}
	 	 // Test Step: 009, 10
	 	@Test(description="add to Wish list",dependsOnMethods="reviewProd")
	 	public void addToWishList() throws InterruptedException, IOException
	 	{
	 		 logger = extent.startTest("addToWishList");
	 	driver.findElement(By.linkText("Add to Wish List")).click();
	 	Thread.sleep(5000);
	 	/*WebDriverWait wdw2=new  WebDriverWait(driver,100);
	 	wdw2.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='notification']/div/img"))).click(); */
	  //  driver.findElement(By.xpath("//div[@id='notification']/div/img")).click();
	    Thread.sleep(3000);
	    //This is a Wish list link  // Test Step: 011
	    driver.findElement(By.id("wishlist-total")).click();
	    /*Checkpoint to validate the product number in the link and 
	    check point to validate number of table records in the table - and both had to be compared for the success. */
	 	    String wishlisttext=driver.findElement(By.id("wishlist-total")).getText();
	    System.out.println("The text of the Wishlist is " + wishlisttext);
	    String wishListItems=wishlisttext.substring(11,wishlisttext.length()-1);
	    
	    int wishListItemInt= Integer.parseInt(wishListItems);
	    System.out.println("wishList Items are:" +wishListItems);
	    
	    List<WebElement> WishlistCount =driver.findElements(By.xpath(".//*[@id='content']/div[2]/table/tbody"));
	    System.out.println("List Size is:  "+WishlistCount.size());
	    
	    Assert.assertEquals(WishlistCount.size(), wishListItemInt, "WishList Items are matching");
	    
		//Writing to a flat file
	    
	 // Test Step: 012 ,13
	 	driver.findElement(By.xpath(".//*[@id='currency']/a[2]")).click();
	 	//File myfile = new File("D:\\SELENIUM_PRACTISE\\Unit_price.txt");
	 	File myfile = new File(System.getProperty("user.dir") + "/target/OutputFiles/Unit_price.txt");
	 	String sterling=driver.findElement(By.xpath(".//*[@id='wishlist-row49']/tr/td[5]/div")).getText();
	 	System.out.println("Unit Price is" +sterling);
        BufferedWriter pw = new BufferedWriter(new FileWriter(myfile));
        pw.write("The Unit price is " + sterling);
        
     // Test Step: 014 ,15
	 	driver.findElement(By.xpath(".//*[@id='currency']/a[1]")).click();
	 	String euro=driver.findElement(By.xpath(".//*[@id='wishlist-row49']/tr/td[5]/div")).getText();
	 	System.out.println("Unit Price is" + euro);
        pw.newLine();
        pw.append("The Unit price is " + euro);
        
     // Test Step: 016 ,17
	 	driver.findElement(By.xpath(".//*[@id='currency']/a[3]")).click();	
	 	String dollar=driver.findElement(By.xpath(".//*[@id='wishlist-row49']/tr/td[5]/div")).getText();
	 	System.out.println("Unit Price is" + dollar);
	  	pw.newLine();
        pw.append("The Unit price is " + dollar);
        pw.close();
        
        // Test Step: 018
	 	driver.findElement(By.xpath(".//*[@id='wishlist-row49']/tr/td[6]/img")).click();
	 	WebDriverWait wdw3=new  WebDriverWait(driver,30);
	 	wdw3.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='notification']/div/img"))).click();
	 	driver.findElement(By.xpath(".//*[@id='wishlist-row49']/tr/td[6]/a/img")).click();
	 	WebDriverWait wdw4=new  WebDriverWait(driver,30);
	 	wdw4.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='container']/div[4]/img"))).click();
	 	driver.findElement(By.xpath(".//*[@id='content']/div[3]/div/a")).click();
	 	driver.findElement(By.xpath(".//*[@id='welcome']/a[2]")).click();
	 	//Checkpoint for logout message
	 	String logout=driver.findElement(By.xpath("//*[@id='content']/h1")).getText();
	 	System.out.println(logout);
	 	Assert.assertEquals("Account Logout", logout);
	 	//Checkpoint for login message
	 	String loginmessage=driver.findElement(By.xpath("//div[@id='welcome']/a[1]")).getText();
	 	System.out.println(loginmessage);
	 	Assert.assertEquals("login", loginmessage, "Validation is not passed");
        driver.close();
        logger.log(LogStatus.PASS, "Method \"addToWishList\" is passed");
	 	}
	     
}

package com.wipro.chcare.ccc;

import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class openCart002_OrderHistory extends ExtentReportsBaseClass{
	
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
	//System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe" );
	  // driver=new ChromeDriver();
	   //********************************Selenium Grid******************
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
		} catch (IOException e) 
		{
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
	  logger = extent.startTest("openApp");
	  driver.get(prop.getProperty("url"));
	  	  	  System.out.println("opencart app launched for Test case002");
	  	  	logger.log(LogStatus.PASS, "Method \"openApp\" is passed");
			//Thread.sleep(5000);
	  	  	  
	 /* Step 002: Click on "Login" Link*/
	driver.findElement(By.xpath("(//a[text()='login'])")).click();
	//Thread.sleep(3000);
			} 
  
    /*---------------------------------------------------------------*/
  
//Data Provider
		@DataProvider(name="Mydatprovide")
	    public static  Object[][] ReadingData() throws IOException{
		Object[][] obj=ReadingExcel.readExcel("LoginPage","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;	
	}	
	
  /*---------------------------------------------------------------*/
  /* This method is for opening the opencart application
   * Enter Email Address and Password and click on "Login" Button.
   * Step:003
   * */
  
  @Test (dataProvider="Mydatprovide",dependsOnMethods = "openApp")
  public void loginApp(String Username,String Password,String changequantity
,String Faultycomment) throws IOException
  {
	  logger = extent.startTest("loginApp");
	   driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(Username);
	  System.out.println(Username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
		System.out.println(Password);
		driver.findElement(By.xpath("//input[@class='button']")).click();
		/*=====================
		 * Taking the order id after the login
		 */
		driver.findElement(By.linkText("Order History")).click();
		String orderidp= driver.findElement(By.className("order-id")).getText();
		System.out.println("The order id after login is  : " + orderidp);
		String e=driver.findElement(By.linkText("dhana")).getText();
				Assert.assertEquals("dhana", e);
		System.out.println("Validation is passed");
		logger.log(LogStatus.PASS, "Method \"loginApp\" is passed");
		  }  
  
  /*
   *  Step :004   Click the Home Page
   *  Step :005 Click any items displayed  under 'Featured' on the Home page.
   *  Step: 006 Click on the Related Products tab on the page.
   *  Step: 007 Click on Add to Cart for the related product.
   */
    @Test (dependsOnMethods ="loginApp")
  public void homePageNavigation() throws InterruptedException
  {
    	 logger = extent.startTest("homePageNavigation");
	  driver.findElement(By.xpath(prop.getProperty("homepage"))).click();
	  System.out.println("Clicked on Home Page");
	  /*----------------------------------------------------------------------------- 
	   * Validation:
       1.If there is no related product tab, displayed a warning message in the result view else proceed further.
       2.Count the number of items not having related product tab.
	   * 	  	   * */
	  //The list has the no of products of the featured tab
	  List<WebElement> Products = driver.findElements(By.xpath(".//*[@id='content']/div[2]/div[2]/div/div"));
	  int psize= Products.size();
	  
	  int Count=0;
	  System.out.println("The size is " + psize);
	  for( int i=1; i<=psize;i++)
	  {
		  driver.findElement(By.xpath("//*[@id='content']/div[2]/div[2]/div/div["+ i + "]/div[1]/a/img")).click();
		  
		List<WebElement> tab=driver.findElements(By.xpath(".//*[@id='tabs']/a"));
		  
		/*==========Tabs of the Product===============*/
		
		 for(int j=0;j<tab.size();j++){
			 
			 String TabData=tab.get(j).getText();
			 System.out.println("The tab name is "  + TabData);
			 if(TabData.contains("Related Products"))
			 {
				 driver.findElement(By.xpath("//a[contains(text(),'Related Products')]")).click();
				  driver.findElement(By.linkText("Add to Cart"));
				  System.out.println("Add to cart button is clicked for this product");
				  Count++;
			 }			
			 else
			 { }			
					 }  			
		 System.out.println("The related prducts tab not found");	  
		  driver.findElement(By.xpath("//*[@id='content']/div[1]/a[1]")).click();
		  	  }
	  /* ------------------------------------------------------------------------------- */
	  System.out.println("The related products count is " + Count); 
	  System.out.println("The no of products does not have related prod tab is  " + (psize-Count));
	  driver.findElement(By.xpath(prop.getProperty("featureditem"))).click();
	  System.out.println("Clicked on the featured item");
	    
	  driver.findElement(By.xpath(prop.getProperty("relatedproducts"))).click();
	   	  
	  System.out.println("Clicked on the Related Products");
	  driver.findElement(By.xpath(prop.getProperty("addtocart"))).click();
	  System.out.println("Clicked on add to cart at the bottom");
	   Thread.sleep(3000);
	   logger.log(LogStatus.PASS, "Method \"homePageNavigation\" is passed");
  }
  
    /*
     * Step:008  Click on Shopping Cart link displayed in the top right corner of the page.
     * Step:009  Change the quantity of the product.
     * Step : 010  Click on the update icon.
         */
    @Test (dataProvider="Mydatprovide",dependsOnMethods="homePageNavigation")
  public void addToShoppingCart89and10(String Username,String Password,String changequantity,String Faultycomment)
  {
    	 logger = extent.startTest("addToShoppingCart89and10");
	  driver.findElement(By.linkText(prop.getProperty("shoppingcart"))).click();
	   System.out.println("Clicked on the shopping cart link at the right top");
	   driver.findElement(By.xpath(prop.getProperty("changequantity"))).clear();
	   System.out.println("Cleared the Value in the text box");
	   driver.findElement(By.xpath(prop.getProperty("changequantity"))).sendKeys(changequantity);
	   driver.findElement(By.xpath(prop.getProperty("update"))).click();
	   System.out.println("quantity is Updated successfully");
	   
	   //Checkpoint :
	   String Prodname1=driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr/td[2]/a")).getText();
	   System.out.println(Prodname1);
	   String Model1=driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr/td[3]")).getText();
	   System.out.println(Model1);
	   String quantity1 =driver.findElement(By.xpath(prop.getProperty("changequantity"))).getAttribute("value");
	   System.out.println(quantity1);
	   String price1 =driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr/td[5]")).getText();
	   System.out.println(price1);
	   String total1 = driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr/td[6]")).getText();
	   System.out.println(total1);
	    ShoppingCart=new ArrayList<String>();
	   ShoppingCart.add(Prodname1);
	   ShoppingCart.add(Model1);
	   ShoppingCart.add(quantity1);
	   ShoppingCart.add(price1);
	   ShoppingCart.add(total1);
	   System.out.println(ShoppingCart);
	   logger.log(LogStatus.PASS, "Method \"addToShoppingCart89and10\" is passed");	   
  }
    
   /*
    * Step :011  Click on Check out button.
    * Step :012  Click on Continue buttons (2nd, 3rd and 4th)
    * Step :013  Check the Terms and Conditions Checkbox and click Continue
    * Step: 014  Verify the product details is valid.
    * Step: 015  Click Confirm Order
    * 
    */
  @Test (dependsOnMethods="addToShoppingCart89and10")
  public void checkOutConfirmOrder() throws InterruptedException, IOException
  {
	  logger = extent.startTest("checkOutConfirmOrder");
	  driver.findElement(By.xpath(prop.getProperty("checkout"))).click();
	   System.out.println("Clicked on the Checkout button");
	   Thread.sleep(3000);
	   driver.findElement(By.id(prop.getProperty("accountandbillingdetails"))).click();
	   System.out.println("Clicked on first continue button");
	  Thread.sleep(3000);
	   driver.findElement(By.id(prop.getProperty("deliverydetails"))).click();
	   System.out.println("Clicked on second continue button");
	  Thread.sleep(3000);
	   driver.findElement(By.id(prop.getProperty("deliverymethod"))).click();
	   System.out.println("Clicked on third continue button");
	Thread.sleep(5000);
	   driver.findElement(By.name(prop.getProperty("termsandconditions"))).click();
	   driver.findElement(By.id(prop.getProperty("paymentmethodcontinuebutton"))).click();
	   System.out.println("it will navigate to confirm order");
	   
	// Checkout Page
	   String Cproductname=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[1]/a")).getText();
	   String Cmodel=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[2]")).getText();
	   String Cquantity=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[3]")).getText();
	   String Cprice=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[4]")).getText();
	   String Ctotal=driver.findElement(By.xpath("//div[@id='confirm']/div[2]/div[1]/table/tbody/tr/td[5]")).getText();
	   System.out.println(Cproductname);
	   System.out.println(Cmodel);
	   System.out.println(Cquantity);
	   System.out.println(Cprice);
	   System.out.println(Ctotal);
	   Checkout = new ArrayList<String>();
	   Checkout.add(Cproductname);
	   Checkout.add(Cmodel);
	   Checkout.add(Cquantity);
	   Checkout.add(Cprice);
	   Checkout.add(Ctotal);
	   
	  /*----------------------------------------------------------------------
	   * */ 
		 FileOutputStream ExcelOut= new FileOutputStream(System.getProperty("user.dir") + "/target/OutputFiles/TC02_Shoppingcart.xlsx");
		 
		 XSSFWorkbook workbook = new XSSFWorkbook();
		 XSSFSheet sheet=workbook.createSheet();
		 for(int i=0;i<ShoppingCart.size();i++)
		 {
		         sheet.createRow(i).createCell(0).setCellValue(ShoppingCart.get(i));
		 }
		         		         		    
		         for (int i=0;i<Checkout.size();i++)
		 {
		 sheet.getRow(i).createCell(1).setCellValue(Checkout.get(i));
		 }
		    for(int i=0;i<ShoppingCart.size();i++)
		    {
		    if(ShoppingCart.get(i).contentEquals(Checkout.get(i)))
		  
		    {
		    sheet.getRow(i).createCell(2).setCellValue("True");
		    }
		    else 
		    {
		    sheet.getRow(i).createCell(2).setCellValue("False");
		    }
		    
		    }
		    workbook.write(ExcelOut);
		    ExcelOut.close();
		    	 	    
	   
	  Thread.sleep(3000);
	   driver.findElement(By.id(prop.getProperty("confirmorder"))).click();
	   System.out.println("Your Order Has Been Processed");
	 Thread.sleep(5000);
	 logger.log(LogStatus.PASS, "Method \"checkOutConfirmOrder\" is passed");
  }
  /*
   * Step :016  Click on Order History of My Account -footer of the page.
   * Step: 017  Click on "View" icon of the previous order of the product.
   * Step: 018  Click on "Return"  icon
   * Step: 019  Fill the necessary details and click on continue button.
   * Step: 020  Click on continue button.
   * Step: 021  Click on "Logout "
   */
  
  @Test (dataProvider="Mydatprovide",dependsOnMethods="checkOutConfirmOrder")
  public void orderHistory(String Username,String Password,String changequantity,String Faultycomment) throws InterruptedException
  {
	  logger = extent.startTest("orderHistory");
	  driver.findElement(By.xpath(prop.getProperty("orderhistory"))).click();
	  Thread.sleep(5000);
	   System.out.println("Order History page id displayed");
	   //driver.findElement(By.xpath(prop.getProperty("viewicon"))).click();
	   driver.findElement(By.xpath("//div[@id='content']/div[2]/div[3]/div[3]/a[1]/img")).click();
	  Thread.sleep(3000);
	   System.out.println("View is clicked");
	   
	   /*Checkpoint: Step017
	    * Validate the previous order is displayed in the result view.
            If previous order is not displayed Logout from the account.
	    */	   
	  String currentorder =driver.findElement(By.xpath("(//table[@class='list'])[1]//tbody/tr/td[1]")).getText();
	   System.out.println("The full current id is  " + currentorder);
	  String currentorderid=currentorder.substring(0,15);
	  System.out.println("The current id is : " + currentorderid);
	   try
	   {
		  Assert.assertNotSame(orderidp, currentorderid);
		    // Assert.assertEquals(currentorderid, orderidp);
		   
	   }
	   catch(Exception e)
	         {
		   driver.findElement(By.linkText("Logout")).click();
		   	   System.out.println("Logged out successfully since the order id is matched");
		  	   }
	  		   Thread.sleep(5000);
	   driver.findElement(By.xpath(prop.getProperty("returnicon"))).click();
	   System.out.println("Return icon is clicked");
	 Thread.sleep(5000);
	   driver.findElement(By.xpath(prop.getProperty("reasonforreturn"))).click();
	   System.out.println("Reason for return is selected");
	   driver.findElement(By.name(prop.getProperty("faultycomment"))).sendKeys(Faultycomment);
	   Thread.sleep(10000);
	   /*System.out.println("Please Enter the Captcha");
	   Thread.sleep(5000);
	   Scanner sc = new Scanner(System.in);
	   String Captcha=sc.nextLine();
	   driver.findElement(By.xpath(".//*[@name='captcha']")).clear();
	   driver.findElement(By.xpath(".//*[@name='captcha']")).sendKeys(Captcha);
	   Thread.sleep(5000);*/
	   driver.findElement(By.xpath(prop.getProperty("return_details_continue"))).click();
	   System.out.println("Product returns page is displayed");
	   driver.findElement(By.xpath(prop.getProperty("continuebutton_productreturns"))).click();
	   System.out.println("Home page is loaded");
	   driver.findElement(By.xpath(prop.getProperty("logout"))).click();
	   System.out.println("Logged out successfully.");
	    //driver.navigate().back();
	   Thread.sleep(5000);
	   String logout=driver.findElement(By.xpath("//*[@id='content']/h1")).getText();
	 	System.out.println(logout);
	 	Assert.assertEquals("Account Logout", logout);
	 	//Checkpoint for login message
	 	String loginmessage=driver.findElement(By.xpath("//div[@id='welcome']/a[1]")).getText();
	 	System.out.println(loginmessage);
	 	Assert.assertEquals("login", loginmessage);
	 	logger.log(LogStatus.PASS, "Method \"orderHistory\" is passed");
	    }
@AfterClass
	
	public void windup(){
			//driver.close();
	}

  

}

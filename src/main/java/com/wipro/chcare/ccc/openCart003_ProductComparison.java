package com.wipro.chcare.ccc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;

public class openCart003_ProductComparison extends ExtentReportsBaseClass {

	  private static final String depends = null;
	  private static final String dependsonMethods = null;
  private WebDriver driver;
	    private Properties prop;
	  private String value;
	    private static int WAIT_TIME = 10000 ;
	    BufferedWriter pwprodname;
	    String url;
	    
	    @BeforeClass
		public void intialize()
		{	
		/*System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\chromedriver_win32\\chromedriver.exe" );
		   driver=new ChromeDriver();*/
	    	url = "http://10.159.34.131:4444/wd/hub";//http://10.159.34.131:4444/
	        try {
	            DesiredCapabilities capabilities =DesiredCapabilities.internetExplorer();
	            //capabilities.setBrowserName("chrome");
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
	    
	  //Data Provider
	  		@DataProvider(name="Mydatprovide")
	  	    public static  Object[][] ReadingData() throws IOException{
	  		Object[][] obj=ReadingExcel.readExcel("TC03_ProductComparison","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
	  		return obj;	
	  	}	
	  	
	    
	    /* Step:001  Launch  Open Cart application http://10.207.182.108:81/opencart/*/
	    @Test(description = "Launch openCart application")
	    public void openApp() throws InterruptedException
	    {		
	    	logger = extent.startTest("openApp");
	  	  driver.get(prop.getProperty("url"));
	  	  	  	  System.out.println("opencart app launched for TC_003");
	  			//Thread.sleep(5000);
	  	  	  	  
	  	 /* Step 002: Click on "Login" Link*/
	  	driver.findElement(By.xpath("(//a[text()='login'])")).click();
	  	//Thread.sleep(3000);
	  	logger.log(LogStatus.PASS, "Method \"openApp\" is passed");
	  			} 
	    
	    @Test ( dataProvider="Mydatprovide", description="PD", dependsOnMethods="openApp" )
	    public void productComparison(String Username, String Password ,String search) throws InterruptedException, IOException
	    {
	    	logger = extent.startTest("productComparison");
	//   driver.findElement(By.xpath("//div[@id='welcome']/a[1]")).click();
	   driver.findElement(By.xpath("//input[@name='email']")).sendKeys(Username);
	   driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
	   driver.findElement(By.xpath("//input[@class='button']")).click();
	   System.out.println("Logged in successfully");
	   driver.findElement(By.xpath("//div[@id='search']/input")).sendKeys(search);
	   driver.findElement(By.xpath("//div[@id='search']/div")).click();
	   //Checkpoint #1: step004----------------------------------------------------
	   
	   List<WebElement> g1 = driver.findElements(By.xpath("//*[@id='content']/div[6]/div"));
	   System.out.println("List Size of 1st checkpoint is:  "+g1.size());
	   File myfile = new File("D:\\Output\\TC003_step4_output.txt");
       //System.out.println("The entered text " + g1.size());
       FileWriter output1 = new FileWriter(myfile);
       output1.flush();
       output1.write("The list size 1st checkpoint is : " + g1.size());
       output1.close();
      
       /*
        * ----------------------------------------------------------------------------------------
        */
	   Thread.sleep(5000);
	   //Clicking on the Components
	   driver.findElement(By.xpath("//div[@id='menu']/ul/li[3]/a")).click();
	   Thread.sleep(2000);
	   driver.findElement(By.xpath("//div[@class='category-list']/ul/li[2]/a")).click();
	   Thread.sleep(5000);
  //Checkpoint #2: step005----------------------------------------------------
	   
	   List<WebElement> g2 = driver.findElements(By.xpath("//*[@id='content']/div[5]/div"));
	   System.out.println("List Size of second checkpoint is:  "+g2.size());
	   File myfile3 = new File("D:\\Output\\TC003_step5_output.txt");
       //System.out.println("The entered text " + g1.size());
       FileWriter output2 = new FileWriter(myfile3);
       output2.flush();
       output2.write("The list size is : " + g2.size());
       output2.close();
      
       /*
        * ----------------------------------------------------------------------------------------
        */
	   //.//*[@id='content']/div[5]
	   System.out.println("Navigated to Monitors Page");
       driver.findElement(By.xpath("//div[@id='menu']/ul/li[6]/a")).click();
       System.out.println("Clicked on Phones and PDAs and navigated to it");
//Checkpoint #3: step006----------------------------------------------------
	   
	   List<WebElement> g3 = driver.findElements(By.xpath("//*[@id='content']/div[4]/div"));
	   System.out.println("List Size of Third checkpoint is:  "+g3.size());
	   File myfile4 = new File("D:\\Output\\TC003_step6_output.txt");
       //System.out.println("The entered text " + g1.size());
       FileWriter output3 = new FileWriter(myfile4);
       output3.flush();
       output3.write("The list size is : " + g3.size());
       output3.close();
      
       /*
        * ----------------------------------------------------------------------------------------
        */
       
       driver.findElement(By.xpath("//div[@class='sort']/select")).click();
       System.out.println("Clicked on Sort drop down");
       Thread.sleep(2000);
       WebElement sorts = driver.findElement(By.xpath("//div[@id='content']/div[2]/div[3]/select"));
   	   Select dropdown = new Select(sorts);
   	   dropdown.selectByVisibleText("Price (High > Low)");
   	   Thread.sleep(2000);
   	    	System.out.println("Clicked on Sort drop down");
   	Thread.sleep(5000);
   	
   /*===================================Step007 Checkpoint=======================================*/
   	
   	ArrayList<String> prodCost = new ArrayList<String>();
   	for (int k = 1; k<=g3.size();k++)
   	{
   	   // driver.findElement(By.xpath("(//a[text()='Add to Compare'])[i]")).click();
   	 prodCost.add(driver.findElement(By.xpath("//div[@id='content']/div[4]/div["+ k + "]/div[2]/div[2]")).getText());
   	Thread.sleep(2000);
   	}
   	
   	System.out.println("Product cost is " + prodCost);
      	for (String productcost : prodCost) {
      		    		 String productcost1=productcost.substring(0,productcost.length()-15);
      		           System.out.println("Product cost = " + productcost1);
            	}
      	
      	
   	/*============Step008============*/
    File myfile1Prod = new File("D:\\Output\\TC003_step8_output.txt");
    pwprodname = new BufferedWriter(new FileWriter(myfile1Prod));
   	//add to compare
    ArrayList<String> prodName = new ArrayList<String>();
   	for (int i = 1; i<=g3.size();i++)
   	{
   	   // driver.findElement(By.xpath("(//a[text()='Add to Compare'])[i]")).click();
   	 driver.findElement(By.xpath("//*[@id='content']/div[4]/div["+ i + "]/div[1]/div[3]/a")).click();
   	Thread.sleep(2000);
   driver.findElement(By.xpath("//div[@id='notification']/div/img")).click();
 	Thread.sleep(2000);
     System.out.println("Product is added" + i); 
     String Prod1 = driver.findElement(By.xpath("//*[@id='content']/div[4]/div["+ i + "]/div[2]/div[3]/a")).getText();
     prodName.add(driver.findElement(By.xpath("//*[@id='content']/div[4]/div["+ i + "]/div[2]/div[3]/a")).getText());
     System.out.println("The entered text " + Prod1);
    pwprodname.write("The Product name is " + i + " : "  + Prod1);
   pwprodname.newLine();
//   pwprodname.append("The Product name is " + i + " : "  + Prod1);
             	}
   	   	pwprodname.close();
   	 System.out.println(prodName.size());
   	 System.out.println(prodName.get(0));
   	System.out.println(prodName.get(1));
   	System.out.println(prodName.get(2));
   	for (String productName : prodName) {
        System.out.println("Product Name = " + productName);
     } 
     System.out.println("Add comparison is done");
   	//product compare
     	   driver.findElement(By.xpath("//a[@id='compare-total']")).click();
   	System.out.println("Product comparison is done");
   	
   	/*======================Step009=========================================*/
   	
  /*=============checkpoint=====================================*/
   	String first= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
   	System.out.println("First link " + first);
   	Assert.assertEquals(first, prodName.get(0));
   	Thread.sleep(5000);
   	String second= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();
   	System.out.println("Second link " +second);
   	Assert.assertEquals(second, prodName.get(1));
	Thread.sleep(5000);
   	String third= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[4]/a")).getText();
   	System.out.println("Third link " + third);
   	Assert.assertEquals(third, prodName.get(2));
   	
   	//Click the first product hyperlink
   	   	driver.findElement(By.linkText("iPhone")).click();
   	Thread.sleep(5000);
   	
   	//Step011 ========Checkpoint======== 
   	   			List<WebElement> tab1=driver.findElements(By.xpath(".//*[@id='tabs']/a"));
	  	        for(int j=0;j<tab1.size();j++){
		 		 String TabData=tab1.get(j).getText();
		 System.out.println("The tab name is "  + TabData);
		 if(TabData.contains("Description"))
		 {
			 driver.findElement(By.xpath("//a[contains(text(),'Description')]")).click();
			String text=driver.findElement(By.xpath("//div[@id='tab-description']/ul/li[4]")).getText();
   	   	   // To read the text and keep it in text
   	       File myfile1 = new File("D:\\Output\\TC003.txt");
   	      String text1 = driver.findElement(By.xpath("//div[@id='tab-description']/ul/li[4]")).getText();
           System.out.println("The entered text " + text1);
          FileWriter pw = new FileWriter(myfile1);
        pw.write(text1);
        pw.close();
     	        System.out.println("Text added to the flat file");
     	        break;
		 }
		 else{ 
			 System.out.println("Else part is executed");
			 File myfile2 = new File("D:\\Output\\TC003_NoDescription.txt");
	   	     FileWriter pw = new FileWriter(myfile2);
	        pw.write("No Description tab is found");
	        pw.close();	     	     		 
		 }			
		 }				 	        	
		//Click on add to cart for the above selected item
		driver.findElement(By.xpath("//input[@id='button-cart']")).click();
		System.out.println("you have added to your shopping cart");
		// "Shopping Cart" displayed on ribbon message
		driver.findElement(By.xpath("//div[@class='links']/a[4]")).click();
		System.out.println("Shopping cart page is displayed");
		Thread.sleep(5000);
		//Clicking on the check out button
		driver.findElement(By.xpath("//div[@class='buttons']/div[1]/a")).click();
		System.out.println("Check out page is Displayed");
		//Check out page
		Thread.sleep(5000);
		
			//Billing details -2
		driver.findElement(By.id("button-payment-address")).click();
		System.out.println("2nd is Displayed");
		Thread.sleep(5000);
		// Delivery Details -3
		driver.findElement(By.id("button-shipping-address")).click();
		System.out.println("3rd is Displayed");
		Thread.sleep(5000);
		//Delivery method -4
		driver.findElement(By.id("button-shipping-method")).click();
		System.out.println("4th is Displayed");
		Thread.sleep(5000);
		//Payment method
		driver.findElement(By.name("agree")).click();
	     driver.findElement(By.id("button-payment-method")).click();
	     Thread.sleep(5000);
	     //Confirm order
	     driver.findElement(By.id("button-confirm")).click();
		   	     
	     //Clicking on the browser back button
	     driver.navigate().back();
	     System.out.println("Navigated using browser back button");
	     //Step-018-------Checkpoint----------------------------------
	     driver.navigate().back();
	     String cartempty= driver.findElement(By.xpath(".//*[@id='content']/div[2]")).getText();
	     System.out.println("cart empty message is " + cartempty);
	     Assert.assertEquals(cartempty, "Your shopping cart is empty!", "Validation is failed");
	//Navigating to the order history at the bottom
	     driver.findElement(By.xpath("//div[@id='footer']/div[4]/ul/li[2]/a")).click();
	     System.out.println("Order history Displayed");
	     /*
	      * Checkpoint at Step019*****----****Latest Order number and its cost cost should be written to flat file
	      */
	     String latestorderid=driver.findElement(By.className("order-id")).getText();
	     String ordercost=driver.findElement(By.xpath("//*[@id='content']/div[2]/div[3]/div[2]")).getText();
	  	     System.out.println("The latest orderid is  " + latestorderid );
	        // String ordercost1=ordercost.substring(31,ordercost.length()-1);
	  	   String[] costarray= ordercost.split(":");
	     System.out.println("The latest orderid cost is  " + costarray[2] );
	     //Navigating to Newsletter hyperlink
	     
	     driver.findElement(By.linkText("Newsletter")).click();
	     System.out.println(" Newsletter displayed");
	     Thread.sleep(5000);
	     // Extras -> Specials in the footer
	     driver.findElement(By.xpath("//div[@id='footer']/div[3]/ul/li[4]/a")).click();
	     Thread.sleep(2000);
	     System.out.println(" Extras displayed");
	     // clicking on list or grid
	     
	     WebElement element = driver.findElement(By.xpath("//div[@id='content']/div[2]/div[1]/a"));
	     if(element.isEnabled())
	     {
	    	 element.click();
	    	 System.out.println("Grid is clicked");
	     }
	     else
	     {
	    	 element.click();
	    	 System.out.println("list is clicked");
	     }
	     driver.findElement(By.xpath("//div[@id='welcome']/a[2]")).click();
		   System.out.println("Logged out successfully."); 
		   driver.close();
			logger.log(LogStatus.PASS, "Method \"productComparison\" is passed");
	    }
	}


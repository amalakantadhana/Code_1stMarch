package com.wipro.chcare.ccc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
	
	WebDriver driver;
	
public RegistrationPage(WebDriver driver)
{
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

/*========================Test case001=========================*/
  @FindBy(xpath="//a[@href='http://10.207.182.108:81/opencart/index.php?route=account/register']") 
  public WebElement Register;
  @FindBy(name="firstname") public  WebElement fnameR;
  @FindBy(name="lastname") public  WebElement LnameR;
  @FindBy(name="email") public  WebElement emailR;
  @FindBy(name="telephone") public  WebElement telephoneR;
  @FindBy(name="fax") public  WebElement faxR;
  @FindBy(name="company") public  WebElement companyR;
  @FindBy(name="country_id") public  WebElement CountryIdR;
  @FindBy(name="zone_id") public WebElement zone_idRR;
  
  /*========================Test case007=========================*/
  @FindBy(xpath="(//a[text()='login'])")
  public WebElement login;
  @FindBy(xpath="//input[@name='email']")
  public WebElement username;
  @FindBy(xpath="//input[@name='password']")
  public WebElement password;
  @FindBy(xpath="//input[@class='button']")
  public WebElement loginbutton;
}

package com.wipro.chcare.ccc;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class GenericMethods {
	
	public  void DropDwnText(WebElement element,String Text){
        
        
        Select obj = new Select(element);
        obj.selectByVisibleText(Text);
  }


}

package com.ReadExcel;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
 
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadingExcel {
	
	//Data Provider
	
	//@DataProvider(name="Mydatprovide")
	
     public static  Object[][] readExcel(String SheetName,String Filepath) throws IOException
 	{
 		String[][] arrayExcelData = null;
 		//Create an object of File class to open xlsx file
 		File src= new File(Filepath); //"D:\\SELENIUM_PRACTISE\\TestData.xlsx"
 		//Create an object of FileInputStream class to read excel file
 	     FileInputStream fis = new FileInputStream(src);
 	     //create object of XSSFWorkbook class
 	     XSSFWorkbook wb= new XSSFWorkbook(fis); // load the the excel workbook  
 	     XSSFSheet sheet1= wb.getSheet(SheetName);
 		 //Find number of rows and columns in excel file
 		 int rowCount=sheet1.getLastRowNum();
 		 int colcount=sheet1.getRow(0).getLastCellNum();
 		//int totalNoRows = sheet1.getLastRowNum(); 
 		
 		 System.out.println("Row count is " + rowCount);
 		 System.out.println("column count is "+ colcount);
 		 
 		arrayExcelData = new String[rowCount][colcount];
 		//Create a loop over all the rows of excel file to read it
          	    for (int i = 1; i < rowCount+1; i++) 
          	    {
 	       
 	        //Create a loop to print cell values in a row

 	        for (int j = 0; j < sheet1.getRow(0).getLastCellNum(); j++) {
 	            //Print Excel data in console
 	        	arrayExcelData[i-1][j] = sheet1.getRow(i).getCell(j).getStringCellValue();
        	     System.out.println(arrayExcelData);
 	        
 	        }
 	 
 	}
				return arrayExcelData;
			
 	}
}
     
	 


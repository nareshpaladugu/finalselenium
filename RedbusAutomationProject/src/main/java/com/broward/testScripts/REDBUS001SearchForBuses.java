package com.broward.testScripts;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;

import com.broward.base.GenericLibrary;
import com.broward.business.methods.BrowardHeadersMethods;
import com.broward.datadriven.ReadExcelFile;
import com.broward.utilities.TestFailedException;

public class REDBUS001SearchForBuses extends GenericLibrary {
	// Object Creation - Generic Objects
	HSSFWorkbook wb;
	@Test
	public void verifySearchBuses() throws TestFailedException, Exception {
	wb = ReadExcelFile.loadExcel("TD001_Search_For_buses.xls");
	String fromPlace = ReadExcelFile.getData(wb, "Sheet1", "From_place").toString();
	String toPlace = ReadExcelFile.getData(wb, "Sheet1", "To_place").toString();
	
	BrowardHeadersMethods home = new BrowardHeadersMethods(driver);
	
	
     home.invokeRedBusApplication();
     home.seacrhForBuses(fromPlace,toPlace);
     home.selectDate("28", "FromDate");
	}
}

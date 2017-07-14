package com.broward.testScripts;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.broward.base.GenericLibrary;
import com.broward.business.methods.BrowardHeadersMethods;
import com.broward.business.methods.RedBusSearchResultsPage;
import com.broward.datadriven.ReadExcelFile;
import com.broward.utilities.TestFailedException;

public class REDBUS002SelectSeatAndPickupLocation extends GenericLibrary{
	 HSSFWorkbook places= null; 
		
		@BeforeMethod
		public void initilizations() throws IOException
		{
			places = ReadExcelFile.loadExcel("PATH");
		}
		
		@Test
		public void BookATicket() throws TestFailedException, Exception {
		BrowardHeadersMethods home = new BrowardHeadersMethods(driver);
		RedBusSearchResultsPage resultsPage = new RedBusSearchResultsPage(driver);
	     home.invokeRedBusApplication();
	     home.seacrhForBuses(ReadExcelFile.getData(places, "Sheet1", "From_place").toString(),ReadExcelFile.getData(places, "Sheet1", "To_place").toString());
	     home.selectDate("", "FromDate");
	     resultsPage.selectATravelsAndSeatNo("Orange Tours Travels","19");
	     resultsPage.selectBoardingPoint();
		}
}

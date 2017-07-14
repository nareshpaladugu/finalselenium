package com.broward.business.methods;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.broward.base.GenericLibrary;
import com.broward.config.Configurations;
import com.broward.objectRepository.BrowardHeaderRepo;
import com.broward.utilities.HTMLReports;
import com.broward.utilities.TestFailedException;

public class BrowardHeadersMethods extends GenericLibrary {

	// Object Creation - Generic Objects
	public WebDriver driver;
	HTMLReports reports;
	Configurations config;

	public BrowardHeadersMethods(WebDriver driver) {
		this.driver = driver;
		reports = new HTMLReports();
		config=new Configurations();
	}

	/**
	 * @Description login to application with valid credentials
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void invokeBrowardApplication() throws TestFailedException, Exception{
		boolean flag = false;
		try {
			//Invoke Web Application
			invokeWebApplication(config.URL);			
			flag = true;
		} catch (Exception e) {
			throw new TestFailedException("Failed to navigate to the Redbus URL");
		}
		finally{
			if(flag)
				reports.successReport("Navigate to the Broward URL","Successfully navigated to the Broward URL");
			else
				reports.failureReport("Navigate to the Broward URL","Failed to navigate to the Broward URL");
		}
	}
	
	/**
	 * @Description Search for the buses
	 * @param fromplace
	 * @param toplace
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void navigateToApplyNow() throws TestFailedException, Exception {
		boolean bFlag = false;
		try {  
			click(BrowardHeaderRepo.link_apply_now);
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Unable to navigate to Apply now link");
		} 	
		finally{
			if(bFlag)
				reports.successReport("Navigate to Apply now link", "Successfully navigate to Apply now link");
			else
				reports.failureReport("Navigate to Apply now link ", "Failed to navigate Apply now link");
		}

	}
	
	
	
	}
	


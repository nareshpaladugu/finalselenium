package com.broward.objectRepository;

import org.openqa.selenium.By;

public class RedBusSearchResultsRepo 
{	
	public static final By div_private_buses_list  = By.xpath("//div[@id='onwardTrip']/div[@class='PrivateBuses']/ul[@class='BusList']");
	public static final By btn_continue            = By.xpath("//button[contains(text(),'Continue')]");
	public static final By btn_not_now             = By.xpath("//a[contains(text(),'No. Not Now')]");		
}
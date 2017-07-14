package com.broward.base;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.broward.config.Configurations;
import com.broward.utilities.GetDateTime;
import com.broward.utilities.HTMLReports;
import com.broward.utilities.Listener;
import com.broward.utilities.TestFailedException;

public class Base {

	public static WebDriver oDriver;
	public static EventFiringWebDriver driver;

	public static String testCase;
	public static String targetEnvironment;
	public static String suiteStartDateTime;
	public static long suiteStartTime;
	public static long suiteEndTime;
	public static long testCaseStartTime;
	public static long testCaseEndTime;
	public static long stepStartTime;
	public static long stepEndTime;
	public static Map<String, String> TestCaseStatus = new LinkedHashMap<String, String>();
	public static Map<String, String> TestCaseTime = new LinkedHashMap<String, String>();
	HTMLReports reports;
	public static Configurations configurations = new Configurations();

	@BeforeSuite
	public void startUp() throws Exception{
		suiteStartDateTime = GetDateTime.getCurrentDate("dd/MM/yyyy hh:mm:ss");
		System.out.println(suiteStartDateTime);
		suiteStartTime = System.currentTimeMillis();
		System.out.println(suiteStartTime);
		new File(System.getProperty("user.dir")+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\Screenshots").mkdirs();

	}

	@BeforeMethod
	public void testScriptSetup(Method itc) throws Exception {

		DesiredCapabilities capabilities = null;
		System.out.println("======================");
		System.out.println(itc.getName());
		System.out.println("=======================================");

		switch (configurations.Browser.toUpperCase()) {
		case "IE":
			// kill the process before the test execution starts
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Thread.sleep(2000);

			InternetExplorerDriverManager.getInstance().setup();
			capabilities = null;
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("nativeEvents", false);
			capabilities.setCapability("ignoreZoomSetting", true);			
			capabilities.setCapability("enablePersistentHover", true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);	
			capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);		
				oDriver = new InternetExplorerDriver(capabilities);

			break;

		case "CHROME":
			// kill the process before the test execution starts
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Thread.sleep(2000);
			ChromeDriverManager.getInstance().setup();

			capabilities = null;
			capabilities = DesiredCapabilities.chrome();
		     oDriver = new ChromeDriver(capabilities);				
			break;
		}
		driver = new EventFiringWebDriver(oDriver);
		Listener listener = new Listener(oDriver);
		driver.register(listener);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		testCaseStartTime = System.currentTimeMillis();
		stepStartTime = System.currentTimeMillis();
		testCase = itc.getName();
		TestCaseStatus.put(testCase, configurations.Browser+",Skip");
		System.out.println("======================");
		System.out.println(TestCaseStatus);
		System.out.println("======================");

		reports = new HTMLReports();
		reports.detailedReportHeader();
		stepStartTime = System.currentTimeMillis();

	}

	@AfterMethod
	public void shutDown(ITestResult itc) throws Exception, TestFailedException{

		testCaseEndTime = System.currentTimeMillis();	

		if(TestCaseStatus.get(testCase).toLowerCase().contains("pass"))
			TestCaseStatus.put(testCase, configurations.Browser+",Pass,"+GetDateTime.testCaseExecutionTime());
		else if(TestCaseStatus.get(testCase).toLowerCase().contains("fail"))
			TestCaseStatus.put(testCase, configurations.Browser+",Fail,"+GetDateTime.testCaseExecutionTime());
		else
		{
			TestCaseStatus.put(testCase, configurations.Browser+",Skip,"+GetDateTime.testCaseExecutionTime());
			reports.SkippedReport(itc.getThrowable().getMessage());
		}
		reports.detailedReportFooter("");

		//oDriver.close();
		//oDriver.quit();
	}

	@AfterSuite
	public void endExecution(){
		try {			
			suiteEndTime = System.currentTimeMillis();
			reports.summaryReport();
			Iterator<?> it = TestCaseStatus.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
				String[] sValue = pair.getValue().toString().split(",");
				if(sValue[1].equalsIgnoreCase("pass")&&(!(configurations.sScriptPassResult.equalsIgnoreCase("true"))))
					new File(System.getProperty("user.dir")+"\\Reports\\Report_"+((suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_"))+"\\"+pair.getKey()+".html").delete();
			}
			if(!(new File(System.getProperty("user.dir")+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\Screenshots").list().length>0))
				new File(System.getProperty("user.dir")+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\Screenshots").delete();
			if(configurations.sResultFolder!=null)
			{
				File f = new File(configurations.sResultFolder+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_"));
				f.mkdirs();
				if(f.exists())
				{
					if(f.isDirectory())
					{ 		        
						File srcDir = new File(System.getProperty("user.dir")+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_"));
						File destDir = new File(configurations.sResultFolder+"\\Reports\\Report_"+(suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_"));
						FileUtils.copyDirectory(srcDir, destDir);
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
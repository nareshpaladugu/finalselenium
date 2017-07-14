package com.broward.base;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import com.broward.utilities.HTMLReports;
import com.broward.utilities.TestFailedException;

public class GenericLibrary extends Base {	
	
	public GenericLibrary(){
		reports = new HTMLReports();
	}
	/*
	 * Function Description : Function to generate random number Parameters :
	 * None Returns : Random Number
	 */
	public String generateRandomNumber() {

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
		StringBuffer sStringBuffer = new StringBuffer();
		String sCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String sWorkingsetName_1;

		int charactersLength = sCharacters.length();

		for (int i = 0; i < 6; i++) {
			double index = Math.random() * charactersLength;
			sStringBuffer.append(sCharacters.charAt((int) index));
		}
		sWorkingsetName_1 = "AUTOTEST-" + dateFormat.format(date) + "-" + sStringBuffer.toString();
		return sWorkingsetName_1.toUpperCase();
	}

	/*
	 * Function Description : Function to invoke the browser and navigate to the
	 * desired url Parameters : URL - navigation URL Returns : None
	 */
	public boolean invokeWebApplication(String URL) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			driver.get(URL);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			bFlag = true;
			return true;
		} catch (Exception e) {
			bFlag = false;
			return false;
		} finally {
			if (bFlag)
				reports.successReport("Invoke the Application", "Successfully invoked application URL : "+URL);
			else
				reports.failureReport("Invoke the Application", "Failed to invoke web application URL : "+URL);
		}
	}

	/**
	 * @Description Function to find click on checkbox based on index
	 * @param chkBoxindex
	 *            Index of the checkbox
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void selectCheckboxBasedOnIndex(int chkBoxindex) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			waitForElementVisible(By.xpath("//input[@type='checkbox']"), 20);
			List<WebElement> oElement = driver.findElements(By.xpath("//input[@type='checkbox']"));
			oElement.get(chkBoxindex).click();
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to select the checkbox " + ex.toString());
		}

		finally {
			if (!bFlag)
				reports.failureReport("Select checkbox based on index", "Failed to select the checkbox with index :" + chkBoxindex);
		}
	}

	/**
	 * @Description Function to click on object based on location
	 * @param locator
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void click(By locator) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			waitForElementVisible(locator, 20);	    
			driver.findElement(locator).click();	    
			bFlag = true;
		} catch (Exception e) {
			bFlag = false;
			throw new TestFailedException("Failed to click on locator " + e.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Click on locator ", "Failed to click on locator " + locator);
		}
	}

	/**
	 * @Description Function to enter data by locator
	 * @param locator
	 * @param data
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void type(By locator, String data) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			waitForElementVisible(locator, 20);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(data);
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to enter data " + ex.getMessage());
		}

		finally {
			if (!bFlag)
				reports.failureReport("Enter data based on locator", "Failed to enter data based on locator " + locator);
		}

	}

	/**
	 * @Description Function to select the value from the drop down
	 * @param dropDownPath
	 * @param dropDownValue
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void selectValueFromDropDownByText(By dropDownPath, String dropDownValue) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			waitForElementVisible(dropDownPath, 20);
			Select oDropDown = new Select(driver.findElement(dropDownPath));
			oDropDown.selectByVisibleText(dropDownValue);	    
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to select value from dropdown " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Select the value from the drop down", "Failed to select value from dropdown :" + dropDownValue);
		}

	}

	/**
	 * @Description Function to select the value from the drop down
	 * @param dropDownPath
	 * @param index
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void selectValueFromDropDownByIndex(By dropDownPath, int index) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			Select oDropDown = new Select(driver.findElement(dropDownPath));
			oDropDown.selectByIndex(index);
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to select value from dropdown " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Select the value from the drop down", "Failed to select value from dropdown with index:" + index);
		}

	}

	/**
	 * @Description Function to verify element exists
	 * @param locator
	 * @return 
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public boolean verifyElementPresent(By locator) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			if (!driver.findElement(locator).isDisplayed()) {
				bFlag = false;
				throw new TestFailedException("Element is not presented ");
			}
			bFlag = true;
		}

		catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Element is not presented " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Verify element is presnt", "Element is not present");
		}
		return bFlag;

	}


	/**
	 * @Description Function to perform page refresh
	 * @throws TestFailedException
	 */
	public void pageRefresh() throws TestFailedException {
		try {
			driver.navigate().refresh();
		} catch (Exception ex) {
			throw new TestFailedException("Failed to refresh the page " + ex.toString());
		}
	}

	/**
	 * @Description Function to caprture the text based on locator
	 * @param locator
	 * @return locator text
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public String getText(By locator) throws TestFailedException, Exception {
		boolean bFlag = false;
		String sValue;
		try {

			sValue = driver.findElement(locator).getText();
			bFlag = true;
			return sValue;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to extract text based on locator " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Retrive text of locator ", "Failed to extract text based on locator : " + locator);
		}
	}

	/**
	 * @Description Function to verify the message on the webpage
	 * @param locator
	 * @param expectedMessage
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void verifyTextEquals(By locator, String expectedMessage) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			WebElement oElement = driver.findElement(locator);
			Assert.assertEquals(oElement.getText(), expectedMessage);
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Actual and Expected Messages should be same :" + expectedMessage);
		} 	
		finally{
			if(bFlag)
				reports.successReport("Verify text ", "Actual and Expected text should be same : "+expectedMessage);
			else
				reports.failureReport("Verify text ", "Actual and Expected text are not same : "+expectedMessage);
		}

	}


	/**
	 * @Description Function to verify the message on the webpage
	 * @param locator
	 * @param expectedMessage
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void verifyTextEquals(String actualMessage, String expectedMessage) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {    
			if(actualMessage.equals(expectedMessage)){bFlag = true;};
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Actual and Expected Messages should be same :" + expectedMessage);
		} 	
		finally{
			if(bFlag)
				reports.successReport("Verify text ", "Actual and Expected text should be same : "+expectedMessage);
			else
				reports.failureReport("Verify text ", "Actual and Expected text are not same : "+expectedMessage);
		}

	}

	/**
	 * @Description Function to verify the message on the webpage
	 * @param locator
	 * @param expectedMessage
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void verifyTextContains(By locator, String expectedMessage) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			WebElement oElement = driver.findElement(locator);
			if (!oElement.getText().toLowerCase().contains(expectedMessage.toLowerCase())) {
				bFlag = false;
				throw new TestFailedException("Actual and Expected Messages should be same " + expectedMessage);
			}
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Actual and Expected Messages should be same " + expectedMessage);
		} finally {
			if (!bFlag)
				reports.failureReport("Verify text ", "Actual and Expected text should be same : " + expectedMessage);
		}
	}

	/**
	 * @Description Function for synchronization
	 * @param locator
	 * @param lWait
	 */
	public void waitForElementPresent(By locator, long lWait) {
		try {
			WebDriverWait ww = new WebDriverWait(driver, lWait);
			ww.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @Description Function for synchronization
	 * @param locator
	 * @param lWait
	 */
	public void waitForElementVisible(By locator, long lWait) {
		try {
			WebDriverWait ww = new WebDriverWait(driver, lWait);
			ww.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @Description Function for synchronization
	 * @param lWait
	 */
	public void waitForAlertPresent(long lWait) {
		try {
			WebDriverWait ww = new WebDriverWait(driver, lWait);
			ww.until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
		}
	}

	/**
	 * @Description Function to accept alert
	 * @return
	 * @throws Exception
	 */
	public String getTextAndAcceptAlert() throws Exception {
		boolean bFlag = false;
		try {
			Alert a = driver.switchTo().alert();
			String value = a.getText();
			a.accept();
			bFlag = true;
			return value;
		} catch (Exception e) {
			bFlag = false;
			return null;
		} finally {
			if (!bFlag)
				reports.failureReport("Accept alert", "Failed to accept alert dailog");
		}
	}

	/**
	 * @Description Function to dismiss alert
	 * @return
	 * @throws Exception
	 */
	public String getTextAndDismissAlert() throws Exception {
		boolean bFlag = false;
		try {
			Alert a = driver.switchTo().alert();
			String value = a.getText();
			a.dismiss();
			bFlag = true;
			return value;
		} catch (Exception e) {
			bFlag = false;
			return null;
		} finally {
			if (!bFlag)
				reports.failureReport("Dismiss alert", "Failed to dismiss alert dailog");
		}
	}

	/**
	 * @Description Function to click on locater using java script
	 * @param locator
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public void jsClick(By locator) throws TestFailedException, Exception {
		boolean bFlag = false;
		try {
			waitForElementPresent(locator, 20);
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			bFlag = true;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to click on confirm button " + ex.getMessage());
		} finally {
			if (!bFlag)
				reports.failureReport("Click on locator using java script", "Failed to click on locator using java script :" + locator);
		}
	}

	/**
	 * @Description Function to get attribute based on locator
	 * @param locator
	 * @param sValue
	 * @return
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public String getAttribute(By locator, String sValue) throws TestFailedException, Exception {
		boolean bFlag = false;
		String sData;
		try {
			sData = driver.findElement(locator).getAttribute(sValue);
			bFlag = true;
			return sData;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to get attribute value based on locator " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Get attribute value based on locator", "Failed to get attribute value based on locator : " + locator);
		}
	}

	/**
	 * @Description Function to get tagname
	 * @param locator
	 * @return
	 * @throws TestFailedException
	 * @throws Exception
	 */
	public String getTagName(By locator) throws TestFailedException, Exception {
		boolean bFlag = false;
		String sData;
		try {
			sData = driver.findElement(locator).getTagName();
			bFlag = true;
			return sData;
		} catch (Exception ex) {
			bFlag = false;
			throw new TestFailedException("Failed to get tag name based on locator " + ex.toString());
		} finally {
			if (!bFlag)
				reports.failureReport("Get tag name", "Failed to get tag name :" + locator);
		}
	}
}
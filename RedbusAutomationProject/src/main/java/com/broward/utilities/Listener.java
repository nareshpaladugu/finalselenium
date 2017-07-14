package com.broward.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class Listener implements WebDriverEventListener{

	WebDriver driver;
	JavascriptExecutor js;
	public Listener(WebDriver driver){
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
	}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {	
		js.executeScript(
				"arguments[0].setAttribute('style', arguments[1]);",
				arg2.findElement(arg0), "color: red; border: 3px solid green;");
	}

	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		js.executeScript(
				"arguments[0].setAttribute('style', arguments[1]);",
				arg2.findElement(arg0), "color: red; border: 3px solid green;");
	}

	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
	}


	public void afterClickOn(WebElement arg0, WebDriver arg1) { 
	}

	public void afterNavigateBack(WebDriver arg0) {

	}


	public void afterNavigateForward(WebDriver arg0) {

	}


	public void afterNavigateTo(String arg0, WebDriver arg1) {

	}


	public void afterScript(String arg0, WebDriver arg1) {
	}


	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {

	}

	public void beforeClickOn(WebElement arg0, WebDriver arg1) {

	}

	public void beforeNavigateBack(WebDriver arg0) {
	}


	public void beforeNavigateForward(WebDriver arg0) {

	}


	public void beforeNavigateTo(String arg0, WebDriver arg1) {

	}


	public void beforeScript(String arg0, WebDriver arg1) {

	}


	public void onException(Throwable arg0, WebDriver arg1) {

	}

}

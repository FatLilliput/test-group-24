/*
 * This class contains methods needed for correctly autotests work 
 * and common methods for all application's modules
 */
package com.example.tests;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {

	private static WebDriver driver;
	private static String baseUrl;
	private static boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();

	@BeforeTest
	public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "http://localhost/addressbookv4.1.4/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	protected void fillElement(String name, String value) {
		driver.findElement(By.name(name)).clear();
	    driver.findElement(By.name(name)).sendKeys(value);
	  }
	
	protected void selectElement(String name, String value) {
		if (value == null) {
			return;
		}
		new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
	  }

	protected void clickGroupsList() {
	    driver.findElement(By.linkText("groups")).click();
	  }
	
	protected void clickMainPage() {
	    driver.findElement(By.linkText("home")).click();
	  }
	
	protected void clickAddNew() {
	    driver.findElement(By.linkText("add new")).click();
	  }

	protected void openMainPage() {
	    driver.get(baseUrl + "index.php");
	  }

	protected void clickButton(String name) {
		driver.findElement(By.name(name)).click();
	  }
	
	protected void wait(Long time, String id) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
	}

	@AfterTest
	public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }

}

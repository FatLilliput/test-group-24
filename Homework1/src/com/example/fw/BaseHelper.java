package com.example.fw;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseHelper {
	
	public boolean acceptNextAlert = true;
	public WebDriver driver;	
	protected ApplicationManager manager;
	
	public BaseHelper (ApplicationManager manager) {
		this.manager = manager;
		this.driver = manager.driver;
	}
	
	public void fillElement(String name, String value) {
		if (value != null) {
			driver.findElement(By.name(name)).clear();
			driver.findElement(By.name(name)).sendKeys(value);
		}
	  }

	public void selectElement(String name, String value) {
		if ((value != null) && !(value.isEmpty())) {
			new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
			}
	  }

	public void click(String path) {
		driver.findElement(By.xpath(path)).click();
	}
	
	public void clickButton(String name) {
		driver.findElement(By.name(name)).click();
	  }
	
	public void clickLinkText(String text) {
		driver.findElement(By.linkText(text)).click();
	}
	
	public void openPage(String link) {
		driver.get(link);
	}

	public boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	public boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	public String closeAlertAndGetItsText() {
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
	public String getText(String input) {
		WebElement text = (WebElement) driver.findElement(By.xpath("//*[@name='" + input + "']"));
		return text.getAttribute("value");
	}
	
	public String getTextArea(String input) {
		return driver.findElement(By.xpath("//*[@name='" + input + "']")).getText();
	}

	public void waitMe(Long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

}

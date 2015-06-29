package com.example.fw;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseHelper {
	
	protected ApplicationManager manager;
	public boolean acceptNextAlert = true;
	public WebDriver driver;
	
	public BaseHelper (ApplicationManager manager) {
		this.manager = manager;
		this.manager.driver = driver;
	}
	
	public void fillElement(String name, String value) {
		driver.findElement(By.name(name)).clear();
	    driver.findElement(By.name(name)).sendKeys(value);
	  }

	public void selectElement(String name, String value) {
		if (value == null) {
			return;
		}
		new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
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

	public void wait(Long time, String id) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
	}

}

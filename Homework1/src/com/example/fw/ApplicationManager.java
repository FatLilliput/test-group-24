package com.example.fw;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {
	
	public WebDriver driver;
	public String baseUrl;
	private NavigationHelper navigationHelper;
 	public BaseHelper baseHelper;
	private GroupHelper groupHelper;
	private ContactHelper contactHelper;

	public ApplicationManager() {
		driver = new FirefoxDriver();
	    baseUrl = "http://localhost/addressbookv4.1.4/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
	    driver.get(baseUrl);
	}

	public NavigationHelper getNavigationHelper () {
		if (navigationHelper == null) {
			navigationHelper = new NavigationHelper(this);
		}
		return navigationHelper;
	}
	
	public GroupHelper getGroupHelper () {
		if (groupHelper == null) {
			groupHelper = new GroupHelper (this);
		}
		return groupHelper;
	}

	public ContactHelper getContactHelper () {
		if (contactHelper == null) {
			contactHelper = new ContactHelper (this);
		}
		return contactHelper;
	}

	public void stop() {
//		driver.close();
		driver.quit();
	}

}

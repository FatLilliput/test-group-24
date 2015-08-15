package com.example.fw;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

import com.example.utilits.SortedListOf;

public class ApplicationManager {
	
	private WebDriver driver;
	public String baseUrl;
	private NavigationHelper navigationHelper;
 	public WebDriverBaseHelper baseHelper;
	private GroupHelper groupHelper;
	private ContactHelper contactHelper;
	private JDBCHelper jdbcHelper;
	private Properties properties;
	private HibernateHelper hibernateHelper;
	
	private ApplicationModel model;

	public ApplicationManager(Properties properties) {
		this.properties = properties;
		model = new ApplicationModel();
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public WebDriver getDriver() {
		if (driver == null) {
			String browser = properties.getProperty("browser");
			switch (browser.toLowerCase()) {
				case "firefox" :
					driver = new FirefoxDriver();
					break;
				case "ie" :
					driver = new InternetExplorerDriver();
					break;
				case "chrome" :
					driver = new ChromeDriver();
					break;
				case "opera" :
					driver = new OperaDriver();
					break;
				default :
					throw new Error("Incorrect browser. Got: " + browser + ". Expected: firefox, ie, chrome, opera");
			}
			baseUrl = properties.getProperty("baseUrl");
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		    driver.get(baseUrl);
		}
		return driver;
	}

	public ApplicationModel getModel() {
		return model;
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

	public JDBCHelper getJDBCHelper () {
		if (jdbcHelper == null) {
			jdbcHelper = new JDBCHelper(this);
		}
		return jdbcHelper;
	}

	public HibernateHelper getHibernateHelper () {
		if (hibernateHelper == null) {
			hibernateHelper = new HibernateHelper(this);
		}
		return hibernateHelper;
	}

	public void stop() {
//		driver.close();
		driver.quit();
	}

}

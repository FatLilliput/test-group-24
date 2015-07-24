package com.example.fw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

import com.example.tests.ObjContact;
import com.example.tests.ObjGroup;

public class ApplicationManager {
	
	public WebDriver driver;
	public String baseUrl;
	private NavigationHelper navigationHelper;
 	public BaseHelper baseHelper;
	private GroupHelper groupHelper;
	private ContactHelper contactHelper;
	private Properties properties;
	private Connection dbConnection;

	public ApplicationManager(Properties properties) {
		this.properties = properties;
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
	
	public void dbConnect() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	    
	    //try to connect to DataBase
		try {
	        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void dbClose() {
	//try to close connection
		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dbClear() {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute("truncate `group_list`;");
			statement.execute("truncate `addressbook`;");
			statement.execute("truncate `address_in_groups`;");
				    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	} 
	
	public ObjGroup insertGroup(ObjGroup group) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
					"INSERT INTO `group_list` (group_name, group_header, group_footer) "
					+ "VALUES ('" + group.getName() + "', '" + group.getHeader() + "', '" + group.getFooter() + "');"
			);
			ResultSet res = statement.executeQuery("SELECT MAX(group_id) FROM `group_list`");
			while (res.next()) {
				 group.setId(Integer.parseInt(res.getString("MAX(group_id)")));
			 }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return group;
	}
	
	public ObjContact insertContact(ObjContact contact) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
				"INSERT INTO `addressbook` (firstname, lastname, address, home, mobile, work, email, email2, "
					+ "bday, bmonth, byear, address2, phone2) "
				+ "VALUES ('" + contact.getFirstName() + "', '" + contact.getLastName() + "', '" 
					+ contact.getAddress() + "', '" + contact.getHome() + "', '" + contact.getMobile() + "', '" 
					+ contact.getWork() + "', '" + contact.getEmail1() + "', '" + contact.getEmail2() + "', '" 
					+ contact.getBirthDay() + "', '" + contact.getBirthMonth() + "', '" 
					+ contact.getBirthYear() + "', '" + contact.getAddress2() + "', '" + contact.getPhone2() + "');"
			);
			ResultSet res = statement.executeQuery("SELECT MAX(id) FROM `addressbook`");
			while (res.next()) {
				 contact.setId(Integer.parseInt(res.getString("MAX(id)")));
			 }
				    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return contact;
	}

	public void stop() {
//		driver.close();
		driver.quit();
	}

	public void moveContactToGroup(int idContact, int idGroup) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
				"INSERT INTO `address_in_groups` (id, group_id) VALUES ('" + idContact + "', '" + idGroup + "');"
			);
				    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 		
	}

}

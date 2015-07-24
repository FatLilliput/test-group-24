/*
 * This class contains methods needed for correctly auto-tests work 
 * and common methods for all application's modules
 */
package com.example.tests;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.example.fw.ApplicationManager;

public class TestBase {
	public static ApplicationManager app;
	public static String GROUPS_CSV_FILE = "./data/groups.txt";
	public static String GROUPS_XML_FILE = "./data/groups.xml";
	public static String GROUPS_FOR_SORTING = "./data/groupsSort.txt";
	public static String GROUP_TO_ADD = "./data/groupToAdd.txt";
	public static String GROUPS_2 = "./data/2groups.txt";
	
//	public static String CONTACTS_CSV_FILE = "./data/contacts.txt";
	public static String CONTACTS_XML_FILE = "./data/contacts.xml";
	public static String CONTACT_EDIT_XML_FILE = "./data/contactEdit.xml";
	public static String CONTACTS_2 = "./data/2contacts.xml";
	public static String CONTACT = "./data/contact.xml";

	

	@BeforeClass
	public void setUp() throws Exception {
		String configFile = System.getProperty("configFile", "firefox.properties");
		Properties properties = new Properties();
		properties.load(new FileReader(new File (configFile)));
		app = new ApplicationManager(properties);
		app.dbConnect();
	  }

	@AfterClass
	public void tearDown() throws Exception {
		app.dbClear();
		app.dbClose();
		app.stop();	    
	  }
	
	public static String GetRandomParameter(String parameter) {
		Random rnd = new Random();
		if (rnd.nextInt(2) == 0) {
			return "";
		} else {
			return parameter + rnd.nextInt();
		}	
	}



}

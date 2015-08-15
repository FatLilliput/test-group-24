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
import com.example.fw.HibernateHelper;
import com.example.fw.JDBCHelper;
import com.example.fw.ObjContact;
import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

public class TestBase {
	public static ApplicationManager app;
	/*
	 * Here you can choose database connect type
	 */
//	public static JDBCHelper inDataBase;
	public static HibernateHelper inDataBase;
	
	public static String GROUPS_CSV_FILE = "./data/groups.txt";
	public static String GROUPS_XML_FILE = "./data/groups.xml";
	public static String GROUPS_FOR_SORTING = "./data/groupsSort.txt";
	public static String GROUP_TO_ADD = "./data/groupToAdd.txt";
	public static String GROUPS_2 = "./data/2groups.txt";
	public static String GROUP = "./data/group.txt";

	public static String CONTACTS_XML_FILE = "./data/contacts.xml";
	public static String CONTACT_EDIT_XML_FILE = "./data/contactEdit.xml";
	public static String CONTACTS_2 = "./data/2contacts.xml";
	public static String CONTACT = "./data/contact.xml";
	public static String CONTACT1 = "./data/contact1.xml";
	public static String INVALID_CONTACT = "./data/invalidContact.xml";
	private int checkFrequency;
	private int checkCounter;

	public static SortedListOf<ObjGroup> currentGroupsList;
	public static SortedListOf<ObjContact> currentContactsList;
	
	@BeforeClass
	public void setUp() throws Exception {
		String configFile = System.getProperty("configFile", "firefox.properties");
		Properties properties = new Properties();
		properties.load(new FileReader(new File (configFile)));
		app = new ApplicationManager(properties);
		
		//Here you can choose database connect
//		inDataBase = app.getJDBCHelper();
		inDataBase = app.getHibernateHelper();
		
		inDataBase.dbClear();
		currentGroupsList = app.getModel().setGroups(inDataBase.listGroups()).getGroups();
		currentContactsList = app.getModel().setContacts(inDataBase.listContacts()).getContacts();
		checkCounter = 0;
		checkFrequency = Integer.parseInt(properties.getProperty("check.frequency", "0"));
		
	  }
	
	@AfterClass
	public void tearDown() throws Exception {
//		inDataBase.dbClear();
		inDataBase.dbClose();
		app.stop();	    
	  }
	protected boolean needToCheck () {
		checkCounter++;
		if (checkCounter > checkFrequency) {
			checkCounter = 0;
			return true;
		} else {
			return false;
		}
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

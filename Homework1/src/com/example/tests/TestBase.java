/*
 * This class contains methods needed for correctly auto-tests work 
 * and common methods for all application's modules
 */
package com.example.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.example.fw.ApplicationManager;

public class TestBase {
	public static ApplicationManager app;

	@BeforeTest
	public void setUp() throws Exception {
		app = new ApplicationManager();
	  }

	@AfterSuite
	public void tearDown() throws Exception {
		app.stop();	    
	  }
	
	public String GetRandomParameter(String parameter) {
		Random rnd = new Random();
		if (rnd.nextInt(2) == 0) {
			return "";
		} else {
			return parameter + rnd.nextInt();
		}	
	}



}

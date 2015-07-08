/*
 * This class contains methods needed for correctly auto-tests work 
 * and common methods for all application's modules
 */
package com.example.tests;

import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.example.fw.ApplicationManager;

public class TestBase {
	public static ApplicationManager app;

	@BeforeClass
	public void setUp() throws Exception {
		app = new ApplicationManager();
	  }

	@AfterClass
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

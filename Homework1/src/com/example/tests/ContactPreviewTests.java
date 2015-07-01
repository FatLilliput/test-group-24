package com.example.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContactPreviewTests extends TestBase {

	@BeforeTest
	public void setUp() throws Exception {
		//This condition needed if only ContactPreviewTests class is executed 
		if (app == null) {
			TestBase temp = new TestBase();
			temp.setUp();
		}
		ObjGroup group = new ObjGroup("TestGroup1", "Header123", "Footer123");
		app.getNavigationHelper().openGroupsPage();
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickGroupsList();
		group = new ObjGroup("TestGroup2", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickMainPage();
		
		ObjContact contact = new ObjContact();
		contact.firstName = "Name 1";
 		contact.lastName = "Soname 1";
 		contact.address = "123456 Contry City Address 1 1";
 		contact.home = "123 45 67";
 		contact.mobile = "8 123 456 78 90";
 		contact.work = "234657";
 		contact.email1 = "e@ya.ru";
 		contact.email2 = "e1@ya.ru";
 		contact.birthDay = "1";
 		contact.birthMonth = "January";
 		contact.group = "TestGroup1";
 		contact.birthYear = "1975";
 		contact.address2 = "987654 Contry2 City2 Street2 2 22";
 		contact.phone2 = "Sweet Home 123";
		app.getContactHelper().addContact(contact);		
		
		contact.firstName = "Name 2";
 		contact.lastName = "Soname 2";
 		contact.address = "654321 Contry12 City12 Address 12 12";
 		contact.home = "765 43 21";
 		contact.mobile = "8 908 765 43 21";
 		contact.work = "76549";
 		contact.email1 = "e2@ya.ru";
 		contact.email2 = "e23@ya.ru";
 		contact.birthDay = "10";
 		contact.birthMonth = "October";
 		contact.group = "TestGroup2";
 		contact.birthYear = "1969";
 		contact.address2 = "6543 Contry22 City22 Street22 22";
 		contact.phone2 = "Not Sweet Home 321";
		app.getContactHelper().addContact(contact);		
	  }
	
	@Test
	public void testSearchContact() {
		app.getNavigationHelper().openMainPage();
		app.getContactHelper().searchContact("Name 1");
		app.getContactHelper().searchContact("Name 2");
		app.getContactHelper().searchContact("");
	}
	
	@Test
	public void testSortByGroupContact() {
		app.getNavigationHelper().openMainPage();
		app.getContactHelper().selectGroup("TestGroup1");
		app.getContactHelper().selectGroup("TestGroup2");
		app.getContactHelper().selectGroup("[all]");
	}
	
	@Test
	//Make sure that url doesn't lead to "heavy" site 
	public void testCheckContactMainPage() {
		app.getNavigationHelper().openMainPage();
		app.getContactHelper().clickHomePage(null);
	}
}

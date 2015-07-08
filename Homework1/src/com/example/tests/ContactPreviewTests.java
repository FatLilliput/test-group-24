package com.example.tests;

import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class ContactPreviewTests extends TestBase {

	@BeforeClass
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
		
		//get full list of contacts
		List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		app.getContactHelper().searchContact("Name 1");
		//check that there is contact named "Name 1"
		boolean name1 = false;
		List<ObjContact> contacts1 = app.getContactHelper().getContactsList();
		for (ObjContact contact1 : contacts1) {
			if (contact1.firstName.equals("Name 1")) {
				name1 = true;
			}
		}
		assertTrue(name1);
		
		app.getContactHelper().searchContact("Name 2");
		//check that there is contact named "Name 2"
		boolean name2 = false;
		List<ObjContact> contacts2 = app.getContactHelper().getContactsList();
		for (ObjContact contact2 : contacts2) {
			if (contact2.firstName.equals("Name 2")) {
				name2 = true;
			}
		}
		assertTrue(name2);
		app.getContactHelper().searchContact("");
		
		//Check that empty searching returns default list
		List<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
		assertEquals(beforeTestingContacts, afterTestingContacts);
	}
	
	@Test
	public void testSortByGroupContact() {
		//save contacts parameters
		ObjContact contact1 = new ObjContact();
		contact1.firstName = "Name 1";
 		contact1.lastName = "Soname 1";
 		contact1.home = "1234567";
 		contact1.email1 = "e@ya.ru";
 		
 		ObjContact contact2 = new ObjContact();
		contact2.firstName = "Name 2";
 		contact2.lastName = "Soname 2";
 		contact2.home = "7654321";
 		contact2.email1 = "e2@ya.ru";
		
		app.getNavigationHelper().openMainPage();
		//get contacts list before testing
		List<ObjContact> beforeTestingContacts = app.getContactHelper().getContactsList();
		
		app.getContactHelper().selectGroup("TestGroup1");
		
 		//get contacts found in TestGroup1
 		List<ObjContact> contactsInGroup1 = app.getContactHelper().getContactsList();
 		//check that there is only first contact
 		assertTrue(contactsInGroup1.contains(contact1));
 		assertFalse(contactsInGroup1.contains(contact2));
 		
		app.getContactHelper().selectGroup("TestGroup2");
		
 		//get contacts found in TestGroup2
 		List<ObjContact> contactsInGroup2 = app.getContactHelper().getContactsList();
 		//check that there is only second contact
 		assertTrue(contactsInGroup2.contains(contact2));
 		assertFalse(contactsInGroup2.contains(contact1));
 		
		app.getContactHelper().selectGroup("[all]");
		//get contacts found in all groups
		List<ObjContact> afterTestingContacts = app.getContactHelper().getContactsList();
		assertEquals(beforeTestingContacts, afterTestingContacts);
		
		app.getContactHelper().selectGroup("[none]");
		//get contacts found outside groups
 		List<ObjContact> contactsOutOfGroup = app.getContactHelper().getContactsList();
 		//check that there is no contact
 		assertFalse(contactsOutOfGroup.contains(contact1));
 		assertFalse(contactsOutOfGroup.contains(contact2));
	}
}

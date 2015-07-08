/*
 * ContactsCreation class contains 2 tests for contacts creation:
 * - Correct contact creation
 * - Creating contact with empty parameters
 */
package com.example.tests;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ContactCreationTests extends ContactsTests {

	@Test(dataProvider = "randomValidContactDataGenerator")
	public void testAddNonEmptyContact(ObjContact contact) throws Exception {
		app.getNavigationHelper().openMainPage();
		
		//get Contacts list before testing
		List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		app.getContactHelper().addContact(contact);
		app.getNavigationHelper().clickMainPage();
		
		//get contacts list after testing
		List<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//modify contact
		if (contact.email1.equals("")) {
			contact.email1 = contact.email2;
		}
		if (contact.home.equals("")) {
			if (contact.mobile.equals("")) {
				contact.home = contact.work;
			} else {
				contact.home = contact.mobile;
			}
		}
		
		//Compare results
		beforeTestingContacts.add(contact);
		Collections.sort(beforeTestingContacts);
		Collections.sort(afterTestingContacts);
		assertEquals(beforeTestingContacts, afterTestingContacts);
		
	}
	
	@Test()
	public void testContactsPages() throws Exception {
		app.getNavigationHelper().openMainPage();
		ObjContact contact = new ObjContact();
		contact = contact.DefaultContact(contact);
		
		app.getContactHelper().addContact(contact);
		app.getNavigationHelper().clickMainPage();
		
		//check the contacts birthday is present
		app.getNavigationHelper().clickBirthList();
		assertTrue(app.getContactHelper().isBirthdayPresent(contact));
		
		//check that contact is present in print list
		app.getNavigationHelper().clicPrintAll();
		assertTrue(app.getContactHelper().isContactPresent(contact));
		
		//check that contact is present in phones list
		app.getNavigationHelper().openPrintPhones();
		assertTrue(app.getContactHelper().isPhonePresent(contact));
		
	}
	
	@Test
	public void testAddInvalidyContact() throws Exception {
		app.getNavigationHelper().openMainPage();
		
		//get Contacts list before testing
		List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		ObjContact contact = new ObjContact();
		contact.firstName = "Name'";
		contact.lastName = "Soname 1`";
		contact.address = "123456 Contry City Address 1 1";
		contact.home = "1234567";
		contact.mobile = "8 123 456 78 90";
		contact.work = "234657";
		contact.email1 = "e@ya.ru";
		contact.email2 = "e2@ya.ru";
		contact.birthDay = "1";
		contact.birthMonth = "January";
		contact.birthYear = "1975";
		contact.address2 = "987654 Contry2 City2 Street2 2 22";
		contact.phone2 = "Sweet Home 123";
		app.getContactHelper().addContact(contact);
		app.getNavigationHelper().clickMainPage();
		
		//get contacts list after testing
		List<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//Compare results
		assertEquals(beforeTestingContacts, afterTestingContacts);
		assertFalse(afterTestingContacts.contains(contact));
		
	}

}
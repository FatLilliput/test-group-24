/*
 * ContactsCreation class contains 2 tests for contacts creation:
 * - Correct contact creation
 * - Creating contact with empty parameters
 */
package com.example.tests;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

	@Test
	public void testAddNonEmptyContact() throws Exception {
		app.getNavigationHelper().openMainPage();
		ObjContact contact = new ObjContact();
		contact.firstName = "Name 1";
		contact.lastName = "Soname 1";
		contact.address = "123456 Contry City Address 1 1";
		contact.home = "123 45 67";
		contact.mobile = "8 123 456 78 90";
		contact.work = "234657";
		contact.email1 = "e@mail.com";
		contact.email2 = "e@mail.ru";
		contact.birthDay = "1";
		contact.birthMonth = "January";
		contact.birthYear = "1975";
		contact.address2 = "987654 Contry2 City2 Street2 2 22";
		contact.phone2 = "Sweet Home 123";
		app.getContactHelper().addContact(contact);
		app.getNavigationHelper().clickMainPage();
	}
	
	@Test
	public void testAddEmptyContact() throws Exception {
		app.getNavigationHelper().openMainPage();
		ObjContact contact = new ObjContact();
		app.getContactHelper().addContact(contact);		
		app.getNavigationHelper().clickMainPage();
	}
  
}
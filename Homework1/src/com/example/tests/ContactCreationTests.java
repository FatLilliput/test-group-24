/*
 * ContactsCreation class contains 2 tests for contacts creation:
 * - Correct contact creation
 * - Creating contact with empty parameters
 */
package com.example.tests;

import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactCreationTests extends ContactsTests {

	@Test(dataProvider = "randomValidContactDataGenerator")
	public void testAddNonEmptyContact(ObjContact contact) throws Exception {
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		app.getContactHelper().addContact(contact);
		
		//get contacts list after testing
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//modify contact
		if (contact.getEmail1().equals("")) {
			contact.setEmail1(contact.getEmail2());
		}
		if (contact.getHome().equals("")) {
			if (contact.getMobile().equals("")) {
				contact.setHome(contact.getWork());
			} else {
				contact.setHome(contact.getMobile());
			}
		}
		//Compare results
		assertThat(afterTestingContacts, equalTo(beforeTestingContacts.withAdded(contact)));

	}
	
	@Test()
	public void testContactsPages() throws Exception {
		ObjContact contact = new ObjContact();
		contact = contact.DefaultContact(contact);
		
		app.getContactHelper().addContact(contact);
		
		//check the contacts birthday is present
		assertThat(app.getContactHelper().isBirthdayPresent(contact), is(true));
		
		//check that contact is present in print list
		assertThat(app.getContactHelper().isContactPresent(contact), is(true));
		
		//check that contact is present in phones list
		assertThat(app.getContactHelper().isPhonePresent(contact), is(true));
		
	}
	
	@Test
	public void testAddInvalidyContact() throws Exception {
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		ObjContact contact = new ObjContact()
			.setFirstName ("Name'")
			.setLastName  ("Soname 1`")
			.setAddress   ("123456 Contry City Address 1 1")
			.setHome      ("1234567")
			.setMobile    ("8 123 456 78 90")
			.setWork      ("234657")
			.setEmail1    ("e@ya.ru")
			.setEmail2    ("e2@ya.ru")
			.setBirthDay  ("1")
			.setBirthMonth("January")
			.setBirthYear ("1975")
			.setAddress2  ("987654 Contry2 City2 Street2 2 22")
			.setPhone2    ("Sweet Home 123")
		;
		app.getContactHelper().addContact(contact);
		
		//get contacts list after testing
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//Compare results
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
		assertThat(afterTestingContacts, not(contains(contact)));
	}
}
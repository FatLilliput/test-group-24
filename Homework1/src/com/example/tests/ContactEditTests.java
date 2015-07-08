package com.example.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

public class ContactEditTests extends ContactsTests {

	@Test
	public void testEditPartOfContact () {
		int index = smartContactChoosing();
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper().clickEditContact(idContact);
		
		//save contact before testing
		ObjContact beforeContact = new ObjContact();
		beforeContact = app.getContactHelper().getContactParams();
		
		ObjContact contact = new ObjContact();
		contact.email1 = "editedE@ya.ru";
		contact.email2 = "edited2E@ya.ru";
		contact.birthDay = "10";
		contact.birthMonth = "May";
		app.getContactHelper().fillForm(contact);
		app.getContactHelper().clickUpdateContact();
		app.getNavigationHelper().clickMainPage();
		
		//save contact after testing
		app.getContactHelper().clickEditContact(idContact);
		ObjContact afterContact = new ObjContact();
		afterContact = app.getContactHelper().getContactParams();
		
		//Compare results
		beforeContact.email1 = contact.email1;
		beforeContact.email2 = contact.email2;
		beforeContact.birthDay = contact.birthDay;
		beforeContact.birthMonth = contact.birthMonth;
		assertEquals (beforeContact, afterContact);
		app.getNavigationHelper().clickMainPage();
		app.getContactHelper().clickViewContact(idContact);

		//Check preview page
		assertTrue(app.getContactHelper().isContactPresent(afterContact));

	}

	@Test(dataProvider = "randomValidContactDataGenerator")
	public void testFullEditContact (ObjContact contact) {
		int index = smartContactChoosing();
		
		//get Contacts list before testing
		List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper().clickEditContact(idContact);
		app.getContactHelper().fillForm(contact);
		app.getContactHelper().clickUpdateContact();
		app.getNavigationHelper().clickMainPage();
		
		//Check preview page 
		app.getContactHelper().clickViewContact(idContact);
		assertTrue(app.getContactHelper().isContactPresent(contact));
		
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
		beforeTestingContacts.remove(index);
		beforeTestingContacts.add(contact);
		Collections.sort(beforeTestingContacts);
		Collections.sort(afterTestingContacts);
		assertEquals(beforeTestingContacts, afterTestingContacts);
	}
	
	@Test
	public void testInvalidEditContact () {
		int index = smartContactChoosing();
		
		//get Contacts list before testing
		List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		int idContact = app.getContactHelper().getIdContact(index);
		ObjContact contact = new ObjContact();
		contact.lastName = "name'";
		app.getContactHelper().clickEditContact(idContact);
		
		//save old contact params
		ObjContact beforeContact = new ObjContact();
		beforeContact = app.getContactHelper().getContactParams();
		
		app.getContactHelper().fillForm(contact);
		app.getContactHelper().clickUpdateContact();
		app.getNavigationHelper().clickMainPage();
		
		//Check preview page 
		app.getContactHelper().clickViewContact(idContact);
		assertTrue(app.getContactHelper().isContactPresent(beforeContact));
		
		app.getNavigationHelper().clickMainPage();
		
		//get contacts list after testing
		List<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//Compare results
		assertEquals(beforeTestingContacts, afterTestingContacts);
	}
	
	@Test
	public void testAddContactToGroup () {
		app.getNavigationHelper().openGroupsPage();
		ObjGroup group = new ObjGroup("GroupToAdd", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickMainPage();
		int index = smartContactChoosing();
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper().addContactToGroup(idContact, "GroupToAdd");
		app.getNavigationHelper().clickMainPage();
		app.getContactHelper().clickViewContact(idContact);
		List<String> contact_groups = app.getContactHelper().getGroup();
		assertTrue(contact_groups.contains(group.name));
	}
}

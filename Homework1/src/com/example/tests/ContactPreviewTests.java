package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactPreviewTests extends TestBase {

	protected ObjContact contact1 = new ObjContact();
	protected ObjContact contact2 = new ObjContact();
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		
		//add test groups
		ObjGroup group = new ObjGroup("TestGroup1", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		group = new ObjGroup("TestGroup2", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		
		//add test contacts
		contact1 = contact1.contact1(contact1);
		app.getContactHelper().addContact(contact1);				
		contact2 = contact2.contact2(contact2);
		app.getContactHelper().addContact(contact2);		
	  }

	@Test
	public void testSearchContact() {
		//get full list of contacts
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		//search contacts with name of contact1
		app.getContactHelper().searchContact(contact1.getFirstName());
		//check that there is contact named as contact1
		boolean name1 = false;
		SortedListOf<ObjContact> contacts1 = app.getContactHelper().getContactsList();
		for (ObjContact contact : contacts1) {
			if (contact.getFirstName().equals(contact1.getFirstName())) {
				name1 = true;
			}
		}
		assertThat(name1, is(true));
		
		//search contacts with name of contact2
		app.getContactHelper().searchContact(contact2.getFirstName());
		//check that there is contact named as contact2
		boolean name2 = false;
		SortedListOf<ObjContact> contacts2 = app.getContactHelper().getContactsList();
		for (ObjContact contact : contacts2) {
			if (contact.getFirstName().equals(contact2.getFirstName())) {
				name2 = true;
			}
		}
		assertThat(name2, is(true));
		
		app.getContactHelper().searchContact("");
		//Check that empty searching returns default list
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
	}
	
	@Test
	public void testSortByGroupContact() {
		//get contacts list before testing
 		SortedListOf<ObjContact> beforeTestingContacts = app.getContactHelper().getContactsList();
		
 		//sort contacts from group "TestGroup1"
		app.getContactHelper().selectGroup("TestGroup1");
 		//get contacts found in TestGroup1
		SortedListOf<ObjContact> contactsInGroup1 = app.getContactHelper().getContactsList();
 		//check that there is only first contact
 		assertThat(contactsInGroup1, hasItem(contact1));
 		assertThat(contactsInGroup1, not(hasItem(contact2)));
 		
 		//sort contacts from group "TestGroup2"
		app.getContactHelper().selectGroup("TestGroup2");
 		//get contacts found in TestGroup2
		SortedListOf<ObjContact> contactsInGroup2 = app.getContactHelper().getContactsList();
 		//check that there is only second contact
 		assertThat(contactsInGroup2, hasItem(contact2));
 		assertThat(contactsInGroup2, not(hasItem(contact1)));
 		
 		//sort contacts from all groups
		app.getContactHelper().selectGroup("[all]");
		//get contacts found in all groups
		SortedListOf<ObjContact> afterTestingContacts = app.getContactHelper().getContactsList();
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
		
		//sort contacts from without groups
		app.getContactHelper().selectGroup("[none]");
		//get contacts found outside groups
		SortedListOf<ObjContact> contactsOutOfGroup = app.getContactHelper().getContactsList();
 		//check that there is no contact
 		assertThat(contactsOutOfGroup, not(hasItem(contact1)));
 		assertThat(contactsOutOfGroup, not(hasItem(contact2)));
	}
	
	@Test
	public void testPhonePageContent() throws Exception {
		//add several contacts if needed
		SortedListOf<ObjContact> contactList = app.getContactHelper().getContactsList();

		//check that contact is present in phones list
		for (ObjContact contact : contactList) {
			assertThat(app.getContactHelper().isPhonePresent(contact), is(true));
		}
		//check contacts count
		assertThat(contactList.size(), equalTo(app.getContactHelper().getContactsCount()));
	}
}

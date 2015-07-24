package com.example.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static com.example.tests.GroupsTests.addGroups;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactPreviewTests extends ContactsTests {

	protected List<ObjContact> contacts = new ArrayList<ObjContact>();
	protected List<ObjGroup> groups = new ArrayList<ObjGroup>();
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		
		//add test groups
		groups = loadGroupsFromFile(GROUPS_2);
		addGroups(groups);
		//add test contacts
		contacts = loadContactsFromFile(CONTACTS_2);
		addContacts(contacts);
		//move contacts to groups
		moveContactToGroup(contacts.get(0), groups.get(0));
		moveContactToGroup(contacts.get(1), groups.get(1));
	  }

	@Test
	public void testSearchContact() {
		//get full list of contacts
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		//search contacts with name of contact1
		app.getContactHelper().searchContact(contacts.get(0).getFirstName());
		//check that there is contact named as contact1
		boolean name1 = false;
		SortedListOf<ObjContact> contacts1 = app.getContactHelper().getContactsList();
		for (ObjContact contact : contacts1) {
			if (contact.getFirstName().equals(contacts.get(0).getFirstName())) {
				name1 = true;
			}
		}
		assertThat(name1, is(true));
		
		//search contacts with name of contact2
		app.getContactHelper().searchContact(contacts.get(1).getFirstName());
		//check that there is contact named as contact2
		boolean name2 = false;
		SortedListOf<ObjContact> contacts2 = app.getContactHelper().getContactsList();
		for (ObjContact contact : contacts2) {
			if (contact.getFirstName().equals(contacts.get(1).getFirstName())) {
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
		app.getContactHelper().selectGroup(groups.get(0).getName());
 		//get contacts found in TestGroup1
		SortedListOf<ObjContact> contactsInGroup1 = app.getContactHelper().getContactsList();
 		//check that there is only first contact
 		assertThat(contactsInGroup1, hasItem(contacts.get(0)));
 		assertThat(contactsInGroup1, not(hasItem(contacts.get(1))));
 		
 		//sort contacts from group "TestGroup2"
		app.getContactHelper().selectGroup(groups.get(1).getName());
 		//get contacts found in TestGroup2
		SortedListOf<ObjContact> contactsInGroup2 = app.getContactHelper().getContactsList();
 		//check that there is only second contact
 		assertThat(contactsInGroup2, hasItem(contacts.get(1)));
 		assertThat(contactsInGroup2, not(hasItem(contacts.get(0))));
 		
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
 		assertThat(contactsOutOfGroup, not(hasItem(contacts.get(0))));
 		assertThat(contactsOutOfGroup, not(hasItem(contacts.get(1))));
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

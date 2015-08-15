package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjContact;
import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactPageTests extends ContactsTests {

	private SortedListOf<ObjContact> allContacts;
	private SortedListOf<ObjGroup> groups;
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		
		//add test groups and contacts
		inDataBase.insertGroups(loadGroupsFromFile(GROUPS_2));
		groups = inDataBase.listGroups();
		inDataBase.insertContact(loadContactsFromFile(CONTACTS_2));
		allContacts = inDataBase.listContacts();
		//move contacts to groups
		inDataBase.moveContactToGroup(allContacts.get(0).getId(), groups.get(0).getId());
		inDataBase.moveContactToGroup(allContacts.get(1).getId(), groups.get(1).getId());
	  }

	@Test
	public void testSearchContact() {
		//search contacts with name of contact1
		app.getContactHelper().searchContact(allContacts.get(0).getFirstName());
		//check that there is contact named as contact1
		checkSearchingNamePresent(allContacts.get(0).getFirstName(), allContacts.get(1).getFirstName());
		
		//search contacts with name of contact2
		app.getContactHelper().searchContact(allContacts.get(1).getFirstName());
		//check that there is contact named as contact2
		checkSearchingNamePresent(allContacts.get(1).getFirstName(), allContacts.get(0).getFirstName());
		
		app.getContactHelper().searchContact("");
		//Check that empty searching returns default list
		SortedListOf<ObjContact> contacts =
				new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList());
		assertThat(allContacts, equalTo(contacts));
	}

	@Test
	public void testSortByGroupContact() {
		SortedListOf<ObjContact> contacts;
 		//sort contacts from group "TestGroup1"
		app.getContactHelper().selectGroup(groups.get(0).getName());
		checkContactsInGroup(allContacts.get(0), allContacts.get(1));
		
 		//sort contacts from group "TestGroup2"
		app.getContactHelper().selectGroup(groups.get(1).getName());
		checkContactsInGroup(allContacts.get(1), allContacts.get(0));
		
 		//sort contacts from all groups
		app.getContactHelper().selectGroup("[all]");
		if (app.getProperty("check.ui").equals("true")) {
			//get contacts found in all groups
			contacts = new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList());
			assertThat(allContacts, equalTo(contacts));
		}
		
		//sort contacts from without groups
		app.getContactHelper().selectGroup("[none]");
		if (app.getProperty("check.ui").equals("true")) {
			//get contacts found outside groups
			contacts = new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList());
			//check that there is no contact
			assertThat(contacts, not(hasItem(allContacts.get(0))));
			assertThat(contacts, not(hasItem(allContacts.get(1))));
		}
	}

	@Test
	public void testPhonePageContent() throws Exception {
		if (app.getProperty("check.ui").equals("true")) {
			//check that contact is present in phones list
			for (ObjContact contact : allContacts) {
				assertThat(app.getContactHelper().isPhonePresent(contact), is(true));
			}
			//check contacts count
			assertThat(allContacts.size(), equalTo(app.getContactHelper().getContactsCount()));
		}
	}
	
	private void checkSearchingNamePresent(String namePresent, String nameNotPresent) {
		if (app.getProperty("check.ui").equals("true")) {
			boolean nameTrue = false;
			boolean nameFalse = false;
			SortedListOf<ObjContact> contacts = 
				new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList());
			for (ObjContact contact : contacts) {
				if (contact.getFirstName().equals(namePresent)) {
					nameTrue = true;
				}
				if (contact.getFirstName().equals(nameNotPresent)) {
					nameFalse = true;
				}
			}
			assertThat((nameTrue && !nameFalse), is(true));
		}
	}
	
	private void checkContactsInGroup(ObjContact contactPresent, ObjContact contactNotPresent) {
		SortedListOf<ObjContact> contacts;
		if (app.getProperty("check.ui").equals("true")) {
			//get contacts found in TestGroup1
			contacts = new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList());
			//check that there is only first contact
			assertThat(contacts, hasItem(contactPresent));
			assertThat(contacts, not(hasItem(contactNotPresent)));
		}
	}
	
}

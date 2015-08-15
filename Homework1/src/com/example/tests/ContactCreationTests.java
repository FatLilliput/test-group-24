/*
 * ContactsCreation class contains 2 tests for contacts creation:
 * - Correct contact creation
 * - Creating contact with empty parameters
 */
package com.example.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.example.fw.ObjContact;
import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactCreationTests extends ContactsTests {

	@Test(dataProvider = "validContactsDataFromFile")
	public void testAddValidContact(ObjContact contact) throws Exception {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		//add contact
		app.getContactHelper().addContact(contact);

		//Compare results
		assertThat(currentContactsList, equalTo(beforeContactsList.withAdded(contact)));
		complicatedCheck();
	}

	@Test()
	public void testContactsPages() throws Exception {
		ObjContact contact = loadContactsFromFile(CONTACT1).get(0);	
		app.getContactHelper().addContact(contact);
		
		if (app.getProperty("check.ui").equals("true")) {
			//check the contacts birthday is present
			assertThat(app.getContactHelper().isBirthdayPresent(contact), is(true));
		
			//check that contact is present in print list
			app.getNavigationHelper().clickPrintAll();
			assertThat(app.getContactHelper().isContactPresent(contact), is(true));
		
			//check that contact is present in phones list
			assertThat(app.getContactHelper().isPhonePresent(contact), is(true));
		}
	}
	
	@Test
	public void testAddInvalidContact() throws Exception {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		//add contact with invalid parameters
		ObjContact contact = loadContactsFromFile(INVALID_CONTACT).get(0);		
		app.getContactHelper().addContact(contact);
		
		//Compare results
		assertThat(currentContactsList, equalTo(beforeContactsList));
		assertThat(currentContactsList, not(contains(contact)));
		complicatedCheck();
	}
	
	@Test
	public void testAddSimilarContacts() throws Exception {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		//init similar contacts parameters
		List<ObjContact> contact = loadContactsFromFile(CONTACT);
		
		//add similar groups
		inDataBase.insertContact(contact);
		currentContactsList = app.getModel().setContacts(inDataBase.listContacts()).getContacts();
		app.getContactHelper().addContact(contact.get(0));
		
		//Compare results
		assertThat(currentContactsList, equalTo(beforeContactsList.withAdded(contact.get(0)).withAdded(contact.get(0))));
		complicatedCheck();
	}
	
	@Test()
	public void testAddContactWithGroup() throws Exception {
		//init group creation
		List<ObjGroup> group = loadGroupsFromFile(GROUP);
		inDataBase.insertGroups(group);
		
		//add contact with group
		ObjContact contact = loadContactsFromFile(CONTACTS_2).get(1);
		contact.setGroup(group.get(0).getName());
		app.getContactHelper().addContact(contact);
		
		//get contact id
		SortedListOf<ObjContact> listContacts = inDataBase.listContacts();
		contact = listContacts.get(listContacts.size() - 1);
		
		//check that contact is present in phones list
		if (app.getProperty("check.ui").equals("true"))
			assertThat(app.getContactHelper().clickViewContact(contact.getId()).getGroup(), contains(group.get(0).getName()));
	}
}
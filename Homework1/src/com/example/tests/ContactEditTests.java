package com.example.tests;

import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjContact;
import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

public class ContactEditTests extends ContactsTests {
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		inDataBase.insertContact(loadContactsFromFile(CONTACTS_XML_FILE));
		currentContactsList = app.getModel().setContacts(inDataBase.listContacts()).getContacts();
	}
	
	@Test
	public void testEditPartOfContact () throws IOException {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		//select testing contact and edit it
		ObjContact beforeContact = beforeContactsList.getSome();
		ObjContact contact = loadContactsFromFile(CONTACT_EDIT_XML_FILE).get(0);
		app.getContactHelper().editContact(beforeContact.getId(), contact);
		
		//save contact after testing
		app.getContactHelper().clickEditContact(beforeContact.getId());
		ObjContact afterContact = app.getContactHelper().getContactParams();
		
		if (app.getProperty("check.ui").equals("true")) {
			//Compare results
			beforeContact
				.setEmail1    (contact.getEmail1())
				.setEmail2    (contact.getEmail2())
				.setBirthDay  (contact.getBirthDay())
				.setBirthMonth(contact.getBirthMonth())
				.setAddress   (contact.getEmail1())
				.setAddress2  (contact.getEmail1())
				;
			assertThat(beforeContact, equalTo(afterContact));
			app.getContactHelper().clickViewContact(beforeContact.getId());

			//Check preview page
			app.getNavigationHelper().clickPrintAll();
			assertThat(app.getContactHelper().isContactPresent(afterContact), is(true));
		}
	}

	@Test(dataProvider = "randomValidContactDataGenerator")
	public void testFullEditContact (ObjContact contact) {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
				
		//select testing contact
		ObjContact beforeContact = beforeContactsList.getSome();
		app.getContactHelper().editContact(beforeContact.getId(), contact);
		
		//modify contact and updating beforeTestingContacts
		contact.setId(beforeContact.getId());
				
		//Check preview page 
		app.getContactHelper().clickViewContact(contact.getId());
		if (app.getProperty("check.ui").equals("true")) 
			assertThat(app.getContactHelper().isContactPresent(contact), is(true));
		
		//Compare results
		complicatedCheck();
	}

	@Test
	public void testInvalidEditContact () {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
				
		//select testing contact
		ObjContact beforeContact = beforeContactsList.getSome();		
		ObjContact contact = new ObjContact().setLastName("name'");
		
		//save old contact parameters
		app.getContactHelper().editContact(beforeContact.getId(), contact);
		
		//Compare results
		if (app.getProperty("check.ui").equals("true"))
			assertThat(app.getContactHelper().clickViewContact(beforeContact.getId()).isContactPresent(beforeContact),
						is(true));
		
		assertThat(currentContactsList, equalTo(beforeContactsList));
		assertThat(currentContactsList, not(contains(contact)));
		complicatedCheck();
	}
	
	@Test
	public void testAddContactToGroup () throws IOException {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
				
		//add group to add contact
		List<ObjGroup> group = loadGroupsFromFile(GROUP_TO_ADD);
		inDataBase.insertGroups(group);
		
		//select testing contact and add it to the group
		ObjContact contact = beforeContactsList.getSome();
		app.getContactHelper()
			.addContactToGroup(contact.getId(), group.get(0).getName())
			.clickViewContact(contact.getId());
		
		//check that there is wanted group on the page
		if (app.getProperty("check.ui").equals("true")) {
			List<String> contact_groups = app.getContactHelper().getGroup();
			assertThat(contact_groups, hasItem(group.get(0).getName()));
		}
	}
	
	@Test
	public void testSimilarModfyingContacts() throws Exception {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		//init similar contacts parameters
		List<ObjContact> similarContact = loadContactsFromFile(CONTACT);
		
		//choose random contacts to edit
		ObjContact[] contacts = new ObjContact[2];
		contacts[0] = beforeContactsList.getSome();
			while (true) {
				contacts[1] = beforeContactsList.getSome();
				if(contacts[0] != contacts[1]) {
					break;
				}
			}
			
		//add similar groups
		app.getContactHelper().editContact(contacts[0].getId(), similarContact.get(0));
		app.getContactHelper().editContact(contacts[1].getId(), similarContact.get(0));
		
		//Compare results
		assertThat(currentContactsList, equalTo(
				beforeContactsList.without(contacts[0]).withAdded(similarContact.get(0))
					.without(contacts[1]).withAdded(similarContact.get(0))));
		complicatedCheck();
	}
}

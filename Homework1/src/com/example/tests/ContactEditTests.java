package com.example.tests;

import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

public class ContactEditTests extends ContactsTests {

	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		addContacts(loadContactsFromFile(CONTACTS_XML_FILE));
	}
	
	@Test
	public void testEditPartOfContact () throws IOException {
		//select testing contact
		int idContact = app.getContactHelper().getIdContact(app.getContactHelper().choosePosition());
		app.getContactHelper().clickEditContact(idContact);
		
		//save contact before testing
		ObjContact beforeContact = app.getContactHelper().getContactParams();
		
		ObjContact contact = loadContactsFromFile(CONTACT_EDIT_XML_FILE).get(0);
		app.getContactHelper()
			.fillForm(contact)
			.clickUpdateContact();
		
		//save contact after testing
		app.getContactHelper().clickEditContact(idContact);
		ObjContact afterContact = app.getContactHelper().getContactParams();
		
		//Compare results
		beforeContact
			.setEmail1    (contact.getEmail1())
			.setEmail2    (contact.getEmail2())
			.setBirthDay  (contact.getBirthDay())
			.setBirthMonth(contact.getBirthMonth())
		;
		assertThat(beforeContact, equalTo(afterContact));
		app.getContactHelper().clickViewContact(idContact);

		//Check preview page
		assertThat(app.getContactHelper().isContactPresent(afterContact), is(true));
	}

	//TODO Tests are failing. Need to fix
	@Test(dataProvider = "randomValidContactDataGenerator")
	public void testFullEditContact (ObjContact contact) {
		int index = app.getContactHelper().choosePosition();
		
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		//select testing contact
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper()
			.clickEditContact(idContact)
			.fillForm(contact)
			.clickUpdateContact();
		
		//Check preview page 
		app.getContactHelper().clickViewContact(idContact);
		assertThat(app.getContactHelper().isContactPresent(contact), is(true));
		
		//get contacts list after testing
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
						
		//modify contact
		contact = app.getContactHelper().formatContactForMainPage(contact);
		
		//Compare results
		assertThat(afterTestingContacts, equalTo(beforeTestingContacts.without(index).withAdded(contact)));
	}

	@Test
	public void testInvalidEditContact () {
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		//select testing contact
		int idContact = app.getContactHelper().getIdContact(app.getContactHelper().choosePosition());
		ObjContact contact = new ObjContact().setLastName("name'");
		app.getContactHelper().clickEditContact(idContact);
		
		//save old contact params
		ObjContact beforeContact = app.getContactHelper().getContactParams();
		
		app.getContactHelper()
			.fillForm(contact)
			.clickUpdateContact();
		
		//Check preview page 
		app.getContactHelper().clickViewContact(idContact);
		assertThat(app.getContactHelper().isContactPresent(beforeContact), is(true));
		
		//get contacts list after testing
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
				
		//Compare results
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
	}
	
	@Test
	public void testAddContactToGroup () throws IOException {
		//add group to add contact
		ObjGroup group = loadGroupsFromFile(GROUP_TO_ADD).get(0);
		app.getGroupHelper().addGroup(group);
		//select testing contact and add it to the group
		int idContact = app.getContactHelper().getIdContact(app.getContactHelper().choosePosition());
		app.getContactHelper()
			.addContactToGroup(idContact, group.getName())
			.clickViewContact(idContact);
		List<String> contact_groups = app.getContactHelper().getGroup();
		
		//check that there is wanted group on the page
		assertThat(contact_groups, hasItem(group.getName()));
	}
}

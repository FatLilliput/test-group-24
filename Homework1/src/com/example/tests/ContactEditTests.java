package com.example.tests;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;

public class ContactEditTests extends ContactsTests {

	@Test
	public void testEditPartOfContact () {
		int index = smartContactChoosing();
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper().clickEditContact(idContact);
		
		//save contact before testing
		ObjContact beforeContact = new ObjContact();
		beforeContact = app.getContactHelper().getContactParams();
		
		ObjContact contact = new ObjContact()
			.setEmail1    ("editedE@ya.ru")
			.setEmail2    ("edited2E@ya.ru")
			.setBirthDay  ("10")
			.setBirthMonth("May")
		;
		app.getContactHelper()
			.fillForm(contact)
			.clickUpdateContact();
		
		//save contact after testing
		app.getContactHelper().clickEditContact(idContact);
		ObjContact afterContact = new ObjContact();
		afterContact = app.getContactHelper().getContactParams();
		
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
		int index = smartContactChoosing();
		
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
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
		int index = smartContactChoosing();
		
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		int idContact = app.getContactHelper().getIdContact(index);
		ObjContact contact = new ObjContact().setLastName("name'");
		app.getContactHelper().clickEditContact(idContact);
		
		//save old contact params
		ObjContact beforeContact = new ObjContact();
		beforeContact = app.getContactHelper().getContactParams();
		
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
	public void testAddContactToGroup () {
		ObjGroup group = new ObjGroup("GroupToAdd", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		int index = smartContactChoosing();
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper()
			.addContactToGroup(idContact, "GroupToAdd")
			.clickViewContact(idContact);
		List<String> contact_groups = app.getContactHelper().getGroup();
		
		//check that there is wanted group on the page
		assertThat(contact_groups, hasItem(group.getName()));
	}
}

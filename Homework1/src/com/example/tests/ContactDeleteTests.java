package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContactDeleteTests extends ContactsTests{
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		addContacts(loadContactsFromFile(CONTACTS_XML_FILE));
	}
	//TODO Tests are failing. Need to fix
	@Test
	public void testDeleteContact () {
		int index = app.getContactHelper().choosePosition();
		
		//get Contacts list before testing
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		int idContact = app.getContactHelper().getIdContact(index);
		app.getContactHelper()
			.clickEditContact(idContact)
			.clickDeleteContact();
		
		//get contacts list after testing
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
		
		//Compare results
		assertThat(afterTestingContacts, equalTo(beforeTestingContacts.without(index)));
	}
}

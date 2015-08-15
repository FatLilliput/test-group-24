package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjContact;
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
		inDataBase.insertContact(loadContactsFromFile(CONTACTS_XML_FILE));
		currentContactsList = app.getModel().setContacts(inDataBase.listContacts()).getContacts();
	}

	@Test
	public void testDeleteContact () {
		//save model before testing
		SortedListOf<ObjContact> beforeContactsList = app.getModel().getContactsCopy();
		
		ObjContact contact = beforeContactsList.getSome();
		app.getContactHelper().deleteContact(contact);
		
		//Compare results
		assertThat(currentContactsList, equalTo(beforeContactsList.without(contact)));
		complicatedCheck();
	}
}

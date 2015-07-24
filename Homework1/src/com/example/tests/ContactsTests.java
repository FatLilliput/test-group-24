package com.example.tests;

import static com.example.tests.ContactsDataGenerator.generatedRandomContacts;
import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

public class ContactsTests extends TestBase{

	@DataProvider
	public Iterator<Object[]> randomValidContactDataGenerator() {
		return wrapContactsForDataProvider(generatedRandomContacts(5)).iterator();
	}
	
	@DataProvider
	public Iterator<Object[]> validContactsDataFromFile() throws IOException {
		return wrapContactsForDataProvider(loadContactsFromFile(CONTACTS_XML_FILE)).iterator();
	}

	private List<Object[]> wrapContactsForDataProvider(List<ObjContact> contacts) {
		List<Object[]> wrappedContacts = new ArrayList<Object[]>();
		for(ObjContact contact : contacts) {
			wrappedContacts.add(new Object[]{contact});
		}
		return wrappedContacts;
	}

	protected void addContacts(List<ObjContact> contacts) {
		for (ObjContact contact : contacts) {
			app.insertContact(contact);
			app.getNavigationHelper().openMainPage();
		}
		
	}
	protected void moveContactToGroup(ObjContact contact, ObjGroup group) {
		app.moveContactToGroup(contact.getId(), group.getId());
	}

}

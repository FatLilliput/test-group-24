package com.example.tests;

import static com.example.tests.ContactsDataGenerator.generatedRandomContacts;
import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.example.fw.ObjContact;
import com.example.utilits.SortedListOf;

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

	protected void complicatedCheck() {
		if (needToCheck()) {
			if (app.getProperty("check.db").equals("true")) {
				assertThat(currentContactsList, equalTo(inDataBase.listContacts()));
			}
			if (app.getProperty("check.ui").equals("true")) {
				assertThat(
					app.getContactHelper().formatContactsListForMainPage(app.getModel().getContactsCopy()), 
					equalTo(new SortedListOf<ObjContact>(app.getContactHelper().getUnsortedContactsList())));
			}
		}
	}

}

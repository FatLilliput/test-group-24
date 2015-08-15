package com.example.tests;

import static com.example.tests.ContactsDataGenerator.loadContactsFromFile;
import com.example.fw.ObjContact;
import com.example.utilits.SortedListOf;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ContactsPreviewTests extends ContactsTests {

	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		
		//add test groups and contacts
		inDataBase.insertContact(loadContactsFromFile(CONTACTS_2));
	  }
	
	@Test
	public void testContentList() throws IOException {
		inDataBase.insertContact(loadContactsFromFile(CONTACTS_XML_FILE));
		if (app.getProperty("check.ui").equals("true")) {
			SortedListOf<ObjContact> allContacts = inDataBase.listContacts();
			assertThat(app.getContactHelper().formatContactsListForMainPage(allContacts), 
					equalTo(new SortedListOf<ObjContact> (app.getContactHelper().getUnsortedContactsList())));
		}
	}
	
	@Test
	public void testContentSorted() {
		if (app.getProperty("check.ui").equals("true")) {
			SortedListOf<ObjContact> allContacts = inDataBase.listContacts();
			assertThat(app.getContactHelper().formatContactsListForMainPage(allContacts), 
					equalTo(app.getContactHelper().getUnsortedContactsList()));
		}
	}
}

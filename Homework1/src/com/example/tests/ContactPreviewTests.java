package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ContactPreviewTests extends TestBase {

	//TODO hide contact filling
	@BeforeClass
	public void setUp() throws Exception {
		TestBase temp = new TestBase();
		temp.setUp();
		ObjGroup group = new ObjGroup("TestGroup1", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		group = new ObjGroup("TestGroup2", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		
		ObjContact contact = new ObjContact()
			.setFirstName("Name 1")
 			.setLastName  ("Soname 1")
 			.setAddress   ("123456 Contry City Address 1 1")
 			.setHome      ("123 45 67")
 			.setMobile    ("8 123 456 78 90")
 			.setWork      ("234657")
 			.setEmail1    ("e@ya.ru")
 			.setEmail2    ("e1@ya.ru")
 			.setBirthDay  ("1")
 			.setBirthMonth("January")
 			.setGroup     ("TestGroup1")
 			.setBirthYear ("1975")
 			.setAddress2  ("987654 Contry2 City2 Street2 2 22")
 			.setPhone2    ("Sweet Home 123")
 		;
		app.getContactHelper().addContact(contact);		
		
		contact
			.setFirstName ("Name 2")
 			.setLastName  ("Soname 2")
 			.setAddress   ("654321 Contry12 City12 Address 12 12")
 			.setHome      ("765 43 21")
 			.setMobile    ("8 908 765 43 21")
 			.setWork      ("76549")
 			.setEmail1    ("e2@ya.ru")
 			.setEmail2    ("e23@ya.ru")
 			.setBirthDay  ("10")
 			.setBirthMonth("October")
 			.setGroup     ("TestGroup2")
 			.setBirthYear ("1969")
 			.setAddress2  ("6543 Contry22 City22 Street22 22")
 			.setPhone2    ("Not Sweet Home 321")
 		;
		app.getContactHelper().addContact(contact);		
	  }

	@Test
	public void testSearchContact() {
		//get full list of contacts
		SortedListOf<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
		
		app.getContactHelper().searchContact("Name 1");
		//check that there is contact named "Name 1"
		boolean name1 = false;
		SortedListOf<ObjContact> contacts1 = app.getContactHelper().getContactsList();
		for (ObjContact contact1 : contacts1) {
			if (contact1.getFirstName().equals("Name 1")) {
				name1 = true;
			}
		}
		assertThat(name1, is(true));
		
		app.getContactHelper().searchContact("Name 2");
		//check that there is contact named "Name 2"
		boolean name2 = false;
		SortedListOf<ObjContact> contacts2 = app.getContactHelper().getContactsList();
		for (ObjContact contact2 : contacts2) {
			if (contact2.getFirstName().equals("Name 2")) {
				name2 = true;
			}
		}
		assertThat(name2, is(true));
		
		app.getContactHelper().searchContact("");
		
		//Check that empty searching returns default list
		SortedListOf<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
	}
	
	@Test
	public void testSortByGroupContact() {
		//save contacts parameters
		ObjContact contact1 = new ObjContact()
			.setFirstName("Name 1")
 			.setLastName ("Soname 1")
 			.setHome     ("1234567")
 			.setEmail1   ("e@ya.ru")
 		;
 		
 		ObjContact contact2 = new ObjContact()
			.setFirstName("Name 2")
 			.setLastName ("Soname 2")
 			.setHome     ("7654321")
 			.setEmail1   ("e2@ya.ru")
 		;
		
		//get contacts list before testing
 		SortedListOf<ObjContact> beforeTestingContacts = app.getContactHelper().getContactsList();
		
		app.getContactHelper().selectGroup("TestGroup1");
		
 		//get contacts found in TestGroup1
		SortedListOf<ObjContact> contactsInGroup1 = app.getContactHelper().getContactsList();
 		//check that there is only first contact
 		assertThat(contactsInGroup1, hasItem(contact1));
 		assertThat(contactsInGroup1, not(hasItem(contact2)));
 		
		app.getContactHelper().selectGroup("TestGroup2");
		
 		//get contacts found in TestGroup2
		SortedListOf<ObjContact> contactsInGroup2 = app.getContactHelper().getContactsList();
 		//check that there is only second contact
 		assertThat(contactsInGroup2, hasItem(contact2));
 		assertThat(contactsInGroup2, not(hasItem(contact1)));
 		
		app.getContactHelper().selectGroup("[all]");
		//get contacts found in all groups
		SortedListOf<ObjContact> afterTestingContacts = app.getContactHelper().getContactsList();
		assertThat(beforeTestingContacts, equalTo(afterTestingContacts));
		
		app.getContactHelper().selectGroup("[none]");
		//get contacts found outside groups
		SortedListOf<ObjContact> contactsOutOfGroup = app.getContactHelper().getContactsList();
 		//check that there is no contact
 		assertThat(contactsOutOfGroup, not(hasItem(contact1)));
 		assertThat(contactsOutOfGroup, not(hasItem(contact2)));
	}
	
	//TODO Implement
	@Test()
	public void testPhonePageContent() throws Exception {
		//add several contacts if needed
		ObjContact contact = new ObjContact();
		contact = contact.DefaultContact(contact);
		app.getContactHelper().addContact(contact);
			
		//check that contact is present in phones list
		assertThat(app.getContactHelper().isPhonePresent(contact), is(true));
		
	}
}

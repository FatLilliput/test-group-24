package com.example.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

public class ContactDeleteTests extends ContactsTests{
		
		@Test
		public void testDeleteContact () {
			int index = smartContactChoosing();
			
			//get Contacts list before testing
			List<ObjContact> beforeTestingContacts= app.getContactHelper().getContactsList();
			
			int idContact = app.getContactHelper().getIdContact(index);
			app.getContactHelper().clickEditContact(idContact);
			app.getContactHelper().clickDeleteContact();
			app.getNavigationHelper().clickMainPage();
			
			//get contacts list after testing
			List<ObjContact> afterTestingContacts= app.getContactHelper().getContactsList();
			
			//Compare results
			beforeTestingContacts.remove(index);
			assertEquals(beforeTestingContacts, afterTestingContacts);
		}


}

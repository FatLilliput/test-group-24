package com.example.tests;

import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContactDeleteTests extends ContactsTests{
		
		@Test
		public void testDeleteContact () {
			int index = smartContactChoosing();
			
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

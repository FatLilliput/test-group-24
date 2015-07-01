package com.example.tests;

import org.testng.annotations.Test;

public class ContactDeleteTests extends TestBase{
		
		@Test
		public void testDeleteContact () {
			app.getNavigationHelper().openMainPage();
			if (!app.getContactHelper().contactExist()) {
				ObjContact contact = new ObjContact();
				contact.DefaultContact(contact);
				app.getContactHelper().addContact(contact);		
				app.getNavigationHelper().clickMainPage();
			}
			app.getContactHelper().clickEditContact(null);
			app.getContactHelper().clickDeleteContact();
			app.getNavigationHelper().clickMainPage();
		}


}

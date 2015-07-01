package com.example.tests;

import org.testng.annotations.Test;

public class ContactEditTests extends TestBase {

	@Test
	public void testEditContact () {
		app.getNavigationHelper().openMainPage();
		if (!app.getContactHelper().contactExist()) {
			ObjContact contact = new ObjContact();
			contact.DefaultContact(contact);
			app.getContactHelper().addContact(contact);		
			app.getNavigationHelper().clickMainPage();
		}
		app.getContactHelper().clickEditContact(null);
		ObjContact contact = new ObjContact();
		contact.email1 = "editedE@mail.com";
		contact.email2 = "editedE@mail.ru";
		contact.birthDay = "10";
		contact.birthMonth = "May";
		app.getContactHelper().fillForm(contact);
		app.getContactHelper().clickUpdateContact();
		app.getNavigationHelper().clickMainPage();
		app.getContactHelper().clickViewContact(null);
	}
	
	@Test
	public void testAddContactToGroup () {
		app.getNavigationHelper().openMainPage();
		if (!app.getContactHelper().contactExist()) {
			ObjContact contact = new ObjContact();
			contact.DefaultContact(contact);
			app.getContactHelper().addContact(contact);		
			app.getNavigationHelper().clickMainPage();
		}
		app.getNavigationHelper().openGroupsPage();
		ObjGroup group = new ObjGroup("GroupToAdd", "Header123", "Footer123");
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickMainPage();
		app.getContactHelper().addContactToGroup(null, "GroupToAdd");
		app.getNavigationHelper().clickMainPage();
		app.getContactHelper().clickViewContact(null);
	}
}

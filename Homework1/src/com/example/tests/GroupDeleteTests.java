package com.example.tests;

import org.testng.annotations.Test;

public class GroupDeleteTests extends TestBase{
	
	@Test
	public void deleteGroup () {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		if (!app.getGroupHelper().groupExist()) {
			ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
			app.getGroupHelper().addGroup(group);
			app.getNavigationHelper().clickGroupsList();
		}
		app.getGroupHelper().selectGroup(null);
		app.getGroupHelper().deleteGroup();
		app.getNavigationHelper().clickGroupsList();
	}

}

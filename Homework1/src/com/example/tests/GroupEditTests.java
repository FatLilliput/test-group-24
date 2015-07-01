package com.example.tests;
import org.testng.annotations.Test;

public class GroupEditTests extends TestBase {
	@Test
	public void testGriopEdit () {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		if (!app.getGroupHelper().groupExist()) {
			ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
			app.getGroupHelper().addGroup(group);
			app.getNavigationHelper().clickGroupsList();
		}
		app.getGroupHelper().clickEdit(null);
		ObjGroup group = new ObjGroup();
		group.header =  "Header11";
		app.getGroupHelper().fillForm(group);
		app.getGroupHelper().clickModify();
		app.getNavigationHelper().openGroupsPage();
		app.getGroupHelper().clickEdit(null);
	}

}

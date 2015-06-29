package com.example.tests;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

	@Test
	public void testNonEmptyGroupCreation() throws Exception {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		app.getGroupHelper().clickNewGroup();
		ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
		app.getGroupHelper().fillForm(group);
		app.getGroupHelper().clickSubmitGroup();
		app.getNavigationHelper().clickGroupsList();
	}
  
  @Test
  public void testEmptyGroupCreation() throws Exception {
	  app.getNavigationHelper().openMainPage();
	  app.getNavigationHelper().clickGroupsList();
	  app.getGroupHelper().clickNewGroup();
	  ObjGroup group = new ObjGroup("", "", "");
	  app.getGroupHelper().fillForm(group);
	  app.getGroupHelper().clickSubmitGroup();
	  app.getNavigationHelper().clickGroupsList();
  }
  
}

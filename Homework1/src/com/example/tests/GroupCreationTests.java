package com.example.tests;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

	@Test
	public void testNonEmptyGroupCreation() throws Exception {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickGroupsList();
	}
  
  @Test
  public void testEmptyGroupCreation() throws Exception {
	  app.getNavigationHelper().openMainPage();
	  app.getNavigationHelper().clickGroupsList();
	  ObjGroup group = new ObjGroup("", "", "");
	  app.getGroupHelper().addGroup(group);
	  app.getNavigationHelper().clickGroupsList();
  }
  
}

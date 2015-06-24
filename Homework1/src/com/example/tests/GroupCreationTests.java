package com.example.tests;

import org.testng.annotations.Test;

public class GroupCreationTests extends GroupHelper {
  @Test
  /**
   * Add new group test
   */
  public void testNonEmptyGroupCreation() throws Exception {
	openMainPage();
    openGroupsList();
    clickNewGroup();
    ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
    fillForm(group);
    clickSubmitGroup();
    openGroupsList();
  }
  
  @Test
  /**
   * Add new group with empty values test
   */
  public void testEmptyGroupCreation() throws Exception {
	openMainPage();
    openGroupsList();
    clickNewGroup();
    ObjGroup group = new ObjGroup("", "", "");
    fillForm(group);
    clickSubmitGroup();
    openGroupsList();
  }
  
}

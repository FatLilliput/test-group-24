/*
 * ContactsCreation class will contain tests for contacts creation only
 * There are list of todos for Homework 2 below:
 * TODO: Remove testNonEmptyGroupCreation() and testEmptyGroupCreation()
 * TODO: Add testNonEmptyContactCreation() and testEmptyContactCreation()
 * TODO: Add ContactHeper class and make ContactsCreationTestsextending it
 */
package com.example.tests;

import org.testng.annotations.Test;

public class ContactsCreationTests extends GroupHelper {

  @Test
  /**
   * Add new group test
   */
  public void testNonEmptyGroupCreation() throws Exception {
	openMainPage();
    openGroupsList();
    clickNewGroup();
    ObjGroup group = new ObjGroup();
    group.name = "Group2";
    group.header = "Header2";
    group.footer = "Footer2";
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

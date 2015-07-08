package com.example.tests;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;


public class GroupCreationTests extends GroupsTests {
	
	@Test (dataProvider = "randomValidDataGenerator")
	public void testGroupWithValidDataCreation(ObjGroup group) throws Exception {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		
		//get list of groups before test
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
		
		//add new group
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickGroupsList();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
		
		//Check that test group was correctly added
		beforeGroupsList.add(group);
		Collections.sort(beforeGroupsList);
		assertEquals(beforeGroupsList, afterGroupsList);
	}
	
	@Test
	public void testGroupWithInalidDataCreation() throws Exception {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		
		//get list of groups before test
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//edit first group
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper().addGroup(group);
		app.getNavigationHelper().clickGroupsList();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test invalid group was not added
		assertEquals(beforeGroupsList, afterGroupsList);
		assertFalse(afterGroupsList.contains(group));
	}
	
}

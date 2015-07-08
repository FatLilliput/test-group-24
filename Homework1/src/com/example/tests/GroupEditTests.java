package com.example.tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

public class GroupEditTests extends GroupsTests {
	
	@Test(dataProvider = "randomValidDataGenerator")
	public void testFullGroupEdit (ObjGroup group) {
		int index = extendedGroupIndexGettind();
		//get list of groups before test
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//edit first group
		int idGroup = app.getGroupHelper().getIdGroup(index);
		app.getGroupHelper().clickEdit(idGroup);
		app.getGroupHelper().fillForm(group);
		app.getGroupHelper().clickModify();
		app.getNavigationHelper().openGroupsPage();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly edited
		beforeGroupsList.remove(index);
		beforeGroupsList.add(group);
		Collections.sort(beforeGroupsList);
		assertEquals(beforeGroupsList, afterGroupsList);
	}
	
	@Test
	public void testPartGroupEdit () {
		int index = extendedGroupIndexGettind();
				
		//select group to edit
		int idGroup = app.getGroupHelper().getIdGroup(index);
		app.getGroupHelper().clickEdit(idGroup);
		
		//get groups parameters before editing
		ObjGroup beforeGroup = new ObjGroup();
		beforeGroup = app.getGroupHelper().getGroupParams(beforeGroup);
		
		//edit group
		ObjGroup group = new ObjGroup();
		group.header =  "NewHeader11";
		app.getGroupHelper().fillForm(group);
		app.getGroupHelper().clickModify();
		app.getNavigationHelper().openGroupsPage();
		app.getGroupHelper().clickEdit(idGroup);
		
		//get groups parameters after editing
		ObjGroup afterGroup = new ObjGroup();
		afterGroup = app.getGroupHelper().getGroupParams(afterGroup);
		
		//Check that test group was correctly edited
		ObjGroup groupEdited = new ObjGroup();
		groupEdited.header = group.header;
		
		beforeGroup.header = group.header;
		assertEquals(beforeGroup, afterGroup);
	}
	
	@Test
	public void testNotValidGroupEdit () {
		int index = extendedGroupIndexGettind();
		//get list of groups before test
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//edit first group
		int idGroup = app.getGroupHelper().getIdGroup(index);
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper().clickEdit(idGroup);
		app.getGroupHelper().fillForm(group);
		app.getGroupHelper().clickModify();
		app.getNavigationHelper().openGroupsPage();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that group was not incorrectly edited
		assertEquals(beforeGroupsList, afterGroupsList);
		assertFalse(afterGroupsList.contains(group));
	}
}

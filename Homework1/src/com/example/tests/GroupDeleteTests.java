package com.example.tests;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

public class GroupDeleteTests extends GroupsTests {
		
	@Test
	public void deleteOneGroup () {
		int index = extendedGroupIndexGettind();
		
		//get list of groups before test
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
		
		//delete group
		int idGroup = app.getGroupHelper().getIdGroup(index);
		app.getGroupHelper().selectGroup(idGroup);
		app.getGroupHelper().deleteGroup();
		app.getNavigationHelper().clickGroupsList();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		beforeGroupsList.remove(index);
		Collections.sort(beforeGroupsList);
		assertEquals(beforeGroupsList, afterGroupsList);
	}

	@Test
	public void deleteSeveralGroups () {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		List<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList();
		if (beforeGroupsList.size() < 3) {
			ObjGroup group;
			for (int i = 0; i < 4; i++) {
				group = new ObjGroup("Group1ToDelede" + i, "Header1td" + i, "Footer1td" + i);
				app.getGroupHelper().addGroup(group);
				app.getNavigationHelper().clickGroupsList();
			}
		}
		int index1 = app.getGroupHelper().choosePosition();
		int index2;
		while (true) {
			index2 = app.getGroupHelper().choosePosition();
			 if(index1 != index2) {
			    break;
			 }
		}
		
		//get list of groups before test
		beforeGroupsList = app.getGroupHelper().getGroupList(); 
		
		//delete group
		int idGroup1 = app.getGroupHelper().getIdGroup(index1);
		int idGroup2 = app.getGroupHelper().getIdGroup(index2);
		app.getGroupHelper().selectGroup(idGroup1);
		app.getGroupHelper().selectGroup(idGroup2);
		app.getGroupHelper().deleteGroup();
		app.getNavigationHelper().clickGroupsList();
		
		//get groups list after adding test group
		List<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		beforeGroupsList.remove(Integer.min(index1, index2));
		beforeGroupsList.remove(Integer.max(index1, index2) - 1);
		Collections.sort(beforeGroupsList);
		assertEquals(beforeGroupsList, afterGroupsList);
	}
	
}

package com.example.tests;

import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class GroupDeleteTests extends GroupsTests {
		
	@Test
	public void deleteOneGroup () {
		int index = extendedGroupIndexGettind();
		
		//get list of groups before test
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
		
		//delete group
		int idGroup = app.getGroupHelper().getIdGroup(index);
		app.getGroupHelper()
			.selectGroup(idGroup)
			.deleteGroup();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		assertThat(afterGroupsList, equalTo(beforeGroupsList.without(index)));
	}

	@Test
	public void deleteSeveralGroups () {
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList();
		if (beforeGroupsList.size() < 3) {
			ObjGroup group;
			for (int i = 0; i < 4; i++) {
				group = new ObjGroup("Group1ToDelede" + i, "Header1td" + i, "Footer1td" + i);
				app.getGroupHelper().addGroup(group);
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
		app.getGroupHelper()
			.selectGroup(idGroup1)
			.selectGroup(idGroup2)
			.deleteGroup();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		assertThat(
				afterGroupsList, 
				equalTo(beforeGroupsList.without(Integer.min(index1, index2)).without(Integer.max(index1, index2) - 1))
			);
	}
	
}

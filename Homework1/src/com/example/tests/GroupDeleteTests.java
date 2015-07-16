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
		app.getGroupHelper()
			.selectGroup(index)
			.deleteGroup();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		assertThat(afterGroupsList, equalTo(beforeGroupsList.without(index-1)));
	}

	@Test
	public void deleteSeveralGroups () {
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList();
		setUpDeleteSeveralGroups(beforeGroupsList.size());
		
		int index1 = app.getGroupHelper().choosePosition();
		int index2;
		while (true) {
			index2 = app.getGroupHelper().choosePosition();
			 if(index1 != index2) {
			    break;
			 }
		}
		
		//delete group
		app.getGroupHelper()
			.selectGroup(index1)
			.selectGroup(index2)
			.deleteGroup();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly added
		assertThat(
				afterGroupsList, 
				equalTo(beforeGroupsList.without(Integer.min((index1-1), (index2-1))).without(Integer.max((index1-1), (index2-1)) - 1))
			);
	}
	
}

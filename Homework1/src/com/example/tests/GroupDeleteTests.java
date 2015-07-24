package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class GroupDeleteTests extends GroupsTests {
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		addGroups(loadGroupsFromFile(GROUPS_XML_FILE));
	}
	
	@Test
	public void deleteOneGroup () {
		int index = app.getGroupHelper().choosePosition();
		
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

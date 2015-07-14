package com.example.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;

public class GroupCreationTests extends GroupsTests {
	
	@Test (dataProvider = "randomValidDataGenerator")
	public void testGroupWithValidDataCreation(ObjGroup group) throws Exception {
		//get list of groups before test
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
		
		//add new group
		app.getGroupHelper().addGroup(group);
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
		
		//Check that test group was correctly added
		assertThat(afterGroupsList, equalTo(beforeGroupsList.withAdded(group)));
	}
	
	@Test
	public void testGroupWithInalidDataCreation() throws Exception {
		//get list of groups before test
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//add group with invaild parameters
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper().addGroup(group);
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test invalid group was not added
		assertThat(afterGroupsList, equalTo(beforeGroupsList));
		assertThat(afterGroupsList, not(contains(group)));
	}
	
}

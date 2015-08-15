package com.example.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

public class GroupCreationTests extends GroupsTests {
	
	@Test (dataProvider = "validGroupsDataFromFile")
	public void testGroupWithValidDataCreation(ObjGroup group) throws Exception {
		//save model before testing
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
		
		//add new group
		app.getGroupHelper().addGroup(group);

		//checks
		assertThat(currentGroupsList, equalTo(beforeGroupsList.withAdded(group)));
		
		ComplicatedCheck();
	}
	
	@Test
	public void testGroupWithInvalidDataCreation() throws Exception {
		//get groups list before adding test group
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
				
		//add group with invaild parameters
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper().addGroup(group);
		
		//Check that test invalid group was not added
		assertThat(beforeGroupsList, equalTo(currentGroupsList));
		assertThat(currentGroupsList, not(contains(group)));
		ComplicatedCheck();
	}
	
	@Test
	public void testSimilarGroupsCreation() throws Exception {
		//get groups list before adding test group
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
				
		//add 2 same groups
		ObjGroup group = new ObjGroup("Name", "Header", "Footer");
		app.getGroupHelper().addGroup(group);
		app.getGroupHelper().addGroup(group);
		
		//Check that test invalid group was not added
		assertThat(currentGroupsList, equalTo(beforeGroupsList.withAdded(group).withAdded(group)));

		ComplicatedCheck();
	}
	
}

package com.example.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjGroup;
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
		inDataBase.insertGroups(loadGroupsFromFile(GROUPS_XML_FILE));
		currentGroupsList = app.getModel().setGroups(inDataBase.listGroups()).getGroups();		
	}
	
	@Test
	public void deleteOneGroup () {
		//save model before testing
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
		
		ObjGroup[] groups = {beforeGroupsList.getSome()};
		
		//delete group
		app.getGroupHelper().deleteGroups(groups);
				
		//Check that test group was correctly added
		assertThat(currentGroupsList, equalTo(beforeGroupsList.without(groups[0])));
		
		ComplicatedCheck();
	}

	@Test
	public void deleteSeveralGroups () {
		//save model before testing
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
				
		//choose random groups
		ObjGroup[] groups = new ObjGroup[2];
		groups[0] = beforeGroupsList.getSome();
		while (true) {
			groups[1] = beforeGroupsList.getSome();
			 if(groups[0] != groups[1]) {
			    break;
			 }
		}
		
		//delete group
		app.getGroupHelper().deleteGroups(groups);
		
		//Check that test group was correctly added
		assertThat(currentGroupsList, equalTo(beforeGroupsList.without(groups[0]).without(groups[1])));
		
		ComplicatedCheck();
	}
	
}

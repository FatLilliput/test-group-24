package com.example.tests;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

public class GroupEditTests extends GroupsTests {
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		inDataBase.insertGroups(loadGroupsFromFile(GROUPS_XML_FILE));
		currentGroupsList = app.getModel().setGroups(inDataBase.listGroups()).getGroups();
	}

	@Test(dataProvider = "randomValidDataGenerator")
	public void testFullGroupEdit (ObjGroup group) {
		//save model before testing
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
		
		//select group to edit
		ObjGroup oldGroup = beforeGroupsList.getSome();
		
		//edit group
		app.getGroupHelper().editGroup(oldGroup.getId(), group);
				
		//Check that test group was correctly edited
		assertThat(currentGroupsList, equalTo(beforeGroupsList.without(oldGroup).withAdded(group)));
		
		ComplicatedCheck();
	}

	@Test
	public void testPartGroupEdit () {	
		//select group to edit
		ObjGroup beforeGroup = currentGroupsList.getSome();
		
		//edit group
		ObjGroup group = new ObjGroup().setHeader("NewHeader11");
		app.getGroupHelper()
			.editGroup(beforeGroup.getId(), group)
			.clickEdit(beforeGroup.getId());

		//get groups parameters after editing
		ObjGroup afterGroup = app.getGroupHelper().getGroupParams().setId(beforeGroup.getId());
		
		//Check that test group was correctly edited
		assertThat(afterGroup, equalTo(beforeGroup.setHeader(group.getHeader())));
		
		if (app.getProperty("check.db").equals("true")) {
			assertThat(currentGroupsList, equalTo(inDataBase.listGroups()));
		}
	}
	
	@Test
	public void testNotValidGroupEdit () {
		//save model before testing
		SortedListOf<ObjGroup> beforeGroupsList = app.getModel().getGroupsCopy();
		
		//select group to edit
		ObjGroup beforeGroup = beforeGroupsList.getSome();
				
		//edit first group
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper().editGroup(beforeGroup.getId(), group);
		
		//Check that group was not incorrectly edited
		assertThat(currentGroupsList, equalTo(beforeGroupsList));
		assertThat(currentGroupsList, not(contains(group)));
		
		ComplicatedCheck();
	}
	
	@Test
	public void testSimilarGroupsEdit () {
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
		//edit selected groups
		ObjGroup group = new ObjGroup("Name", "Header", "Footer");
		app.getGroupHelper().editGroup(groups[0].getId(), group);
		app.getGroupHelper().editGroup(groups[1].getId(), group);
		
		//Check that test group was correctly edited
		assertThat(currentGroupsList, equalTo(
				beforeGroupsList.without(groups[0]).without(groups[1]).withAdded(group).withAdded(group)));
		
		ComplicatedCheck();
	}
}

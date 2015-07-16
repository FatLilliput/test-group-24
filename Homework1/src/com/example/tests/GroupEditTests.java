package com.example.tests;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import org.testng.annotations.Test;
import com.example.utilits.SortedListOf;

public class GroupEditTests extends GroupsTests {
	//TODO Tests are failing. Need to fix
	@Test(dataProvider = "randomValidDataGenerator")
	public void testFullGroupEdit (ObjGroup group) {
		
		//select group to edit
		int index = extendedGroupIndexGettind();
		
		//get list of groups before test
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//edit first group
		app.getGroupHelper()
			.clickEdit(index)
			.fillForm(group)
			.clickModify();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that test group was correctly edited
		assertThat(afterGroupsList, equalTo(beforeGroupsList.without(index-1).withAdded(group)));
	}
	
	@Test
	public void testPartGroupEdit () {
		//select group to edit
		int index = extendedGroupIndexGettind();
		app.getGroupHelper().clickEdit(index);
		
		//get groups parameters before editing
		ObjGroup beforeGroup = new ObjGroup();
		beforeGroup = app.getGroupHelper().getGroupParams(beforeGroup);
		
		//edit group
		ObjGroup group = new ObjGroup().withHeader("NewHeader11");
		app.getGroupHelper()
			.fillForm(group)
			.clickModify()
			.clickEdit(index);
		
		//get groups parameters after editing
		ObjGroup afterGroup = new ObjGroup();
		afterGroup = app.getGroupHelper().getGroupParams(afterGroup);
		
		//Check that test group was correctly edited
		assertThat(afterGroup, equalTo(beforeGroup.withHeader(group.getHeader())));
		
	}
	
	@Test
	public void testNotValidGroupEdit () {
		//select group to edit
		int index = extendedGroupIndexGettind();
		//get list of groups before test
		SortedListOf<ObjGroup> beforeGroupsList = app.getGroupHelper().getGroupList(); 
				
		//edit first group
		ObjGroup group = new ObjGroup("Name'", "Header'", "Footer.");
		app.getGroupHelper()
			.clickEdit(index)
			.fillForm(group)
			.clickModify();
		
		//get groups list after adding test group
		SortedListOf<ObjGroup> afterGroupsList = app.getGroupHelper().getGroupList();
				
		//Check that group was not incorrectly edited
		assertThat(afterGroupsList, equalTo(beforeGroupsList));
		assertThat(afterGroupsList, not(contains(group)));
	}
}

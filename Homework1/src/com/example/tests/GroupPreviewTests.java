package com.example.tests;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class GroupPreviewTests extends GroupsTests {
	
	
	@BeforeClass
	public void setUp() throws Exception {
		//main set up
		TestBase temp = new TestBase();
		temp.setUp();
		inDataBase.insertGroups(loadGroupsFromFile(GROUPS_FOR_SORTING));
		app.getNavigationHelper().openGroupsPage();
	}
	
	//this test fails cause there is a bug in application
	@Test
	public void testGroupSorting () {
		SortedListOf<ObjGroup> sortedGroups = inDataBase.listGroups();
		
		//get group list from the interface
		List<ObjGroup> interfaceGroups = app.getGroupHelper().getUnsortedGroupList();
		
		//Check that group are correctly sorted
		assertThat(interfaceGroups, equalTo(sortedGroups));
	}
	
	@Test
	public void testGroupPageContent () {
		SortedListOf<ObjGroup> sortedGroups = inDataBase.listGroups();
		
		//get group list from the interface
		List<ObjGroup> interfaceGroups = app.getGroupHelper().getUnsortedGroupList();
		
		//Check that group are correctly sorted
		assertThat(new SortedListOf<ObjGroup>(interfaceGroups), equalTo(sortedGroups));
	}
}

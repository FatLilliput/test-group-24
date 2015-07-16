package com.example.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.example.utilits.SortedListOf;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class GroupPreviewTests extends GroupsTests {
	//this test fails cause there is a bug in application
	@Test
	public void testGroupSorting () {
		
		ObjGroup group = new ObjGroup();
		group.withName("");
		app.getGroupHelper().addGroup(group);
		group.withName("1group");
		app.getGroupHelper().addGroup(group);
		group.withName("1_group");
		app.getGroupHelper().addGroup(group);
		group.withName("test123");
		app.getGroupHelper().addGroup(group);
		group.withName("test24");
		app.getGroupHelper().addGroup(group);
		group.withName("test_12");
		app.getGroupHelper().addGroup(group);
		group.withName("test_test");
		app.getGroupHelper().addGroup(group);
		group.withName("zztest");
		app.getGroupHelper().addGroup(group);
		group.withName("zzz_test");
		app.getGroupHelper().addGroup(group);
		
		//get sorted list of groups
		SortedListOf<ObjGroup> sortedGroups = app.getGroupHelper().getGroupList(); 
		//get group list from the interface
		List<ObjGroup> interfaceGroups = app.getGroupHelper().getUnsortedGroupList();
		
		//Check that group are correctly sorted
		assertThat(interfaceGroups, equalTo(sortedGroups));
	}
	//TODO for other tests correctly work deleting this groups in tearDown needed
}

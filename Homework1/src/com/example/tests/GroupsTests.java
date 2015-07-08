package com.example.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

public class GroupsTests extends TestBase{

	@DataProvider
	public Iterator<Object[]> randomValidDataGenerator() {
		List<Object[]> list = new ArrayList<Object[]>();
		
		for (int i = 0; i < 5; i++) {
			ObjGroup group = new ObjGroup();
			group.name = GetRandomParameter("test_group_name");
			group.header = GetRandomParameter("test_group_header");
			group.footer = GetRandomParameter("test_group_footer");
			list.add(i, new Object[]{group});
			
		}
		return list.iterator();
	}
	
	/*
	 * This method checks that any group exist and get random group index
	 * If there is no group in the list this method add it
	 */
	protected int extendedGroupIndexGettind() {
		app.getNavigationHelper().openMainPage();
		app.getNavigationHelper().clickGroupsList();
		int index = 0;
		if (!app.getGroupHelper().groupExist()) {
			ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
			app.getGroupHelper().addGroup(group);
			app.getNavigationHelper().clickGroupsList();
			app.getNavigationHelper().clickGroupsList();
		} else {
			index = app.getGroupHelper().choosePosition();
		}
		return index;
	}
}

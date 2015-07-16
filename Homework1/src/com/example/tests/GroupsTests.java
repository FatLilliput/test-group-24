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
			ObjGroup group = new ObjGroup()
				.withName  (GetRandomParameter("testGroupName"))
				.withHeader(GetRandomParameter("test_group_header"))
				.withFooter(GetRandomParameter("test_group_footer"))
			;
			list.add(i, new Object[]{group});
		}
		return list.iterator();
	}
	
	/*
	 * This method checks that any group exist and get random group index
	 * If there is no group in the list this method add it
	 */
	protected int extendedGroupIndexGettind() {
		int index = 0;
		if (!app.getGroupHelper().groupExist()) {
			ObjGroup group = new ObjGroup("Group1", "Header1", "Footer1");
			app.getGroupHelper().addGroup(group);
		} else {
			index = app.getGroupHelper().choosePosition();
		}
		return index;
	}

	protected void setUpDeleteSeveralGroups(int listSize) {
		if (listSize < 3) {
			ObjGroup group;
			for (int i = 0; i < 4; i++) {
				group = new ObjGroup("Group1ToDelede" + i, "Header1td" + i, "Footer1td" + i);
				app.getGroupHelper().addGroup(group);
			}
		}
	}
}

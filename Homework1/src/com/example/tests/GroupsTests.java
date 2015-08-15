package com.example.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.tests.GroupsDataGenerator.generatedRandomGroups;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

//import org.junit.BeforeClass;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;
public class GroupsTests extends TestBase{
	
	@DataProvider
	public Iterator<Object[]> randomValidDataGenerator() {
		return wrapGroupsForDataProvider(generatedRandomGroups(5)).iterator();
	}
	
	@DataProvider
	public Iterator<Object[]> validGroupsDataFromFile() throws IOException {
		return wrapGroupsForDataProvider(loadGroupsFromFile(GROUPS_XML_FILE)).iterator();
	}
	
	private List<Object[]> wrapGroupsForDataProvider(List<ObjGroup> groups) {
		List<Object[]> wrapedGroups = new ArrayList<Object[]>();
		for (ObjGroup group : groups) {
			wrapedGroups.add(new Object[]{group});
		}
		return wrapedGroups;
	}

	protected void ComplicatedCheck() {
		if (needToCheck()) {
			if (app.getProperty("check.db").equals("true")) {
				assertThat(currentGroupsList, equalTo(inDataBase.listGroups()));
			}
			if (app.getProperty("check.db").equals("true")) {
				assertThat(currentGroupsList, equalTo(new SortedListOf<ObjGroup>(app.getGroupHelper().getUnsortedGroupList())));
			}
		}
	}

}

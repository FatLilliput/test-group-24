package com.example.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.tests.GroupsDataGenerator.generatedRandomGroups;
import static com.example.tests.GroupsDataGenerator.loadGroupsFromFile;

import org.testng.annotations.DataProvider;

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

	//TODO : This method should be replaced by method in ApplicationManager class adding group direct in database
	//without interface implementation
	public static void addGroups(List<ObjGroup> groups) {
		for (ObjGroup group : groups) {
			app.insertGroup(group);
			app.getNavigationHelper().openGroupsPage();
		}
		
	}
}

/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.fw;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.example.utilits.SortedListOf;

public class GroupHelper extends WebDriverBaseHelper {
	//There are some constants of element, links and xpaths names
	//They are needed for fast refactoring in case of software changes
	
	private static String EDIT_GROUP_BUTTON = "edit";
	private static String NEW_GROUP_BUTTON = "new";
	private static String SUBMIT_GROUP_CREATION = "submit";
	private static String DELETE_GROUP = "delete";
	private static String SUBMIT_GROUP_MODIFICATION = "update";
	
//	private static String GROUP_LOCATOR = "selected[]";
	private static String GROUP_XPATH = "//input[@name='selected[]']";
	private static String GROUP_ID_XPATH = "//input[@name='selected[]'";
	private static String GROUP_NAME = "title";
	private static String GROUP_NAME_INPUT = "group_name";
	private static String GROUP_HEADER_INPUT = "group_header";
	private static String GROUP_FOOTER_INPUT = "group_footer";
	
	private static String GROUPS_PAGE = "group.php";
		
	public GroupHelper (ApplicationManager manager) {
		super(manager);
	}
	
	public GroupHelper addGroup (ObjGroup group) {
		if (!checkGroupsPage()) {
			manager.getNavigationHelper().openGroupsPage();
		}
		clickNewGroup();
		fillForm(group);
		clickSubmitGroup();
		manager.getModel().addGroup(group);
		return this;
	}
	
	public GroupHelper editGroup(int id, ObjGroup group) {
		clickEdit(id);
		fillForm(group);
		clickModify();
		manager.getModel().updateGroup(id, group);
		return this;
	}
		
	public GroupHelper deleteGroups(ObjGroup[] groups) {
		for (ObjGroup group : groups) {
			selectGroup(group.getId());
			manager.getModel().removeGroup(group);
		}
		clickDeleteGroup();
		return this;
	}

	public List<ObjGroup> getUnsortedGroupList() {
		List<ObjGroup> groupsList = new ArrayList<ObjGroup>();
		waitMe ((long)2);
		if (!(checkGroupsPage() && isElementPresent(By.name(NEW_GROUP_BUTTON)))) {
			manager.getNavigationHelper().openGroupsPage();
		}
		List<WebElement> groupCheckboxes = driver.findElements(By.xpath(GROUP_XPATH));
		for (WebElement checkbox : groupCheckboxes) {
			String title = checkbox.getAttribute(GROUP_NAME);
			String name = title.substring("Select (".length(), title.length() - ")".length());
			ObjGroup group = new ObjGroup().setName(name);
			groupsList.add(group);
		}
		waitMe ((long)0);
		return groupsList;
	}
	
	public ObjGroup getGroupParams() {
		ObjGroup group = new ObjGroup()
			.setName(getText(GROUP_NAME_INPUT))
			.setHeader(getText(GROUP_HEADER_INPUT))
			.setFooter(getText(GROUP_FOOTER_INPUT))
			;
		return group;
	}

	/*
	 * Fill add new group form
	 */
	private GroupHelper fillForm(ObjGroup group) {
		fillElement(GROUP_NAME_INPUT, group.getName());
		fillElement(GROUP_HEADER_INPUT, group.getHeader());
		fillElement(GROUP_FOOTER_INPUT, group.getFooter());
		return this;
	}

	public ObjGroup choosePosition(SortedListOf<ObjGroup> beforeGroupsList) {
		return beforeGroupsList.getSome();
	}
	
	public GroupHelper clickEdit(int id) {
		selectGroup(id);
		clickButton(EDIT_GROUP_BUTTON);
		return this;
	}
		
	private GroupHelper clickModify() {
		clickButton(SUBMIT_GROUP_MODIFICATION);
		manager.getNavigationHelper().clickGroupsList();
		return this;
	}
	
	private GroupHelper clickDeleteGroup() {
		clickButton(DELETE_GROUP);
		manager.getNavigationHelper().clickGroupsList();
		return this;
	}
		
	private void clickSubmitGroup() {
		clickButton(SUBMIT_GROUP_CREATION);
		manager.getNavigationHelper().clickGroupsList();
	}

	private void clickNewGroup() {
		clickButton(NEW_GROUP_BUTTON);
	}
	
	private GroupHelper selectGroup(int id) {
		if (!checkGroupsPage()) {
			manager.getNavigationHelper().openGroupsPage();
		}
		click(GROUP_ID_XPATH + " and @value='" + id + "']");
		return this;
	}
	
	private boolean checkGroupsPage() {
		waitMe ((long)2);
		boolean isGroupsList = isElementPresent(By.xpath(GROUP_XPATH));
		waitMe ((long)0);
		return (checkPage(GROUPS_PAGE) && isGroupsList);		
	}

}

/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.fw;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.example.tests.ObjGroup;
import com.example.utilits.SortedListOf;

public class GroupHelper extends BaseHelper {
	//There are some constants of element, links and xpaths names
	//They are needed for fast refactoring in case of software changes
	
	private static String EDIT_GROUP_BUTTON = "edit";
	private static String NEW_GROUP_BUTTON = "new";
	private static String SUBMIT_GROUP_CREATION = "submit";
	private static String DELETE_GROUP = "delete";
	private static String SUBMIT_GROUP_MODIFICATION = "update";
	
	private static String GROUP_LOCATOR = "selected[]";
	private static String GROUP_XPATH = "//input[@name='selected[]']";
	private static String GROUP_NAME = "title";
	private static String GROUP_NAME_INPUT = "group_name";
	private static String GROUP_HEADER_INPUT = "group_header";
	private static String GROUP_FOOTER_INPUT = "group_footer";
	
	private static String GROUPS_PAGE = "group.php";
	
	private SortedListOf<ObjGroup> cachedGroupsList;
	
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
		rebuildCachedGroupsList();
		return this;
	}
	
	public SortedListOf<ObjGroup> getGroupList() {
		if (!(checkGroupsPage() && isElementPresent(By.name(NEW_GROUP_BUTTON)))) {
			manager.getNavigationHelper().openGroupsPage();
		}
		if (cachedGroupsList == null) {
			rebuildCachedGroupsList();
		}
		return cachedGroupsList;
	}
	
	private void rebuildCachedGroupsList() {
		cachedGroupsList = new SortedListOf<ObjGroup>();
//		List<ObjGroup> cachedGroupsList = new ArrayList<ObjGroup>();
		List<WebElement> groupCheckboxes = driver.findElements(By.xpath(GROUP_XPATH));
		for (WebElement checkbox : groupCheckboxes) {
			String title = checkbox.getAttribute(GROUP_NAME);
			String name = title.substring("Select (".length(), title.length() - ")".length());
			ObjGroup group = new ObjGroup().withName(name);
			cachedGroupsList.add(group);
		}
	}

	public ObjGroup getGroupParams(ObjGroup group) {
		group
			.withName(getText(GROUP_NAME_INPUT))
			.withHeader(getText(GROUP_HEADER_INPUT))
			.withFooter(getText(GROUP_FOOTER_INPUT))
			;
		return group;
	}

	/*
	 * Fill add new group form
	 */
	public GroupHelper fillForm(ObjGroup group) {
		fillElement(GROUP_NAME_INPUT, group.getName());
		fillElement(GROUP_HEADER_INPUT, group.getHeader());
		fillElement(GROUP_FOOTER_INPUT, group.getFooter());
		return this;
	}

	public Integer choosePosition() {
		int count = manager.driver.findElements(By.xpath(GROUP_XPATH)).size();
		
		if (count == 1) {
			return 0;
		} else {
			Random rnd = new Random();
			return rnd.nextInt(count-1);
		}
	}
	
	public int getIdGroup(int index) {
		WebElement group =  (WebElement) manager.driver.findElement(By.xpath(GROUP_XPATH + "[" + (index + 1) + "]"));
		String id = group.getAttribute("value");
		return Integer.parseInt(id);
	}

	  public GroupHelper selectGroup(int id) {
			String path;
			if (id == 0) {
				path = GROUP_XPATH + "[1]";
			} else {
				path = GROUP_XPATH.substring(0, (GROUP_XPATH.length() - 1)) + " and @value='" + id + "']";
			}	
			click(path);
			return this;
	  }
		
	  public boolean groupExist() {
			manager.getNavigationHelper().openGroupsPage();
			waitMe((long)0);
			boolean result = isElementPresent(By.name(GROUP_LOCATOR));
			waitMe((long)10);
			return result;
	  }
	  
	  public GroupHelper clickEdit(int id) {
			selectGroup(id);
			clickButton(EDIT_GROUP_BUTTON);
			return this;
		}
		
		public GroupHelper clickModify() {
			clickButton(SUBMIT_GROUP_MODIFICATION);
			cachedGroupsList = null;
			manager.getNavigationHelper().clickGroupsList();
			rebuildCachedGroupsList();
			return this;
		}
		
		public GroupHelper deleteGroup() {
			clickButton(DELETE_GROUP);
			cachedGroupsList = null;
			manager.getNavigationHelper().clickGroupsList();
			rebuildCachedGroupsList();
			return this;
		}
		
	  private void clickSubmitGroup() {
		  clickButton(SUBMIT_GROUP_CREATION);
		  cachedGroupsList = null;
		  manager.getNavigationHelper().clickGroupsList();
		  rebuildCachedGroupsList();
	  }

	  private void clickNewGroup() {
		  clickButton(NEW_GROUP_BUTTON);
	  }
	  
		
	  private boolean checkGroupsPage() {
		  return checkPage(manager.baseUrl + GROUPS_PAGE);		
	  }


}

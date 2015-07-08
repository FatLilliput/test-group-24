/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.fw;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.example.tests.ObjGroup;

public class GroupHelper extends BaseHelper {
	
	public GroupHelper (ApplicationManager manager) {
		super(manager);
	}
	public void addGroup (ObjGroup group) {
		clickNewGroup();
		fillForm(group);
		clickSubmitGroup();
	}
	/*
	 * Fill add new group form
	 */
	  public void fillForm(ObjGroup group) {
		  fillElement("group_name", group.name);
		  fillElement("group_header", group.header);
		  fillElement("group_footer", group.footer);
	  }

	  public void clickSubmitGroup() {
		  clickButton("submit");
	  }

	  public void clickNewGroup() {
//		  waitMe((long) 0);
		  clickButton("new");
	  }
	  
	public void selectGroup(int id) {
		String path;
		if (id == 0) {
			path = "//input[@name='selected[]'][1]";
		} else {
			path = "//input[@name='selected[]' and @value='" + id + "']";
		}	
		click(path);
	}
	
	public void deleteGroup() {
		clickButton("delete");
	}
	
	public boolean groupExist() {
		  waitMe((long)0);
		  boolean result = isElementPresent(By.name("selected[]"));
		  waitMe((long)10);
		  return result;
	}
	public void clickEdit(int id) {
		selectGroup(id);
		clickButton("edit");
	}
	
	public void clickModify() {
		clickButton("update");
		
	}
	public List<ObjGroup> getGroupList() {
		List<ObjGroup> groups = new ArrayList<ObjGroup>();
		List<WebElement> groupCheckboxes = driver.findElements(By.xpath("//input[@name='selected[]']"));
		for (WebElement checkbox : groupCheckboxes) {
			ObjGroup group = new ObjGroup();
			String title = checkbox.getAttribute("title");
			group.name = title.substring("Select (".length(), title.length() - ")".length());
			groups.add(group);
		}
		return groups;
	}
	public int getIdGroup(int index) {
		WebElement group =  (WebElement) manager.driver.findElement(By.xpath("//input[@type='checkbox'][" + (index + 1) + "]"));
		String id = group.getAttribute("value");
		return Integer.parseInt(id);
	}
	public Integer choosePosition() {
		int count = manager.driver.findElements(By.xpath("//input[@type='checkbox']")).size();
		Random rnd = new Random();
		return rnd.nextInt(count-1);
	}
	public ObjGroup getGroupParams(ObjGroup group) {
		group.name = getText("group_name");
		group.header = getText("group_header");
		group.footer = getText("group_footer");
		return group;
	}


}

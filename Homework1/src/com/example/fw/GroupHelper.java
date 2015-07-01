/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.fw;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	  
	public void selectGroup(Integer id) {
		String path;
		if (id == null) {
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
	public void clickEdit(Integer id) {
		selectGroup(id);
		clickButton("edit");
	}
	
	public void clickModify() {
		clickButton("update");
		
	}

}

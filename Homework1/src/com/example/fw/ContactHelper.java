/*
 * This class contains methods needed for contacts testing. 
 * Right now there are only contacts creation tests are implemented. 
 * All the other contact test classes should extend ContactHelper
 */
package com.example.fw;

import org.openqa.selenium.By;
import com.example.tests.ObjContact;

public class ContactHelper extends BaseHelper {

	public ContactHelper(ApplicationManager manager) {
		super(manager);
	}
	
	public void clickAddNew() {
	    clickLinkText("add new");
	}

	public void clickSubmitContact() {
		clickButton("submit");
	}

	public void fillForm(ObjContact contact) {
		fillElement  ("firstname", contact.firstName);
		fillElement  ("lastname",  contact.lastName);
		fillElement  ("address",   contact.address);
		fillElement  ("home",      contact.home);
		fillElement  ("mobile",    contact.mobile);
		fillElement  ("work",      contact.work);
		fillElement  ("email",     contact.email1);
		fillElement  ("email2",    contact.email2);
		selectElement("bday",    contact.birthDay);
		selectElement("bmonth",  contact.birthMonth);
		fillElement  ("byear",     contact.birthYear);
		fillElement  ("address2",  contact.address2);
		fillElement  ("phone2",    contact.phone2);	
	}

	public void selectContact(Integer id) {
		String path;
		if (id == null) {
			path = "//[@name='entry'][1]/td/input";
		} else {
			path = "//[@name='entry']//input[@value='" + id + "']";
		}
		click(path);
	}

	public void clickEditContact(Integer id) {
		String path;
		if (id == null) {
			path = "//[@name='entry'][1]//img[@alt='Edit']";
		} else {
			path = "//[@href='edit.php?id=" + id + "']/img";
		}
		click(path);
	}

	public void clickDeleteContact() {
		click("//input[@value='Delete']");
		
	}
	public void clickUpdateContact() {
		click(".//input[@value='Update']");
		
	}

	public boolean contactExist() {
		  waitMe((long)0);
		  boolean result = isElementPresent(By.name("entry"));
		  waitMe((long)10);
		  return result;
	}

	public void clickViewContact(Integer id) {
		String path;
		if (id == null) {
			path = "//[@name='entry'][1]//img[@alt='Details']";
		} else {
			path = "//a[@href='view.php?id=" + id + "']/img";
		}
		click(path);
	}

	public void addContactToGroup(Integer id, String group) {
		selectContact(id);
		selectElement("to_group", group);
		clickButton("Add to");
	}
}

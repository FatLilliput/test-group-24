/*
 * This class contains methods needed for contacts testing. 
 * Right now there are only contacts creation tests are implemented. 
 * All the other contact test classes should extend ContactHelper
 */
package com.example.fw;
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

}

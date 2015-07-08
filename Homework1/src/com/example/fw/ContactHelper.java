/*
 * This class contains methods needed for contacts testing. 
 * Right now there are only contacts creation tests are implemented. 
 * All the other contact test classes should extend ContactHelper
 */
package com.example.fw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

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
		selectElement("bday",      contact.birthDay);
		selectElement("bmonth",    contact.birthMonth);
		selectElement("new_group", contact.group);
		fillElement  ("byear",     contact.birthYear);
		fillElement  ("address2",  contact.address2);
		fillElement  ("phone2",    contact.phone2);	
	}

	public void selectContact(Integer id) {
		String path;
		if (id == null) {
			path = "//tr[@name='entry'][1]//input[@id]";
		} else {
			path = "//input[@id='id" + id + "']";
		}
		click(path);
	}

	public void clickEditContact(Integer id) {
		String path;
		if (id == null) {
			path = "//tr[@name='entry'][1]//img[@alt='Edit']";
		} else {
			path = "//a[@href='edit.php?id=" + id + "']/img";
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
			path = "//tr[@name='entry'][1]//img[@alt='Details']";
		} else {
			path = "//a[@href='view.php?id=" + id + "']/img";
		}
		click(path);
	}

	public void addContactToGroup(int id, String group) {
		selectContact(id);
		selectElement("to_group", group);
		clickButton("add");
	}

	public void addContact(ObjContact contact) {
		clickAddNew();
		fillForm(contact);
		clickSubmitContact();
	}

	public void selectGroup(String group) {
		selectElement("group", group);
	}

	public void searchContact(String contact) {
		fillElement("searchstring", contact);
		driver.findElement(By.name("searchstring")).sendKeys(Keys.RETURN);
	}

	public void clickHomePage(Integer id) {
		String path;
		if (id == null) {
			path = "//tr[@name='entry']//img[@src='icons/house.png']";
		} else {
			path = "//a[@href='view.php?id=" + id + "']/parent::*/parent::*//img[@src='icons/house.png']";
		}
		click(path);
	}

	//TODO: There is a bug in addressbook. In address table first and last names are changed places
	public List<ObjContact> getContactsList() {
			List<ObjContact> contacts = new ArrayList<ObjContact>();
			List<WebElement> contactRows = driver.findElements(By.xpath("//tr[@name='entry']"));
			for (WebElement contactRow : contactRows) {
				ObjContact contact = new ObjContact();
				contact.firstName = contactRow.findElement(By.xpath("./td[3]")).getText();
				contact.lastName = contactRow.findElement(By.xpath("./td[2]")).getText();
				contact.email1 = contactRow.findElement(By.xpath("./td[4]")).getText();
				contact.home = contactRow.findElement(By.xpath("./td[5]")).getText();
				contacts.add(contact);
			}
			return contacts;
	}

	public int getIdContact(int index) {
		WebElement contact =  (WebElement) manager.driver.findElement(By.xpath("//tr[@name='entry'][" + (index + 1) + "]/td[7]/a"));
		String id = contact.getAttribute("href").substring("http://localhost/addressbookv4.1.4/edit.php?id=".length()); 
		return Integer.parseInt(id);
	}
	
	public int choosePosition() {
		int count = manager.driver.findElements(By.xpath("//img[@alt='Edit']")).size();
		if (count == 1) {
			return 0;
		} else {
			Random rnd = new Random();
			return rnd.nextInt(count-1);
		}
	}

	public ObjContact getContactParams() {
		ObjContact contact = new ObjContact();
		contact.firstName  = getText("firstname");
		contact.lastName   = getText("lastname");
		contact.address    = getTextArea("address");
		contact.home       = getText("home");
		contact.mobile     = getText("mobile");
		contact.work       = getText("work");
		contact.email1     = getText("email");
		contact.email2     = getText("email2");
		contact.birthDay   = getText("bday");
		contact.birthMonth = getText("bmonth");
		contact.birthYear  = getText("byear");
		contact.group      = null;
		contact.address2   = getTextArea("address2");
		contact.phone2     = getText("phone2");
		return contact;
	}

	public boolean isContactPresent(ObjContact contact) {
		waitMe ((long)0);
		boolean name = isElementPresent(By.xpath("//*[contains(text(),'" + contact.firstName + " " + contact.lastName +"')]"));
		boolean address = isElementPresent(By.xpath("//*[text()='" + contact.address + "']"));
		if (contact.address.equals("")) {
			address = !address;
		}
		boolean home;
		if (contact.home.equals("")) {
			home = !isElementPresent(By.xpath("//*[text()='H: ']"));
		} else {
			home = isElementPresent(By.xpath("//*[text()='H: " + contact.home + "']"));
		}
		boolean mobile;
		if(contact.mobile.equals("")) {
			mobile = !isElementPresent(By.xpath("//*[text()='M: ']"));
		} else {
			mobile = isElementPresent(By.xpath("//*[text()='M: " + contact.mobile + "']"));
		}
		boolean work;
		if(contact.work.equals("")) {
			work = !isElementPresent(By.xpath("//*[text()='W: ']"));
		} else {
			work = isElementPresent(By.xpath("//*[text()='W: " + contact.work + "']"));
		}
		boolean email= isElementPresent(By.xpath("//*[contains(text(),'" + contact.email1 +"')]"));
		if (contact.email1.equals("")) {
			email = !email;
		} 
		boolean email2 = isElementPresent(By.xpath("//*[contains(text(),'" + contact.email2 +"')]"));
		if (contact.email2.equals("")) {
			email2 = !email2;
		} 
		boolean birth;
		//building birth date according to contact's data
		String birthdate;
		String bday;
		String bmonth;
		String byear;
		bday = contact.birthDay.replace("-", "");
		bmonth = contact.birthMonth.replace("-", "");
		byear = contact.birthYear.replace("-", "");

		if (bday.isEmpty()) {
			if (bmonth.isEmpty() && byear.isEmpty()) {
				birthdate = "";
			} else {
				birthdate = bmonth + " " + byear;
			}

		} else {
			birthdate = bday + ". " + bmonth + " " + byear;
		}
		
		if (birthdate.isEmpty()) {
			birth = !isElementPresent(By.xpath("//*[text()='Birthday: ']"));
		} else {
			birth  = isElementPresent(By.xpath("//*[text()='Birthday: " + birthdate + "']"));
		}
			
		boolean address2 = isElementPresent(By.xpath("//*[text()='" + contact.address2 + "']"));
		
		if (contact.address2.equals("")) {
			address2 = !isElementPresent(By.xpath("//*[text()='" + contact.address2 + "']"));
		} else {
			address2= isElementPresent(By.xpath("//*[text()='" + contact.address2 + "']"));
		}
		boolean phone2;
		if (contact.phone2.equals("")) {
			phone2 = !isElementPresent(By.xpath("//*[text()='P: ']"));
		} else {
			phone2 = isElementPresent(By.xpath("//*[text()='P: " + contact.phone2 + "']"));
		}
		boolean result = name && address && home && mobile && work && email && email2 && birth && address2 && phone2;
		waitMe((long)10);
		return result;
		
	}

	public String getGroup() {
		return driver.findElement(By.xpath("//*[@id='content']/i/a")).getText();
	}

	public boolean isPhonePresent(ObjContact contact) {
		// TODO Auto-generated method stub
		return false;
	}
}

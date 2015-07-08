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
		String Contactname = convertContactsNameToFullName(contact);
		boolean name = isElementPresent(By.xpath("//*[contains(text(),'" + Contactname +"')]"));
		boolean address = isTagedElementPresent(contact.address);
		boolean home = isUnTagedElementFound("//*[text()='H: ", contact.home);
		boolean mobile = isUnTagedElementFound("//*[text()='M: ", contact.mobile);
		boolean work =  isUnTagedElementFound("//*[text()='W: ", contact.work);
		boolean email = isTagedElementPresent(contact.email1);
		boolean email2 = isTagedElementPresent(contact.email2);
		String birthdate = convertBirthdayToDate(contact);
		boolean birth =  isUnTagedElementFound("//*[text()='Birthday: ", birthdate);	
		boolean address2 = isTagedElementPresent(contact.address2);
		boolean phone2 = isUnTagedElementFound("//*[text()='P: ", contact.phone2);

		boolean result = name && address && home && mobile && work && email && email2 && birth && address2 && phone2;
		waitMe((long)10);
		return result;
		
	}

	private String convertContactsNameToFullName(ObjContact contact) {
		String Contactsname = "";
		if (contact.firstName.equals("")){
			if (!contact.firstName.equals("")) {
				Contactsname = contact.lastName;
			}
		} else {
			Contactsname = contact.firstName;
			if (!contact.firstName.equals("")) {
				Contactsname = Contactsname + " " + contact.lastName;
			}
		}
		return Contactsname;
	}

	private boolean isTagedElementPresent(String element) {
		boolean isPresent = isElementPresent(By.xpath("//*[text()='" + element + "']"));
		if (element.equals("")) {
			isPresent = !isPresent;
		}
		return isPresent;
	}

	public List<String> getGroup() {
		List<String> groups  = new ArrayList<String>() ;
		List<WebElement> elements = driver.findElements(By.xpath("//*[@id='content']/i/a"));
		for (WebElement element : elements) {
			groups.add(element.getText());
		}
		return groups;
	}

	public boolean isPhonePresent(ObjContact contact) {
		waitMe ((long)5);
		String square =  "//*[text() = '" + contact.firstName + " " + contact.lastName + "']";
		boolean name = isElementPresent(By.xpath(square));
		boolean home = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='H: ", contact.home);
		boolean mobile = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='M: ", contact.mobile);
		boolean work = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='W: ", contact.work);
		String birthdate = convertBirthdayToDate(contact);
		boolean birth = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='Birthday: ", birthdate);
		boolean phone2 = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='P: ", contact.phone2);
		boolean result = name  && home && mobile && work && birth &&  phone2;
		waitMe((long)10);
		return result;
	}

	private String convertBirthdayToDate(ObjContact contact) {
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
		return birthdate;
	}
	
	private boolean isUnTagedElementFound(String path, String parameter) {
		if (parameter.equals("")) {
			return !isElementPresent(By.xpath(path + "']"));
		} else {
			return isElementPresent(By.xpath(path + parameter + "']"));
		}
	}
	
	/*
	 * Right now this test works only if there is a Surname
	 * TODO Extend it
	 */
	public boolean isBirthdayPresent(ObjContact contact) {
		//find contact's xpath
		String path;
		if (contact.lastName.equals("")) {
			if (contact.firstName.equals("")) {
				if (contact.email1.equals("")) {
					if (contact.email2.equals("")) {
						if (contact.home.equals("")) {
							if (contact.mobile.equals("")) {
								if (contact.work.equals("")) {
									return true;
								} else {
									path = "//*[text()='" + contact.work + "']";
								}
							} else {
								path = "//*[text()='" + contact.mobile + "']";
							}
						} else {
							path = "//*[text()='" + contact.home + "']";
						}
					} else {
						path = "//*[text()='" + contact.email2 + "']";
					}
				} else {
					path = "//*[text()='" + contact.email1 + "']";
				}
			} else {
				path = "//*[text()='" + contact.firstName + "']";
			}
		} else {
			path = "//*[text()='" + contact.lastName + "']";
		}
		
		//check correctly displayed
		String dayPresent = manager.driver.findElement(By.xpath(path + "/parent::*/td[1]")).getText();
		if (contact.birthDay.equals("")){
			if (!dayPresent.equals(".")) {
				return false;
			}
		} else {
			if (!(dayPresent.equals(contact.birthDay + "."))) {
				return false;
			}
		}
		String ln = manager.driver.findElement(By.xpath(path + "/parent::*/td[2]")).getText();
		if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[2]")).getText().equals(contact.lastName)) {
			return false;
		}
		
		if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[3]")).getText().equals(contact.firstName)) {
			String fn = manager.driver.findElement(By.xpath(path + "/parent::*/td[3]")).getText();
			return false;
		}
		
		if (contact.email1.equals("")) {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.email2)) {
				return false;
			}
		} else {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.email1)) {
				return false;
			}
		}
		if (contact.home.equals("")) {
			if (contact.mobile.equals("")) {
				if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.work)) {
					return false;
				}
			} else {
				if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.mobile)) {
					return false;
				}
			}
		} else {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.home.replace(" ", ""))) {
				return false;
			}
		}
		
		return true;
	}
}

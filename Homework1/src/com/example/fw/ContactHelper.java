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

import com.example.utilits.SortedListOf;

public class ContactHelper extends WebDriverBaseHelper {
	
	//There are some constants of element, links and xpaths names
	//They are needed for fast refactoring in case of software changes
	private static String CONTACT_FIRSTNAME_INPUT = "firstname";
	private static String CONTACT_LASTNAME_INPUT = "lastname";
	private static String CONTACT_ADDRESS_INPUT = "address";
	private static String CONTACT_HOME_INPUT = "home";
	private static String CONTACT_MOBILE_INPUT = "mobile";
	private static String CONTACT_WORK_INPUT = "work";
	private static String CONTACT_EMAIL1_INPUT = "email";
	private static String CONTACT_EMAIL2_INPUT = "email2";
	private static String CONTACT_BIRTHDAY_DAY_INPUT = "bday";
	private static String CONTACT_BIRTHDAY_MONTH_INPUT = "bmonth";
	private static String CONTACT_BIRTHDAY_YEAR_INPUT = "byear";
	private static String CONTACT_GROUP_INPUT = "new_group";
	private static String CONTACT_ADDRESS2_INPUT = "address2";
	private static String CONTACT_PHONE2_INPUT = "phone2";
	
	private static String CONTACTS_ADD_LINK = "add new";
	private static String CONTACTS_SUBMIT_CREATION  = "submit";
	private static String CONTACTS_GROUP_SELECT = "to_group";
	private static String CONTACTS_GROUP_BUTTON = "add";
	private static String CONTACTS_BY_GROUP_SORTING = "group";
	private static String CONTACTS_SEARCHING_INPUT = "searchstring";
	private static String PHONES_PRINT_PAGE = "view.php?all&print&phones";
	
	
	private static String CONTACTS_XPATH = "//tr[@name='entry']";
	private static String CONTACT_DELETE_ICON = "//input[@value='Delete']";
	private static String CONTACT_UPDATE_ICON = ".//input[@value='Update']";
	private static String CONTACT_EDIT_ICON = "//img[@alt='Edit']";
	private static String CONTACT_DETAILS_ICON = "//img[@alt='Details']";
	private static String CONTACTS_ON_PRINT_PAGE = "*//tbody/tr/td/br/..";
	
	public ContactHelper(ApplicationManager manager) {
		super(manager);
	}
	
	//TODO: There is a bug in addressbook. In address table first and last names are changed places
	
	public List<ObjContact> getUnsortedContactsList() {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		List<ObjContact> contacts = new ArrayList<ObjContact>();
		waitMe ((long)2);
		List<WebElement> contactRows = driver.findElements(By.xpath(CONTACTS_XPATH));
		for (WebElement contactRow : contactRows) {
			ObjContact contact = new ObjContact()
				.setFirstName(contactRow.findElement(By.xpath("./td[3]")).getText())
				.setLastName(contactRow.findElement(By.xpath("./td[2]")).getText())
				.setEmail1(contactRow.findElement(By.xpath("./td[4]")).getText())
				.setHome(contactRow.findElement(By.xpath("./td[5]")).getText())
			;
			contacts.add(contact);
		}
		waitMe ((long)10);
		return contacts;
	}

	public ContactHelper fillForm(ObjContact contact) {
		fillElement  (CONTACT_FIRSTNAME_INPUT, 		contact.getFirstName());
		fillElement  (CONTACT_LASTNAME_INPUT,  		contact.getLastName());
		fillElement  (CONTACT_ADDRESS_INPUT,   		contact.getAddress());
		fillElement  (CONTACT_HOME_INPUT,      		contact.getHome());
		fillElement  (CONTACT_MOBILE_INPUT,    		contact.getMobile());
		fillElement  (CONTACT_WORK_INPUT,      		contact.getWork());
		fillElement  (CONTACT_EMAIL1_INPUT,     	contact.getEmail1());
		fillElement  (CONTACT_EMAIL2_INPUT,    		contact.getEmail2());
		selectElement(CONTACT_BIRTHDAY_DAY_INPUT,   contact.getBirthDay());
		selectElement(CONTACT_BIRTHDAY_MONTH_INPUT, contact.getBirthMonth());
		selectElement(CONTACT_GROUP_INPUT, 			contact.getGroup());
		fillElement  (CONTACT_BIRTHDAY_YEAR_INPUT,  contact.getBirthYear());
		fillElement  (CONTACT_ADDRESS2_INPUT,  		contact.getAddress2());
		fillElement  (CONTACT_PHONE2_INPUT,    		contact.getPhone2());
		return this;
	}
	
	public ContactHelper addContactToGroup(int id, String group) {
		selectContact(id);
		selectElement(CONTACTS_GROUP_SELECT, group);
		clickButton(CONTACTS_GROUP_BUTTON);
		manager.getNavigationHelper().clickMainPage();
		return this;
	}

	public ContactHelper addContact(ObjContact contact) {
		clickAddNew();
		fillForm(contact);
		clickSubmitContact();
		manager.getModel().addContact(contact);
		return this;
	}

	public ContactHelper selectGroup(String group) {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		selectElement(CONTACTS_BY_GROUP_SORTING, group);
		return this;
	}

	public ContactHelper searchContact(String contact) {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		fillElement(CONTACTS_SEARCHING_INPUT, contact);
		driver.findElement(By.name(CONTACTS_SEARCHING_INPUT)).sendKeys(Keys.RETURN);
		return this;
	}

//	public ContactHelper clickHomePage(Integer id) {
//		String path;
//		if (id == null) {
//			path = CONTACTS_XPATH + "//img[@src='icons/house.png']";
//		} else {
//			path = "//a[@href='view.php?id=" + id + "']/parent::*/parent::*//img[@src='icons/house.png']";
//		}
//		click(path);
//		return this;
//	}

	public int getIdContact(int index) {
		WebElement contact =  (WebElement)driver.findElement(By.xpath(CONTACTS_XPATH + "[" + (index+1) + "]/td[7]/a"));
		String id = contact.getAttribute("href").substring("http://localhost/addressbookv4.1.4/edit.php?id=".length()); 
		return Integer.parseInt(id);
	}
	
	public int choosePosition() {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		int count = driver.findElements(By.xpath(CONTACT_EDIT_ICON)).size();
		if (count == 1) {
			return 0;
		} else {
			Random rnd = new Random();
			return rnd.nextInt(count-1);
		}
	}

	public List<String> getGroup() {
		List<String> groups  = new ArrayList <String>();
		List<WebElement> elements = driver.findElements(By.xpath("//*[@id='content']/i/a"));
		for (WebElement element : elements) {
			groups.add(element.getText());
		}
		return groups;
	}
	
	public ObjContact getContactParams() {
		ObjContact contact = new ObjContact()
			.setFirstName  (getText(CONTACT_FIRSTNAME_INPUT))
			.setLastName   (getText(CONTACT_LASTNAME_INPUT))
			.setAddress    (getTextArea(CONTACT_ADDRESS_INPUT))
			.setHome       (getText(CONTACT_HOME_INPUT))
			.setMobile     (getText(CONTACT_MOBILE_INPUT))
			.setWork       (getText(CONTACT_WORK_INPUT))
			.setEmail1     (getText(CONTACT_EMAIL1_INPUT))
			.setEmail2     (getText(CONTACT_EMAIL2_INPUT))
			.setBirthDay   (getText(CONTACT_BIRTHDAY_DAY_INPUT))
			.setBirthMonth (getText(CONTACT_BIRTHDAY_MONTH_INPUT))
			.setBirthYear  (getText(CONTACT_BIRTHDAY_YEAR_INPUT))
			.setAddress2   (getTextArea(CONTACT_ADDRESS2_INPUT))
			.setPhone2     (getText(CONTACT_PHONE2_INPUT))
		;
		return contact;
	}

	//TODO: Tests are failing from Print all pages for address wrote with <br>
	public boolean isContactPresent(ObjContact contact) {
		waitMe ((long)0); 
		String Contactname = convertContactsNameToFullName(contact);

		if (!isElementPresent(By.xpath("//*[contains(text(),'" + Contactname +"')]"))) {
			System.out.println("Name " + Contactname + " false");
			return false;
		}
/*
		if (!isTagedElementPresent(contact.getAddress())) {
			System.out.println("Address " + contact.getAddress() + " false");
			return false;
		}
*/
		if (!isUnTagedElementFound("//*[text()='H: ", contact.getHome())) {
			System.out.println("Home " + contact.getHome() + " false.");
			return false;
		}
		if (!isUnTagedElementFound("//*[text()='M: ", contact.getMobile())) {
			System.out.println("Mobile " + contact.getMobile() + " false.");
			return false;
		}
		if (!isUnTagedElementFound("//*[text()='W: ", contact.getWork())) {
			System.out.println("Work " + contact.getWork() + " false.");
			return false;
		}
		if (!isTagedElementPresent(contact.getEmail1())) {
			System.out.println("Email1 " + contact.getEmail1() + " false.");
			return false;
		}
		if (!isTagedElementPresent(contact.getEmail2())) {
			System.out.println("Email2 " + contact.getEmail2() + " false.");
			return false;
		}
		String birthdate = convertBirthdayToDate(contact);
		if (!isUnTagedElementFound("//*[text()='Birthday: ", birthdate)) {
			System.out.println("birthdate " + birthdate + " false");
			return false;
		}
/*		
		if (!isTagedElementPresent(contact.getAddress2())) {
			System.out.println("Address2 " + contact.getAddress2() + " false");
			return false;
		}
*/
		if (!isUnTagedElementFound("//*[text()='P: ", contact.getPhone2())) {
			System.out.println("Phone2 " + contact.getPhone2() + " false.");
			return false;
		}
		waitMe((long)10);
		return true;
		
	}

	public boolean isPhonePresent(ObjContact contact) {
		if (!checkPage(PHONES_PRINT_PAGE)) {
			manager.getNavigationHelper().openPrintPhones();
		}
		waitMe ((long)5);
		String square =  "//*[text() = '" + contact.getFirstName() + " " + contact.getLastName() + "']";
		if (!isElementPresent(By.xpath(square))) {
			System.out.println("Name " + square + "is not present");
			return false;
		}
		if (!isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='H: ", contact.getHome())) {
			System.out.println("Home " + contact.getHome() + "is not present");
			return false;
		}
		if (!isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='M: ", contact.getMobile())) {
			System.out.println("Mobile " + contact.getMobile() + "is not present");
			return false;
		} 
		if (!isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='W: ", contact.getWork())) {
			System.out.println("Work " + contact.getWork() + "is not present");
			return false;
		}
		String birthdate = convertBirthdayToDate(contact);
		if (!isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='Birthday: ", birthdate)) {
			System.out.println("Birthday " + birthdate + "is not present");
			return false;
		}
		if (!isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='P: ", contact.getPhone2())) {
			System.out.println("Phone2 " + contact.getPhone2() + "is not present");
			return false;
		}
		waitMe((long)10);
		return true;
	}

	public boolean isBirthdayPresent(ObjContact contact) {
		manager.getNavigationHelper().clickBirthList();
		
		//find contact's xpath
		String path = "//*[text()='" + contact.getWork() + "']";
		if (!contact.getMobile().equals("")) {
			path = "//*[text()='" + contact.getMobile() + "']";
		}
		if (!contact.getHome().equals("")) {
			path = "//*[text()='" + contact.getHome() + "']";
		}
		if (!contact.getEmail2().equals("")) {
			path = "//*[text()='" + contact.getEmail2() + "']";
		}
		if (!contact.getEmail1().equals("")) {
			path = "//*[text()='" + contact.getEmail1() + "']";
		}
		if (!contact.getFirstName().equals("")) {
			path = "//*[text()='" + contact.getFirstName() + "']";
		}
		if (!contact.getLastName().equals("")) {
			path = "//*[text()='" + contact.getLastName() + "']";
		}
		if (path.equals("")) {
			return true;
		}
		
		//check correctly displayed
		String dayPresent = driver.findElement(By.xpath(path + "/parent::*/td[1]")).getText();
		if (contact.getBirthDay().equals("")){
			if (!dayPresent.equals(".")) {
				System.out.println("Date " + dayPresent + " is incorrect");
				return false;
			}
		} else {
			if (!(dayPresent.equals(contact.getBirthDay() + "."))) {
				System.out.println("Date " + dayPresent + " is incorrect");
				return false;
			}
		}
		if (!driver.findElement(By.xpath(path + "/parent::*/td[2]")).getText().equals(contact.getLastName())) {
			System.out.println("Last Name " + contact.getLastName() + " is not present");
			return false;
		}
		
		if (!driver.findElement(By.xpath(path + "/parent::*/td[3]")).getText().equals(contact.getFirstName())) {
			System.out.println("First Name " + contact.getFirstName() + " is not present");
			return false;
		}
		
		if (contact.getEmail1().equals("")) {
			if (!driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.getEmail2())) {
				System.out.println("Email2 " + contact.getEmail2() + " is not present");
				return false;
			}
		} else {
			if (!driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.getEmail1())) {
				System.out.println("Email1 " + contact.getEmail1() + " is not present");
				return false;
			}
		}
		if (contact.getHome().equals("")) {
			if (contact.getMobile().equals("")) {
				if (!driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getWork())) {
					System.out.println("Work " + contact.getWork() + " is not present");
					return false;
				}
			} else {
				if (!driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getMobile())) {
					System.out.println("Mobile " + contact.getMobile() + " is not present");
					return false;
				}
			}
		} else {
			if (!driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getHome().replace(" ", ""))) {
				System.out.println("Home " + contact.getHome() + " is not present");
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isTagedElementPresent(String element) {
		boolean isPresent = isElementPresent(By.xpath("//*[text()='" + element + "']"));
		if (element.equals("")) {
			isPresent = !isPresent;
		}
		return isPresent;
	}

	private boolean isUnTagedElementFound(String path, String parameter) {
		if (parameter == null || parameter.equals("")) {
			waitMe ((long)0);
			boolean result = !isElementPresent(By.xpath(path + "']"));
			waitMe ((long)10);
			return result;
		} else {
			return isElementPresent(By.xpath(path + parameter + "']"));
		}
	}
	
	private String convertContactsNameToFullName(ObjContact contact) {
		String Contactsname = "";
		if (contact.getFirstName().equals("")){
			if (!contact.getFirstName().equals("")) {
				Contactsname = contact.getLastName();
			}
		} else {
			Contactsname = contact.getFirstName();
			if (!contact.getFirstName().equals("")) {
				Contactsname = Contactsname + " " + contact.getLastName();
			}
		}
		return Contactsname;
	}

	private String convertBirthdayToDate(ObjContact contact) {
		String birthdate;
		String bday;
		String bmonth;
		String byear;
		if (contact.getBirthDay() == null) {
			bday = "";
		} else {
			bday   = contact.getBirthDay()  .replace("-", "");
		}
		if (contact.getBirthMonth() == null) {
			bmonth = "";
		} else {
			bmonth = contact.getBirthMonth().replace("-", "");
		}
		if (contact.getBirthYear() == null) {
			byear = "";
		} else {
			byear  = contact.getBirthYear() .replace("-", "");
		}
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
	
	public ContactHelper clickEditContact(Integer id) {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		String path;
		if (id == null) {
			path = CONTACTS_XPATH + "[1]" + CONTACT_EDIT_ICON;
		} else {
			path = "//a[@href='edit.php?id=" + id + "']/img";
		}
		click(path);
		return this;
	}

	private ContactHelper clickDeleteContact() {
		click(CONTACT_DELETE_ICON);
		manager.getNavigationHelper().clickMainPage();
		return this;
		
	}
	public ContactHelper clickUpdateContact() {
		click(CONTACT_UPDATE_ICON);
		manager.getNavigationHelper().clickMainPage();
		return this;
	}

	public ContactHelper clickViewContact(Integer id) {
		if (!driver.getCurrentUrl().contains("/view.php?id=")) {
			manager.getNavigationHelper().clickMainPage();
		}
		String path;
		if (id == null) {
			path = CONTACTS_XPATH + "[1]" + CONTACT_DETAILS_ICON;
		} else {
			path = "//a[@href='view.php?id=" + id + "']/img";
		}
		click(path);
		return this;
	}
	
	public SortedListOf<ObjContact> formatContactsListForMainPage(SortedListOf<ObjContact> contacts) {
		SortedListOf<ObjContact> formattedContacts = new SortedListOf<ObjContact>();
		for (ObjContact contact : contacts) {
			formattedContacts.add(formatContactForMainPage(contact));
		}
		return formattedContacts;
	}
	
	public ObjContact formatContactForMainPage(ObjContact contact) {
		if (contact.getEmail1().equals("")) {
			contact.setEmail1(contact.getEmail2());
		}
		if (contact.getHome().equals("")) {
			if (contact.getMobile().equals("")) {
				contact.setHome(contact.getWork());
			} else {
				contact.setHome(contact.getMobile());
			}
		}
		return contact;
	}

	private ContactHelper clickAddNew() {
	    clickLinkText(CONTACTS_ADD_LINK);
	    return this;
	}

	private ContactHelper clickSubmitContact() {
		clickButton(CONTACTS_SUBMIT_CREATION);
		manager.getNavigationHelper().clickMainPage();
		return this;
	}
	
	private ContactHelper selectContact(Integer id) {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		String path;
		if (id == null) {
			path = CONTACTS_XPATH + "[1]//input[@id]";
		} else {
			path = "//input[@id='id" + id + "']";
		}
		click(path);
		return this;
	}
	
	private boolean checkHomePage() {
		return isElementPresent(By.xpath(".//*[@id='MassCB']"));
	}

	public int getContactsCount() {
		return driver.findElements(By.xpath(CONTACTS_ON_PRINT_PAGE)).size();
	}

	public ContactHelper deleteContact(ObjContact contact) {
		clickEditContact(contact.getId());
		clickDeleteContact();
		manager.getModel().removeContact(contact);
		return null;
	}

	public ContactHelper editContact(int id, ObjContact contact) {
		clickEditContact(id);
		fillForm(contact);
		clickUpdateContact();
		manager.getModel().updateContact(id, contact);
		return null;
	}

}

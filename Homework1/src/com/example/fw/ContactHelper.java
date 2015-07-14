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
//import com.example.tests.ObjGroup;
import com.example.utilits.SortedListOf;

public class ContactHelper extends BaseHelper {
	
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
	
	private static String CONTACTS_XPATH = "//tr[@name='entry']";
	private static String CONTACT_DELETE_ICON = "//input[@value='Delete']";
	private static String CONTACT_UPDATE_ICON = ".//input[@value='Update']";
	private static String CONTACT_EDIT_ICON = "//img[@alt='Edit']";
	private static String CONTACT_DETAILS_ICON = "//img[@alt='Details']";
	
	private SortedListOf<ObjContact> cachedContactsList;
	
	public ContactHelper(ApplicationManager manager) {
		super(manager);
	}
	
	//TODO: There is a bug in addressbook. In address table first and last names are changed places
	
	public SortedListOf<ObjContact> getContactsList() {
		if (!checkHomePage()) {
			manager.getNavigationHelper().openMainPage();
		}
		if (cachedContactsList == null) {
			rebuildCachedContactsList();
		}
		return cachedContactsList;
	}
	private void rebuildCachedContactsList() {
		cachedContactsList = new SortedListOf<ObjContact>();
		List<WebElement> contactRows = driver.findElements(By.xpath(CONTACTS_XPATH));
		for (WebElement contactRow : contactRows) {
			ObjContact contact = new ObjContact()
				.setFirstName(contactRow.findElement(By.xpath("./td[3]")).getText())
				.setLastName(contactRow.findElement(By.xpath("./td[2]")).getText())
				.setEmail1(contactRow.findElement(By.xpath("./td[4]")).getText())
				.setHome(contactRow.findElement(By.xpath("./td[5]")).getText())
			;
			cachedContactsList.add(contact);
		}
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
		cachedContactsList = null;
		manager.getNavigationHelper().clickMainPage();
		rebuildCachedContactsList();
		return this;
	}

	public ContactHelper addContact(ObjContact contact) {
		clickAddNew();
		fillForm(contact);
		clickSubmitContact();
		return this;
	}

	public ContactHelper selectGroup(String group) {
		selectElement(CONTACTS_BY_GROUP_SORTING, group);
		cachedContactsList = null;
		rebuildCachedContactsList();
		return this;
	}

	public ContactHelper searchContact(String contact) {
		fillElement(CONTACTS_SEARCHING_INPUT, contact);
		driver.findElement(By.name(CONTACTS_SEARCHING_INPUT)).sendKeys(Keys.RETURN);
		cachedContactsList = null;
		rebuildCachedContactsList();
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
		WebElement contact =  (WebElement) manager.driver.findElement(By.xpath(CONTACTS_XPATH + "[" + (index + 1) + "]/td[7]/a"));
		String id = contact.getAttribute("href").substring("http://localhost/addressbookv4.1.4/edit.php?id=".length()); 
		return Integer.parseInt(id);
	}
	
	public int choosePosition() {
		int count = manager.driver.findElements(By.xpath(CONTACT_EDIT_ICON)).size();
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
			.setGroup      (null)
			.setAddress2   (getTextArea(CONTACT_ADDRESS2_INPUT))
			.setPhone2     (getText(CONTACT_PHONE2_INPUT))
		;
		return contact;
	}

	public boolean isContactPresent(ObjContact contact) {
		manager.getNavigationHelper().clicPrintAll();
		waitMe ((long)0); 
		String Contactname = convertContactsNameToFullName(contact);
		boolean name = isElementPresent(By.xpath("//*[contains(text(),'" + Contactname +"')]"));
		boolean address = isTagedElementPresent(contact.getAddress());
		boolean home = isUnTagedElementFound("//*[text()='H: ", contact.getHome());
		boolean mobile = isUnTagedElementFound("//*[text()='M: ", contact.getMobile());
		boolean work =  isUnTagedElementFound("//*[text()='W: ", contact.getWork());
		boolean email = isTagedElementPresent(contact.getEmail1());
		boolean email2 = isTagedElementPresent(contact.getEmail2());
		String birthdate = convertBirthdayToDate(contact);
		boolean birth =  isUnTagedElementFound("//*[text()='Birthday: ", birthdate);	
		boolean address2 = isTagedElementPresent(contact.getAddress2());
		boolean phone2 = isUnTagedElementFound("//*[text()='P: ", contact.getPhone2());

		boolean result = name && address && home && mobile && work && email && email2 && birth && address2 && phone2;
		waitMe((long)10);
		return result;
		
	}

	public boolean isPhonePresent(ObjContact contact) {
		manager.getNavigationHelper().openPrintPhones();
		waitMe ((long)5);
		String square =  "//*[text() = '" + contact.getFirstName() + " " + contact.getLastName() + "']";
		boolean name = isElementPresent(By.xpath(square));
		boolean home = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='H: ", contact.getHome());
		boolean mobile = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='M: ", contact.getMobile());
		boolean work = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='W: ", contact.getWork());
		String birthdate = convertBirthdayToDate(contact);
		boolean birth = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='Birthday: ", birthdate);
		boolean phone2 = isUnTagedElementFound(square + "/parent::*/parent::*/*[text()='P: ", contact.getPhone2());
		boolean result = name  && home && mobile && work && birth &&  phone2;
		waitMe((long)10);
		return result;
	}

	public boolean isBirthdayPresent(ObjContact contact) {
		manager.getNavigationHelper().clickBirthList();
		//find contact's xpath
		String path;
		if (contact.getLastName().equals("")) {
			if (contact.getFirstName().equals("")) {
				if (contact.getEmail1().equals("")) {
					if (contact.getEmail2().equals("")) {
						if (contact.getHome().equals("")) {
							if (contact.getMobile().equals("")) {
								if (contact.getWork().equals("")) {
									return true;
								} else {
									path = "//*[text()='" + contact.getWork() + "']";
								}
							} else {
								path = "//*[text()='" + contact.getMobile() + "']";
							}
						} else {
							path = "//*[text()='" + contact.getHome() + "']";
						}
					} else {
						path = "//*[text()='" + contact.getEmail2() + "']";
					}
				} else {
					path = "//*[text()='" + contact.getEmail1() + "']";
				}
			} else {
				path = "//*[text()='" + contact.getFirstName() + "']";
			}
		} else {
			path = "//*[text()='" + contact.getLastName() + "']";
		}
		
		//check correctly displayed
		String dayPresent = manager.driver.findElement(By.xpath(path + "/parent::*/td[1]")).getText();
		if (contact.getBirthDay().equals("")){
			if (!dayPresent.equals(".")) {
				return false;
			}
		} else {
			if (!(dayPresent.equals(contact.getBirthDay() + "."))) {
				return false;
			}
		}
		if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[2]")).getText().equals(contact.getLastName())) {
			return false;
		}
		
		if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[3]")).getText().equals(contact.getFirstName())) {
			return false;
		}
		
		if (contact.getEmail1().equals("")) {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.getEmail2())) {
				return false;
			}
		} else {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[4]")).getText().equals(contact.getEmail1())) {
				return false;
			}
		}
		if (contact.getHome().equals("")) {
			if (contact.getMobile().equals("")) {
				if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getWork())) {
					return false;
				}
			} else {
				if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getMobile())) {
					return false;
				}
			}
		} else {
			if (!manager.driver.findElement(By.xpath(path + "/parent::*/td[5]")).getText().equals(contact.getHome().replace(" ", ""))) {
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
		if (parameter.equals("")) {
			return !isElementPresent(By.xpath(path + "']"));
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
		bday   = contact.getBirthDay()  .replace("-", "");
		bmonth = contact.getBirthMonth().replace("-", "");
		byear  = contact.getBirthYear() .replace("-", "");

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
	
	public boolean contactExist() {
		  waitMe((long)0);
		  boolean result = isElementPresent(By.name("entry"));
		  waitMe((long)10);
		  return result;
	}
	
	public ContactHelper clickEditContact(Integer id) {
		String path;
		if (id == null) {
			path = CONTACTS_XPATH + "[1]" + CONTACT_EDIT_ICON;
		} else {
			path = "//a[@href='edit.php?id=" + id + "']/img";
		}
		click(path);
		return this;
	}

	public ContactHelper clickDeleteContact() {
		click(CONTACT_DELETE_ICON);
		cachedContactsList = null;
		manager.getNavigationHelper().clickMainPage();
		rebuildCachedContactsList();
		return this;
		
	}
	public ContactHelper clickUpdateContact() {
		click(CONTACT_UPDATE_ICON);
		cachedContactsList = null;
		manager.getNavigationHelper().clickMainPage();
		rebuildCachedContactsList();
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

	private ContactHelper clickAddNew() {
	    clickLinkText(CONTACTS_ADD_LINK);
	    return this;
	}

	private ContactHelper clickSubmitContact() {
		clickButton(CONTACTS_SUBMIT_CREATION);
		cachedContactsList = null;
		manager.getNavigationHelper().clickMainPage();
		rebuildCachedContactsList();
		return this;
	}
	
	private ContactHelper selectContact(Integer id) {
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
		checkPage("");
		return false;
	}

}

package com.example.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.testng.annotations.DataProvider;

public class ContactsTests extends TestBase{

	@DataProvider
	public Iterator<Object[]> randomValidContactDataGenerator() {
		List<Object[]> list = new ArrayList<Object[]>();
		
		for (int i = 0; i < 5; i++) {
			ObjContact contact = new ObjContact()
				.setFirstName (GetRandomParameter("Name"))
				.setLastName  (GetRandomParameter("Surname"))
				.setAddress   (GetRandomParameter("Address "))
				.setHome      (GetRandomParameter(""))
				.setMobile	  (GetRandomParameter(""))
				.setWork	  (GetRandomParameter(""))
				.setEmail1	  (GetRandomParameter("email") + "@ya.ru")
				.setEmail2	  (GetRandomParameter("email") + "@ya.ru")
				.setBirthDay  (GetRandomDate("D"))
				.setBirthMonth(GetRandomDate("M"))
				.setBirthYear (GetRandomDate("Y"))
				.setAddress2  (GetRandomParameter("Address "))
				.setPhone2    (GetRandomParameter(""))
			;
			
//			contact.firstName = GetRandomParameter("Name");
//			contact.lastName  = GetRandomParameter("Surname");
//			contact.address   = GetRandomParameter("Address ");
//			contact.home      = GetRandomParameter("");
//			contact.mobile    = GetRandomParameter("");
//			contact.work      = GetRandomParameter("");
//			contact.email1    = GetRandomParameter("email") + "@ya.ru";
//			contact.email2    = GetRandomParameter("email") + "@ya.ru";
//			contact.birthDay  = GetRandomDate("D");
//			contact.birthMonth= GetRandomDate("M");
//			contact.birthYear = GetRandomDate("Y");
//			contact.address2  = GetRandomParameter("Address ");
//			contact.phone2    = GetRandomParameter("");
			list.add(i, new Object[]{contact});
			
		}
	return list.iterator();
	}

	private String GetRandomDate(String parameter) {
		Random rnd = new Random();
		switch (parameter) {
			case "D": 
				String day = String.valueOf(rnd.nextInt(30));
				if (day.equals("0")) {
					return "-"; 
				} else {
					return day;
				}
			case "Y": 
				String year = String.valueOf(rnd.nextInt(100)); 
				if (year.equals("0")) {
					return "-";
				} else {
					return String.valueOf(1900 + Integer.parseInt(year));
				}	
			case "M": 
				int month = rnd.nextInt(11);
				switch (month) {
					case 0:  return "-";
			    	case 1:  return "January";
			    	case 2:  return "February";
			    	case 3:  return "March";
			    	case 4:  return "April";
			    	case 5:  return "May";
			    	case 6:  return "June";
			    	case 7:  return "July";
			    	case 8:  return "August";
			    	case 9:  return "September";
			    	case 10: return "October";
			    	case 11: return "November";
			    	case 12: return "December";
			}
		} 
		return null;
	}
	protected int smartContactChoosing() {
		int index = 0;
		app.getNavigationHelper().openMainPage();
		if (!app.getContactHelper().contactExist()) {
			ObjContact contact = new ObjContact();
			contact.defaultContact(contact);
			app.getContactHelper().addContact(contact);		
			app.getNavigationHelper().clickMainPage();
		} else {
			index = app.getContactHelper().choosePosition();
		}
		return index;
	}

}

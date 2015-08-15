package com.example.tests;

import static com.example.tests.TestBase.GetRandomParameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.fw.ObjContact;
import com.thoughtworks.xstream.XStream;

public class ContactsDataGenerator {

	public static void main(String[] params) throws IOException {
		if (params.length < 2) {
			System.out.println("Incorrect input parameters: <test contacts amount>, <file> expected");
			return;
		}	
		File file = new File(params[1]);
		if (file.exists()) {
			System.out.println("File " + file + " exists. Remove it manually if You want to get new contacts data");
			return;
		}
		List<ObjContact> contacts = generatedRandomContacts(Integer.parseInt(params[0]));
		String fileType = params[1].substring(params[1].lastIndexOf(".") + 1);
		if (fileType.toLowerCase().equals("csv") || fileType.toLowerCase().equals("txt")) {
			saveContactsToCSVFile(contacts, file);
		} else if (fileType.toLowerCase().equals("xml")) {
			saveContactsToXMLFile(contacts, file);
		} else {
			System.out.println("Incorrect file format. Got "  + fileType + " . CSV, TXT or XML expected");
			return;
		}
	}

	private static void saveContactsToCSVFile(List<ObjContact> contacts, File file) throws IOException{
		FileWriter writer = new FileWriter(file);
		for (ObjContact contact : contacts) {
			writer.write(
				contact.getFirstName() + "," + 
				contact.getLastName() + "," + 
				contact.getAddress() + "," + 
				contact.getHome() + "," + 
				contact.getMobile() + "," + 
				contact.getWork() + "," + 
				contact.getEmail1() + "," + 
				contact.getEmail2() + "," + 
				contact.getBirthDay() + "," + 
				contact.getBirthMonth() + "," + 
				contact.getBirthYear() + "," + 
				contact.getAddress2() + "," + 
				contact.getPhone2() + ",!\n"
			);
		}
		writer.close();
	}

	private static void saveContactsToXMLFile(List<ObjContact> contacts, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		XStream xstream = new XStream();
		xstream.alias("contact", ObjContact.class);
		String xml = xstream.toXML(contacts);
		writer.write(xml);
		writer.close();
	}

	public static List<ObjContact> loadContactsFromFile(String filePath) throws IOException {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
		File file = new File (filePath);
		if (!file.exists()) {
			System.out.println("File " + file + "not found.");
			return null;
		}
		if (fileType.toLowerCase().equals("csv") || fileType.toLowerCase().equals("txt")) {
			return loadContactsFromCSVFile(file);
		} else if (fileType.toLowerCase().equals("xml")) {
			return loadContactsFromXMLFile(file);
		} else {
			System.out.println("Incorrect file format. Got "  + fileType + " . CSV, TXT or XML expected");
			return null;
		}
			
	}
		
	private static List<ObjContact> loadContactsFromCSVFile(File file) throws IOException {
		List<ObjContact> list = new ArrayList<ObjContact>();
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		String contactLine = buffer.readLine();
		while (contactLine != null) {
			String[] contactFields = contactLine.split(",");
			ObjContact contact = new ObjContact()
				.setFirstName (contactFields[0])
				.setLastName  (contactFields[1])
				.setAddress   (contactFields[2])
				.setHome      (contactFields[3])
				.setMobile	  (contactFields[4])
				.setWork	  (contactFields[5])
				.setEmail1	  (contactFields[6])
				.setEmail2	  (contactFields[7])
				.setBirthDay  (contactFields[8])
				.setBirthMonth(contactFields[9])
				.setBirthYear (contactFields[10])
				.setAddress2  (contactFields[11])
				.setPhone2    (contactFields[12]);
			list.add(contact);
			contactLine = buffer.readLine();
		}
			
		buffer.close();
		return list;
	}
		
	private static List<ObjContact> loadContactsFromXMLFile(File file) throws IOException {
		XStream xstream = new XStream();
		xstream.alias("contact", ObjContact.class);
		return (List<ObjContact>) xstream.fromXML(file);
	}
		
	public static List<ObjContact> generatedRandomContacts(int count) {
		List<ObjContact> list = new ArrayList<ObjContact>();
		for (int i = 0; i < count; i++) {
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
			list.add(contact);
		}
		return list;
	}
		
	private static String GetRandomDate(String parameter) {
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

}

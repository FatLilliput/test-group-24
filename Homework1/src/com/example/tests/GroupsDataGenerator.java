package com.example.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.fw.ObjGroup;
import com.thoughtworks.xstream.XStream;

import static com.example.tests.TestBase.GetRandomParameter;

public class GroupsDataGenerator {

	public static void main(String[] params) throws IOException {
		if (params.length < 2) {
			System.out.println("Incorrect input parameters: <test groups amount>, <file path>");
			return;
		}
		
		File file = new File(params[1]);
		if (file.exists()) {
			System.out.println("File " + file + "exists. Remove it manually if You want to get new groups data");
			return;
		}
		
		List<ObjGroup> groups = generatedRandomGroups(Integer.parseInt(params[0]));
		String fileType = params[1].toLowerCase().substring(params[1].lastIndexOf(".") + 1);
		if (fileType.equals("csv") || fileType.equals("txt")) {
			saveGroupsToCSVFile(groups, file);
		} else if (fileType.equals("xml")) {
			saveGroupsToXMLFile(groups, file);
		} else {
			System.out.println("Incorrect file format. Got "  + fileType + " . TXT, CSV or XML expected");
			return;
		}
		
	}

	private static void saveGroupsToCSVFile(List<ObjGroup> groups, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		for (ObjGroup group : groups) {
			writer.write(group.getName() + "," + group.getHeader() + "," + group.getFooter() + ",!\n");
			writer.close();
		}
	}
	
	private static void saveGroupsToXMLFile(List<ObjGroup> groups, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		XStream xstream = new XStream();
		xstream.alias("group", ObjGroup.class);
		String xml = xstream.toXML(groups);
		writer.write(xml);
		writer.close();
	}
	
	public static List<ObjGroup> loadGroupsFromFile(String filePath) throws IOException {
		String fileType = filePath.toLowerCase().substring(filePath.lastIndexOf(".") + 1);
		File file = new File (filePath);
		if (!file.exists()) {
			System.out.println("File " + file + "not found.");
			return null;
		}
		if (fileType.equals("csv") || fileType.equals("txt")) {
			return loadGroupsFromCSVFile(file);
		} else if (fileType.equals("xml")) {
			return loadGroupsFromXMLFile(file);
		} else {
			System.out.println("Incorrect file format. Got "  + fileType + " . TXT, CSV or XML expected");
			return null;
		}
	}
	
	private static List<ObjGroup> loadGroupsFromXMLFile(File file) {
		XStream xstream = new XStream();
		xstream.alias("group", ObjGroup.class);
		return (List<ObjGroup>) xstream.fromXML(file);
		
	}

	private static List<ObjGroup> loadGroupsFromCSVFile(File file) throws IOException {
		List<ObjGroup> list = new ArrayList<ObjGroup>();
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		String groupLine = buffer.readLine();
		while (groupLine != null) {
			String[] groupFields = groupLine.split(",");
			ObjGroup group = new ObjGroup()
				.setName(groupFields[0])
				.setHeader(groupFields[1])
				.setFooter(groupFields[2]);
			list.add(group);
			groupLine = buffer.readLine();
		}
		buffer.close();
		return list;
	}
	
	public static List<ObjGroup> generatedRandomGroups(int count) {
		List<ObjGroup> list = new ArrayList<ObjGroup>();
		
		for (int i = 0; i < count; i++) {
			ObjGroup group = new ObjGroup()
				.setName  (GetRandomParameter("testGroupName"))
				.setHeader(GetRandomParameter("test_group_header"))
				.setFooter(GetRandomParameter("test_group_footer"))
			;
			list.add(group);
		}
		return list;
	}

}

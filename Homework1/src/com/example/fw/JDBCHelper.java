package com.example.fw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.example.fw.ObjGroup;
import com.example.fw.ObjContact;
import com.example.utilits.SortedListOf;

public class JDBCHelper extends BaseHelper {

	private Connection dbConnection;
	
	public JDBCHelper(ApplicationManager manager) {
		super(manager);
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	    
	    //try to connect to DataBase
		try {
	        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
//	public void dbConnect() {
//		try {
//		    Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println(e.getMessage());
//		}
//	    
//	    //try to connect to DataBase
//		try {
//	        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","");
//	    } catch (SQLException e) {
//	        System.out.println(e.getMessage());
//	    }
//	}
	
	public void dbClose() {
	//try to close connection
		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dbClear() {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute("truncate `group_list`;");
			statement.execute("truncate `addressbook`;");
			statement.execute("truncate `address_in_groups`;");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	public void insertGroups (List<ObjGroup> groups) {
		Statement statement = null;
		String insertGroupsQuery = "INSERT INTO group_list (group_name, group_header, group_footer) VALUES";
		for (ObjGroup group : groups) {
			if (group != groups.get(0)) {
				insertGroupsQuery = insertGroupsQuery + ",";
			}
			insertGroupsQuery = insertGroupsQuery + " ('" + group.getName() + "', '" + group.getHeader() + "', '" + group.getFooter() + "')";
		}
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(insertGroupsQuery);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertContact(List<ObjContact> contacts) {
		Statement statement = null;
		String insertContactsQuery = "INSERT INTO `addressbook` (firstname, lastname, address, home, mobile, work, "
				+ "email, email2, bday, bmonth, byear, address2, phone2) VALUES";
		for (ObjContact contact : contacts) {
			if (contact != contacts.get(0)) {
				insertContactsQuery = insertContactsQuery + ",";
			}
			insertContactsQuery = insertContactsQuery + " ('" + contact.getFirstName() + "', '" + contact.getLastName() + "', '" 
					+ contact.getAddress() + "', '" + contact.getHome() + "', '" + contact.getMobile() + "', '" 
					+ contact.getWork() + "', '" + contact.getEmail1() + "', '" + contact.getEmail2() + "', '" 
					+ contact.getBirthDay() + "', '" + contact.getBirthMonth() + "', '" 
					+ contact.getBirthYear() + "', '" + contact.getAddress2() + "', '" + contact.getPhone2() + "')";
		}
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(insertContactsQuery);				    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveContactToGroup(int idContact, int idGroup) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
				"INSERT INTO `address_in_groups` (id, group_id) VALUES ('" + idContact + "', '" + idGroup + "');"
			);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public SortedListOf<ObjGroup> listGroups() {
		SortedListOf<ObjGroup> groups = new SortedListOf<ObjGroup>();
		Statement st = null;
		ResultSet res = null;
		try {
			st = dbConnection.createStatement();
			res = st.executeQuery("SELECT group_id,group_name,group_header,group_footer FROM group_list");
			while (res.next()) {
				ObjGroup gr = new ObjGroup().setId(Integer.parseInt(res.getString(1))).setName(res.getString(2)).setHeader(res.getString(3)).setFooter(res.getString(4));
				groups.add(gr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return groups;
	}
	
	public SortedListOf<ObjContact> listContacts() {
		SortedListOf<ObjContact> contacts = new SortedListOf<ObjContact>();
		Statement st = null;
		ResultSet res = null;
		try {
			st = dbConnection.createStatement();
			res = st.executeQuery("SELECT id, firstname, lastname, address, home, mobile, work, email, email2, "
					+ "bday, bmonth, byear, address2, phone2 FROM addressbook");
			while (res.next()) {
				ObjContact contact = new ObjContact().setId(Integer.parseInt(res.getString(1)))
					.setFirstName (res.getString(2))
					.setLastName  (res.getString(3))
					.setAddress   (res.getString(4))
					.setHome      (res.getString(5))
					.setMobile    (res.getString(6))
					.setWork      (res.getString(7))
					.setEmail1    (res.getString(8))
					.setEmail2    (res.getString(9))
					.setBirthDay  (res.getString(10))
					.setBirthMonth(res.getString(11))
					.setBirthYear (res.getString(12))
					.setAddress2  (res.getString(13))
					.setPhone2    (res.getString(14));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return contacts;
	}

}

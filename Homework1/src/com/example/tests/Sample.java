package com.example.tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample {

	public static void main(String[] args) throws SQLException {
		//check jdbc driver existence
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		    return;
		}
		
		Connection dbConnection = null;
	    Statement statement = null;
	    
	    //try to connect to DataBase
		try {
	        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
		
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
					"INSERT INTO `group_list` (group_name, group_header, group_footer) VALUES ('name1', 'header1', 'footer1');"
			);
			
			ResultSet res = statement.executeQuery("SELECT MAX(group_id) FROM `group_list`");
			while (res.next()) {
				 String userid = res.getString("MAX(group_id)"); 
				 System.out.println("userid : " + userid);
			 }
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//try to close connection
		try {
	        dbConnection.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
}

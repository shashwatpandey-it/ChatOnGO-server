package com.shashwat.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTextArea;

public class DatabaseConnector {
	
	private static DatabaseConnector instance;
	private Connection connection;
	
	public static DatabaseConnector getDatabaseConnector() {
		if(instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}
	
	private DatabaseConnector() {

	}
	
	public void connectToDatabase() throws SQLException {
		
		String server = "localhost";
		String database = "chatongo_db";
		String port = "3306";
		String user = "Shashwat";
		String password = "shashwat2001";
			
		connection = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+database, user, password);
					
	}
	
	public Connection getConnection() {
		return connection;
	}

}

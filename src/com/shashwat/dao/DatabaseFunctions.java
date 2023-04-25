package com.shashwat.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shashwat.models.LoginStatusModel;
import com.shashwat.models.RegistryModel;
import com.shashwat.models.RegistryStatusModel;
import com.shashwat.models.UserAccountModel;

public class DatabaseFunctions {
	
	private Connection con;
	private static DatabaseFunctions instance;
	
	//------SQL queries------
	private final String INSERT = "insert into user(name, password) values(?,?);";
	private final String CHECK = "select name from user where name = ?";
	private final String VERIFY = "select * from user where name = ? and password = ?";
	private final String INSERT_USER_ACCOUNT = "insert into user_account(userId, name, status) values(?, ?, false)";
	private final String SELECT_USER_ACCOUNT = "select * from user_account where userId <> ? and status = '1'";
	private final String UPDATE_STATUS_LOGIN = "update user_account set status = '1' where userId = ?";
	private final String UPDATE_STATUS_LOGOUT = "update user_account set status = '0' where userId = ?";
	
	public static DatabaseFunctions getDatabaseFunctions() {
		if (instance == null) {
			instance = new DatabaseFunctions();
		}
		return instance;
	}
	
	private DatabaseFunctions() {
		this.con = DatabaseConnector.getDatabaseConnector().getConnection();
	}
	
	public RegistryStatusModel checkAndInsert(RegistryModel data) {
		try {
			
			PreparedStatement pc = con.prepareStatement(CHECK);
			pc.setString(1, data.getUserName());
			
			ResultSet resultset = pc.executeQuery();
			if(resultset.next()) {
				return new RegistryStatusModel("Username already taken");
			}
			else {
				resultset.close();
				pc.close();
				
				//------register the user details------ 
				PreparedStatement pi = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
				pi.setString(1, data.getUserName());
				pi.setString(2, data.getPassword());
				
				pi.execute();
				resultset = pi.getGeneratedKeys();
				resultset.first();
				int userID = resultset.getInt(1);
				resultset.close();
				pi.close();
				
				//------create user account------
				con.setAutoCommit(false);   		//by default 'true' , since we are doing transaction management so we need control in our hands
				
				PreparedStatement pca = con.prepareStatement(INSERT_USER_ACCOUNT);
				pca.setInt(1, userID);
				pca.setString(2, data.getUserName());
				
				pca.execute();
				pca.close();
				
				con.commit();
				con.setAutoCommit(true);
			}
			
		} catch (Exception e) {
			try {
				if(!con.getAutoCommit()) {			//to handle exception occurred in creating user account
					con.rollback();       			//to restore to the last successful commit performed
					con.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			return new RegistryStatusModel("Server Error");
		}
		return new RegistryStatusModel("");
	}
	
	public boolean logout(Integer userId) {
		try {
			PreparedStatement plo = con.prepareStatement(UPDATE_STATUS_LOGOUT);
			plo.setInt(1, userId);
			plo.execute();
			plo.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public LoginStatusModel validateLogin(RegistryModel data) {
		try {
			PreparedStatement p = con.prepareStatement(VERIFY);
			p.setString(1, data.getUserName());
			p.setString(2, data.getPassword());
			ResultSet resultSet = p.executeQuery();
			
			if (resultSet.next()) {
				int userId = resultSet.getInt("userId");
				String userName = resultSet.getString("name");
				p.close();
				PreparedStatement pu = con.prepareStatement(UPDATE_STATUS_LOGIN);
				pu.setInt(1, userId);
				pu.execute();
				pu.close();

				return new LoginStatusModel(true, "OK", userId, userName);
			}
			
			resultSet.close();
			p.close();
			
		} catch (Exception e) {
			
			return new LoginStatusModel(false, "Server error", 0, null);
		}
		return new LoginStatusModel(false, "Invalid", 0, null);
	}
	
	public List<UserAccountModel> getUsers(Integer except){
		List<UserAccountModel> users = new ArrayList<>();
		
		try {
			PreparedStatement p = con.prepareStatement(SELECT_USER_ACCOUNT);
			p.setInt(1, except);
			ResultSet resultSet = p.executeQuery();
			
			while(resultSet.next()) {
				int userId = resultSet.getInt(1);
				String name = resultSet.getString(2);
				users.add(new UserAccountModel(userId, name, true));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

}

/* 													------NOTE------
 * addBatch method, can be used with a single PreparedStatement to execute the same insert multiple times with different parameters,
 * or be used on a Statement object to add more queries to the batch, but without the ability to add parameters 
*/
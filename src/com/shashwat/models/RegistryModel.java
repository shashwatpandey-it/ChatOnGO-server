package com.shashwat.models;


public class RegistryModel {
	
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	// default constructor mandatory
	public RegistryModel() {
		
	}
	
	public RegistryModel(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
		
}

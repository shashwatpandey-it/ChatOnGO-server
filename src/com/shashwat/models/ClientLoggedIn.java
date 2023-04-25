package com.shashwat.models;
import com.corundumstudio.socketio.SocketIOClient;

public class ClientLoggedIn {
	
	private UserAccountModel userAccount;
	private SocketIOClient client;
	
	public UserAccountModel getUserAccount() {
		return userAccount;
	}
	
	public SocketIOClient getClient() {
		return client;
	}
	
	public void setUserAccount(UserAccountModel userAccount) {
		this.userAccount = userAccount;
	}
	
	public void setClient(SocketIOClient client) {
		this.client = client;
	}
	
	public ClientLoggedIn() {
		
	}
	
	public ClientLoggedIn(UserAccountModel userAccount, SocketIOClient client) {
		this.userAccount = userAccount;
		this.client = client;
	}

}

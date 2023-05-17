package com.shashwat.main;
import com.shashwat.dao.DatabaseConnector;
import com.shashwat.service.Service;

public class ServerMain {
	
	public static void main(String[] args) {

		try {
			DatabaseConnector.getDatabaseConnector().connectToDatabase();
			System.out.println(">> Connection to database successful . . .");

			Service.getService().startService();
			
		} catch (Exception e) {
			System.out.println(">> Error: "+e+" . . .");
			System.exit(1);
		}
		
	}
}


/*                ------Netty------
	Netty is a client server framework based on NIO, which can be used to develop network applications quickly and easily.
	It greatly simplifies and optimizes network programming such as TCP and UDP socket server, and has even better performance and security
	It is easier to use than directly using the NIO related API of JDK.
	The unified API supports multiple transmission types, blocking and non blocking.
	Simple and powerful threading model.
	The built-in codec solves the problem of TCP packet sticking / unpacking.
	With various protocol stacks.
	True connectionless packet socket support.
	It has higher throughput, lower latency, lower resource consumption and less memory replication than using the Java core API directly.
	Good security, complete SSL/TLS and StartTLS support.
*/
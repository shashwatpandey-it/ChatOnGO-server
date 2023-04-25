package com.shashwat.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.shashwat.components.MyScrollBar;
import com.shashwat.dao.DatabaseConnector;
import com.shashwat.service.Service;

public class ServerMain extends JFrame{
	
	private JScrollPane scrollPane;
	private JTextArea text;
	
	public ServerMain() {
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setTitle("ChatOnGoServer");
		super.setSize(1000,600);
		super.setLocation(450,100);
		
		this.init();
		
		super.setVisible(true);
	}
	
	private void init() {
		text = new JTextArea();
		text.setBackground(Color.BLACK);
		text.setForeground(Color.GREEN);
		text.setFont(new Font("Apple Casual",Font.PLAIN,16));;
		text.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		scrollPane = new JScrollPane(text);
		scrollPane.setVerticalScrollBar(new MyScrollBar());
		scrollPane.setHorizontalScrollBar(new MyScrollBar());
		scrollPane.setOpaque(false);
		
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				formWindowOpened(e);
			}
		
		});
		
	}

	private void formWindowOpened(WindowEvent event) {
		try {
			
			DatabaseConnector.getDatabaseConnector().connectToDatabase();
			text.append(">> Connection to database successful . . .\n");

			Service.getService(text).startService();
			
		} catch (Exception e) {
			text.append(">> Error: "+e+" . . .\n");
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ServerMain();
			}
		});
		
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
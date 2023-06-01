package com.docmgmt.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnect {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
	

		try {
			java.lang.Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			
			Connection conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://90.0.0.15:1433;DatabaseName=KnowledgeNET-Local-AUS","sa","Sarjen");
			
	         System.out.println("connected"+conn);
		} catch (Exception e) {
			/*
			 * LOG.error("Error when attempting to obtain DB Driver: " +
			 * config.getDbDriverName() + " on " + new Date().toString(), e);
			 */

			System.out.println("Error when attempting to obtain DB Driver: ");
		}
		
	}

}

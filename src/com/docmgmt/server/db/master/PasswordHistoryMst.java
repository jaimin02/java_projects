package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOPasswordHistory;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.webinterface.services.CryptoLibrary;

public class PasswordHistoryMst {
		
	public static CryptoLibrary encryption = new CryptoLibrary();
	
	public static ArrayList<DTOPasswordHistory> getPasswordHistoryDtls(int UserID){
		ArrayList<DTOPasswordHistory> pwdHistory = new ArrayList<DTOPasswordHistory>();
		try 
		{		  
			Connection con = ConnectionManager.ds.getConnection();
			DataTable dataTable = new DataTable();
			ResultSet rs=dataTable.getDataSet(con,"*","PasswordHistory" ,"nUserID='"+UserID+"'","iSrNo");
			
			while(rs.next())
			{
				DTOPasswordHistory dto = new DTOPasswordHistory();
				dto.setUserID(rs.getLong("nUserID"));
				dto.setSrNo(rs.getInt("iSrNo"));
				dto.setPassword(encryption.decrypt(rs.getString("vPassword")));
				dto.setChangedDate(rs.getTimestamp("dChangedDate"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				pwdHistory.add(dto);
									
			}
			rs.close();
			con.close();
		}   
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return pwdHistory;
	}
	
	public static ArrayList<DTOPasswordHistory> getPasswordHistoryDtlsForPasswordMatch(int userID,int matchHistory){
		ArrayList<DTOPasswordHistory> pwdHistory = new ArrayList<DTOPasswordHistory>();
		try 
		{		  
			Connection con = ConnectionManager.ds.getConnection();
			DataTable dataTable = new DataTable();
			ResultSet rs=dataTable.getDataSet(con,"top "+matchHistory +"*","PasswordHistory" ,"nUserID='"+userID+"'","iSrNo desc");
			
			while(rs.next())
			{
				DTOPasswordHistory dto = new DTOPasswordHistory();
				dto.setUserID(rs.getLong("nUserID"));
				dto.setSrNo(rs.getInt("iSrNo"));
				dto.setPassword(encryption.decrypt(rs.getString("vPassword")));
				dto.setChangedDate(rs.getTimestamp("dChangedDate"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				pwdHistory.add(dto);
									
			}
			rs.close();
			con.close();
		}   
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return pwdHistory;
	}
}
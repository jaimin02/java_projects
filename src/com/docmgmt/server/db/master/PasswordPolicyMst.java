package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class PasswordPolicyMst {

	public static ArrayList<DTOPasswordPolicyMst> getPolicyDetails(String policy){
		ArrayList<DTOPasswordPolicyMst> policyMst = new ArrayList<DTOPasswordPolicyMst>();
		try 
		{	
			
			//For initialization Only as this is the call first call to database when user logins.
			if(ConnectionManager.ds == null){
				new ConnectionManager(new Configuration());
			}
			Connection con = ConnectionManager.ds.getConnection();
			DataTable dataTable = new DataTable();
			String whr = " vPolicyDesc='"+policy+"' " +
						 " AND cActiveFlag = 'Y' "; //Important Filter for Policies
			ResultSet rs=dataTable.getDataSet(con,"*","PasswordPolicyMst" , whr,"nPolicyNo");
			
			while(rs.next())
			{
				DTOPasswordPolicyMst dto = new DTOPasswordPolicyMst();
				dto.setPolicyNo(rs.getInt("nPolicyNo"));
				dto.setPolicyDesc(rs.getString("vPolicyDesc"));
				dto.setValue(rs.getString("vValue"));
				dto.setActiveFlag(rs.getString("cActiveFlag").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				policyMst.add(dto);
									
			}
			rs.close();
			con.close();
		}   
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return policyMst;
	}

	public static boolean isPasswordValidate(int userID)
	{
			boolean isExpire = false;
			int dayDiff=1;//Default value set to 1 because dayDiff will redirect user to ChangePassword form
		try 
		{
			Connection con = ConnectionManager.ds.getConnection();
			
			CallableStatement proc=con.prepareCall("{call Proc_CheckPasswordValidity(?,?)}");
			
			proc.setInt(1,userID);
			proc.registerOutParameter(2,java.sql.Types.INTEGER);
			proc.execute();
			dayDiff = proc.getInt(2);
			proc.close();
			con.close();
			
		}
		catch(SQLException e){
		    e.printStackTrace();
		}
		if(dayDiff<=0){
			isExpire=true;
		}
		
		return isExpire;
	}
	public static ArrayList<DTOPasswordPolicyMst> getAllPolicyDetails(String policy){
		ArrayList<DTOPasswordPolicyMst> policyMst = new ArrayList<DTOPasswordPolicyMst>();
		try 
		{	
			
			//For initialization Only as this is the call first call to database when user logins.
			if(ConnectionManager.ds == null){
				new ConnectionManager(new Configuration());
			}
			Connection con = ConnectionManager.ds.getConnection();
			DataTable dataTable = new DataTable();
			String whr = " vPolicyDesc='"+policy+"' "; //Important Filter for Policies
			ResultSet rs=dataTable.getDataSet(con,"*","PasswordPolicyMst" , whr,"nPolicyNo");
			
			while(rs.next())
			{
				DTOPasswordPolicyMst dto = new DTOPasswordPolicyMst();
				dto.setPolicyNo(rs.getInt("nPolicyNo"));
				dto.setPolicyDesc(rs.getString("vPolicyDesc"));
				dto.setValue(rs.getString("vValue"));
				dto.setActiveFlag(rs.getString("cActiveFlag").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				policyMst.add(dto);
									
			}
			rs.close();
			con.close();
		}   
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return policyMst;
	}
}


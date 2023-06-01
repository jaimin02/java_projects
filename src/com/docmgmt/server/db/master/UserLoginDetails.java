package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.docmgmt.dto.DTOUserLoginDetails;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserLoginDetails {
	static DataTable dataTable=new DataTable();
	
public static int UserLoginDetailsOp(int UserId,int Mode)
{
	int timeDiff=0;
	try
	{
		
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_UserLoginDetails(?,?,?)}");
		
		cs.setInt(1,UserId);
		cs.setInt(2,Mode);
		cs.registerOutParameter(3,java.sql.Types.INTEGER);
		cs.execute();
		timeDiff=cs.getInt(3);
		cs.close();
		con.close();
				
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return timeDiff;
}
		
public static int checkUserloginDetails(int UserId){
	int flag = 0;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		DataTable dataTable = new DataTable();
		String whr = " nUserID='"+UserId+"' "; 
		ResultSet rs=dataTable.getDataSet(con,"*","UserLoginDetails" , whr,"nUserID");
		
		while(rs.next())
		{
			flag = 1;
		}
		rs.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return flag;
	
}
public static Vector<DTOUserLoginDetails> getLastActivity(int userCode) {
	Vector<DTOUserLoginDetails> userDtl = new Vector<DTOUserLoginDetails>();
	Timestamp lastActivityDate;
	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "dLastActivityDate",
				"UserLoginDetails", "nUserID=" + userCode, "");
		if (rs.next()) {
			DTOUserLoginDetails dto = new DTOUserLoginDetails();
			dto.setLastActivitydate(rs.getTimestamp("dLastActivityDate")); 
			userDtl.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDtl;

}

}

	
	




package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOUserLoginFailureDetails;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserLoginFailureDetails {
	
	/*
	 * @param Mode 1: insert new record
	 * @param Mode 2: update existing record
	 * @param Mode 3: unblock a user
	 * */
public static boolean insertFailureUserDtls(DTOUserLoginFailureDetails dto ,int Mode)
{
	 try
	  {
	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 CallableStatement proc = con.prepareCall("{ Call Insert_UserLoginFailureDetailsForBlockUserByUserCode(?,?,?,?,?)}");
			 proc.setString(1,dto.getUserName());
			 proc.setLong(2, dto.getAttemptCount());
			 proc.setString(3, Character.toString(dto.getBlockedFlag()));
			 proc.setInt(4, dto.getUserCode());
			 proc.setInt(5, Mode);
			 proc.execute();
			 proc.close();
			 con.close();
	 
		 
		 /*	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 CallableStatement proc = con.prepareCall("{ Call Insert_UserLoginFailureDetails(?,?,?,?,?)}");
			 proc.setLong(1,dto.getSrNo());
			 proc.setString(2,dto.getUserName());
			 proc.setLong(3, dto.getAttemptCount());
			 proc.setString(4, Character.toString(dto.getBlockedFlag()));
			 proc.setInt(5, Mode);
			 proc.execute();
			 proc.close();
			 con.close();
	 */} 
	 catch(SQLException e){
	   e.printStackTrace();
	 }

return true;
}
public static ArrayList<DTOUserLoginFailureDetails> getFailureUserDetail(String username)
{
	 ArrayList<DTOUserLoginFailureDetails> userFailure = new ArrayList<DTOUserLoginFailureDetails>();
	 try
	  {	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 DataTable dataTable = new DataTable();
		 ResultSet rs=dataTable.getDataSet(con,"top 1 *", "UserLoginFailureDetails","vUserName ='"+username+"'", "dlastFailedLogin desc");
		 while(rs.next()){
			 DTOUserLoginFailureDetails dto = new DTOUserLoginFailureDetails();
			 dto.setSrNo(rs.getLong("nSrNo"));
			 dto.setUserName(rs.getString("vUserName"));
			 dto.setLastFailedLogin(rs.getTimestamp("dLastFailedLogin"));
			 dto.setAttemptCount(rs.getLong("nAttemptCount"));
			 dto.setBlockedFlag(rs.getString("cBlockedFlag").charAt(0));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 userFailure.add(dto);
		 }
		 rs.close();
		 con.close();
	 } 
	 catch(SQLException e){
	   e.printStackTrace();
	 }

return userFailure;
}
public static int getTimeDifference(String UserName)
{
	int timeDiff=0;
	 try
	  {	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 CallableStatement proc = con.prepareCall("{ Call Proc_FailureDetailsCount(?,?)}");
			 
			 proc.setString(1,UserName);
			 proc.registerOutParameter(2,java.sql.Types.INTEGER);
			 proc.execute();
			 
			 timeDiff = proc.getInt(2);
			 
			 proc.close();
			 con.close();
	 } 
	 catch(SQLException e){
	   e.printStackTrace();
	 }

	 return timeDiff;
}
public static boolean insertFailureUserDtlsForBlockUser(DTOUserLoginFailureDetails dto ,int Mode)
{
	 try
	  {	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 CallableStatement proc = con.prepareCall("{ Call Insert_UserLoginFailureDetailsForBlockUser(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 proc.setLong(1,dto.getSrNo());
			 proc.setString(2,dto.getUserName());
			 proc.setLong(3, dto.getAttemptCount());
			 proc.setString(4, Character.toString(dto.getBlockedFlag()));
			 proc.setInt(5,dto.getUserCode());
			 proc.setInt(6,dto.getUserGroupCode());
			 proc.setString(7,dto.getLoginName());
			 proc.setString(8,dto.getUserName());
			 proc.setString(9,dto.getUserTypeCode());
			 proc.setInt(10,dto.getModifyBy());
			 proc.setString(11,dto.getRoleCode());
			 proc.setString(12,dto.getLocationCode());
			 proc.setInt(13, Mode);
			 proc.execute();
			 proc.close();
			 con.close();
	 } 
	 catch(SQLException e){
	   e.printStackTrace();
	 }

return true;
}


public static ArrayList<DTOUserLoginFailureDetails> getFailureDetailForUser(String username,int userCode)
{
	 ArrayList<DTOUserLoginFailureDetails> userFailure = new ArrayList<DTOUserLoginFailureDetails>();
	 try
	  {	    	
		 Connection con=ConnectionManager.ds.getConnection();
		 DataTable dataTable = new DataTable();
		 ResultSet rs=dataTable.getDataSet(con,"top 1 *", "UserLoginFailureDetails","vUserName ='"+username+"' and iUserCode="+userCode, "dlastFailedLogin desc");
		 while(rs.next()){
			 DTOUserLoginFailureDetails dto = new DTOUserLoginFailureDetails();
			 dto.setSrNo(rs.getLong("nSrNo"));
			 dto.setUserName(rs.getString("vUserName"));
			 dto.setLastFailedLogin(rs.getTimestamp("dLastFailedLogin"));
			 dto.setAttemptCount(rs.getLong("nAttemptCount"));
			 dto.setBlockedFlag(rs.getString("cBlockedFlag").charAt(0));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 userFailure.add(dto);
		 }
		 rs.close();
		 con.close();
	 } 
	 catch(SQLException e){
	   e.printStackTrace();
	 }

return userFailure;

}


}

	
	




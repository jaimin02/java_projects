package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOUserProfile;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserProfileMst {
	static DataTable dataTable=new DataTable();
	public void InserUserProfile(DTOUserProfile dtoUserProfile,int DataOpMode)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_UserProfile(?,?,?,?,?,?,?,?)}");
			procStatement.setInt(1, dtoUserProfile.getUserCode());
			procStatement.setString(2, dtoUserProfile.getProfileType().toString());
			procStatement.setString(3, dtoUserProfile.getProfileSubType());
			procStatement.setString(4, dtoUserProfile.getProfilevalue());
			procStatement.setString(5, dtoUserProfile.getAlertOn().toString());
			procStatement.setString(6, dtoUserProfile.getRemark());
			procStatement.setInt(7, dtoUserProfile.getModifyBy());
			procStatement.setInt(8, DataOpMode);
			procStatement.executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
	public ArrayList<DTOUserProfile> getUserProfileDetails(int userCode)
	{
		ArrayList<DTOUserProfile> arrUserProfile = new ArrayList<DTOUserProfile>();
		Connection con=null;
		ResultSet rs=null;
		try
		{
			con = ConnectionManager.ds.getConnection();
			String FieldNames = "cProfileType,cProfileSubType, vProfileValue, vProfileRemark, " +
								"cAlertOn, dProfileModifyOn, iProfileModifyBy, cProfileStatusIndi";
			rs=dataTable.getDataSet(con,FieldNames,"view_userprofile", "iUserCode = "+userCode, "");
			while(rs.next())
			{
				DTOUserProfile dto = new DTOUserProfile();
				dto.setProfileType(rs.getString("cProfileType").charAt(0));
				dto.setProfileSubType(rs.getString("cProfileSubType"));
				dto.setProfilevalue(rs.getString("vProfileValue"));
				dto.setAlertOn(rs.getString("cAlertOn").charAt(0));
				dto.setRemark(rs.getString("vProfileRemark"));
				dto.setModifyBy(rs.getInt("iProfileModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dProfileModifyOn"));
				dto.setStatusIndi(rs.getString("cProfileStatusIndi").charAt(0));
				arrUserProfile.add(dto);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return arrUserProfile;
	}
	public ArrayList<DTOUserProfile> getUserForAlert(Character profiletype)
	{
		ArrayList<DTOUserProfile> arrUserDetail = new ArrayList<DTOUserProfile>();
		Connection con=null;
		ResultSet rs=null;
		try
		{
			con = ConnectionManager.ds.getConnection();
			String whr = "cprofilestatusindi <> 'D' and cusermststatusindi <> 'D' and calerton = 'Y' and " +
					"(vprofilevalue <> null or vprofilevalue <> '') and cprofiletype = '"+profiletype+"'";
			rs=dataTable.getDataSet(con,"DISTINCT iUserCode, vUserName","view_userprofile", whr, "");
			while(rs.next())
			{
				DTOUserProfile dtoUserProfile = new DTOUserProfile();
				dtoUserProfile.setUserCode(rs.getInt("iUserCode"));
				dtoUserProfile.setUserName(rs.getString("vUserName"));
				arrUserDetail.add(dtoUserProfile);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return arrUserDetail;
	}
	public ArrayList<String> getUserDetailForAlert(Character profiletype, int usercode)
	{
		ArrayList<String> arrUserlist = new ArrayList<String>();
		Connection con=null;
		ResultSet rs=null;
		try
		{
			con = ConnectionManager.ds.getConnection();
			String whr = "cprofilestatusindi <> 'D' and cusermststatusindi <> 'D' and calerton = 'Y' and " +
					"(vprofilevalue <> null or vprofilevalue <> '') and cprofiletype = '"+profiletype+"' and iusercode="+usercode;
			rs=dataTable.getDataSet(con,"vprofilevalue","view_userprofile", whr, "");
			while(rs.next())
			{
				arrUserlist.add(rs.getString("vprofilevalue"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return arrUserlist;
	}


	
}

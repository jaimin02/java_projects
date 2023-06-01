package com.docmgmt.server.db.master;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOLoginHistory;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class LoginHistoryMst {
	
	ConnectionManager conMgr;
	
	public LoginHistoryMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
	}
	
public String LoginHistoryMstOp(DTOUserMst userMst,int Mode)
{
	String loginId = "";
	try
	{
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_LoginHistory(?,?,?,?,?,?,?)}");
		if(Mode==1)
		{
			cs.setString(1, "");
			cs.setInt(2,userMst.getUserCode());
			cs.setInt(3,userMst.getUserGroupCode());
			cs.setString(4,userMst.getUserType());
			cs.setString(5,userMst.getLoginIP());
			cs.setInt(6,Mode);
			cs.registerOutParameter(7,java.sql.Types.VARCHAR);
			cs.execute();
			loginId=cs.getString(7);
		}
		else
		{	
			String LognId=ActionContext.getContext().getSession().get("loginId").toString();
			cs.setString(1,LognId ); 
			cs.setInt(2,1); //not in proc use
			cs.setInt(3,1); //not in proc use
			cs.setString(4,""); //not in proc use
			cs.setString(5,""); //not in proc use
			cs.setInt(6,Mode);
			cs.registerOutParameter(7,java.sql.Types.VARCHAR);
			cs.execute();
		}
		cs.close();
		con.close();
				
	}catch (SQLException e) {
		e.printStackTrace();
	}
		
	return loginId;
}
public  String checkLoginHistory(int userCode){
	String loginIp = "";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		DataTable dataTable = new DataTable();
		String whr = " vLoginId in(select  max(vLoginId) from loginhistory where iUserId ="+userCode+")"; 
		ResultSet rs=dataTable.getDataSet(con,"vLoginIp","loginHistory" , whr,"");
		
		while(rs.next())
		{
			loginIp=rs.getString(1);
		}
		rs.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return loginIp;
	
}
public ArrayList<DTOLoginHistory> getLastLoginTime(int userCode){
	ArrayList<DTOLoginHistory> loginHistory = new ArrayList<DTOLoginHistory>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		DataTable dataTable = new DataTable();
		String whr = " iUserId ="+userCode; 
		ResultSet rs=dataTable.getDataSet(con,"top 2 *","loginHistory" , whr,"dLoginon desc");
		
		while(rs.next())
		{
			DTOLoginHistory dto = new DTOLoginHistory();
			dto.setLoginId(rs.getString("vLoginId"));
			dto.setUserId(rs.getInt("iUserId"));
			dto.setUserGroupCode(rs.getString("iUserGroupCode"));
			dto.setLoginOn(rs.getTimestamp("dLoginon"));
			loginHistory.add(dto);
			dto=null;
		}
		rs.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return loginHistory;
}
}//main class ended	

	
	




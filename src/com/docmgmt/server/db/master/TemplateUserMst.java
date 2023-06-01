package com.docmgmt.server.db.master;

import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import java.util.*;
import java.util.Date;
import java.sql.*;

import com.docmgmt.dto.*;

public class TemplateUserMst 
{
	ConnectionManager conMgr;
	DataTable dataTable;
	
	
public TemplateUserMst()
{
	 conMgr=new ConnectionManager(new Configuration());
	 dataTable=new DataTable(); 
}
	

public Vector getTemplateUserDetailList(String templateId)
{
	  
	Vector templateUserDtl = new Vector();
	try 
	{		  
		String whr=" templateId='"+templateId+"' and (statusindi='N' or statusindi='E')";
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*", "view_templateUserDetail",whr,"");
		while(rs.next())
		{
			DTOTemplateUserMst dto = new DTOTemplateUserMst();
			dto.setTemplateId(rs.getString("templateId"));
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserCode(rs.getInt("UserCode"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setFromDt(rs.getTimestamp("FromDt"));
			dto.setToDt(rs.getTimestamp("ToDt"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setUsername(rs.getString("Username"));
			dto.setUserGroupName(rs.getString("userGroupname"));
			dto.setTemplateDesc(rs.getString("templateDesc"));
			dto.setAdminFlag(rs.getString("AdminFlag").charAt(0));
			dto.setLastAccessedOn(rs.getTimestamp("LastAccessedOn"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			templateUserDtl.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
    return templateUserDtl;
}

public void insertUpdateUsertoTemplate(DTOTemplateUserMst obj, int[] userCode)
{
	try 
	{
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc=con.prepareCall("{call Insert_templateUserMst(?,?,?,?,?,?,?,?,?,?)}");
		for(int i=0;i<userCode.length;i++)
		{
			proc.setString(1, obj.getTemplateId());
			proc.setInt(2,obj.getUserGroupCode());
			proc.setInt(3, userCode[i]);
			proc.setString(4, Character.toString(obj.getAdminFlag()));
			proc.setString(5, obj.getRemark());
			proc.setInt(6, obj.getModifyBy());
			proc.setString(7, "E");
			long t = obj.getFromDt().getTime();
			proc.setDate(8, new java.sql.Date(t));
			long t1 = obj.getToDt().getTime();
			proc.setDate(9, new java.sql.Date(t1));
			proc.setInt(10,1); //datamode 1 for insert or (if available then)update
			proc.execute();
		}	
		proc.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}	 					
}	

public Vector getTemplateUserDetail(String templateId,DTOUserMst usermst)
{
	Vector userDtlVector = new Vector();
	try  
	{
		String whr="UserCode <> "+ usermst.getUserCode()+" and templateId='"+templateId+"' and statusIndi <> 'D'";
		Connection con = ConnectionManager.ds.getConnection();
        ResultSet rs=dataTable.getDataSet(con,"userGroupCode,usercode,username","view_templateUserDetail",whr,"");
    	while(rs.next())  
		{
			DTOUserMst userMstDTO = new DTOUserMst(); 
			userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
			userMstDTO.setUserCode(rs.getInt("userCode"));
			userMstDTO.setLoginName(rs.getString("username"));
			userDtlVector.addElement(userMstDTO);
			userMstDTO = null;
		}
		rs.close();
		con.close();
        
	}
	catch(SQLException e)  {
        e.printStackTrace();
	}
	return userDtlVector; 
}	

public DTOTemplateUserMst getUserDetails(DTOTemplateUserMst obj)
{
	DTOTemplateUserMst dto = new DTOTemplateUserMst();
	try 
	{
		StringBuffer query = new StringBuffer();
		query.append("templateId='"+obj.getTemplateId()+"' and UserGroupCode="+obj.getUserGroupCode()+" and UserCode="+obj.getUserCode());
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","view_templateUserDetail",query.toString(),"");
		if(rs.next())
		{
			dto.setTemplateId(rs.getString("templateId"));
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserCode(rs.getInt("UserCode"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setFromDt(rs.getTimestamp("FromDt"));
			dto.setToDt(rs.getTimestamp("ToDt"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setUsername(rs.getString("Username"));
			dto.setUserGroupName(rs.getString("userGroupname"));
			dto.setTemplateDesc(rs.getString("templateDesc"));
			dto.setAdminFlag(rs.getString("AdminFlag").charAt(0));
			dto.setLastAccessedOn(rs.getTimestamp("LastAccessedOn"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			
		}
		 rs.close();
		 con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return dto; 
}

public void inActiveUserFromTemplate(DTOTemplateUserMst obj)
{
	try 
	{		  
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc=con.prepareCall("{call Insert_templateUserMst(?,?,?,?,?,?,?,?,?,?)}");
		proc.setString(1, obj.getTemplateId());
		proc.setInt(2,obj.getUserGroupCode());
		proc.setInt(3,obj.getUserCode());
		proc.setString(4, Character.toString(obj.getAdminFlag()));
		proc.setString(5, obj.getRemark());
		proc.setInt(6, obj.getModifyBy());
		proc.setString(7, "D");
		obj.setToDt(new Date());
		obj.setFromDt(new Date());
		long t = obj.getFromDt().getTime();
		proc.setDate(8, new java.sql.Date(t));
		long t1 = obj.getToDt().getTime();
		proc.setDate(9, new java.sql.Date(t1));
		proc.setInt(10,2); //datamode 2
		proc.execute();
		proc.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}

}

}//main class end

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserGroupMst {
	
	DataTable dataTable;
	public UserGroupMst()
	{
		 dataTable=new DataTable();
	}
	
public Vector<DTOUserGroupMst> getAllUserGroupByUserType(String userType) 
{   				
	Vector<DTOUserGroupMst> userGroupDtl = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst","UserTypeCode ="+userType,"");
		while(rs.next())
		{
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("usergroupcode"));
			dto.setUserGroupName(rs.getString("usergroupName"));
			dto.setLocationCode(rs.getString("locationcode"));
			dto.setDeptCode(rs.getString("deptcode"));
			dto.setClientCode(rs.getString("clientcode"));
			dto.setProjectCode(rs.getString("projectcode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtl.addElement(dto);
			dto = null;
   		}
  	}catch (Exception e) {
   		e.printStackTrace();
   	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
   	
   	return userGroupDtl;
}
public Vector<DTOUserGroupMst> getAllUserGroupByUserTypeandClientCode(String userType,String clientCode) 
{   				
	Vector<DTOUserGroupMst> userGroupDtl = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst","clientcode=" + clientCode +" and UserTypeCode ="+userType,"");
		while(rs.next())
		{
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("usergroupcode"));
			dto.setUserGroupName(rs.getString("usergroupName"));
			dto.setLocationCode(rs.getString("locationcode"));
			dto.setDeptCode(rs.getString("deptcode"));
			dto.setClientCode(rs.getString("clientcode"));
			dto.setProjectCode(rs.getString("projectcode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtl.addElement(dto);
			dto = null;
   		}
  	}catch (Exception e) {
   		e.printStackTrace();
   	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
   	
   	return userGroupDtl;
}

public Vector<DTOUserGroupMst> getUserGroupDtl() 
{
	Vector<DTOUserGroupMst> data = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","view_usergroupmst","statusindi='N' or statusindi='E'","UserGroupName");
		while(rs.next())
		{
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationName(rs.getString("LocationName"));//locationName from locationMst
			dto.setProjectName(rs.getString("ProjectName"));//projectName from projectMst
			dto.setDeptName(rs.getString("DeptName"));//departmentName from deptMst
			dto.setClientName(rs.getString("ClientName"));//clientName from clientMst
			dto.setUserTypeName(rs.getString("UserTypeName"));//userTypeName from userTypeMst
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));//statusIndi from userGroupMst
			data.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return data;
}
	 
public void UserGroupMstOp(DTOUserGroupMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	
	 try
	  {	    	
		 con=ConnectionManager.ds.getConnection();
		 proc = con.prepareCall("{ Call Insert_userGroupMst(?,?,?,?,?,?,?,?,?,?,?)}");
		 proc.setInt(1,dto.getUserGroupCode());
		 proc.setString(2,dto.getUserGroupName());
		 proc.setString(3,dto.getLocationCode());
		 proc.setString(4,dto.getDeptCode());
		 proc.setString(5,dto.getClientCode());
		 proc.setString(6,dto.getProjectCode());
		 proc.setString(7,dto.getUserTypeCode());
		 proc.setString(8, dto.getRemark());
		 proc.setInt(9,dto.getModifyBy());
		 if(isrevert==false)
		 {
			 if(Mode==1)
				 proc.setString(10,"N");
		    else
		    	proc.setString(10,"E");	
		  }
		 else //if revert is true
		 {
			 char statusIndi=dto.getStatusIndi();
			 if('D'==statusIndi)
			 {
				 statusIndi='E';
			 }	
			 else statusIndi='D';
			 proc.setString(10,Character.toString(statusIndi));
		 }
		 proc.setInt(11,Mode); 
		 proc.executeUpdate();
	}
	catch(SQLException e){
	  e.printStackTrace();
	}finally{
		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
	}
	
}

public DTOUserGroupMst getUserGroupDtlByGroupId(String userGroupId)
{
	DTOUserGroupMst dto= new DTOUserGroupMst();;
	Connection con = null;
	ResultSet rs = null;
	try
	{ 
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst","usergroupcode="+userGroupId, ""); 
		if(rs.next())
		{
			dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
	    }
	}catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}    
	
	return dto;        
}    

public Vector<DTOUserGroupMst> getAllUserGroups()
{
	Vector<DTOUserGroupMst> userGroupDtlVector = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst"," UsertypeCode <> '0001' and UsertypeCode <> '0005' and StatusIndi <> 'D' ", ""); 
		while(rs.next())
		{   
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtlVector.addElement(dto);
			dto=null;
	    }
	}catch(SQLException e)  {
		e.printStackTrace();   
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return userGroupDtlVector;     
}	    
	    
public Vector<DTOUserGroupMst> getAllUserGroupsByClientCode(String clientcode)
{
	Vector<DTOUserGroupMst> userGroupDtlVector = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst","clientCode=" + clientcode + "and UserGroupName <> 'Super User Group' and StatusIndi <> 'D' ", ""); 
		while(rs.next())
		{   
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtlVector.addElement(dto);
			dto=null;
	    }
	}catch(SQLException e)  {
		e.printStackTrace();   
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return userGroupDtlVector;     
}	
public Vector<DTOUserGroupMst> getAllUserGroupsByDeptCode(String deptcode)
{
	Vector<DTOUserGroupMst> userGroupDtlVector = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String where="";
		if(deptcode != null && !deptcode.isEmpty()) {
			if(deptcode.equalsIgnoreCase("0001")){
				where="DeptCode <> 0002 and UserGroupName <> 'Super User Group' and StatusIndi <> 'D' ";
			}else if(deptcode.equalsIgnoreCase("0002")){
				where="DeptCode <> 0001 and UserGroupName <> 'Super User Group' and StatusIndi <> 'D' ";
			}
			else{
				where="UserGroupName <> 'Super User Group' and StatusIndi <> 'D' ";
			}
		}else{
			where="UserGroupName <> 'Super User Group' and StatusIndi <> 'D' ";
		}
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst",where+"", ""); 
		while(rs.next())
		{   
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtlVector.addElement(dto);
			dto=null;
	    }
	}catch(SQLException e)  {
		e.printStackTrace();   
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return userGroupDtlVector;     
}
public String getUserType(int userGroupId)
{
	DTOUserGroupMst dto= new DTOUserGroupMst();
	String userTypeName="";
	Connection con = null;
	ResultSet rs = null;
	try
	{ 
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"UserTypeName", "view_usergroupmst","usergroupcode="+userGroupId, ""); 
		if(rs.next())
		{
			dto = new DTOUserGroupMst();
			dto.setUserTypeName(rs.getString("UserTypeName"));//userGroupName from userGroupMst
			userTypeName = dto.getUserTypeName();
	    }
	}catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}    
	
	return userTypeName;        
}    
public Vector<DTOUserGroupMst> getAllUserGroupsForDocSign()
{
	Vector<DTOUserGroupMst> userGroupDtlVector = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst"," UserTypeName ='WU' and StatusIndi <> 'D' ", ""); 
		while(rs.next())
		{   
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtlVector.addElement(dto);
			dto=null;
	    }
	}catch(SQLException e)  {
		e.printStackTrace();   
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return userGroupDtlVector;     
}
public Vector<DTOUserGroupMst> getUserGroupDetails()
{
	Vector<DTOUserGroupMst> userGroupDtlVector = new Vector<DTOUserGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "view_usergroupmst"," UserTypeCode <> '0001' and StatusIndi <> 'D' ", ""); 
		while(rs.next())
		{   
			DTOUserGroupMst dto = new DTOUserGroupMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));//userGroupCode from userGroupMst
			dto.setUserGroupName(rs.getString("UserGroupName"));//userGroupName from userGroupMst
			dto.setLocationCode(rs.getString("LocationCode"));//locationName from locationMst
			dto.setDeptCode(rs.getString("DeptCode"));
			dto.setClientCode(rs.getString("clientCode"));
			dto.setProjectCode(rs.getString("ProjectCode"));
			dto.setDocTypeCode(rs.getString("DocTypeCode"));
			dto.setUserTypeCode(rs.getString("UserTypeCode"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			userGroupDtlVector.addElement(dto);
	    }
	}catch(SQLException e)  {
		e.printStackTrace();   
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return userGroupDtlVector;     
}	
}//main class ended

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class RoleMst {

	DataTable datatable;
	
	public RoleMst()
	{
		datatable=new DataTable();
	}
	

public Vector<DTORoleMst> getRoleDtl() 
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTORoleMst> data = new Vector<DTORoleMst>();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","Rolemst" ,"","vRoleName");
		while(rs.next())
		{
			DTORoleMst dto = new DTORoleMst();
			dto.setRoleCode(rs.getString("vRoleCode"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return data;
}
public Vector<DTORoleMst> getRoleDtl(String roleCode) 
{
	Vector<DTORoleMst> data = new Vector<DTORoleMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","Rolemst" ,"vRoleCode<>'"+roleCode+"'","vRoleName");
		while(rs.next())
		{
			DTORoleMst dto = new DTORoleMst();
			dto.setRoleCode(rs.getString("vRoleCode"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return data;
}

public String getRoleName(String roleCode) 
{
	String roleName="";
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","Rolemst" ,"vRoleCode='"+roleCode+"'","");
		while(rs.next())
		{
			roleName = rs.getString("vRoleName");
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return roleName;
}


public void  RoleMstOp(DTORoleMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_roleMst(?,?,?,?,?,?)}");
		proc.setString(1,dto.getRoleCode());
		proc.setString(2,dto.getRoleName());
		proc.setString(3,dto.getRemark() );
		proc.setInt(4,dto.getModifyBy());
		if(isrevert==false)
		{
			if(Mode==1)
				proc.setString(5,"N");
			else
				proc.setString(5,"E");	
		}
		else //if revert is true
		{
			char statusIndi=dto.getStatusIndi();
			if('D'==statusIndi)
			{
				statusIndi='A';
			}
			else statusIndi='D';
		    	 proc.setString(5,Character.toString(statusIndi));
		}
		proc.setInt(6,Mode); 
		proc.executeUpdate();
	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
}
 
public boolean roleNameExist(String roleName)
{
	boolean flag = false;
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"vRoleCode","roleMst","vroleName = '" + roleName + "'","");
		if(rs.next())
		{
			flag = true;
		}
		rs.close();
    	con.close();
 	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
    	
	return flag;
}

public DTORoleMst getRoleInfo(String ClientCode)
{
	DTORoleMst dto = new DTORoleMst();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		String whr="vRoleCode="+ClientCode;
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","RoleMst" ,whr,"");
		if(rs.next())
		{
			dto.setRoleCode(rs.getString("vRoleCode"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}

public Vector<DTORoleMst> getRoleDetailHistory(String clientCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTORoleMst> clientDetailHistory = new Vector<DTORoleMst>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "roleCode = '" + clientCode +"'";
		rs = datatable.getDataSet(con, "*","View_rolemsthistory", Where,	"TranNo");

		while (rs.next()) {
			DTORoleMst dto = new DTORoleMst();
			dto.setRoleCode(rs.getString("RoleCode")); // ClientCode
			dto.setRoleName(rs.getString("RoleName")); // ClientName
			dto.setUserName(rs.getString("UserName")); // UserName
			dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
			dto.setRemark(rs.getString("Remark")); // remark
			dto.setModifyOn(rs.getTimestamp("ModifyOn")); // modifyOn
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0)); // statusIndi
			clientDetailHistory.addElement(dto);
			dto = null;
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}
}//main class ended

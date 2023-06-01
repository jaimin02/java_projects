package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class DepartmentMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	public DepartmentMst()
	{
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		datatable=new DataTable();
	}
	

public Vector<DTODepartmentMst> getDepartmentDetail() 
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	Vector<DTODepartmentMst> departmentVector = new Vector<DTODepartmentMst>();		
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();	
		ResultSet rs=datatable.getDataSet(con,"*","deptMst" ,"cStatusIndi<>'D'","vDeptName");
		while(rs.next())
		{
			DTODepartmentMst dto = new DTODepartmentMst();
			dto.setDeptCode(rs.getString("vDeptCode"));
			dto.setDeptName(rs.getString("vDeptName"));
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
			departmentVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return departmentVector;
}
public Vector<DTODepartmentMst> getDepartmentDetailByDeptCode(String deptcode) 
{
	
	Vector<DTODepartmentMst> departmentVector = new Vector<DTODepartmentMst>();		
	try 
	{		  
		String Wherecond="";
		if(deptcode != null && !deptcode.isEmpty()) {
			Wherecond="vDeptCode='"+deptcode+"'";
		}
		 
		
		Connection con = ConnectionManager.ds.getConnection();	
		ResultSet rs=datatable.getDataSet(con,"*","deptMst" ,Wherecond,"vDeptName");
		while(rs.next())
		{
			DTODepartmentMst dto = new DTODepartmentMst();
			dto.setDeptCode(rs.getString("vDeptCode"));
			dto.setDeptName(rs.getString("vDeptName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			departmentVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return departmentVector;
}

public void DepartmentMstOp(DTODepartmentMst dto,int Mode,boolean isrevert)
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_deptmst(?,?,?,?,?,?)}");
		proc.setString(1,dto.getDeptCode());
		proc.setString(2,dto.getDeptName());
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
		proc.close();
		con.close();
	}
	catch(SQLException e){
	   e.printStackTrace();
   }

}

public DTODepartmentMst getDepartmentInfo(String DeptCode)
{
	DTODepartmentMst dto=new DTODepartmentMst();
	try 
	{		  
		//String Wherecond="vDeptCode='"+DeptCode+"' and cStatusIndi<> 'D'";
		String Wherecond="vDeptCode='"+DeptCode+"'";
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","DeptMst" ,Wherecond,"");
		if(rs.next())
		{
			dto.setDeptCode(rs.getString("vDeptCode"));
			dto.setDeptName(rs.getString("vDeptName"));
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
	}
	return dto;
}

public boolean deptNameExist(String deptName)
{
	boolean flag = false;
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"vDeptCode","deptMst","vDeptName = '" + deptName + "'","");
		if(rs.next())
		{
			flag = true;
		}
		rs.close();
    	con.close();
 	}
	catch(SQLException e){
		e.printStackTrace();
	}    	
    	
	return flag;
}

//Added by Virendra Barad for get Department Detail History
public Vector<DTODepartmentMst> getDeptDetailHistory(String deptCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTODepartmentMst> deptDetailHistory = new Vector<DTODepartmentMst>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "deptCode = '" + deptCode +"'";
		rs = datatable.getDataSet(con, "*","view_deptmsthistory", Where,"TranNo");

		while (rs.next()) {
			DTODepartmentMst dto = new DTODepartmentMst();
			dto.setDeptCode(rs.getString("DeptCode")); // DeptCode
			dto.setDeptName(rs.getString("DeptName")); // DeptName
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
			deptDetailHistory.addElement(dto);
			dto = null;
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return deptDetailHistory;	
}	

public Vector<DTODepartmentMst> getDepartmentDetailForEditProject(String deptcode) 
{
	
	Vector<DTODepartmentMst> departmentVector = new Vector<DTODepartmentMst>();		
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();	
		ResultSet rs=datatable.getDataSet(con,"*","deptMst" ,"cStatusIndi<>'D'","vDeptName");
		while(rs.next())
		{
			DTODepartmentMst dto = new DTODepartmentMst();
			dto.setDeptCode(rs.getString("vDeptCode"));
			dto.setDeptName(rs.getString("vDeptName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			departmentVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return departmentVector;
}

	
}//main class ended


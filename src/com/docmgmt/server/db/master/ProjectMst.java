package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class ProjectMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	
	public ProjectMst()
	{
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}

public Vector<DTOProjectMst> getProjectType() 
{
   	Vector<DTOProjectMst> projectVector = new Vector<DTOProjectMst>();
	try
	{	
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","projectMst" ,"vProjectCode in ('0002','0003','0007') And cStatusIndi<>'D'","vProjectCode");
		while(rs.next())
		{
			DTOProjectMst dto = new DTOProjectMst();
			dto.setProjectCode(rs.getString("vProjectCode"));
			dto.setProjectName(rs.getString("vProjectName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			projectVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(Exception e){
		e.printStackTrace();
	}
	return projectVector;
}
	
public void ProjectMstOp(DTOProjectMst dto,int Mode,boolean isrevert)
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_projectMst(?,?,?,?,?,?)}");
		proc.setString(1,dto.getProjectCode());
		proc.setString(2,dto.getProjectName());
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
				statusIndi='E';
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
	
public DTOProjectMst getProjectInfo(String ProjectCode)
{
	DTOProjectMst dto=new DTOProjectMst();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","projectmst" ,"vProjectCode="+ProjectCode,"");
		if(rs.next())
		{
			dto.setProjectCode(rs.getString("vProjectCode"));
			dto.setProjectName(rs.getString("vProjectName"));
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

public boolean projectNameExist(String projectName)
{
	boolean flag = false;
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"vProjectCode","projectMst","vprojectName = '"+projectName+"'","");
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
public Vector<DTOProjectMst> getProjectTypeForESignDoc() 
{
   	Vector<DTOProjectMst> projectVector = new Vector<DTOProjectMst>();
	try
	{	
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","projectMst" ,"vProjectCode in ('0004','0005','0006','0008')","vProjectName");
		while(rs.next())
		{
			DTOProjectMst dto = new DTOProjectMst();
			dto.setProjectCode(rs.getString("vProjectCode"));
			dto.setProjectName(rs.getString("vProjectName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			projectVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(Exception e){
		e.printStackTrace();
	}
	return projectVector;
}

public Vector<DTOProjectMst> getProjectTypeForESign() 
{
   	Vector<DTOProjectMst> projectVector = new Vector<DTOProjectMst>();
	try
	{	
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","projectMst" ,"vProjectCode in ('0008')","vProjectName");
		while(rs.next())
		{
			DTOProjectMst dto = new DTOProjectMst();
			dto.setProjectCode(rs.getString("vProjectCode"));
			dto.setProjectName(rs.getString("vProjectName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			projectVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(Exception e){
		e.printStackTrace();
	}
	return projectVector;
}

public Vector<DTOProjectMst> getProjectTypeForSize() 
{
   	Vector<DTOProjectMst> projectVector = new Vector<DTOProjectMst>();
	try
	{	
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","projectMst" ,"vProjectCode in ('0002','0008') And cStatusIndi<>'D'","vProjectCode");
		while(rs.next())
		{
			DTOProjectMst dto = new DTOProjectMst();
			dto.setProjectCode(rs.getString("vProjectCode"));
			dto.setProjectName(rs.getString("vProjectName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			projectVector.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(Exception e){
		e.printStackTrace();
	}
	return projectVector;
}

}//main class ended

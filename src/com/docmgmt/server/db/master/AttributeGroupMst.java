package com.docmgmt.server.db.master;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeGroupMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;


public class AttributeGroupMst {

	static DataTable dataTable = new DataTable();

	public Vector<DTOAttributeGroupMst> getAllAttributeGroup() {
       
		Vector<DTOAttributeGroupMst> attrGroup = new Vector<DTOAttributeGroupMst>();
    	try 
		{	
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con,"*", "view_getAttributeGroup", "", "vAttrGroupName");
			while(rs.next())
			{
				DTOAttributeGroupMst dto = new DTOAttributeGroupMst();
				dto.setAttrGroupId(rs.getString("vattrGroupId"));//attributeGroupId
				dto.setAttrGroupName(rs.getString("vAttrGroupName"));//attributeGroupName
				dto.setProjectCode(rs.getString("vProjectCode"));//projectCode
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
				dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
				dto.setProjectName(rs.getString("vProjectName"));
				attrGroup.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		}   
			catch(SQLException e){
				e.printStackTrace();
		}
        return attrGroup;
	}
 
	
	 public DTOAttributeGroupMst getAttributeGroupById(String attrGroupId)   {
			
		 DTOAttributeGroupMst dto = new DTOAttributeGroupMst();
		 try
		 {
			 Connection con = ConnectionManager.ds.getConnection();
			 ResultSet rs = dataTable.getDataSet(con, "*", "AttributeGroupmst","vattrGroupId = '"+attrGroupId+"'", "");
			 while(rs.next())
			 {
				dto.setAttrGroupId(rs.getString("vattrGroupId"));//attributeGroupId
				dto.setAttrGroupName(rs.getString("vAttrGroupName"));//attributeGroupName
				dto.setProjectCode(rs.getString("vProjectCode"));//projectCode
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
				dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			 }
			 rs.close();
			 con.close();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }

		 return dto;
	 }
	 
	 public void InsertAttributeGroupMst(DTOAttributeGroupMst dto, int Mode){
		
		 try
		  {	  
			Connection con = ConnectionManager.ds.getConnection();
	  		CallableStatement proc = con.prepareCall("{Call Insert_AttributeGroupMst(?,?,?,?,?,?)}");
		    proc.setString(1,dto.getAttrGroupId());
		    proc.setString(2,dto.getAttrGroupName());
		    proc.setString(3,dto.getProjectCode() );
		    proc.setString(4, Character.toString(dto.getStatusIndi()));
		    proc.setInt(5,dto.getModifyBy());
		    proc.setInt(6, Mode);
		    proc.executeUpdate();
		    proc.close();
		    con.close();
	   }
	   catch(SQLException e){
		   e.printStackTrace();
	   }
		 
	 }
}
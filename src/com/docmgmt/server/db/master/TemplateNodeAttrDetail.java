package com.docmgmt.server.db.master;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TemplateNodeAttrDetail {
	

DataTable dataTable;
	
	public TemplateNodeAttrDetail()
	{
	
		 dataTable = new DataTable();
	}
	
public void insertIntoTemplateNodeAttrDtl(DTOTemplateNodeAttrDetail dto,int mode)
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_IntotemplateNodeAttrDetail(?,?,?,?,?,?,?,?,?,?)}");
		proc.setString(1, dto.getTemplateId());
		proc.setInt(2, dto.getNodeId());
		proc.setInt(3, dto.getAttrId());
		proc.setString(4, dto.getAttrName());
		proc.setString(5, dto.getAttrValue());
		proc.setString(6, dto.getValidValues());
		proc.setString(7, dto.getAttrForIndiId());
		proc.setString(8, "");
		proc.setInt(9, dto.getModifyBy());
		proc.setInt(10, mode);
		proc.execute();
		
	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
		try {if(proc != null){proc.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
			 
}
	
public Vector getTemplateNodeAttrDtl(DTOTemplateNodeAttrDetail dto)
{
		
	Vector data = new Vector();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con,"*", "templateNodeAttrDetail", "vTemplateId = '"+dto.getTemplateId()+"' and iNodeId ="+dto.getNodeId(), "");
		while(rs.next())
		{
			DTOTemplateNodeAttrDetail dto1 = new DTOTemplateNodeAttrDetail();
			dto1.setTemplateId(rs.getString("vTemplateId"));//templateId
			dto1.setNodeId(rs.getInt("iNodeId"));//nodeId
			dto1.setAttrId(rs.getInt("iAttrId"));//attrId
			dto1.setAttrName(rs.getString("vAttrName"));//attrName
			dto1.setAttrValue(rs.getString("vAttrValue"));//attrValue
			dto1.setValidValues(rs.getString("vValidValues"));//validValues
			dto1.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));//requiredFlag
			dto1.setEditableFlag(rs.getString("cEditableFlag").charAt(0));//editableFlag
			dto1.setAttrForIndiId(rs.getString("vAttrForIndiId"));//attrForIndiId
			dto1.setRemark(rs.getString("vRemark"));//remark
			dto1.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			dto1.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
			dto1.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
			data.addElement(dto1);
			dto1 = null;
		}
		rs.close();
		con.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
	return data;
	
}

	/* use in tempatenodedetail for addchild */
public boolean insertIntoTemplateNodeAttrDetailForAddChild(DTOTemplateNodeAttrDetail dto) 
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_templateNodeAttrDetail(?,?,?)}");
		proc.setString(1, dto.getTemplateId());
		proc.setInt(2, dto.getNodeId());
		proc.setInt(3, dto.getParentNodeId());
		proc.execute();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return  false;
}
	
public Vector setBulkAttributeValueOnTemplate(String templateId, int attrId)
{
	Vector data = new Vector();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con,"*", "view_BulkTemplateAttributeValue", "vTemplateId = '"+templateId+"' and iAttrId ="+attrId, "");
		while(rs.next())
		{
			DTOTemplateNodeAttrDetail dto1 = new DTOTemplateNodeAttrDetail();
			dto1.setTemplateId(rs.getString("vTemplateId"));//templateId
			dto1.setNodeId(rs.getInt("iNodeId"));//nodeId
			dto1.setAttrId(rs.getInt("iAttrId"));//attrId
			dto1.setAttrName(rs.getString("vAttrName"));//attrName
			dto1.setAttrValue(rs.getString("vAttrValue"));//attrValue
			dto1.setRemark(rs.getString("vNodeName")+"  ("+rs.getString("vNodeDisplayName")+")");// set Remark as Node Name
			dto1.setAttrForIndiId(rs.getString("vAttrType"));// set attrforindiid as  attribute Type 
			data.addElement(dto1);
			dto1 = null;
		}
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}
	
public void updateTemplateNodeAttrValue(DTOTemplateNodeAttrDetail dto)
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call proc_UpdateTemplateNodeAttrValue(?,?,?,?)}");
		proc.setString(1, dto.getTemplateId());
		proc.setString(2, dto.getAttrValue());
		proc.setInt(3, dto.getAttrId());
		proc.setInt(4, dto.getNodeId());
		proc.execute();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
}


public void InsertNodeAttributeFromXml(Vector NodeAttrData)
{
	
	
	CallableStatement proc = null;
	try
		{
			Connection con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_IntotemplateNodeAttrDetail(?,?,?,?,?,?,?,?,?)}");
		
			
			System.out.println("TotalNodeAttr: "+NodeAttrData.size());
			
			int size=NodeAttrData.size();
			
			for(int i=0;i<size;i++)
			{
					
				
				
				DTOTemplateNodeAttrDetail dto=(DTOTemplateNodeAttrDetail)NodeAttrData.get(i);
		
		
				proc.setString(1, dto.getTemplateId());
				proc.setInt(2, dto.getNodeId());
				proc.setInt(3, dto.getAttrId());
				proc.setString(4, dto.getAttrName());
				proc.setString(5, dto.getAttrValue());
				proc.setString(6, dto.getAttrForIndiId());
				proc.setString(7, dto.getRemark());
				proc.setInt(8, dto.getModifyBy());
				proc.setInt(9,1);//mode = 1 for insert
				
				
				proc.execute();
		
				//System.out.println(i);
				
				//System.out.println(i+"."+dto.getAttrName()+"--"+dto.getAttrValue());
				
				dto=null;
				
			}
			
			proc.close();
			con.close();
		
		}
		catch(SQLException e)
		{
			
		  e.printStackTrace();
		}
		 
	
	
	
}





}//main class ended


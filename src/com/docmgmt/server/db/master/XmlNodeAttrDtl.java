package com.docmgmt.server.db.master;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.docmgmt.dto.DTOXmlNodeAttrDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class XmlNodeAttrDtl {

	ConnectionManager conMgr;
	DataTable datatable;
	public XmlNodeAttrDtl()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
 
public ArrayList<DTOXmlNodeAttrDtl> getXmlNodeAttrDtl(long xmlWorkspaceId,int xmlNodeId)
{
	ArrayList<DTOXmlNodeAttrDtl> lstXmlNodeAttributes = new ArrayList<DTOXmlNodeAttrDtl>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " iXmlWorkspaceId = "+xmlWorkspaceId+" AND iXmlNodeId = "+xmlNodeId;
		
		ResultSet rs=datatable.getDataSet(con,"*","XmlNodeAttrDtl" ,whr,"");
		while(rs.next())
		{
			DTOXmlNodeAttrDtl dto = new DTOXmlNodeAttrDtl();
			dto.setXmlNodeAttrDtlId(rs.getLong("iXmlNodeAttrDtlId"));
			dto.setXmlWorkspaceId(rs.getLong("iXmlWorkspaceId"));
			dto.setXmlNodeId(rs.getInt("iXmlNodeId"));
			dto.setAttrId(rs.getInt("iAttrId"));
			dto.setAttrName(rs.getString("vAttrName"));
			dto.setDefaultAttrValue(rs.getString("vDefaultAttrValue"));
			dto.setTableName(rs.getString("vTableName"));
			dto.setColumnName(rs.getString("vColumnName"));
			dto.setFixed(rs.getString("cFixed").charAt(0));
			dto.setRequired(rs.getString("cRequired").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
			
			lstXmlNodeAttributes.add(dto);
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstXmlNodeAttributes;
}

public ArrayList<String> getXmlAttrValue(String workspaceId,String tablename,HashMap<String,String> inputFields,String outputField)
{
	ArrayList<String> lstXmlNodeAttributes = new ArrayList<String>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " vworkspaceId = '"+workspaceId+"' ";
		
		for (Iterator<Entry<String,String>> iterator = inputFields.entrySet().iterator(); iterator.hasNext();){
			Entry<String,String> inputField = iterator.next();
			
			whr += " AND "+inputField.getKey()+" = '"+inputField.getValue()+"' ";
		}
		
		ResultSet rs=datatable.getDataSet(con," DISTINCT " + outputField + " ",tablename ,whr,"");
		while(rs.next())
		{
			lstXmlNodeAttributes.add(rs.getString(outputField));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstXmlNodeAttributes;
}

public ArrayList<DTOXmlNodeAttrDtl> getXmlAttrDtl(long xmlNodeAttrDtlId)
{
	ArrayList<DTOXmlNodeAttrDtl> lstXmlNodeAttributes = new ArrayList<DTOXmlNodeAttrDtl>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " iXmlNodeAttrDtlId = "+xmlNodeAttrDtlId;
		
		ResultSet rs=datatable.getDataSet(con,"*","XmlNodeAttrDtl" ,whr,"");
		if(rs.next())
		{
			DTOXmlNodeAttrDtl dto = new DTOXmlNodeAttrDtl();
			dto.setXmlNodeAttrDtlId(rs.getLong("iXmlNodeAttrDtlId"));
			dto.setXmlWorkspaceId(rs.getLong("iXmlWorkspaceId"));
			dto.setXmlNodeId(rs.getInt("iXmlNodeId"));
			dto.setAttrId(rs.getInt("iAttrId"));
			dto.setAttrName(rs.getString("vAttrName"));
			dto.setDefaultAttrValue(rs.getString("vDefaultAttrValue"));
			dto.setTableName(rs.getString("vTableName"));
			dto.setColumnName(rs.getString("vColumnName"));
			dto.setFixed(rs.getString("cFixed").charAt(0));
			dto.setRequired(rs.getString("cRequired").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
			
			lstXmlNodeAttributes.add(dto);
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstXmlNodeAttributes;
}
 
}//main class ended

package com.docmgmt.server.db.master;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.docmgmt.dto.DTOXmlNodeDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class XmlNodeDtl {

	ConnectionManager conMgr;
	DataTable datatable;
	public XmlNodeDtl()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
 

public DTOXmlNodeDtl getXmlNodeDtl(long xmlWorkspaceId,int xmlNodeId)
{
	DTOXmlNodeDtl dto = new DTOXmlNodeDtl();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " iXmlWorkspaceId = "+xmlWorkspaceId+" AND iXmlNodeId = "+xmlNodeId;
		
		ResultSet rs=datatable.getDataSet(con,"*","View_XmlNodeDtl" ,whr,"");
		if(rs.next())
		{
			dto.setXmlWorkspaceName(rs.getString("vXmlWorkspaceName"));
			dto.setXmlFileName(rs.getString("vXmlFileName"));
			dto.setFilePath(rs.getString("vFilePath"));
			dto.setXmlHeader(rs.getString("vXmlHeader"));
			dto.setXmlWorkspaceId(rs.getLong("iXmlWorkspaceId"));
			dto.setXmlNodeDtlId(rs.getLong("iXmlNodeDtlId"));
			dto.setXmlNodeId(rs.getInt("iXmlNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setXmlNodeName(rs.getString("vXmlNodeName"));
			dto.setParentNodeId(rs.getInt("iParentNodeId"));
			dto.setRepeatable(rs.getString("cRepeatable").charAt(0));
			dto.setEmpty(rs.getString("cEmpty").charAt(0));
			dto.setMandatory(rs.getString("cMandatory").charAt(0));
			dto.setTableName(rs.getString("vTableName"));
			dto.setColumnName(rs.getString("vColumnName"));
			dto.setPrimaryXmlAttrId(rs.getLong("iPrimaryXmlAttrId"));
			dto.setWhereClauseColumns(rs.getString("vWhereClauseColumns"));
			
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
	
public ArrayList<DTOXmlNodeDtl> getXmlChildNodeDtl(long xmlWorkspaceId,int xmlParentNodeId)
{
	ArrayList<DTOXmlNodeDtl> lstXmlNodes = new ArrayList<DTOXmlNodeDtl>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " iXmlWorkspaceId = "+xmlWorkspaceId+" AND iParentNodeId = "+xmlParentNodeId;
		
		ResultSet rs=datatable.getDataSet(con,"*","View_XmlNodeDtl" ,whr,"iNodeNo");
		while(rs.next())
		{
			DTOXmlNodeDtl dto = new DTOXmlNodeDtl();
			
			dto.setXmlWorkspaceName(rs.getString("vXmlWorkspaceName"));
			dto.setXmlFileName(rs.getString("vXmlFileName"));
			dto.setFilePath(rs.getString("vFilePath"));
			dto.setXmlHeader(rs.getString("vXmlHeader"));
			dto.setXmlWorkspaceId(rs.getLong("iXmlWorkspaceId"));
			dto.setXmlNodeDtlId(rs.getLong("iXmlNodeDtlId"));
			dto.setXmlNodeId(rs.getInt("iXmlNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setXmlNodeName(rs.getString("vXmlNodeName"));
			dto.setParentNodeId(rs.getInt("iParentNodeId"));
			dto.setRepeatable(rs.getString("cRepeatable").charAt(0));
			dto.setEmpty(rs.getString("cEmpty").charAt(0));
			dto.setMandatory(rs.getString("cMandatory").charAt(0));
			dto.setTableName(rs.getString("vTableName"));
			dto.setColumnName(rs.getString("vColumnName"));
			dto.setPrimaryXmlAttrId(rs.getLong("iPrimaryXmlAttrId"));
			dto.setWhereClauseColumns(rs.getString("vWhereClauseColumns"));
			
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
			
			lstXmlNodes.add(dto);
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstXmlNodes;
}

public ArrayList<String> getXmlNodeValue(String workspaceId,String tablename,HashMap<String,String> inputFields,String outputField)
{
	return getRequiredData(workspaceId, tablename, inputFields, outputField);
	
	/*ArrayList<String> lstXmlNodeValues= new ArrayList<String>();
	try 
	{		  
		Connection con = conMgr.ds.getConnection();
		
		String whr = " vworkspaceId = '"+workspaceId+"' ";
		
		ResultSet rs=datatable.getDataSet(con," DISTINCT " + outputField + " ",tablename ,whr,"");
		while(rs.next())
		{
			lstXmlNodeValues.add(rs.getString(outputField));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstXmlNodeValues;*/
}


public ArrayList<String> getXmlAttrValuesForRepeatableNode(String workspaceId,String tablename,HashMap<String,String> inputFields,String outputField)
{
	return getRequiredData(workspaceId, tablename, inputFields, outputField);
}

public ArrayList<String> getRequiredData(String workspaceId,String tablename,HashMap<String,String> inputFields,String outputField){
	ArrayList<String> lstXmlNodeAttributes = new ArrayList<String>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " vworkspaceId = '"+workspaceId+"' ";
		
		for (Iterator<Entry<String,String>> iterator = inputFields.entrySet().iterator(); iterator.hasNext();){
			Entry<String,String> inputField = iterator.next();
			
			whr += " AND "+inputField.getKey()+" = '"+inputField.getValue()+"' ";
		}
		System.out.println("select DISTINCT "+outputField+" from "+tablename+" where "+whr);
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

}//main class ended

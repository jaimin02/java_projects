package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSTFNodeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;


public class STFNodeMst {
	
	ConnectionManager conMgr;
	DataTable dataTable;
	
	public STFNodeMst()
	{
		dataTable = new DataTable();
	}
	
public Vector<DTOSTFNodeMst> getAllSTFNodes() 
{
	Vector<DTOSTFNodeMst> stfvector = new Vector<DTOSTFNodeMst>();		
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"vId,vNodeName,vNodeCategory,vRemark,iModifyBy,dModifyOn,cStatusIndi,cMultiple", "stfNodeMst", "", "vNodeName");
		while(rs.next())
		{
			DTOSTFNodeMst dto = new DTOSTFNodeMst();		        		        
			dto.setId(rs.getString("vId"));//Id
			dto.setNodeName(rs.getString("vNodeName"));//nodeName
			dto.setNodeCategory(rs.getString("vNodeCategory"));//nodeCategory
			dto.setRemark(rs.getString("vRemark"));//remark
			dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
			dto.setMultiple(rs.getString("cMultiple").charAt(0));//isNodeTypeMultiple
			stfvector.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return stfvector;
}
public DTOSTFNodeMst getSTFNodeById(String STFNodeId) 
{
	DTOSTFNodeMst dto = new DTOSTFNodeMst();		
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"vId,vNodeName,vNodeCategory,vRemark,iModifyBy,dModifyOn,cStatusIndi,cMultiple", "stfNodeMst", "vId='"+STFNodeId+"'", "");
		if(rs.next())
		{
			dto.setId(rs.getString("vId"));//Id
			dto.setNodeName(rs.getString("vNodeName"));//nodeName
			dto.setNodeCategory(rs.getString("vNodeCategory"));//nodeCategory
			dto.setRemark(rs.getString("vRemark"));//remark
			dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
			dto.setMultiple(rs.getString("cMultiple").charAt(0));//isNodeTypeMultiple
		}
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return dto;
}

	public Vector<DTOSTFNodeMst> getMulipleFlagedSTFNodes() 
	{
		Vector<DTOSTFNodeMst> stfvector = new Vector<DTOSTFNodeMst>();		
		try 
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con,"vId,vNodeName,vNodeCategory,vRemark,iModifyBy,dModifyOn,cStatusIndi,cMultiple", "stfNodeMst", "cMultiple='Y'", "");
			while(rs.next())
			{
				DTOSTFNodeMst dto = new DTOSTFNodeMst();		        		        
				dto.setId(rs.getString("vId"));//Id
				dto.setNodeName(rs.getString("vNodeName"));//nodeName
				dto.setNodeCategory(rs.getString("vNodeCategory"));//nodeCategory
				dto.setRemark(rs.getString("vRemark"));//remark
				dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
				dto.setMultiple(rs.getString("cMultiple").charAt(0));//isNodeTypeMultiple
				stfvector.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
	
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return stfvector;
	}

	public void insertIntoSTFNodeMst(DTOSTFNodeMst stfnodemst) {
		
		CallableStatement proc = null;

	  	try
	  	{	
			Connection con=ConnectionManager.ds.getConnection();
	  		proc = con.prepareCall("{ Call Insert_stfNodeMst(?,?,?,?)}");
		    proc.setString(1,stfnodemst.getNodeName());
		    proc.setString(2,stfnodemst.getNodeCategory());
		    proc.setString(3,stfnodemst.getRemark() );
		    proc.setInt(4,stfnodemst.getModifyBy());
		    proc.execute();
		    proc.close();
		    con.close();
	  	}catch(SQLException e){
	  		e.printStackTrace();
	  	}

	}
	
}
package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class STFStudyIdentifierMst {
	
	DataTable dataTable;
	public STFStudyIdentifierMst()
	{
		dataTable=new DataTable();
	}
	
	//Modified by : Ashmak Shah
    //Modified On : 22 July 2009
	//Added Operation Mode : MODE=1 for insert and MODE=2 for update
public void insertIntoSTFStudyIdentifierMst(DTOSTFStudyIdentifierMst dto,int MODE)  
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_stfstudyIdentifiermst(?,?,?,?,?,?,?,?,?,?)}");
		proc.setString(1, "");
		proc.setInt(2, dto.getTagId());
		proc.setString(3, dto.getWorkspaceId());
		proc.setInt(4, dto.getNodeId());
		proc.setString(5, dto.getTagName());
		proc.setString(6, dto.getAttrName());
		proc.setString(7, dto.getAttrValue());
		proc.setString(8, dto.getNodeContent());
		proc.setInt(9, dto.getModifyBy());
		proc.setInt(10,MODE);
		proc.execute();
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try{if(proc != null){proc.close();}}catch (Exception e) {e.printStackTrace();}
		try{if(con != null){con.close();}}catch (Exception e) {e.printStackTrace();}
	}
			
}
	
//Modified by : Ashmak Shah
//Modified On : 17 June 2009
//Reason : To Get maxTagSequenceId by both workspaceid and nodeid 
public int getMaxTagId(String vWorkSpaceId,int iNodeId) 
{
	int maxNodeCount = 0;
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"max(iTagSequenceId) as iTagSequenceId","stfstudyidentifiermst","vWorkspaceId='"+vWorkSpaceId+"' and iNodeId="+iNodeId ,"" );
		if(rs.next()){
			maxNodeCount = rs.getInt("iTagSequenceId");
		}else{
			maxNodeCount = 0;
		}
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return maxNodeCount;
}	
	
public Vector<DTOSTFStudyIdentifierMst> getSTFIdentifierByNodeId(String wsId,int iNodeId) 
{
	Vector<DTOSTFStudyIdentifierMst> stfvector = new Vector<DTOSTFStudyIdentifierMst>();		
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","stfstudyIdentifiermst", "vWorkspaceId ='"+wsId+"' and iNodeId="+iNodeId, "iTagSequenceId");
		while(rs.next())
		{
			DTOSTFStudyIdentifierMst dto = new DTOSTFStudyIdentifierMst();
			dto.setKeyId(rs.getString("vSTFStudyIdentifierId"));
			dto.setTagId(rs.getInt("iTagSequenceId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTagName(rs.getString("vTagName"));
			dto.setAttrName(rs.getString("vAttrName"));
			dto.setAttrValue(rs.getString("vAttrValue"));
			dto.setNodeContent(rs.getString("vNodeContent"));
			dto.setStatusIndi(rs.getString("cStatuIndi").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			stfvector.addElement(dto);
		}
	}catch(Exception e ){
		e.printStackTrace();
	}finally{
		try{if(rs != null){rs.close();}}catch (Exception e) {e.printStackTrace();}
		try{if(con != null){con.close();}}catch (Exception e) {e.printStackTrace();}
	}
	return stfvector;
}
	
public DTOSTFStudyIdentifierMst getSTFIdentifierOfNodeByTagNameAndAttrName(String wsId,int iNodeId,String tagName,String attrName) 
{
	DTOSTFStudyIdentifierMst dto = new DTOSTFStudyIdentifierMst();
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","stfstudyIdentifiermst", "vWorkspaceId ='"+wsId+"' and iNodeId="+iNodeId+" and vTagName='"+tagName+"' and vAttrName='"+attrName+"'", "");
		if(rs.next())
		{
			dto.setKeyId(rs.getString("vSTFStudyIdentifierId"));
			dto.setTagId(rs.getInt("iTagSequenceId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTagName(rs.getString("vTagName"));
			dto.setAttrName(rs.getString("vAttrName"));
			dto.setAttrValue(rs.getString("vAttrValue"));
			dto.setNodeContent(rs.getString("vNodeContent"));
			dto.setStatusIndi(rs.getString("cStatuIndi").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
		
		}
		rs.close();
		con.close();
		
	}catch(SQLException e ){
		e.printStackTrace();
	}
	
	return dto;
}


}//main class ended
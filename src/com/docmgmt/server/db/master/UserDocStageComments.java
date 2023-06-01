package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOUserDocStageComments;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserDocStageComments {
	
	DataTable datatable;
	
	public UserDocStageComments()
	{
		datatable=new DataTable();
	}
	public boolean insertUserDocStageComments(DTOUserDocStageComments dto,int mode)
	{
		Connection con = null;
		CallableStatement proc = null;
		boolean returnValue = false;	
		try 
		{
			con=ConnectionManager.ds.getConnection();
			proc=con.prepareCall("{ call Insert_UserDocStageComments(?,?,?,?,?,?,?,?,?,?,?) }");						
			proc.setString(1,dto.getWorkspaceId());
			proc.setInt(2,dto.getNodeId());
			proc.setInt(3,dto.getStageId());
			proc.setInt(4,dto.getTranNo());
			proc.setInt(5,dto.getUserCode());
			proc.setString(6,dto.getUserComments());
			proc.setString(7,dto.getUserRefDocPath());
			proc.setString(8,dto.getUserRefDocName());
			proc.setString(9,dto.getRemarks());
			proc.setInt(10,dto.getModifyBy());
			proc.setInt(11,mode);			
			returnValue=proc.execute();
		} 
		catch (Exception e){			
			e.printStackTrace();
		}finally{
	   		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
	   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
		}
		return returnValue;
	}
	
	public ArrayList<DTOUserDocStageComments> getStageWiseDocComments(String workspaceId,int nodeId,String stageId){
		
		ArrayList<DTOUserDocStageComments> userDocComments = new ArrayList<DTOUserDocStageComments>();
		Connection con = null;
		ResultSet rs = null;
		try
		{
			 con = ConnectionManager.ds.getConnection();
			 String fields = "vWorkspaceId,iNodeId,iStageId,iTranNo,iUserCode,vUserDefineVersionId," +
			 		"vUserName,vUserComments,vUserRefDocPath,vUserRefDocName,vRemarks,iModifyBy,dModifyOn,cStatusIndi,vFileName,vFileType,vFolderName,vBaseWorkFolder,vBasePublishFolder";
			 String where = " vWorkspaceId = '" + workspaceId + "'" +
			 		" AND iNodeId = " + nodeId +
			 		" AND iStageId in (" + stageId+")";
			 rs = datatable.getDataSet(con, fields, "View_UserDocStageComments", where, "iTranNo desc , dModifyOn desc");
			while(rs.next())
			{
				DTOUserDocStageComments dto = new DTOUserDocStageComments();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setUserComments(rs.getString("vUserComments"));
				dto.setUserRefDocPath(rs.getString("vUserRefDocPath"));
				dto.setUserRefDocName(rs.getString("vUserRefDocName"));
				dto.setRemarks(rs.getString("vRemarks"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				userDocComments.add(dto);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{if(rs != null){rs.close();}}catch(Exception e){e.printStackTrace();}
			try{if(con != null){con.close();}}catch(Exception e){e.printStackTrace();}
		}
		return userDocComments;
	}
		
}

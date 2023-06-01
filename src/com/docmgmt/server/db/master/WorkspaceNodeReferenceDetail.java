package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkspaceNodeReferenceDetail;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkspaceNodeReferenceDetail {
	DataTable dataTable;
	public WorkspaceNodeReferenceDetail() 
	{
		dataTable = new DataTable();
	}
	
	public void insertWorkspaceNodeReferenceDetail(ArrayList<DTOWorkspaceNodeReferenceDetail> workspaceNodeReferenceList,int DATAOPMODE)
	{
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			/*
				@nRefNo NUMERIC(18,0),
				@vWorkspaceId VARCHAR(50),
				@iNodeId INT,
				@vRefWorkspaceId VARCHAR(50),
				@iRefNodeId INT,
				@iModifyBy INT,
				@cStatusIndi CHAR(1),
				@DATAOPMODE INT
			 */
			proc = con.prepareCall("{ Call Insert_WorkspaceNodeReferenceDetail(?,?,?,?,?,?,?,?)}");
			if(workspaceNodeReferenceList != null)
			{
				for (DTOWorkspaceNodeReferenceDetail dtOWorkspaceNodeReferenceDetail  : workspaceNodeReferenceList) 
				{
					proc.setInt(1,dtOWorkspaceNodeReferenceDetail.getRefNo());
					proc.setString(2, dtOWorkspaceNodeReferenceDetail.getWorkspaceId());
					proc.setInt(3, dtOWorkspaceNodeReferenceDetail.getNodeId());
					proc.setString(4, dtOWorkspaceNodeReferenceDetail.getRefWorkspaceId());
					proc.setInt(5, dtOWorkspaceNodeReferenceDetail.getRefNodeId());
					proc.setInt(6, dtOWorkspaceNodeReferenceDetail.getModifyBy());					
					proc.setString(7,Character.toString(dtOWorkspaceNodeReferenceDetail.getStatusIndi()));
					proc.setInt(8, DATAOPMODE);
					proc.execute();
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try{if (proc != null)proc.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	}
	
	
	public ArrayList<DTOWorkspaceNodeReferenceDetail> getWorkspaceNodeRefereceDtl(String workspaceId,int nodeId,boolean isActive)
	{
		ArrayList<DTOWorkspaceNodeReferenceDetail> workspaceNodeReferenceDtlList = new ArrayList<DTOWorkspaceNodeReferenceDetail>();
		Connection con = null;
		ResultSet rs = null;
		String whereCond="";
		if(!workspaceId.trim().equals("")) 
		{
			whereCond+=" vWorkspaceId = '"+workspaceId+"'";
			
		}
		if(nodeId != 0) {
			if (!whereCond.trim().equals(""))
				whereCond += " and ";
			whereCond += " iNodeId = "+nodeId;
		}
		
		if (isActive) 
		{
			if (!whereCond.trim().equals(""))
				whereCond += " and ";
			whereCond += " cStatusIndi = 'N' ";
		}

		try{
			con = ConnectionManager.ds.getConnection();
			String select = "nRefNo,vWorkspaceId,iNodeId,vRefWorkspaceId,iRefNodeId,cStatusIndi," +
							"iModifyBy,dModifyOn,vWorkspaceDesc,vRemark,vNodeName,vNodeDisplayName," +
							"vFolderName,iParentNodeId,vRefWorkspaceDesc,vRefRemark,vRefNodeName," +
							"vRefNodeDisplayName,vRefFolderName,iRefParentNodeId,vModifyByUser," +
							"dCreatedOn,iCreatedBy,vCreatedByUser";
			
			rs = dataTable.getDataSet(con, select, " View_WorkspaceNodeReferenceDetail ",whereCond," nRefNo ");
			while(rs.next())
			{
				DTOWorkspaceNodeReferenceDetail dtoWorkspaceNodeReferenceDetail = new DTOWorkspaceNodeReferenceDetail();
				dtoWorkspaceNodeReferenceDetail.setRefNo(rs.getInt("nRefNo"));
				dtoWorkspaceNodeReferenceDetail.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWorkspaceNodeReferenceDetail.setNodeId(rs.getInt("iNodeId"));
				dtoWorkspaceNodeReferenceDetail.setRefWorkspaceId(rs.getString("vRefWorkspaceId"));
				dtoWorkspaceNodeReferenceDetail.setRefNodeId(rs.getInt("iRefNodeId"));
				dtoWorkspaceNodeReferenceDetail.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dtoWorkspaceNodeReferenceDetail.setModifyBy(rs.getInt("iModifyBy"));
				dtoWorkspaceNodeReferenceDetail.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWorkspaceNodeReferenceDetail.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
				dtoWorkspaceNodeReferenceDetail.setRemark(rs.getString("vRemark"));
				dtoWorkspaceNodeReferenceDetail.setNodeName(rs.getString("vNodeName"));
				dtoWorkspaceNodeReferenceDetail.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dtoWorkspaceNodeReferenceDetail.setFolderName(rs.getString("vFolderName"));
				dtoWorkspaceNodeReferenceDetail.setParentNodeId(rs.getInt("iParentNodeId"));
				dtoWorkspaceNodeReferenceDetail.setRefWorkspaceDesc(rs.getString("vRefWorkspaceDesc"));
				dtoWorkspaceNodeReferenceDetail.setRefRemark(rs.getString("vRefRemark"));
				dtoWorkspaceNodeReferenceDetail.setRefNodeName(rs.getString("vRefNodeName"));
				dtoWorkspaceNodeReferenceDetail.setRefNodeDisplayName(rs.getString("vRefNodeDisplayName"));
				dtoWorkspaceNodeReferenceDetail.setRefFolderName(rs.getString("vRefFolderName"));
				dtoWorkspaceNodeReferenceDetail.setRefParentNodeId(rs.getInt("iRefParentNodeId"));
				dtoWorkspaceNodeReferenceDetail.setModifyByUser(rs.getString("vModifyByUser"));
				dtoWorkspaceNodeReferenceDetail.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				dtoWorkspaceNodeReferenceDetail.setCreatedBy(rs.getInt("iCreatedBy"));
				dtoWorkspaceNodeReferenceDetail.setCreatedByUser(rs.getString("vCreatedByUser"));
				workspaceNodeReferenceDtlList.add(dtoWorkspaceNodeReferenceDetail);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return workspaceNodeReferenceDtlList;
	}
	

}

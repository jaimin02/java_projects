package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkspaceUserAuditTrailMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkspaceUserAuditTrailMst {
	DataTable datatable;
	
	public WorkspaceUserAuditTrailMst() 
	{
		datatable = new DataTable();
	}
	
	public void insertWorkspaceUserAuditTrailDetail(ArrayList<DTOWorkspaceUserAuditTrailMst> userAuditTrailList)
	{
		
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			/*
			 nAuditId NUMERIC(18,0),
			 vWorkspaceId VARCHAR(50),
			 iNodeId INT,
			 iUserCode INT,
			 iStageId INT,
			 cStatusIndi VARCHAR(1),
			 iModifyBy INT 
			 */
			proc = con.prepareCall("{ Call Insert_WorkspaceUserAuditTrailMst(?,?,?,?,?,?,?)}");
			if(userAuditTrailList != null)
			{
				for (DTOWorkspaceUserAuditTrailMst dtoWorkspaceUserAuditTrail : userAuditTrailList) {
					proc.setInt(1, dtoWorkspaceUserAuditTrail.getAuditId());
					proc.setString(2, dtoWorkspaceUserAuditTrail.getWorkspaceId());
					proc.setInt(3, dtoWorkspaceUserAuditTrail.getNodeId());
					proc.setInt(4, dtoWorkspaceUserAuditTrail.getUserCode());
					proc.setInt(5, dtoWorkspaceUserAuditTrail.getStageId());
					proc.setString(6, Character.toString(dtoWorkspaceUserAuditTrail.getStatusIndi()));
					proc.setInt(7, dtoWorkspaceUserAuditTrail.getModifyBy());
					proc.execute();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{if (proc != null)proc.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	
	}
	
	public ArrayList<DTOWorkspaceUserAuditTrailMst> getWorkspaceUserAuditTrail(ArrayList<DTOWorkspaceUserAuditTrailMst> wsUserAuditTrailList,boolean wsId,boolean nodeId,boolean stageId,boolean assignedBy,boolean assignedTo)
	{
		String wsIds="";
		String nodeIds="";
		String stageIds="";
		String assignedByUser="";
		String assignedToUser="";
		int wsIdCnt,ndIdCnt,stageIdCnt,assByUsrCnt,assToUsrCnt;
		wsIdCnt=ndIdCnt=stageIdCnt=assByUsrCnt=assToUsrCnt=0;
		
		if (wsId) {
			wsIds = " vWorkspaceId IN ( ";
			for (int i = 0; i < wsUserAuditTrailList.size(); i++)
			{
				wsIds += "'"+wsUserAuditTrailList.get(i).getWorkspaceId()+"',";
				wsIdCnt++;
			}
			wsIds = wsIds.substring(0, wsIds.length()-1)+")";
		}
		if (nodeId) { 
			nodeIds = " iNodeId IN ( ";
			for (int i = 0; i < wsUserAuditTrailList.size(); i++)
			{
				nodeIds += wsUserAuditTrailList.get(i).getNodeId()+",";
				ndIdCnt++;
			}
			nodeIds = nodeIds.substring(0, nodeIds.length()-1)+")";
		}
		if (stageId) {
			stageIds = " iStageId IN ( ";
			for (int i = 0; i < wsUserAuditTrailList.size(); i++)
			{
				stageIds += wsUserAuditTrailList.get(i).getStageId()+",";
				stageIdCnt++;
			}
			stageIds = stageIds.substring(0, stageIds.length()-1)+")";
		}
		if (assignedBy) {
			assignedByUser = " iModifyBy IN ( ";
			for (int i = 0; i < wsUserAuditTrailList.size(); i++)
			{
				assignedByUser += wsUserAuditTrailList.get(i).getModifyBy()+",";
				assByUsrCnt++;
			}
			assignedByUser = assignedByUser.substring(0, assignedByUser.length()-1)+")";
		} 
		if (assignedTo) {
			assignedToUser = " iUserCode IN ( ";
			for (int i = 0; i < wsUserAuditTrailList.size(); i++)
			{
				assignedToUser += wsUserAuditTrailList.get(i).getUserCode()+",";
				assToUsrCnt++;
			}
			assignedToUser = assignedToUser.substring(0, assignedToUser.length()-1)+")";
		}
		
		String whereCondition =wsIds;
		
		if (nodeId) {
			whereCondition += " AND "+nodeIds;
		}
		if (stageId) {
			whereCondition += " AND "+stageIds;
		}
		if (assignedBy) {
			whereCondition += " AND "+assignedByUser;
		}
		if (assignedTo) {
			whereCondition += " AND "+assignedToUser;
		}
		
		
		ArrayList<DTOWorkspaceUserAuditTrailMst> workspaceUserAuditTrailList = new ArrayList<DTOWorkspaceUserAuditTrailMst>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			String select = "nAuditId,vWorkspaceId,iNodeId,iUserCode,iStageId,cStatusIndi,dModifyOn,iModifyBy,VworkspaceDesc,"+
							"vRemark,vNodeName,vNodeDisplayName,vFolderName,vStageDesc,AssignedTo,AssignedBy,vUserGroupName";
			
			rs = datatable.getDataSet(con, select, "View_WorkspaceUserAuditTrailMst", whereCondition, " nAuditId ");
			while(rs.next()){				
				DTOWorkspaceUserAuditTrailMst dtoWsUserAuditTrailMst = new DTOWorkspaceUserAuditTrailMst();
				dtoWsUserAuditTrailMst.setAuditId(rs.getInt("nAuditId"));
				dtoWsUserAuditTrailMst.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsUserAuditTrailMst.setNodeId(rs.getInt("iNodeId"));
				dtoWsUserAuditTrailMst.setUserCode(rs.getInt("iUserCode"));
				dtoWsUserAuditTrailMst.setStageId(rs.getInt("iStageId"));
				dtoWsUserAuditTrailMst.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dtoWsUserAuditTrailMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsUserAuditTrailMst.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsUserAuditTrailMst.setWorkspaceDesc(rs.getString("VworkspaceDesc"));
				dtoWsUserAuditTrailMst.setRemark(rs.getString("vRemark"));
				dtoWsUserAuditTrailMst.setNodeName(rs.getString("vNodeName"));
				dtoWsUserAuditTrailMst.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dtoWsUserAuditTrailMst.setFolderName(rs.getString("vFolderName"));
				dtoWsUserAuditTrailMst.setStageDesc(rs.getString("vStageDesc"));
				dtoWsUserAuditTrailMst.setAssignedTo(rs.getString("AssignedTo"));
				dtoWsUserAuditTrailMst.setAssignedBy(rs.getString("AssignedBy"));
				dtoWsUserAuditTrailMst.setUserGroupName(rs.getString("vUserGroupName"));
				workspaceUserAuditTrailList.add(dtoWsUserAuditTrailMst);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return workspaceUserAuditTrailList;
	}
	
	public int getMaxAuditId()
	{
		Connection con = null;
		ResultSet rs = null;
		int maxAuditId=0;
		try{
			con = ConnectionManager.ds.getConnection();
			String select = "max(nAuditId) as nAudutId";
			
			rs = datatable.getDataSet(con, select, "View_WorkspaceUserAuditTrailMst","","");
			if(rs.next())
				maxAuditId = rs.getInt("nAudutId");
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return maxAuditId;
	}
}

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.docmgmt.dto.DTOSubmissionInfoForManualMode;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoForManualMode {

	ConnectionManager conMgr;
	static DataTable datatable;
	public SubmissionInfoForManualMode()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}

  
public void insertSubmissionInfoForManualMode(ArrayList<DTOSubmissionInfoForManualMode> subDtl)
{
	if (subDtl.size() > 0) {
		try
		{	    	
			Connection con=ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_SubmissionInfoForManualMode(?,?,?,?,?,?,?,?,?,?,?)}");
			
			for (Iterator<DTOSubmissionInfoForManualMode> iterator = subDtl.iterator(); iterator.hasNext();) {
				DTOSubmissionInfoForManualMode dto = iterator.next();			
				proc.setLong(1,dto.getSubInfoManualModeId());
				proc.setString(2,dto.getWorkspaceId());
				proc.setString(3,dto.getRegion());
				proc.setString(4,dto.getSubmissionId());
				proc.setInt(5,dto.getNodeId());
				proc.setInt(6,dto.getTranNo());
				proc.setString(7,dto.getRefID());
				proc.setString(8,dto.getOperation());
				proc.setString(9,dto.getRelSeqNo());
				proc.setInt(10,dto.getModifyBy());
				proc.setInt(11,1); 
				proc.execute();
			
			}
			proc.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
 
public static ArrayList<DTOSubmissionInfoForManualMode> getSubmissionInfoForManualMode(String workspaceId,String submissionId){
	ArrayList<DTOSubmissionInfoForManualMode> subDtl = new ArrayList<DTOSubmissionInfoForManualMode>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		boolean isANDRequired = false;
		String whr="";
		if(workspaceId != null && !workspaceId.equals("")){
			whr += " vWorkspaceId = '"+workspaceId+"' ";
			isANDRequired = true;
		}
		if(submissionId != null && !submissionId.equals("")){
			if(isANDRequired)
				whr += " AND ";
			whr += " vSubmissionId = '"+submissionId+"' ";
			isANDRequired = true;
		}
		ResultSet rs = datatable.getDataSet(con, "*", "View_SubmissionInfoForManualMode", whr, "");
		while(rs.next()){
			DTOSubmissionInfoForManualMode dtoManualMode = new DTOSubmissionInfoForManualMode();
			dtoManualMode.setSubInfoManualModeId(rs.getLong("iSubInfoManualModeId"));
			dtoManualMode.setWorkspaceId(rs.getString("vWorkspaceId"));
			dtoManualMode.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
			dtoManualMode.setRegion(rs.getString("vRegion"));
			dtoManualMode.setSubmissionId(rs.getString("vSubmissionId"));
			dtoManualMode.setNodeId(rs.getInt("iNodeId"));
			dtoManualMode.setTranNo(rs.getInt("iTranNo"));
			dtoManualMode.setRefID(rs.getString("vRefID"));
			dtoManualMode.setOperation(rs.getString("vOperation"));			
			dtoManualMode.setRelSeqNo(rs.getString("vRelSeqNo"));
			dtoManualMode.setModifyBy(rs.getInt("iModifyBy"));
			dtoManualMode.setModifyOn(rs.getTimestamp("dModifyOn"));
			dtoManualMode.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dtoManualMode.setCountryCode(rs.getString("vCountryCode"));
			dtoManualMode.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
			dtoManualMode.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
			dtoManualMode.setSubmissionPath(rs.getString("vSubmissionPath"));
			dtoManualMode.setSubmitedOn(rs.getTimestamp("dSubmitedOn"));
			dtoManualMode.setSubmitedBy(rs.getInt("iSubmitedBy"));
			dtoManualMode.setSubmissionType(rs.getString("vSubmissionType"));
			dtoManualMode.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
			dtoManualMode.setSubRelatedSeqNo(rs.getString("vRelatedSeqNo"));
			dtoManualMode.setConfirm(rs.getString("cConfirm").charAt(0));
			dtoManualMode.setSubmissionMode(rs.getString("vSubmissionMode"));
			dtoManualMode.setNodeNo(rs.getInt("iNodeNo"));
			dtoManualMode.setNodeName(rs.getString("vNodeName"));
			dtoManualMode.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dtoManualMode.setFolderName(rs.getString("vFolderName"));
			dtoManualMode.setParentNodeId(rs.getInt("iParentNodeId"));
			subDtl.add(dtoManualMode);
		}
		rs.close();
		con.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return subDtl;
}
public  ArrayList<DTOSubmissionInfoForManualMode> getLeafIdsForManualMode(String workspaceId,String submissionId){
	ArrayList<DTOSubmissionInfoForManualMode> subDtl = new ArrayList<DTOSubmissionInfoForManualMode>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
			
		ResultSet rs = datatable.getDataSet(con, "DISTINCT iNodeId", "View_SubmissionInfoForManualMode", "vWorkspaceId='"+workspaceId+"' and vSubmissionId='"+submissionId+"'", "");
		
		while(rs.next()){
			DTOSubmissionInfoForManualMode dtoManualMode = new DTOSubmissionInfoForManualMode();
			dtoManualMode.setNodeId(rs.getInt("iNodeId"));
			subDtl.add(dtoManualMode);

			System.out.println("NodeIds for leafIds->"+rs.getInt("iNodeId"));
		}
		rs.close();
		con.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return subDtl;
}
public DTOSubmissionInfoForManualMode getWorkspaceManualDetail(String workspaeId,String submissionId,int nodeId) {

	DTOSubmissionInfoForManualMode dtoManualMode = new DTOSubmissionInfoForManualMode();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = null;
		String whr="vWorkspaceId='"+workspaeId+"' and vSubmissionId='"+submissionId+"' and iNodeId="+nodeId;
		rs = datatable.getDataSet(con, "*", "View_SubmissionInfoForManualMode",
				whr, "");
		if (rs.next()) {
			dtoManualMode.setSubInfoManualModeId(rs.getLong("iSubInfoManualModeId"));
			dtoManualMode.setWorkspaceId(rs.getString("vWorkspaceId"));
			dtoManualMode.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
			dtoManualMode.setRegion(rs.getString("vRegion"));
			dtoManualMode.setSubmissionId(rs.getString("vSubmissionId"));
			dtoManualMode.setNodeId(rs.getInt("iNodeId"));
			dtoManualMode.setTranNo(rs.getInt("iTranNo"));
			dtoManualMode.setRefID(rs.getString("vRefID"));
			dtoManualMode.setOperation(rs.getString("vOperation"));			
			dtoManualMode.setRelSeqNo(rs.getString("vRelSeqNo"));
			dtoManualMode.setModifyBy(rs.getInt("iModifyBy"));
			dtoManualMode.setModifyOn(rs.getTimestamp("dModifyOn"));
			dtoManualMode.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dtoManualMode.setCountryCode(rs.getString("vCountryCode"));
			dtoManualMode.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
			dtoManualMode.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
			dtoManualMode.setSubmissionPath(rs.getString("vSubmissionPath"));
			dtoManualMode.setSubmitedOn(rs.getTimestamp("dSubmitedOn"));
			dtoManualMode.setSubmitedBy(rs.getInt("iSubmitedBy"));
			dtoManualMode.setSubmissionType(rs.getString("vSubmissionType"));
			dtoManualMode.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
			dtoManualMode.setSubRelatedSeqNo(rs.getString("vRelatedSeqNo"));
			dtoManualMode.setConfirm(rs.getString("cConfirm").charAt(0));
			dtoManualMode.setSubmissionMode(rs.getString("vSubmissionMode"));
			dtoManualMode.setNodeNo(rs.getInt("iNodeNo"));
			dtoManualMode.setNodeName(rs.getString("vNodeName"));
			dtoManualMode.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dtoManualMode.setFolderName(rs.getString("vFolderName"));
			dtoManualMode.setParentNodeId(rs.getInt("iParentNodeId"));
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}

	return dtoManualMode;
}
}// main class ended

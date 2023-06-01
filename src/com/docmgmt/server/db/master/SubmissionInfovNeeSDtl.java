package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfovNeeSDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfovNeeSDtl {
	
	
	DataTable datatable;
	public SubmissionInfovNeeSDtl()
	{
		 datatable=new DataTable();
	}

public String insertSubmissionInfovNeeSDtl(DTOSubmissionInfovNeeSDtl dto,int DATAOPMODE)
{
	String submissionInfovNeeSDtlId = "";
	try
	{		
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfovNeeSDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						
			proc.setString(1,dto.getSubmissionInfovNeeSDtlId());
			proc.setString(2,dto.getWorkspaceId());
			proc.setString(3,dto.getCountryCode());
			proc.setString(4,dto.getCurrentSeqNumber());
			proc.setString(5,dto.getLastPublishedVersion());
			proc.setString(6,dto.getSubmissionPath());
			proc.setInt(7,dto.getSubmitedBy());
			proc.setString(8,dto.getSubmissionType());
			proc.setTimestamp(9,dto.getDateOfSubmission());
			proc.setString(10,dto.getRelatedSeqNo());
			proc.setString(11,Character.toString(dto.getConfirm()));
			proc.setInt(12,dto.getModifyBy());
			proc.setString(13,dto.getLabelId());
			proc.setString(14,dto.getSubmissionMode());
			proc.setString(15,dto.getSubVariationMode());
			proc.setString(16,Character.toString(dto.getRMSSubmited()));
			proc.setString(17, dto.getTrackingNo());
			proc.setInt(18,DATAOPMODE);
			
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfovNeeSDtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfovNeeSDtlId;
}

public Vector<DTOSubmissionInfovNeeSDtl> getWorkspaceSubmissionInfovNeeSDtl(String workspaceId)
{
		
		Vector<DTOSubmissionInfovNeeSDtl> data = new Vector<DTOSubmissionInfovNeeSDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfovNeeSDtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfovNeeSDtl dto = new DTOSubmissionInfovNeeSDtl();
				dto.setSubmissionInfovNeeSDtlId(rs.getString("SubmissionInfovNeeSDtlId"));
				dto.setWorkspaceId(rs.getString("WorkspaceId"));
				dto.setCountryCode(rs.getString("CountryCode"));
				dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
				dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
				dto.setSubmissionPath(rs.getString("SubmissionPath"));
				dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
				dto.setSubmitedBy(rs.getInt("SubmitedBy"));
				dto.setSubmissionType(rs.getString("SubmissionType"));
				dto.setDateOfSubmission(rs.getTimestamp("DateOfSubmission"));
				dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
				dto.setConfirm(rs.getString("Confirm").charAt(0));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
				dto.setCountryName(rs.getString("CountryName"));
				
				dto.setLabelId(rs.getString("LabelId"));
				
				
				
				if(rs.getString("RMSSubmited") != null && !rs.getString("RMSSubmited").equals(""))
				dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));
				
				data.addElement(dto);
				dto = null;
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
}


public Vector<DTOSubmissionInfovNeeSDtl> getWorkspaceSubmissionInfovNeeSDtlForConfirmSeq(String workspaceId)
{
		
		Vector<DTOSubmissionInfovNeeSDtl> data = new Vector<DTOSubmissionInfovNeeSDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfovNeeSDtl" , "WorkspaceId='"+workspaceId+"' AND Confirm='Y'", "");
			while(rs.next())
			{
				DTOSubmissionInfovNeeSDtl dto = new DTOSubmissionInfovNeeSDtl();
				dto.setSubmissionInfovNeeSDtlId(rs.getString("SubmissionInfovNeeSDtlId"));
				dto.setWorkspaceId(rs.getString("WorkspaceId"));
				dto.setCountryCode(rs.getString("CountryCode"));
				dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
				dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
				dto.setSubmissionPath(rs.getString("SubmissionPath"));
				dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
				dto.setSubmitedBy(rs.getInt("SubmitedBy"));
				dto.setSubmissionType(rs.getString("SubmissionType"));
				dto.setDateOfSubmission(rs.getTimestamp("DateOfSubmission"));
				dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
				dto.setConfirm(rs.getString("Confirm").charAt(0));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
				dto.setCountryName(rs.getString("CountryName"));
				
				dto.setLabelId(rs.getString("LabelId"));
				
				
				
				if(rs.getString("RMSSubmited") != null && !rs.getString("RMSSubmited").equals(""))
				dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));
				
				data.addElement(dto);
				dto = null;
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
}


public DTOSubmissionInfovNeeSDtl getWorkspaceSubmissionInfovNeeSDtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfovNeeSDtl dto = new DTOSubmissionInfovNeeSDtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfovNeeSDtl" , "SubmissionInfovNeeSDtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfovNeeSDtlId(rs.getString("SubmissionInfovNeeSDtlId"));
				dto.setWorkspaceId(rs.getString("WorkspaceId"));
				dto.setCountryCode(rs.getString("CountryCode"));
				dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
				dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
				dto.setSubmissionPath(rs.getString("SubmissionPath"));
				dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
				dto.setSubmitedBy(rs.getInt("SubmitedBy"));
				dto.setSubmissionType(rs.getString("SubmissionType"));
				dto.setDateOfSubmission(rs.getTimestamp("DateOfSubmission"));
				dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
				dto.setConfirm(rs.getString("Confirm").charAt(0));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
				dto.setCountryName(rs.getString("CountryName"));
				
				dto.setLabelId(rs.getString("LabelId"));
				
				dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return dto;
}



}//main class ended

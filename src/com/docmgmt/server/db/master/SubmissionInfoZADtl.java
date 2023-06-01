package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoZADtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoZADtl {
	
	
	DataTable datatable;
	public SubmissionInfoZADtl()
	{
		 datatable=new DataTable();
	}

public String insertSubmissionInfoZADtl(DTOSubmissionInfoZADtl dto,int DATAOPMODE)
{
	String submissionInfoZADtlId = "";
	try
	{		
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoZADtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1,dto.getSubmissionInfoZADtlId());
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
			proc.setString(17, dto.getApplicationNo());
			proc.setString(18, dto.getEfficacy());
			proc.setString(19, dto.getEfficacyDescription());
			proc.setString(20, dto.getPropriateryName());
			
			proc.setInt(21,DATAOPMODE);
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfoZADtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfoZADtlId;
}

public Vector<DTOSubmissionInfoZADtl> getWorkspaceSubmissionInfoZADtl(String workspaceId)
{
		
		Vector<DTOSubmissionInfoZADtl> data = new Vector<DTOSubmissionInfoZADtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoZADtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfoZADtl dto = new DTOSubmissionInfoZADtl();
				dto.setSubmissionInfoZADtlId(rs.getString("SubmissionInfoZADtlId"));
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
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				dto.setSubVariationMode(rs.getString("SubVariationMode"));
				dto.setApplicationNo(rs.getString("TrackingNo"));
				
				dto.setEfficacy(rs.getString("Efficacy"));
				dto.setEfficacyDescription(rs.getString("EfficacyDescription"));
				dto.setPropriateryName(rs.getString("PropriateryName"));
				
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


public DTOSubmissionInfoZADtl getWorkspaceSubmissionInfoZADtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfoZADtl dto = new DTOSubmissionInfoZADtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoZADtl" , "SubmissionInfoZADtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfoZADtlId(rs.getString("SubmissionInfoZADtlId"));
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
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				dto.setSubVariationMode(rs.getString("SubVariationMode"));
				dto.setApplicationNo(rs.getString("TrackingNo"));
				dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));
				
				
				dto.setEfficacy(rs.getString("Efficacy"));
				dto.setEfficacyDescription(rs.getString("EfficacyDescription"));
				dto.setPropriateryName(rs.getString("PropriateryName"));
				
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

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoEUDtl {
	
	DataTable datatable;
	public SubmissionInfoEUDtl()
	{
		 datatable=new DataTable();
	}

public String insertSubmissionInfoEUDtl(DTOSubmissionInfoEUDtl dto,int DATAOPMODE)
{
	String submissionInfoEUDtlId = "";
	try
	{		
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoEUDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						
			proc.setString(1,dto.getSubmissionInfoEUDtlId());
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
			proc.setString(15,Character.toString(dto.getRMSSubmited()));
			proc.setString(16, dto.getApplicationNo());
			proc.setString(17,dto.getSubVariationMode());
			proc.setString(18,dto.getSubmissionUnitType());
			proc.setInt(19,DATAOPMODE);
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfoEUDtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfoEUDtlId;
}

public Vector<DTOSubmissionInfoEUDtl> getWorkspaceSubmissionInfoEUDtl(String workspaceId)
{
		
		Vector<DTOSubmissionInfoEUDtl> data = new Vector<DTOSubmissionInfoEUDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoEUDtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfoEUDtl dto = new DTOSubmissionInfoEUDtl();
				dto.setSubmissionInfoEUDtlId(rs.getString("SubmissionInfoEUDtlId"));
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
				dto.setApplicationNo(rs.getString("ApplicationNo"));	
				dto.setSubmissionUnitType(rs.getString("SubmissionUnitType"));
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


public DTOSubmissionInfoEUDtl getWorkspaceSubmissionInfoEUDtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfoEUDtl dto = new DTOSubmissionInfoEUDtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoEUDtl" , "SubmissionInfoEUDtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfoEUDtlId(rs.getString("SubmissionInfoEUDtlId"));
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
				dto.setApplicationNo(rs.getString("ApplicationNo"));
				dto.setSubmissionUnitType(rs.getString("SubmissionUnitType"));
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

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoEU20Dtl {
	
	
	DataTable datatable;
	public SubmissionInfoEU20Dtl()
	{
		 datatable=new DataTable();
	}

public String insertSubmissionInfoEU20Dtl(DTOSubmissionInfoEU20Dtl dto,int DATAOPMODE)
{
	String submissionInfoEU20DtlId = "";
	try
	{		
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoEU20Dtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						
			proc.setString(1,dto.getSubmissionInfoEU20DtlId());
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
			proc.setString(18,dto.getSubmissionUnitType());
			proc.setInt(19,DATAOPMODE);
			
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfoEU20DtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfoEU20DtlId;
}

public Vector<DTOSubmissionInfoEU20Dtl> getWorkspaceSubmissionInfoEU20Dtl(String workspaceId)
{
		
		Vector<DTOSubmissionInfoEU20Dtl> data = new Vector<DTOSubmissionInfoEU20Dtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoEU20Dtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfoEU20Dtl dto = new DTOSubmissionInfoEU20Dtl();
				dto.setSubmissionInfoEU20DtlId(rs.getString("SubmissionInfoEU20DtlId"));
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
				dto.setTrackingNo(rs.getString("TrackingNo"));
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


public DTOSubmissionInfoEU20Dtl getWorkspaceSubmissionInfoEU20DtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfoEU20Dtl dto = new DTOSubmissionInfoEU20Dtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoEU20Dtl" , "SubmissionInfoEU20DtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfoEU20DtlId(rs.getString("SubmissionInfoEU20DtlId"));
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
				dto.setTrackingNo(rs.getString("TrackingNo"));
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

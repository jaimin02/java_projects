package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoTHDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoTHDtl {
	

	DataTable datatable;
	public SubmissionInfoTHDtl()
	{
		 datatable=new DataTable();
	}
	
	public String insertSubmissionInfoTHDtl(DTOSubmissionInfoTHDtl dto,int DATAOPMODE)
	{
		String submissionInfoTHDtlId = "";
		try
		{		
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
			
				proc = con.prepareCall("{ Call Insert_SubmissionInfoTHDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setString(1,dto.getSubmissionInfoTHDtlId());
				proc.setString(2,dto.getWorkspaceId());
				proc.setString(3,dto.getCountryCode());
				proc.setString(4,dto.getCurrentSeqNumber());
				proc.setString(5,dto.getLastPublishedVersion());
				proc.setString(6,dto.getSubmissionPath());
				proc.setInt(7,dto.getSubmitedBy());
				proc.setTimestamp(8,dto.getDateOfSubmission());
				proc.setString(9,dto.getRelatedSeqNo());
				proc.setString(10,Character.toString(dto.getConfirm()));
				proc.setInt(11,dto.getModifyBy());
				proc.setString(12,dto.getLabelId());
				proc.setString(13, dto.geteSubmissionId());
				proc.setString(14, dto.getSequenceType());
				proc.setString(15, dto.getSequenceDescription());
				proc.setString(16, dto.getEmail());
				proc.setInt(17,DATAOPMODE);
				
				ResultSet rs =proc.executeQuery();
				
				if(rs.next()) {
					submissionInfoTHDtlId = rs.getString(1);
				}
				proc.close();
				
			con.close();
				
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		return submissionInfoTHDtlId;
	}

	public Vector<DTOSubmissionInfoTHDtl> getWorkspaceSubmissionInfoTHDtl(String workspaceId)
	{
			
			Vector<DTOSubmissionInfoTHDtl> data = new Vector<DTOSubmissionInfoTHDtl>();
			try
			{
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;
				
				rs = datatable.getDataSet(con,"*","View_SubmissionInfoTHDtl" , "WorkspaceId='"+workspaceId+"'", "");
				while(rs.next())
				{
					DTOSubmissionInfoTHDtl dto = new DTOSubmissionInfoTHDtl();
					dto.setSubmissionInfoTHDtlId(rs.getString("SubmissionInfoTHDtlId"));
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setCountryCode(rs.getString("CountryCode"));
					dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
					dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
					dto.setSubmissionPath(rs.getString("SubmissionPath"));
					dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
					dto.setSubmitedBy(rs.getInt("SubmitedBy"));
					dto.setDateOfSubmission(rs.getTimestamp("DateOfSubmission"));
					dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
					dto.setConfirm(rs.getString("Confirm").charAt(0));
					dto.setModifyOn(rs.getTimestamp("ModifyOn"));
					dto.setModifyBy(rs.getInt("ModifyBy"));
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
					dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
					dto.setCountryName(rs.getString("CountryName"));
					dto.setLabelId(rs.getString("LabelId"));
					dto.seteSubmissionId(rs.getString("eSubmissionId"));
					dto.setAgencyName(rs.getString("AgencyName"));
					dto.setSequenceType(rs.getString("SequenceType"));
					dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
					dto.setEmail(rs.getString("Email"));
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
	
	public DTOSubmissionInfoTHDtl getWorkspaceSubmissionInfoTHDtlBySubmissionId(String submissionId)
	{
			
		DTOSubmissionInfoTHDtl dto = new DTOSubmissionInfoTHDtl();
			try
			{
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;
				
				rs = datatable.getDataSet(con,"*","View_SubmissionInfoTHDtl" , "SubmissionInfoTHDtlId='"+submissionId+"'", "");
				if(rs.next())
				{
					dto.setSubmissionInfoTHDtlId(rs.getString("SubmissionInfoTHDtlId"));
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setCountryCode(rs.getString("CountryCode"));
					dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
					dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
					dto.setSubmissionPath(rs.getString("SubmissionPath"));
					dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
					dto.setSubmitedBy(rs.getInt("SubmitedBy"));
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
					
					dto.seteSubmissionId(rs.getString("eSubmissionId"));
					dto.setSequenceType(rs.getString("SequenceType"));
					dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
					dto.setEmail(rs.getString("Email"));
					
					
				}
				
				rs.close();
				con.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
			return dto;
	}




}

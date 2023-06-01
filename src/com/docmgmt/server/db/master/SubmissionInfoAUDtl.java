package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoAUDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoAUDtl {
	DataTable datatable;
	public SubmissionInfoAUDtl()
	{
		 datatable=new DataTable();
	}
	
	public String insertSubmissionInfoAUDtl(DTOSubmissionInfoAUDtl dto,int DATAOPMODE)
	{
		String submissionInfoAUDtlId = "";
		try
		{		
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
			
				proc = con.prepareCall("{ Call Insert_SubmissionInfoAUDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setString(1,dto.getSubmissionInfoAUDtlId());
				proc.setString(2,dto.getWorkspaceId());
				proc.setString(3,dto.getCountryCode());
				proc.setString(4,dto.getCurrentSeqNumber());
				proc.setString(5,dto.getLastPublishedVersion());
				proc.setString(6,dto.getSubmissionPath());
				proc.setInt(7,dto.getSubmitedBy());
				proc.setString(8,dto.getRelatedSeqNo());
				proc.setString(9,Character.toString(dto.getConfirm()));
				proc.setInt(10,dto.getModifyBy());
				proc.setString(11,dto.getLabelId());
				proc.setString(12,dto.getSubmissionMode());
				proc.setString(13, dto.geteSubmissionId());
				proc.setString(14, dto.getSequenceType());
				proc.setString(15, dto.getSequenceDescription());
				proc.setString(16, dto.getSequenceDescriptionValue());
				proc.setString(17, dto.getARTGNumber());
				
				
				proc.setInt(18,DATAOPMODE);
				
				ResultSet rs =proc.executeQuery();
				
				if(rs.next()) {
					submissionInfoAUDtlId = rs.getString(1);
				}
				proc.close();
				
			con.close();
				
		}catch(SQLException e){
			e.printStackTrace();
		}
			
		return submissionInfoAUDtlId;
	}

	public Vector<DTOSubmissionInfoAUDtl> getWorkspaceSubmissionInfoAUDtl(String workspaceId)
	{
			
			Vector<DTOSubmissionInfoAUDtl> data = new Vector<DTOSubmissionInfoAUDtl>();
			try
			{
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;
				
				rs = datatable.getDataSet(con,"*","View_SubmissionInfoAUDtl" , "WorkspaceId='"+workspaceId+"'", "");
				while(rs.next())
				{
					DTOSubmissionInfoAUDtl dto = new DTOSubmissionInfoAUDtl();
					dto.setSubmissionInfoAUDtlId(rs.getString("SubmissionInfoAUDtlId"));
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setCountryCode(rs.getString("CountryCode"));
					dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
					dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
					dto.setSubmissionPath(rs.getString("SubmissionPath"));
					dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
					dto.setSubmitedBy(rs.getInt("SubmitedBy"));
					dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
					dto.setConfirm(rs.getString("Confirm").charAt(0));
					dto.setModifyOn(rs.getTimestamp("ModifyOn"));
					dto.setModifyBy(rs.getInt("ModifyBy"));
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
					dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
					dto.setCountryName(rs.getString("CountryName"));
					dto.setLabelId(rs.getString("LabelId"));
					dto.setSubmissionMode(rs.getString("SubmissionMode"));
					dto.seteSubmissionId(rs.getString("eSubmissionId"));
					dto.setAgencyName(rs.getString("AgencyName"));
					dto.setSequenceType(rs.getString("SequenceType"));
					dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
					dto.setSequenceDescriptionValue(rs.getString("SequenceDesciprionValue"));
					dto.setARTGNumber(rs.getString("ARTGNumber"));
					
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
	
	public DTOSubmissionInfoAUDtl getWorkspaceSubmissionInfoAUDtlBySubmissionId(String submissionId)
	{
			
		DTOSubmissionInfoAUDtl dto = new DTOSubmissionInfoAUDtl();
			try
			{
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;
				
				rs = datatable.getDataSet(con,"*","View_SubmissionInfoAUDtl" , "SubmissionInfoAUDtlId='"+submissionId+"'", "");
				if(rs.next())
				{
					dto.setSubmissionInfoAUDtlId(rs.getString("SubmissionInfoAUDtlId"));
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setCountryCode(rs.getString("CountryCode"));
					dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
					dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
					dto.setSubmissionPath(rs.getString("SubmissionPath"));
					dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
					dto.setSubmitedBy(rs.getInt("SubmitedBy"));
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
					dto.seteSubmissionId(rs.getString("eSubmissionId"));
					dto.setSequenceType(rs.getString("SequenceType"));
					dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
					dto.setSequenceDescriptionValue(rs.getString("SequenceDesciprionValue"));
					dto.setARTGNumber(rs.getString("ARTGNumber"));
					
					
				}
				
				rs.close();
				con.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
			return dto;
	}

	/*public DTOSubmissionInfoAUDtl getWorkspaceSubmissionInfoAUDtlBySubmissionId(String submissionId)
	{
			
		DTOSubmissionInfoAUDtl dto = new DTOSubmissionInfoAUDtl();
			try
			{
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;
				
				rs = datatable.getDataSet(con,"*","View_SubmissionInfoAUDtl" , "SubmissionInfoAUDtlId='"+submissionId+"'", "");
				if(rs.next())
				{
					dto.setSubmissionInfoAUDtlId(rs.getString("SubmissionInfoAUDtlId"));
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setCountryCode(rs.getString("CountryCode"));
					dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
					dto.setLastPublishedVersion(rs.getString("LastPublishedVersion"));
					dto.setSubmissionPath(rs.getString("SubmissionPath"));
					dto.setSubmitedOn(rs.getTimestamp("SubmitedOn"));
					dto.setSubmitedBy(rs.getInt("SubmitedBy"));
					dto.setRelatedSeqNo(rs.getString("RelatedSeqNo"));
					dto.setConfirm(rs.getString("Confirm").charAt(0));
					dto.setModifyOn(rs.getTimestamp("ModifyOn"));
					dto.setModifyBy(rs.getInt("ModifyBy"));
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
					dto.setWorkspaceDesc(rs.getString("WorkspaceDesc"));
					dto.setCountryName(rs.getString("CountryName"));
					dto.setLabelId(rs.getString("LabelId"));
					dto.setSubmissionMode(rs.getString("SubmissionMode"));
					dto.seteSubmissionId(rs.getString("eSubmissionId"));
					dto.setAgencyName(rs.getString("AgencyName"));
					dto.setSequenceType(rs.getString("SequenceType"));
					dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
					dto.setSequenceDescriptionValue(rs.getString("SequenceDesciprionValue"));
					dto.setARTGNumber(rs.getString("ARTGNumber"));
					
				}
				
				rs.close();
				con.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
			return dto;
	}	*/
	

}

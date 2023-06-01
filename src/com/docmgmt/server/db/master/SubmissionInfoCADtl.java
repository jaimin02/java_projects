package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoCADtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoCADtl {
	
	static DataTable datatable=new DataTable();

public static String insertSubmissionInfoCADtl(DTOSubmissionInfoCADtl dto,int DATAOPMODE)
{
	String submissionInfoCADtlId = "";
	try
	{
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_SubmissionInfoCADtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1,dto.getSubmissionInfoCADtlId());
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
			proc.setString(12, dto.getDossierIdentifier());
			proc.setString(13,dto.getRegulatoryActivityType());
			proc.setString(14, dto.getSequenceDescriptionFlag());
			proc.setString(15, dto.getSequenceDescription());
			proc.setString(16, dto.getSequenceDescriptionValue());
			proc.setInt(17,DATAOPMODE);
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfoCADtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfoCADtlId;
}

public static Vector<DTOSubmissionInfoCADtl> getWorkspaceSubmissionInfoCADtl(String workspaceId)
{
		Vector<DTOSubmissionInfoCADtl> data = new Vector<DTOSubmissionInfoCADtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoCADtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfoCADtl dto = new DTOSubmissionInfoCADtl();
				dto.setSubmissionInfoCADtlId(rs.getString("SubmissionInfoCADtlId"));
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
				dto.setDossierIdentifier(rs.getString("DossierIdentifier"));
				dto.setRegulatoryActivityType(rs.getString("RegulatoryActivityType"));
				dto.setSequenceDescriptionFlag(rs.getString("SequenceDesciprionFlag"));
				dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
				dto.setSequenceDescriptionValue(rs.getString("SequenceDesciprionValue"));
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				
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

public static DTOSubmissionInfoCADtl getWorkspaceSubmissionInfoCADtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfoCADtl dto = new DTOSubmissionInfoCADtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoCADtl" , "SubmissionInfoCADtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfoCADtlId(rs.getString("SubmissionInfoCADtlId"));
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
				dto.setDossierIdentifier(rs.getString("DossierIdentifier"));
				dto.setRegulatoryActivityType(rs.getString("RegulatoryActivityType"));
				dto.setSequenceDescriptionFlag(rs.getString("SequenceDesciprionFlag"));
				dto.setSequenceDescription(rs.getString("SequenceDesciprion"));
				dto.setSequenceDescriptionValue(rs.getString("SequenceDesciprionValue"));
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				
				
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

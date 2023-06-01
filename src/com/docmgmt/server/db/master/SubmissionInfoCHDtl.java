package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoCHDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoCHDtl {
	
	
	DataTable datatable;
	public SubmissionInfoCHDtl()
	{
		 datatable=new DataTable();
	}

public String insertSubmissionInfoCHDtl(DTOSubmissionInfoCHDtl dto,int DATAOPMODE)
{
	String submissionInfoCHDtlId = "";
	try
	{		
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoCHDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						
			proc.setString(1,dto.getSubmissionInfoCHDtlId());
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
			proc.setString(15, dto.getApplicationNo());
			proc.setString(16, dto.getGanelicForm());
			proc.setString(17, dto.getParagraph13());
			proc.setInt(18,DATAOPMODE);
			
			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				submissionInfoCHDtlId = rs.getString(1);
			}
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
	return submissionInfoCHDtlId;
}

public Vector<DTOSubmissionInfoCHDtl> getWorkspaceSubmissionInfoCHDtl(String workspaceId)
{
		
		Vector<DTOSubmissionInfoCHDtl> data = new Vector<DTOSubmissionInfoCHDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoCHDtl" , "WorkspaceId='"+workspaceId+"'", "");
			while(rs.next())
			{
				DTOSubmissionInfoCHDtl dto = new DTOSubmissionInfoCHDtl();
				dto.setSubmissionInfoCHDtlId(rs.getString("SubmissionInfoCHDtlId"));
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
				dto.setApplicationNo(rs.getString("ApplicationNo"));
				dto.setGanelicForm(rs.getString("GanelicForm"));
				dto.setParagraph13(rs.getString("Paragraph13"));
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


public DTOSubmissionInfoCHDtl getWorkspaceSubmissionInfoCHDtlBySubmissionId(String submissionId)
{
		
	DTOSubmissionInfoCHDtl dto = new DTOSubmissionInfoCHDtl();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = datatable.getDataSet(con,"*","View_SubmissionInfoCHDtl" , "SubmissionInfoCHDtlId='"+submissionId+"'", "");
			if(rs.next())
			{
				dto.setSubmissionInfoCHDtlId(rs.getString("SubmissionInfoCHDtlId"));
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
				dto.setGanelicForm(rs.getString("GanelicForm"));
				dto.setApplicationNo(rs.getString("ApplicationNo"));
				dto.setParagraph13(rs.getString("Paragraph13"));
			
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

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoUS23Dtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoUS23Dtl {

	ConnectionManager conMgr;
	DataTable datatable;

	public SubmissionInfoUS23Dtl() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public String insertSubmissionInfoUS23Dtl(DTOSubmissionInfoUS23Dtl dto,
			int DATAOPMODE) {
		String submissionInfoUS23DtlId = "";
		try {
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();

			proc = con
					.prepareCall("{ Call Insert_SubmissionInfoUS23Dtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			proc.setString(1, dto.getSubmissionInfoUS23DtlId());
			proc.setString(2, dto.getWorkspaceId());
			proc.setString(3, dto.getCountryCode());
			proc.setString(4, dto.getCurrentSeqNumber());
			proc.setString(5, dto.getLastPublishedVersion());
			proc.setString(6, dto.getSubmissionPath());
			proc.setInt(7, dto.getSubmitedBy());
			proc.setString(8, dto.getSubmissionType());
			proc.setTimestamp(9, dto.getDateOfSubmission());
			proc.setString(10, dto.getRelatedSeqNo());
			proc.setString(11, Character.toString(dto.getConfirm()));
			proc.setInt(12, dto.getModifyBy());

			proc.setString(13, dto.getLabelId());
			proc.setString(14, dto.getSubmissionMode());
			proc.setString(15, dto.getApplicationNo());
			proc.setString(16, dto.getApplicantContactList());
			proc.setString(17, dto.getSubSubType());
			proc.setString(18, dto.getSuppEffectiveDateType());
			proc.setString(19, dto.getCrossReferenceNumber());
			proc.setString(20, dto.getCrossRefAppType());

			proc.setInt(21, DATAOPMODE);

			ResultSet rs = proc.executeQuery();

			if (rs.next()) {
				submissionInfoUS23DtlId = rs.getString(1);
			}
			proc.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return submissionInfoUS23DtlId;
	}

	public Vector getWorkspaceSubmissionInfoUS23Dtl(String workspaceId) {

		Vector data = new Vector();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoUS23Dtl",
					"WorkspaceId='" + workspaceId + "'", "SubmissionInfoUS23DtlId");
			while (rs.next()) {
				DTOSubmissionInfoUS23Dtl dto = new DTOSubmissionInfoUS23Dtl();
				dto.setSubmissionInfoUS23DtlId(rs
						.getString("SubmissionInfoUS23DtlId"));
				dto.setWorkspaceId(rs.getString("WorkspaceId"));
				dto.setCountryCode(rs.getString("CountryCode"));
				dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
				dto.setLastPublishedVersion(rs
						.getString("LastPublishedVersion"));
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
				dto.setApplicationNo(rs.getString("ApplicationNo"));
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				data.addElement(dto);
				dto = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public DTOSubmissionInfoUS23Dtl getWorkspaceSubmissionInfoUS23DtlBySubmissionId(
			String submissionId) {

		DTOSubmissionInfoUS23Dtl dto = new DTOSubmissionInfoUS23Dtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoUS23Dtl",
					"SubmissionInfoUS23DtlId='" + submissionId + "'", "");
			if (rs.next()) {
				dto.setSubmissionInfoUS23DtlId(rs
						.getString("SubmissionInfoUS23DtlId"));
				dto.setWorkspaceId(rs.getString("WorkspaceId"));
				dto.setCountryCode(rs.getString("CountryCode"));
				dto.setCurrentSeqNumber(rs.getString("CurrentSeqNumber"));
				dto.setLastPublishedVersion(rs
						.getString("LastPublishedVersion"));
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
				dto.setApplicationNo(rs.getString("ApplicationNo"));
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				dto.setApplicantContactList(rs
						.getString("ApplicantContactList"));
				dto.setSuppEffectiveDateType(rs
						.getString("SupplementEffectiveDateType"));
				dto.setCrossRefAppType(rs
						.getString("CrossRefAppType"));
				dto.setCrossReferenceNumber(rs
						.getString("CrossRefNumber"));
				
				dto.setSubSubType(rs.getString("SubmissionSubType"));

			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

}// main class ended

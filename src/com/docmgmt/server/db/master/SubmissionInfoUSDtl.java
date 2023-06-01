package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoUSDtl {

	ConnectionManager conMgr;
	DataTable datatable;

	public SubmissionInfoUSDtl() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public String insertSubmissionInfoUSDtl(DTOSubmissionInfoUSDtl dto,
			int DATAOPMODE) {
		String submissionInfoUSDtlId = "";
		try {
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();

			proc = con
					.prepareCall("{ Call Insert_SubmissionInfoUSDtl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			proc.setString(1, dto.getSubmissionInfoUSDtlId());
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
			proc.setInt(16, DATAOPMODE);

			ResultSet rs = proc.executeQuery();

			if (rs.next()) {
				submissionInfoUSDtlId = rs.getString(1);
			}
			proc.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return submissionInfoUSDtlId;
	}

	public Vector getWorkspaceSubmissionInfoUSDtl(String workspaceId) {

		Vector data = new Vector();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoUSDtl",
					"WorkspaceId='" + workspaceId + "'", "");
			while (rs.next()) {
				DTOSubmissionInfoUSDtl dto = new DTOSubmissionInfoUSDtl();
				dto.setSubmissionInfoUSDtlId(rs
						.getString("SubmissionInfoUSDtlId"));
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

	public DTOSubmissionInfoUSDtl getWorkspaceSubmissionInfoUSDtlBySubmissionId(
			String submissionId) {

		DTOSubmissionInfoUSDtl dto = new DTOSubmissionInfoUSDtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoUSDtl",
					"SubmissionInfoUSDtlId='" + submissionId + "'", "");
			if (rs.next()) {
				dto.setSubmissionInfoUSDtlId(rs
						.getString("SubmissionInfoUSDtlId"));
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

			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

}// main class ended

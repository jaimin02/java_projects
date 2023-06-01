package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionInfoGCCSubDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoGCCSubDtl {
	ConnectionManager conMgr;
	DataTable datatable;

	public SubmissionInfoGCCSubDtl() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public void insertSubmissionInfoGCCSubDtl(DTOSubmissionInfoGCCSubDtl dto,
			int Mode) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Insert_SubmissionInfoGCCSubDtl(?,?,?,?,?,?,?,?,?)}");
			//procedure created
			
			proc.setInt(1, dto.getSubmissionInfoGCCSubDtlId());
			proc.setInt(2, dto.getWorkspaceCMSId());
			proc.setString(3, dto.getSubmissionInfoEU14DtlId());
			proc.setTimestamp(4, dto.getDateOfSubmission());
			proc.setString(5, dto.getSubmissionDescription());
			proc.setInt(6, dto.getModifyBy());
			proc.setString(7, Character.toString(dto.getStatusIndi()));
			proc.setString(8, dto.getPublishCMSTrackingNo());
			proc.setInt(9, Mode);
			proc.executeUpdate();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<DTOSubmissionInfoGCCSubDtl> getWorkspaceCMSSubmissionInfoGCC(
			String submissionId) {

		ArrayList<DTOSubmissionInfoGCCSubDtl> lstCMSSubInfo = new ArrayList<DTOSubmissionInfoGCCSubDtl>();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String where = " SubmissionId = '" + submissionId + "' ";
			ResultSet rs = datatable.getDataSet(con, "*",
					"View_WorkspaceCMSSubmissionDtlForGCC", where,
					"CountryCodeName");
			// view created
			
			while (rs.next()) {
				DTOSubmissionInfoGCCSubDtl dto = new DTOSubmissionInfoGCCSubDtl();
				dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWaveNo(rs.getInt("iWaveNo"));
				dto.setCountryName(rs.getString("vCountryName"));
				dto.setCountryCodeName(rs.getString("CountryCodeName"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setSubmissionInfoGCCDtlId(rs.getString("SubmissionId"));
				dto.setSubmissionDescription(rs
						.getString("vSubmissionDescription"));
				dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setCMSTrackingNo(rs.getString("vCMSTrackingNo"));
				dto.setPublishCMSTrackingNo(rs
						.getString("vPublishCMSTrackingNo"));

				lstCMSSubInfo.add(dto);

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstCMSSubInfo;
	}

	public DTOSubmissionInfoGCCSubDtl getWorkspaceRMSSubmissionInfoGCC(
			String submissionId) {

		DTOSubmissionInfoGCCSubDtl dto = new DTOSubmissionInfoGCCSubDtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String where = " vSubmissionInfoGCCDtlId = '" + submissionId
					+ "' AND iWorkspaceCMSId = 0 ";
			ResultSet rs = datatable.getDataSet(con, "*",
					"submissionInfoGCCSubDtl", where, "");
			while (rs.next()) {

				dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));

				dto.setSubmissionInfoGCCDtlId(rs
						.getString("vSubmissionInfoGCCDtlId"));
				dto.setSubmissionDescription(rs
						.getString("vSubmissionDescription"));
				dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
				dto.setCMSTrackingNo(rs.getString("vPublishCMSTrackingNo"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
}// main class ended

package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionInfoEU14SubDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoEU14SubDtl {

	ConnectionManager conMgr;
	DataTable datatable;

	public SubmissionInfoEU14SubDtl() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public void insertSubmissionInfoEU14SubDtl(DTOSubmissionInfoEU14SubDtl dto,
			int Mode) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Insert_SubmissionInfoEU14SubDtl(?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1, dto.getSubmissionInfoEU14SubDtlId());
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

	public ArrayList<DTOSubmissionInfoEU14SubDtl> getWorkspaceCMSSubmissionInfoEU14(
			String submissionId) {

		ArrayList<DTOSubmissionInfoEU14SubDtl> lstCMSSubInfo = new ArrayList<DTOSubmissionInfoEU14SubDtl>();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String where = " SubmissionId = '" + submissionId + "' ";
			ResultSet rs = datatable.getDataSet(con, "*",
					"View_WorkspaceCMSSubmissionDtlForEU14", where,
					"CountryCodeName");
			while (rs.next()) {
				DTOSubmissionInfoEU14SubDtl dto = new DTOSubmissionInfoEU14SubDtl();
				dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWaveNo(rs.getInt("iWaveNo"));
				dto.setCountryName(rs.getString("vCountryName"));
				dto.setCountryCodeName(rs.getString("CountryCodeName"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setSubmissionInfoEU14DtlId(rs.getString("SubmissionId"));
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

	public DTOSubmissionInfoEU14SubDtl getWorkspaceRMSSubmissionInfoEU14(
			String submissionId) {

		DTOSubmissionInfoEU14SubDtl dto = new DTOSubmissionInfoEU14SubDtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String where = " vSubmissionInfoEU14DtlId = '" + submissionId
					+ "' AND iWorkspaceCMSId = 0 ";
			ResultSet rs = datatable.getDataSet(con, "*",
					"SubmissionInfoEU14SubDtl", where, "");
			while (rs.next()) {

				dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));

				dto.setSubmissionInfoEU14DtlId(rs
						.getString("vSubmissionInfoEU14DtlId"));
				dto.setSubmissionDescription(rs
						.getString("vSubmissionDescription"));
				dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
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

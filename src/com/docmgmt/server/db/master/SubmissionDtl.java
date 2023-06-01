package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14Dtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;


public class SubmissionDtl {

	DataTable datatable;

	public SubmissionDtl() {
		datatable = new DataTable();
	}

	public Vector<DTOSubmissionDtl> getSequenceTrackingResult(Timestamp fromdate, Timestamp todate) {

		Vector<DTOSubmissionDtl> subDtl = new Vector<DTOSubmissionDtl>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			String whr="";
			whr="dSubmitedOn between '"+fromdate+"' and '"+todate+"' ";
			rs = datatable.getDataSet(con, "*", "view_allworkspaceSubmissionDtl",
					whr, "vWorkspaceId");
			
			while(rs.next()){
				DTOSubmissionDtl dto=new DTOSubmissionDtl();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setCurrentSequenceNumber(rs.getString("vCurrentSeqNumber"));
				dto.setConfirm(rs.getString("cConfirm").trim().charAt(0));
				
				dto.setSubmitedOn(rs.getTimestamp("dSubmitedOn"));
				
				subDtl.addElement(dto);
				dto=null;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subDtl;
	}

	public Vector<DTOSubmissionInfoEU14Dtl> getWorkspaceSubmissionInfoEU14Dtl(
			String workspaceId) {

		Vector<DTOSubmissionInfoEU14Dtl> data = new Vector<DTOSubmissionInfoEU14Dtl>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoEU14Dtl",
					"WorkspaceId='" + workspaceId + "'", "");
			while (rs.next()) {
				DTOSubmissionInfoEU14Dtl dto = new DTOSubmissionInfoEU14Dtl();
				dto.setSubmissionInfoEU14DtlId(rs
						.getString("SubmissionInfoEU14DtlId"));
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
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				dto.setSubVariationMode(rs.getString("SubVariationMode"));
				dto.setTrackingNo(rs.getString("TrackingNo"));

				if (rs.getString("RMSSubmited") != null
						&& !rs.getString("RMSSubmited").equals(""))
					dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));

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

	public DTOSubmissionInfoEU14Dtl getWorkspaceSubmissionInfoEU14DtlBySubmissionId(
			String submissionId) {

		DTOSubmissionInfoEU14Dtl dto = new DTOSubmissionInfoEU14Dtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			rs = datatable.getDataSet(con, "*", "View_SubmissionInfoEU14Dtl",
					"SubmissionInfoEU14DtlId='" + submissionId + "'", "");
			if (rs.next()) {
				dto.setSubmissionInfoEU14DtlId(rs
						.getString("SubmissionInfoEU14DtlId"));
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
				dto.setCountryName(rs.getString("CountryName"));
				dto.setAgencyName(rs.getString("AgencyName"));
				dto.setLabelId(rs.getString("LabelId"));
				dto.setSubmissionMode(rs.getString("SubmissionMode"));
				dto.setSubVariationMode(rs.getString("SubVariationMode"));
				dto.setTrackingNo(rs.getString("TrackingNo"));
				dto.setRMSSubmited(rs.getString("RMSSubmited").charAt(0));
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}
	

}// main class ended

package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOApplicationTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class ApplicationTypeMst {

	DataTable datatable;

	public ApplicationTypeMst() {
		datatable = new DataTable();
	}

	public Vector<DTOApplicationTypeMst> getApplicationTypeDetail() {

		Vector<DTOApplicationTypeMst> applicationTypeVector = new Vector<DTOApplicationTypeMst>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "applicationTypeMst", "",
					"vApplicationTypeCode");
			while (rs.next()) {
				DTOApplicationTypeMst dto = new DTOApplicationTypeMst();
				dto
						.setApplicationTypeCode(rs
								.getString("vApplicationTypeCode"));
				dto.setApplicationTypeDisplay(rs
						.getString("vApplicationTypeDisplay"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				applicationTypeVector.addElement(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return applicationTypeVector;
	}

	public DTOApplicationTypeMst getApplicationTypeByCode(String code) {

		DTOApplicationTypeMst applicationType = new DTOApplicationTypeMst();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "applicationTypeMst", "vApplicationTypeCode='"+ code+"'",
					"vApplicationTypeCode");
			while (rs.next()) {
				
				applicationType
						.setApplicationTypeCode(rs
								.getString("vApplicationTypeCode"));
				applicationType.setApplicationTypeDisplay(rs
						.getString("vApplicationTypeDisplay"));
				applicationType.setRemark(rs.getString("vRemark"));
				applicationType.setModifyBy(rs.getInt("iModifyBy"));
				applicationType.setModifyOn(rs.getTimestamp("dModifyOn"));
				applicationType.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return applicationType;

	}

}

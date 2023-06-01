package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTORegulatoryActivityLeadMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class RegulatoryActivityLeadMst {
	DataTable datatable;

	public RegulatoryActivityLeadMst() {
		datatable = new DataTable();
	}

	public Vector<DTORegulatoryActivityLeadMst> getRegulatoryActivityLeadDetail() {

		Vector<DTORegulatoryActivityLeadMst> regulatoryActivityLeadVector = new Vector<DTORegulatoryActivityLeadMst>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "regulatoryActivityLeadMst", "",
					"vRegActLeadCode");
			while (rs.next()) {
				DTORegulatoryActivityLeadMst dto = new DTORegulatoryActivityLeadMst();
				dto.setRegActLeadCode(rs.getString("vRegActLeadCode"));
				dto.setRegActLeadDescription(rs.getString("vRegActLeadDescription"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				regulatoryActivityLeadVector.addElement(dto);
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
		return regulatoryActivityLeadVector;
	}

	public DTORegulatoryActivityLeadMst getRegulatoryActivityLeadByCode(String code) {

		DTORegulatoryActivityLeadMst regulatoryActivityLead = new DTORegulatoryActivityLeadMst();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "regulatoryActivityLeadMst", "vRegActLeadCode='"+ code+"'",
					"vRegActLeadCode");
			while (rs.next()) {
				
				regulatoryActivityLead.setRegActLeadCode(rs.getString("vRegActLeadCode"));
				regulatoryActivityLead.setRegActLeadDescription(rs.getString("vRegActLeadDescription"));
				regulatoryActivityLead.setModifyBy(rs.getInt("iModifyBy"));
				regulatoryActivityLead.setModifyOn(rs.getTimestamp("dModifyOn"));
				regulatoryActivityLead.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				
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
		return regulatoryActivityLead;

	}

}

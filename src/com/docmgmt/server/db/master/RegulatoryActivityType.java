package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTORegulatoryActivityType;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class RegulatoryActivityType {
	

	DataTable datatable;

	public RegulatoryActivityType() {
		datatable = new DataTable();
	}

	public Vector<DTORegulatoryActivityType> getRegulatoryActivityTypeDetail() {

		Vector<DTORegulatoryActivityType> regulatoryActivityLeadVector = new Vector<DTORegulatoryActivityType>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "regulatoryActivityType", "",
					"vRegActTypeCode");
			while (rs.next()) {
				DTORegulatoryActivityType dto = new DTORegulatoryActivityType();
				dto.setRegActTypeCode(rs.getString("vRegActTypeCode"));
				dto.setRegActTypeDescription(rs.getString("vRegActTypeDescription"));
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

	public DTORegulatoryActivityType getRegulatoryActivityTypeByCode(String code) {

		DTORegulatoryActivityType regulatoryActivityLead = new DTORegulatoryActivityType();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "regulatoryActivityType", "vRegActTypeCode='"+ code+"'",
					"vRegActLeadTypeCode");
			while (rs.next()) {
				
				regulatoryActivityLead.setRegActTypeCode(rs.getString("vRegActTypeCode"));
				regulatoryActivityLead.setRegActTypeDescription(rs.getString("vRegActTypeDescription"));
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

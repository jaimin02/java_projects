package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSequenceTypeMst_Thai;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SequenceTypeMst_Thai {
	

	DataTable datatable;

	public SequenceTypeMst_Thai() {
		datatable = new DataTable();
	}
	
	public Vector<DTOSequenceTypeMst_Thai> getSequenceTypeDetail_Thai() {

		Vector<DTOSequenceTypeMst_Thai> sequenceTypeVector = new Vector<DTOSequenceTypeMst_Thai>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequenceTypeMst_Thai", "",
					"vSeqTypeCode");
			while (rs.next()) {
				DTOSequenceTypeMst_Thai dto = new DTOSequenceTypeMst_Thai();
				dto.setSequenceTypeCode(rs.getString("vSeqTypeCode"));
				dto.setSequenceTypeDescription(rs.getString("vSequenceTypeDescription"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				sequenceTypeVector.addElement(dto);
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
		return sequenceTypeVector;
	}
	public DTOSequenceTypeMst_Thai getSequenceTypeByCode_Thai(String code) {

		DTOSequenceTypeMst_Thai sequenceType = new DTOSequenceTypeMst_Thai();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequenceTypeMst_Thai", "vSeqTypeCode='"+ code+"'",
					"vSeqTypeCode");
			while (rs.next()) {
				
				sequenceType.setSequenceTypeCode(rs.getString("vSeqTypeCode"));
				sequenceType.setSequenceTypeDescription(rs.getString("vSequenceTypeDescription"));
				sequenceType.setModifyBy(rs.getInt("iModifyBy"));
				sequenceType.setModifyOn(rs.getTimestamp("dModifyOn"));
				sequenceType.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				
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
		return sequenceType;

	}



}

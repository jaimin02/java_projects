package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSequenceDescriptionMst_CA;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SequenceDescriptionMst_CA {

	DataTable datatable;

	public SequenceDescriptionMst_CA() {
		datatable = new DataTable();
	}
	
	public Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptionDetail_CA() {

		Vector<DTOSequenceDescriptionMst_CA> sequenceDescriptionVector = new Vector<DTOSequenceDescriptionMst_CA>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequenceDescriptionMst_CA", "",
					"vSeqDescCode");
			while (rs.next()) {
				DTOSequenceDescriptionMst_CA dto = new DTOSequenceDescriptionMst_CA();
				dto.setSeqDescCode(rs.getString("vSeqDescCode"));
				dto.setSequenceDescription(rs.getString("vSequenceDescription"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				sequenceDescriptionVector.addElement(dto);
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
		return sequenceDescriptionVector;
	}
	
	public  Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptionByCode_CA(String code) {

		Vector<DTOSequenceDescriptionMst_CA> sequenceDescriptionVector = new Vector<DTOSequenceDescriptionMst_CA>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequenceDescriptionSubTypeMst_CA", "vSeqDescCode='"+ code+"'",
					"vSeqDescCode");
			while (rs.next()) {
				DTOSequenceDescriptionMst_CA dto = new DTOSequenceDescriptionMst_CA();
				dto.setSeqDescCode(rs.getString("vSeqDescCode"));
				//sequenceDescriptino.setSequenceDescription(rs.getString("vSequenceDescription"));
				dto.setLabelName(rs.getString("vLabelName"));
				dto.setFieldType(rs.getString("vFieldType"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));	
				sequenceDescriptionVector.addElement(dto);
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
		return sequenceDescriptionVector;

	}




}

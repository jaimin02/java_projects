package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSequenceDescriptionMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SequenceDescriptionMst {
	DataTable datatable;

	public SequenceDescriptionMst() {
		datatable = new DataTable();
	}
	
	public Vector<DTOSequenceDescriptionMst> getSequenceDescriptionDetail() {

		Vector<DTOSequenceDescriptionMst> sequenceDescriptionVector = new Vector<DTOSequenceDescriptionMst>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequencedescriptionmst", "",
					"vSeqDescCode");
			while (rs.next()) {
				DTOSequenceDescriptionMst dto = new DTOSequenceDescriptionMst();
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
	
	public  Vector<DTOSequenceDescriptionMst> getSequenceDescriptionByCode(String code) {

		Vector<DTOSequenceDescriptionMst> sequenceDescriptionVector = new Vector<DTOSequenceDescriptionMst>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "*", "sequenceDescriptionSubTypeMst", "vSeqDescCode='"+ code+"'",
					"vSeqDescCode");
			while (rs.next()) {
				DTOSequenceDescriptionMst dto = new DTOSequenceDescriptionMst();
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

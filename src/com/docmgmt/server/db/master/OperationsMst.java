package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOOperationMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class OperationsMst {

	ConnectionManager conMgr;
	DataTable datatable;

	public OperationsMst() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public Vector getOperationParentDetailByParentCode(
			String operationParentCode) {

		Vector operationvector = new Vector();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = datatable.getDataSet(con, "*", "OperationMst",
					"vParentOperationCode = '" + operationParentCode + "'",
					"vOperationName");
			while (rs.next()) {
				DTOOperationMst dto = new DTOOperationMst();
				dto.setOperationCode(rs.getString("vOperationCode"));
				dto.setOperationName(rs.getString("vOperationName"));
				operationvector.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return operationvector;
	}

	public Vector getOperationByUserTypeId(String userType) {
		Vector data = new Vector();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call proc_getOperationByUserTypeId(?)}");
			proc.setString(1, userType);
			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				DTOOperationMst dto = new DTOOperationMst();
				dto.setOperationName(rs.getString("operationName"));
				dto.setOperationPath(rs.getString("operationPath"));
				dto.setActiveFlag(rs.getString("activeFlag").charAt(0));
				dto.setModifyOn(rs.getTimestamp("modifyOn"));
				dto.setOperationCode(rs.getString("operationCode"));
				dto.setUsertype(rs.getString("userTypeCode"));
				data.addElement(dto);
				dto = null;
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector getAllOperationDetail() {
		Vector data = new Vector();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fieldNames = "vOperationCode,vParentOperationCode,vOperationName,vOperationPath,cStatusIndi";
			ResultSet rs = datatable.getDataSet(con, fieldNames,
					"OperationMst", "", "iSeqNo");
			while (rs.next()) {
				DTOOperationMst dto = new DTOOperationMst();
				dto.setOperationCode(rs.getString("vOperationCode"));
				dto
						.setParentOperationCode(rs
								.getString("vParentOperationCode"));
				dto.setOperationName(rs.getString("vOperationName"));
				dto.setOperationPath(rs.getString("vOperationPath"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
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

	public void InsertIntoOperationMst(DTOOperationMst dto, int Mode) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Insert_operationMst(?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getOperationCode());
			proc.setString(2, dto.getOperationName());
			proc.setString(3, dto.getOperationPath());
			proc.setString(4, dto.getParentOperationCode());
			proc.setInt(5, dto.getModifyBy());
			proc.setString(6, Character.toString(dto.getStatusIndi()));
			proc.setInt(7, Mode);
			proc.executeUpdate();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}// end of main class

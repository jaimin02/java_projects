package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.docmgmt.dto.DTOXmlWorkspaceDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class XmlWorkspaceMst {

	ConnectionManager conMgr;
	DataTable datatable;

	public XmlWorkspaceMst() {
		conMgr = new ConnectionManager(new Configuration());
		datatable = new DataTable();
	}

	public DTOXmlWorkspaceDtl getXmlWorkspaceDtl(String XMLWorkspaceName) {
		DTOXmlWorkspaceDtl dto = new DTOXmlWorkspaceDtl();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String whr = " vXmlWorkspaceName = '" + XMLWorkspaceName + "' ";

			ResultSet rs = datatable.getDataSet(con, "*", "XmlWorkspaceMst",
					whr, "");
			if (rs.next()) {
				dto.setXmlWorkspaceId(rs.getLong("iXmlWorkspaceId"));
				dto.setXmlWorkspaceName(rs.getString("vXmlWorkspaceName"));
				dto.setXmlFileName(rs.getString("vXmlFileName"));
				dto.setFilePath(rs.getString("vFilePath"));
				dto.setXmlHeader(rs.getString("vXmlHeader"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

}// main class ended

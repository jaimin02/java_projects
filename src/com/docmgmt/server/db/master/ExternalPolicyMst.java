package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.docmgmt.server.db.dbcp.ExternalConfiguration;
import com.docmgmt.server.db.dbcp.ExternalConnectionManager;

public class ExternalPolicyMst {

	DataTable datatable;

	public ExternalPolicyMst() {
		datatable = new DataTable();
	}

	public void getExternalPolicy() {
		try {
			
			ExternalConnectionManager conMgr=new ExternalConnectionManager(new ExternalConfiguration());
			Connection con = ExternalConnectionManager.ds.getConnection();
			ResultSet rs;

			rs = datatable.getDataSet(con, " * ", "locationmst", "", "");
			while (rs.next()) {
				System.out.println(rs.getString("vLocationName"));
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

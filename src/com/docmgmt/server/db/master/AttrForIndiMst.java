package com.docmgmt.server.db.master;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOAttrForIndiMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;


public class AttrForIndiMst {

	
	DataTable dataTable = new DataTable();

	public Vector<DTOAttrForIndiMst> getAttrForIndiDetails(){
		
		Vector<DTOAttrForIndiMst> data = new Vector<DTOAttrForIndiMst>();
		try
		{
			Connection con = ConnectionManager .ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "attrForIndiMst", "", "");
			while(rs.next())
			{
				DTOAttrForIndiMst dto= new DTOAttrForIndiMst();
				dto.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				dto.setAttrForIndiName(rs.getString("vAttrForIndiName"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setStatusIndi(rs.getString("cstatusIndi").charAt(0)) ;
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				data.addElement(dto);
				dto = null;
				
			}
			rs.close();
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
 
}
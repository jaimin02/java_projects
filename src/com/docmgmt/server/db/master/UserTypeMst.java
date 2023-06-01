package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class UserTypeMst {
	
	DataTable dataTable; 
	public UserTypeMst()
	{
		dataTable=new DataTable();
	}
	
public Vector<DTOUserTypeMst> getAllUserType() 
{
	Vector<DTOUserTypeMst> data=new Vector<DTOUserTypeMst>();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*", "usertypemst"," vUserTypeName <> 'SU' and cStatusIndi<>'D'","");
		while(rs.next())
		{
			DTOUserTypeMst utypedto=new DTOUserTypeMst();
			utypedto.setUserTypeCode(rs.getString("vUserTypeCode"));
			utypedto.setUserTypeName(rs.getString("vUserTypeName"));
			utypedto.setModifyBy(rs.getInt("iModifyBy"));
			utypedto.setModifyOn(rs.getTimestamp("dModifyOn"));
			utypedto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(utypedto);
		}
		rs.close();
		con.close();
		
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}

}//main class ended

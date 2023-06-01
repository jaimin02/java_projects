package com.docmgmt.server.db.master;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTODocTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class DocTypeMst 
{
	static DataTable dataTable=new DataTable();

public Vector<DTODocTypeMst> getDocTypeDetail()
{
	Vector<DTODocTypeMst> docTypeVector = new Vector<DTODocTypeMst>();
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"vDocTypeCode , vDocTypeName","doctypemst", "","");
		while(rs.next()) 
		{
			DTODocTypeMst dto = new DTODocTypeMst();
			dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			dto.setDocTypeName(rs.getString("vDocTypeName"));
			docTypeVector.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return docTypeVector;
	
}

public String getDocTypeName(String docTypeCode){
	String docTypeName = "";
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"vDocTypeName","eDocSignTypeMst","vDoctypeCode='"+docTypeCode+"'","");
		while(rs.next()) 
		{
			docTypeName = rs.getString("vDocTypeName");
		}
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return docTypeName;
	
}
    
}//main class ended

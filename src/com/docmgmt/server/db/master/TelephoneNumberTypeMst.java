package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOTelephoneNumberTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TelephoneNumberTypeMst {

	

	DataTable datatable;

	public TelephoneNumberTypeMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOTelephoneNumberTypeMst> getTelephoneNumberTypeDetail() {

	Vector<DTOTelephoneNumberTypeMst> telephoneNumberTypeVector = new Vector<DTOTelephoneNumberTypeMst>();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","telephoneNumberTypeMst" ,"","vTelephoneNumberTypeCode");
		while(rs.next())
		{
			DTOTelephoneNumberTypeMst dto = new DTOTelephoneNumberTypeMst();
			dto.setTelephoneNumberTypeCode(rs.getString("vTelephoneNumberTypeCode"));
			dto.setTelephoneNumberTypeDisplay(rs.getString("vTelephoneNumberTypeDisplay"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			telephoneNumberTypeVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return telephoneNumberTypeVector;
}
	
	
	
}

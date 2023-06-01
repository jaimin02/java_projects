package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSupplementEffectiveDateTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SupplementEffectiveDateTypeMst {


	DataTable datatable;

	public SupplementEffectiveDateTypeMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOSupplementEffectiveDateTypeMst> getSupplementEffectiveDateTypeDetail() {

	Vector<DTOSupplementEffectiveDateTypeMst> supplementEffectiveDateTypeVector = new Vector<DTOSupplementEffectiveDateTypeMst>();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","supplementEffectiveDateTypeMst" ,"","vSupplementEffectiveDateTypeCode");
		while(rs.next())
		{
			DTOSupplementEffectiveDateTypeMst dto = new DTOSupplementEffectiveDateTypeMst();
			dto.setSupplementEffectiveDateTypeCode(rs.getString("vSupplementEffectiveDateTypeCode"));
			dto.setSupplementEffectiveDateTypeDisplay(rs.getString("vSupplementEffectiveDateTypeDisplay"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			supplementEffectiveDateTypeVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return supplementEffectiveDateTypeVector;
}
	
	
	
	
}

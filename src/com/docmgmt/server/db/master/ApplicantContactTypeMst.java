package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOApplicantContactTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class ApplicantContactTypeMst {

	DataTable datatable;

	public ApplicantContactTypeMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOApplicantContactTypeMst> getApplicantContactTypeDetail() {

	Vector<DTOApplicantContactTypeMst> applicantContactTypeVector = new Vector<DTOApplicantContactTypeMst>();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","applicantContactTypeMst" ,"","vApplicantContactTypeCode");
		while(rs.next())
		{
			DTOApplicantContactTypeMst dto = new DTOApplicantContactTypeMst();
			dto.setApplicantContactTypeCode(rs.getString("vApplicantContactTypeCode"));
			dto.setApplicantContactTypeDisplay(rs.getString("vApplicantContactTypeDisplay"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			applicantContactTypeVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return applicantContactTypeVector;
}
	
}

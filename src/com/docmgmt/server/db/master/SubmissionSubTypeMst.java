package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionSubTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionSubTypeMst {


	DataTable datatable;

	public SubmissionSubTypeMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOSubmissionSubTypeMst> getSubmissionSubTypeDetail() {

	Vector<DTOSubmissionSubTypeMst> submissionSubTypeVector = new Vector<DTOSubmissionSubTypeMst>();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","submissionSubTypeMst" ,"","vSubmissionSubTypeCode");
		while(rs.next())
		{
			DTOSubmissionSubTypeMst dto = new DTOSubmissionSubTypeMst();
			dto.setSubmissionSubTypeCode(rs.getString("vSubmissionSubTypeCode"));
			dto.setSubmissionSubTypeDisplay(rs.getString("vSubmissionSubTypeDisplay"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			submissionSubTypeVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return submissionSubTypeVector;
}
	
	
	
	
	
	
}
